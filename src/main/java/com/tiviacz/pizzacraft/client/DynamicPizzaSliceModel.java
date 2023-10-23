package com.tiviacz.pizzacraft.client;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.math.Transformation;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import com.tiviacz.pizzacraft.util.NBTUtils;
import com.tiviacz.pizzacraft.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.geometry.*;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DynamicPizzaSliceModel implements IUnbakedGeometry<DynamicPizzaSliceModel>
{
    private static final Logger LOGGER = LogManager.getLogger();

    // minimal Z offset to prevent depth-fighting
    private static final float NORTH_Z_COVER = 7.496f / 16f;
    private static final float SOUTH_Z_COVER = 8.504f / 16f;
    private static final float NORTH_Z_FLUID = 7.498f / 16f;
    private static final float SOUTH_Z_FLUID = 8.502f / 16f;

    private static final Transformation FLUID_TRANSFORM = new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1, 1, 1.002f), new Quaternionf());
    private static final Transformation COVER_TRANSFORM = new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1, 1, 1.004f), new Quaternionf());

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
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
    {
        Material particleLocation = context.hasMaterial("particle") ? context.getMaterial("particle") : null;
        Material baseLocation = context.hasMaterial("base") ? context.getMaterial("base") : null;

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

        var rootTransform = context.getRootTransform();
        if (!rootTransform.isIdentity())
            modelState = new SimpleModelState(modelState.getRotation().compose(rootTransform), modelState.isUvLocked());

        var itemContext = StandaloneGeometryBakingContext.builder(context).withGui3d(false).withUseBlockLight(false).build(modelLocation);
        CompositeModel.Baked.Builder builder = CompositeModel.Baked.builder(itemContext, particleSprite, new DynamicPizzaSliceModel.PizzaSliceOverrideHandler(overrides, baker, itemContext, this), context.getTransforms());

        var normalRenderTypes = getLayerRenderTypes();

        if(baseLocation != null)
        {
            // build base (insidest)
            TextureAtlasSprite sprite = spriteGetter.apply(baseLocation);

            // Base texture
            var unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, sprite.contents());
            var quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> sprite, modelState, modelLocation);
            builder.addQuads(normalRenderTypes, quads);
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
                    var transformedState = new SimpleModelState(modelState.getRotation().compose(getLayerTransformation(i)), modelState.isUvLocked());
                    var unbaked = UnbakedGeometryHelper.createUnbakedItemMaskElements(2, sprite.contents()); // Use cover as mask
                    var quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> sprite, transformedState, modelLocation);

                    ColoredQuadTransformer colorizer = new ColoredQuadTransformer();

                    if(tintIndexes.get(j) != -1)
                    {
                        if(handler.getStackInSlot(tintIndexes.get(j)).getItem() == ModItems.CHEESE.get()) continue;

                        int color = RenderUtils.getDominantColor(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(handler.getStackInSlot(tintIndexes.get(j))).getParticleIcon(), false);

                        if(handler.getStackInSlot(tintIndexes.get(j)).getItem() instanceof PotionItem)
                        {
                            color = PotionUtils.getColor(handler.getStackInSlot(tintIndexes.get(j)));
                        }

                        colorizer.color(quads, color);
                    }

                    builder.addQuads(getLayerRenderTypes(), quads);
                }
            }
        }

        builder.setParticle(particleSprite);

        return builder.build();
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
        return new Transformation(new Vector3f(0, 0, 0.00001F + i * 0.00001F), new Quaternionf(), new Vector3f(1, 1, 1F + 0.00001F * i), new Quaternionf());
    }

    public static RenderTypeGroup getLayerRenderTypes()
    {
        return new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
    }

    public enum Loader implements IGeometryLoader<DynamicPizzaSliceModel>
    {
        INSTANCE;

        @Override
        public DynamicPizzaSliceModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext)
        {
            return new DynamicPizzaSliceModel(ItemStack.EMPTY);
        }
    }

    private static final class PizzaSliceOverrideHandler extends ItemOverrides
    {
        private final Map<String, BakedModel> cache = Maps.newHashMap(); // contains all the baked models since they'll never change
        private final ItemOverrides nested;
        private final ModelBaker baker;
        private final IGeometryBakingContext owner;
        private final DynamicPizzaSliceModel parent;

        private PizzaSliceOverrideHandler(ItemOverrides nested, ModelBaker baker, IGeometryBakingContext owner, DynamicPizzaSliceModel parent)
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