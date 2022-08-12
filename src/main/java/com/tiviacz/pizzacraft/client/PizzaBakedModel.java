package com.tiviacz.pizzacraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PizzaBakedModel implements BakedModel
{
    private final BakedModel baseModel;
    private final FaceBakery faceBakery = new FaceBakery();

    public PizzaBakedModel(BakedModel baseModel)
    {
        this.baseModel = baseModel;
    }

    public static ModelProperty<Optional<IItemHandler>> LAYER_PROVIDERS = new ModelProperty<>();
    public static ModelProperty<Optional<Integer>> INTEGER_PROPERTY = new ModelProperty<>();
    public static ModelProperty<Optional<Boolean>> IS_RAW = new ModelProperty<>();

    public static ModelData getEmptyModelData()
    {
        ModelData.Builder builder = ModelData.builder();
        builder.with(LAYER_PROVIDERS, Optional.empty());
        builder.with(INTEGER_PROPERTY, Optional.empty());
        builder.with(IS_RAW, Optional.empty());
        ModelData modelData = builder.build();
        return modelData;
    }

    @Override
    @Nonnull
    public ModelData getModelData(@Nonnull BlockAndTintGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull ModelData blockEntityData)
    {
        return blockEntityData;
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, @Nullable RenderType renderType)
    {
        if(side != null)
        {
            return baseModel.getQuads(state, side, rand);
        }
        return getBakedQuadsFromIModelData(state, side, rand, extraData, renderType);
    }

    private List<BakedQuad> getBakedQuadsFromIModelData(@Nullable BlockState state, Direction side, @Nonnull RandomSource rand, @Nonnull ModelData data, @Nullable RenderType renderType)
    {
        if(!data.has(LAYER_PROVIDERS) || !data.has(INTEGER_PROPERTY) || !data.has(IS_RAW))
        {
            return baseModel.getQuads(state, side, rand);
        }

        Optional<IItemHandler> layerProviders = data.get(LAYER_PROVIDERS);
        Optional<Integer> integerProperty = data.get(INTEGER_PROPERTY);
        Optional<Boolean> isRaw = data.get(IS_RAW);

        if(!layerProviders.isPresent() || !integerProperty.isPresent() || !isRaw.isPresent())
        {
            return baseModel.getQuads(state, side, rand);
        }

        List<BakedQuad> layerQuads = getLayerQuads(layerProviders.get(), integerProperty.get(), isRaw.get());

        List<BakedQuad> allQuads = new LinkedList<>();
        allQuads.addAll(baseModel.getQuads(state, side, rand, data, renderType));
        allQuads.addAll(layerQuads);
        return allQuads;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand)
    {
        throw new AssertionError("IBakedModel::getQuads should never be called, only IForgeBakedModel::getQuads");
    }

    protected static final float[][] VECTORS = new float[][]
            {
                    {1.0F, 1.0F, 1.0F},
                    {3.0F, 1.0F, 1.0F},
                    {5.0F, 1.0F, 1.0F},
                    {7.0F, 1.0F, 1.0F},
                    {9.0F, 1.0F, 1.0F},
                    {11.0F, 1.0F, 1.0F},
                    {13.0F, 1.0F, 1.0F},
                    {15.0F, 1.0F, 15.0F}
            };

    protected static final float[][][] UV = new float[][][]
            {
                    {
                            {1.0F, 1.0F, 15.0F, 15.0F},
                            {3.0F, 1.0F, 15.0F, 15.0F},
                            {5.0F, 1.0F, 15.0F, 15.0F},
                            {7.0F, 1.0F, 15.0F, 15.0F},
                            {9.0F, 1.0F, 15.0F, 15.0F},
                            {11.0F, 1.0F, 15.0F, 15.0F},
                            {13.0F, 1.0F, 15.0F, 15.0F}
                    },
                    {
                            {1.0F, 1.0F, 15.0F, 15.0F},
                            {1.0F, 1.0F, 15.0F, 13.0F},
                            {1.0F, 1.0F, 15.0F, 11.0F},
                            {1.0F, 1.0F, 15.0F, 9.0F},
                            {1.0F, 1.0F, 15.0F, 7.0F},
                            {1.0F, 1.0F, 15.0F, 5.0F},
                            {1.0F, 1.0F, 15.0F, 3.0F}
                    },
                    {
                            {1.0F, 1.0F, 15.0F, 15.0F},
                            {1.0F, 1.0F, 13.0F, 15.0F},
                            {1.0F, 1.0F, 11.0F, 15.0F},
                            {1.0F, 1.0F, 9.0F, 15.0F},
                            {1.0F, 1.0F, 7.0F, 15.0F},
                            {1.0F, 1.0F, 5.0F, 15.0F},
                            {1.0F, 1.0F, 3.0F, 15.0F}
                    },
                    {
                            {1.0F, 1.0F, 15.0F, 15.0F},
                            {1.0F, 3.0F, 15.0F, 15.0F},
                            {1.0F, 5.0F, 15.0F, 15.0F},
                            {1.0F, 7.0F, 15.0F, 15.0F},
                            {1.0F, 9.0F, 15.0F, 15.0F},
                            {1.0F, 11.0F, 15.0F, 15.0F},
                            {1.0F, 13.0F, 15.0F, 15.0F}
                    }
            };

    private List<BakedQuad> getLayerQuads(IItemHandler inventory, int integerProperty, boolean isRaw)
    {
        ImmutableList.Builder<BakedQuad> builder = new ImmutableList.Builder<>();
        int rotation = 0;

        for(int i = 0; i < inventory.getSlots(); i++)
        {
            if(!inventory.getStackInSlot(i).isEmpty())
            {
                for(int j = 0; j < i; j++)
                {
                    if(inventory.getStackInSlot(j).getItem() == inventory.getStackInSlot(i).getItem())
                    {
                        rotation += 90;
                    }
                }
                //rotation += 90;
                for(Direction dir : Direction.values())
                {
                    ResourceLocation layerLocation = null; // = new ResourceLocation("");

                   // if(inventory.getStackInSlot(i).getTags().anyMatch(t -> PizzaLayers.VALID_TAGS.contains(t.location())))
                    //{
                   //     layerLocation = isRaw ? PizzaLayers.getTagToRawLayer().get(inventory.getStackInSlot(i).getTags().findFirst().get().location()) : PizzaLayers.getTagToLayer().get(inventory.getStackInSlot(i).getTags().findFirst().get().location());
                   // }

                    List<TagKey<Item>> tags = inventory.getStackInSlot(i).getTags().toList();
                    for(TagKey<Item> tag : tags)
                    {
                        if(PizzaLayers.VALID_TAGS.contains(tag))
                        {
                            layerLocation = isRaw ? PizzaLayers.getTagToRawLayer().get(tag) : PizzaLayers.getTagToLayer().get(tag);
                        }
                    }
                    //if(inventory.getStackInSlot(i).getTags().anyMatch(PizzaLayers.VALID_TAGS::contains))
                    //{
                    //    layerLocation = isRaw ? PizzaLayers.getTagToRawLayer().get(inventory.getStackInSlot(i).getTags().findFirst().get()) : PizzaLayers.getTagToLayer().get(inventory.getStackInSlot(i).getTags().findFirst().get());
                    //}
                   /* for(ResourceLocation location : inventory.getStackInSlot(i).getItem().getTags())
                    {
                        if(PizzaLayers.VALID_TAGS.contains(location))
                        {
                            layerLocation = isRaw ? PizzaLayers.getTagToRawLayer().get(location) : PizzaLayers.getTagToLayer().get(location);
                        }
                        //layerLocation = isRaw ? PizzaLayers.TAG_TO_RAW_LAYER.get(location) : PizzaLayers.getTagToLayer().get(location);
                    } */

                    if(layerLocation == null)
                    {
                        layerLocation = MissingTextureAtlasSprite.getLocation();
                    }

                    //layerLocation = isRaw ? PizzaLayers.getItemToRawLayerMap().get(inventory.getStackInSlot(i).getItem()) : PizzaLayers.getTagToLayer().get(inventory.getStackInSlot(i).getItem().getTags())//PizzaLayers.getItemToLayerMap().get(inventory.getStackInSlot(i).getItem());
                    builder.add(getQuadForLayer(layerLocation, dir, rotation, UV[(rotation / 90) % 4][integerProperty], VECTORS[integerProperty][0], VECTORS[integerProperty][1], VECTORS[integerProperty][2], VECTORS[7][0], VECTORS[7][1], VECTORS[7][2]));
                }
            }
        }
        return builder.build();
    }

    private BakedQuad getQuadForLayer(ResourceLocation layerLocation, Direction face, int rotation, float[] uvArray, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        Vector3f from = new Vector3f((float)minX, (float)minY, (float)minZ);
        Vector3f to = new Vector3f((float)maxX, (float)maxY, (float)maxZ);

        BlockFaceUV blockFaceUV = new BlockFaceUV(uvArray, rotation);
        BlockElementFace blockElementFace = new BlockElementFace(face, -1, "",  blockFaceUV);

        TextureAtlasSprite layersTextures = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(layerLocation);

        final ResourceLocation DUMMY_RL = new ResourceLocation("dummy_name");
        BakedQuad bakedQuad = faceBakery.bakeQuad(from, to, blockElementFace, layersTextures, face, new SimpleModelState(Transformation.identity()), null, true, DUMMY_RL);
        return bakedQuad;
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return baseModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return baseModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight()
    {
        return baseModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return baseModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon()
    {
        return baseModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides()
    {
        return baseModel.getOverrides();
    }
}
