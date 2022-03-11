package com.tiviacz.pizzacraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
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
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation)
    {
        RenderMaterial particleLocation = owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : null;
        RenderMaterial baseLocation = owner.isTexturePresent("base") ? owner.resolveTexture("base") : null;

        List<RenderMaterial> layersLocations = new ArrayList<>();

        ItemStackHandler handler = Utils.createHandlerFromStack(stack, 9);

        for(int i = 0; i < handler.getSlots(); i++)
        {
            ItemStack stackInSlot = handler.getStackInSlot(i);

            for(ResourceLocation location : stackInSlot.getItem().getTags())
            {
                if(PizzaLayers.VALID_ITEM_TAGS.contains(location))
                {
                    layersLocations.add(new RenderMaterial(PlayerContainer.BLOCK_ATLAS, PizzaLayers.getTagToItemLayer().get(location)));
                }
            }
        }

        IModelTransform transformsFromModel = owner.getCombinedTransform();

        ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transformMap =
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform));

        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;

        if (particleSprite == null) particleSprite = spriteGetter.apply(baseLocation);

        TransformationMatrix transform = modelTransform.getRotation();

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

            for(RenderMaterial material : layersLocations)
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
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
    {
        Set<RenderMaterial> texs = Sets.newHashSet();

        if(owner.isTexturePresent("particle")) texs.add(owner.resolveTexture("particle"));
        if(owner.isTexturePresent("base")) texs.add(owner.resolveTexture("base"));

        return texs;
    }

    public enum Loader implements IModelLoader<DynamicPizzaSliceModel>
    {
        INSTANCE;

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) { }

        @Override
        public DynamicPizzaSliceModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
        {
            return new DynamicPizzaSliceModel(ItemStack.EMPTY);
        }
    }

    private static final class PizzaSliceOverrideHandler extends ItemOverrideList
    {
        private final Map<String, IBakedModel> cache = Maps.newHashMap(); // contains all the baked models since they'll never change
        private final ItemOverrideList nested;
        private final ModelBakery bakery;
        private final IModelConfiguration owner;
        private final DynamicPizzaSliceModel parent;

        private PizzaSliceOverrideHandler(ItemOverrideList nested, ModelBakery bakery, IModelConfiguration owner, DynamicPizzaSliceModel parent)
        {
            this.nested = nested;
            this.bakery = bakery;
            this.owner = owner;
            this.parent = parent;
        }

        @Override
        public IBakedModel resolve(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity)
        {
            IBakedModel overriden = nested.resolve(originalModel, stack, world, entity);
            if(overriden != originalModel) return overriden;
            if(stack.getTag() != null)
            {
                String name = stack.getTag().getCompound("Inventory").toString();

                if(!cache.containsKey(name))
                {
                    DynamicPizzaSliceModel unbaked = this.parent.withStack(stack);
                    IBakedModel bakedModel = unbaked.bake(owner, bakery, ModelLoader.defaultTextureGetter(), ModelRotation.X0_Y0, this, new ResourceLocation("pizzacraft:pizza_slice_override"));
                    cache.put(name, bakedModel);
                    return bakedModel;
                }
                return cache.get(name);
            }
            return originalModel;
        }
    }
}