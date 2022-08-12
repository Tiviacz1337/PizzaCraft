package com.tiviacz.pizzacraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import com.tiviacz.pizzacraft.util.Utils;
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

        List<Material> layersLocations = new ArrayList<>();

        ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            ItemStack stackInSlot = handler.getStackInSlot(i);

           // if(stackInSlot.getTags().anyMatch(PizzaLayers.VALID_ITEM_TAGS::contains))
           // {
           //     layersLocations.add(new Material(InventoryMenu.BLOCK_ATLAS, PizzaLayers.getTagToItemLayer().get(stackInSlot.getTags().findFirst().get())));
           // }

            List<TagKey<Item>> tags = stackInSlot.getTags().toList();
            for(TagKey<Item> tag : tags)
            {
                if(PizzaLayers.VALID_ITEM_TAGS.contains(tag))
                {
                    layersLocations.add(new Material(InventoryMenu.BLOCK_ATLAS, PizzaLayers.getTagToItemLayer().get(tag)));
                }
            }
         /*   for(ResourceLocation location : stackInSlot.getItem().getTags())
            {
                if(PizzaLayers.VALID_ITEM_TAGS.contains(location))
                {
                    layersLocations.add(new Material(InventoryMenu.BLOCK_ATLAS, PizzaLayers.getTagToItemLayer().get(location)));
                }
            } */
        }

        ModelState transformsFromModel = owner.getCombinedTransform();

        ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap =
                PerspectiveMapWrapper.getTransforms(new CompositeModelState(transformsFromModel, modelState));

        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;

        if (particleSprite == null) particleSprite = spriteGetter.apply(baseLocation);

        Transformation transform = modelState.getRotation();

        ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new DynamicPizzaSliceModel.PizzaSliceOverrideHandler(overrides, bakery, owner, this), transformMap);

        if(baseLocation != null)
        {
            // build base (insidest)
            TextureAtlasSprite sprite = spriteGetter.apply(baseLocation);
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
            //builder.addQuads(ItemLayerModel.getLayerRenderType(true), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_COVER, sprite, Direction.NORTH, 0xFFFFFFFF, -1));
            //builder.addQuads(ItemLayerModel.getLayerRenderType(true), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_COVER, sprite, Direction.SOUTH, 0xFFFFFFFF, -1));
            //builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
        }

        if(!layersLocations.isEmpty())
        {
            int i = 0;

            for(Material material : layersLocations)
            {
                TextureAtlasSprite sprite = spriteGetter.apply(material);
                i++;

                if(sprite != null)
                {
                    builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, sprite, sprite, NORTH_Z_COVER - i * 0.0001F, Direction.NORTH, 0xFFFFFFFF, -1));
                    builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, sprite, sprite, SOUTH_Z_COVER + i * 0.0001F, Direction.SOUTH, 0xFFFFFFFF, -1));
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
        private final ModelBakery bakery;
        private final IModelConfiguration owner;
        private final DynamicPizzaSliceModel parent;

        private PizzaSliceOverrideHandler(ItemOverrides nested, ModelBakery bakery, IModelConfiguration owner, DynamicPizzaSliceModel parent)
        {
            this.nested = nested;
            this.bakery = bakery;
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
                    BakedModel bakedModel = unbaked.bake(owner, bakery, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X0_Y0, this, new ResourceLocation("pizzacraft:pizza_slice_override"));
                    cache.put(name, bakedModel);
                    return bakedModel;
                }
                return cache.get(name);
            }
            return originalModel;
        }
    }
}