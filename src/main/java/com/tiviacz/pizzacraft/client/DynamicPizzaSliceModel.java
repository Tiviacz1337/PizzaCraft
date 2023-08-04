package com.tiviacz.pizzacraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import com.tiviacz.pizzacraft.util.NBTUtils;
import com.tiviacz.pizzacraft.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class DynamicPizzaSliceModel implements IModelGeometry<DynamicPizzaSliceModel>
{
    private static final Logger LOGGER = LogManager.getLogger();

    // minimal Z offset to prevent depth-fighting
    private static final float NORTH_Z_COVER = 7.496f / 16f;
    private static final float SOUTH_Z_COVER = 8.504f / 16f;
    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;

    private static final Transformation FLUID_TRANSFORM = new Transformation(new Vector3f(), Quaternion.ONE, new Vector3f(1, 1, 1.002f), Quaternion.ONE);
    private static final Transformation COVER_TRANSFORM = new Transformation(new Vector3f(), Quaternion.ONE, new Vector3f(1, 1, 1.004f), Quaternion.ONE);

    @Nonnull
    private final ItemStack stack;

    public DynamicPizzaSliceModel(ItemStack stack)
    {
        this.stack = stack;
    }

    public DynamicPizzaSliceModel withStack(ItemStack stack)
    {
        return new DynamicPizzaSliceModel(stack);
    }

    @Override
    public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
    {
        Material particleLocation = owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : null;
        Material baseLocation = owner.isTexturePresent("base") ? owner.resolveTexture("base") : null;

        List<TagKey<Item>> tags = new ArrayList<>();
        LayerSelector selector = new LayerSelector(true);

        ItemStackHandler handler = NBTUtils.createHandlerFromStack(stack, 10);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            tags.add(null);
        }

        List<Integer> tintIndexes = initializeNullList(10);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            if(!handler.getStackInSlot(i).isEmpty())
            {
                ResourceLocation layerLocation = null;
                List<TagKey<Item>> itemTags = handler.getStackInSlot(i).getTags().toList();

                for(TagKey<Item> tag : itemTags)
                {
                    if(PizzaLayers.VALID_ITEM_TAGS.contains(tag))
                    {
                        layerLocation = PizzaLayers.getTagToItemLayer().get(tag);
                        tags.set(i, tag);
                        tintIndexes.set(i, -1);
                    }
                }

                if(layerLocation == null)
                {
                    tags.set(i, null);
                    tintIndexes.set(i, i);
                }
            }
        }

        List<ResourceLocation> layers = new ArrayList<>(tags.size());

        for(int i = 0; i < tags.size(); i++)
        {
            TagKey<Item> tagKey = tags.get(i);

            if(tagKey != null)
            {
                selector.processLayer(tagKey);
                layers.add(i, PizzaLayers.getTagToItemLayer().get(tagKey));
            }
            else
            {
                layers.add(i, null);
            }
        }

        for(int i = 0; i < layers.size(); i++)
        {
            if(layers.get(i) == null && !handler.getStackInSlot(i).isEmpty())
            {
                layers.set(i, selector.selectItemLayer());
            }
        }

        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;

        if (particleSprite == null) particleSprite = spriteGetter.apply(baseLocation);

        var rootTransform = owner.getCombinedTransform().getRotation();
        if (!rootTransform.isIdentity())
            modelState = new SimpleModelState(modelState.getRotation().compose(rootTransform));

        ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap =
                PerspectiveMapWrapper.getTransforms(new CompositeModelState(owner.getCombinedTransform(), modelState));

        Transformation transform = modelState.getRotation();
        ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new DynamicPizzaSliceModel.PizzaSliceOverrideHandler(overrides, bakery, owner, this), transformMap);

        if(baseLocation != null)
        {
            // build base (insidest)
            TextureAtlasSprite sprite = spriteGetter.apply(baseLocation);

            // Base texture
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
        }

        if(!layers.isEmpty())
        {
            int i = 0;

            for(int j = 0; j < layers.size(); j++)
            {
                if(layers.get(j) == null) continue;

                Material material = new Material(InventoryMenu.BLOCK_ATLAS, layers.get(j));

                TextureAtlasSprite sprite = spriteGetter.apply(material);
                i++;

                if(sprite != null)
                {
                    var quads = ItemTextureQuadConverter.convertTexture(modelState.getRotation().compose(getLayerTransformation(i)), sprite, sprite, NORTH_Z_COVER, Direction.NORTH, 0xFFFFFFFF, tintIndexes.get(j));
                    var quads1 = ItemTextureQuadConverter.convertTexture(modelState.getRotation().compose(getLayerTransformation(i)), sprite, sprite, SOUTH_Z_COVER, Direction.SOUTH, 0xFFFFFFFF, tintIndexes.get(j));

                    if(tintIndexes.get(j) != -1)
                    {
                        int color = RenderUtils.getDominantColor(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(handler.getStackInSlot(tintIndexes.get(j))).getParticleIcon(), false);

                        if(handler.getStackInSlot(tintIndexes.get(j)).getItem() instanceof PotionItem)
                        {
                            color = PotionUtils.getColor(handler.getStackInSlot(tintIndexes.get(j)));
                        }

                        quads = RenderUtils.colorQuads(quads, color);
                        quads1 = RenderUtils.colorQuads(quads1, color);
                    }

                    quads.addAll(quads1);
                    builder.addQuads(ItemLayerModel.getLayerRenderType(false), quads);
                }
            }
        }

        builder.setParticle(particleSprite);

        return builder.build();
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
    {
        Set<Material> texs = Sets.newHashSet();
        if(owner.isTexturePresent("particle")) texs.add(owner.resolveTexture("particle"));
        if(owner.isTexturePresent("base")) texs.add(owner.resolveTexture("base"));
        return texs;
    }

    public List initializeNullList(int size)
    {
        List list = new ArrayList();

        for(int i = 0; i < size; i++)
        {
            list.add(null);
        }
        return list;
    }

    public static Transformation getLayerTransformation(int i)
    {
        return new Transformation(new Vector3f(0, 0, 0.00001F + i * 0.00001F), Quaternion.ONE, new Vector3f(1, 1, 1F + 0.00001F * i), Quaternion.ONE);
    }

    public enum Loader implements IModelLoader<DynamicPizzaSliceModel>
    {
        INSTANCE;

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) { }

        @Override
        public DynamicPizzaSliceModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
        {
            return new DynamicPizzaSliceModel(ItemStack.EMPTY);
        }
    }

    private static final class PizzaSliceOverrideHandler extends ItemOverrides
    {
        private final Map<String, BakedModel> cache = Maps.newHashMap(); // contains all the baked models since they'll never change
        private final ItemOverrides nested;
        private final ModelBakery baker;
        private final IModelConfiguration owner;
        private final DynamicPizzaSliceModel parent;

        private PizzaSliceOverrideHandler(ItemOverrides nested, ModelBakery baker, IModelConfiguration owner, DynamicPizzaSliceModel parent)
        {
            this.nested = nested;
            this.baker = baker;
            this.owner = owner;
            this.parent = parent;
        }

        @Override
        public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int integer)
        {
            BakedModel overriden = nested.resolve(originalModel, stack, level, entity, integer);
            if(overriden != originalModel) return overriden;
            if(stack.getTag() != null)
            {
                String name = stack.getTag().getCompound("Inventory").toString();

                if(!cache.containsKey(name))
                {
                    DynamicPizzaSliceModel unbaked = this.parent.withStack(stack);
                    BakedModel bakedModel = unbaked.bake(owner, baker, Material::sprite, BlockModelRotation.X0_Y0, this, new ResourceLocation("pizzacraft:pizza_slice_override"));
                    cache.put(name, bakedModel);
                    return bakedModel;
                }
                return cache.get(name);
            }
            return originalModel;
        }
    }
}