package com.tiviacz.pizzacraft.client;

import com.google.common.collect.ImmutableList;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PizzaBakedModel implements IBakedModel
{
    private final IBakedModel baseModel;
    private final FaceBakery faceBakery = new FaceBakery();

    public PizzaBakedModel(IBakedModel baseModel)
    {
        this.baseModel = baseModel;
    }

    public static ModelProperty<Optional<IItemHandler>> LAYER_PROVIDERS = new ModelProperty<>();
    public static ModelProperty<Optional<Integer>> INTEGER_PROPERTY = new ModelProperty<>();
    public static ModelProperty<Optional<Boolean>> IS_RAW = new ModelProperty<>();

    public static ModelDataMap getEmptyIModelData()
    {
        ModelDataMap.Builder builder = new ModelDataMap.Builder();
        builder.withInitial(LAYER_PROVIDERS, Optional.empty());
        builder.withInitial(INTEGER_PROPERTY, Optional.empty());
        builder.withInitial(IS_RAW, Optional.empty());
        ModelDataMap modelDataMap = builder.build();
        return modelDataMap;
    }

    @Override
    @Nonnull
    public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData)
    {
        return tileData;
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData)
    {
        if(side != null)
        {
            return baseModel.getQuads(state, side, rand);
        }
        return getBakedQuadsFromIModelData(state, side, rand, extraData);
    }

    private List<BakedQuad> getBakedQuadsFromIModelData(@Nullable BlockState state, Direction side, @Nonnull Random rand, @Nonnull IModelData data)
    {
        if(!data.hasProperty(LAYER_PROVIDERS) || !data.hasProperty(INTEGER_PROPERTY) || !data.hasProperty(IS_RAW))
        {
            return baseModel.getQuads(state, side, rand);
        }

        Optional<IItemHandler> layerProviders = data.getData(LAYER_PROVIDERS);
        Optional<Integer> integerProperty = data.getData(INTEGER_PROPERTY);
        Optional<Boolean> isRaw = data.getData(IS_RAW);

        if(!layerProviders.isPresent() || !integerProperty.isPresent() || !isRaw.isPresent())
        {
            return baseModel.getQuads(state, side, rand);
        }

        List<BakedQuad> layerQuads = getLayerQuads(layerProviders.get(), integerProperty.get(), isRaw.get());

        List<BakedQuad> allQuads = new LinkedList<>();
        allQuads.addAll(baseModel.getQuads(state, side, rand, data));
        allQuads.addAll(layerQuads);
        return allQuads;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand)
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

                    for(ResourceLocation location : inventory.getStackInSlot(i).getItem().getTags())
                    {
                        if(PizzaLayers.VALID_TAGS.contains(location))
                        {
                            layerLocation = isRaw ? PizzaLayers.getTagToRawLayer().get(location) : PizzaLayers.getTagToLayer().get(location);
                        }
                        //layerLocation = isRaw ? PizzaLayers.TAG_TO_RAW_LAYER.get(location) : PizzaLayers.getTagToLayer().get(location);
                    }

                    if(layerLocation == null)
                    {
                        layerLocation = MissingTextureSprite.getLocation();
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
        BlockPartFace blockPartFace = new BlockPartFace(face, -1, "",  blockFaceUV);

        AtlasTexture blocksStitchedTextures = ModelLoader.instance().getSpriteMap().getAtlasTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite layersTextures = blocksStitchedTextures.getSprite(layerLocation);

        final ResourceLocation DUMMY_RL = new ResourceLocation("dummy_name");
        BakedQuad bakedQuad = faceBakery.bakeQuad(from, to, blockPartFace, layersTextures, face, SimpleModelTransform.IDENTITY, null, true, DUMMY_RL);
        return bakedQuad;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return baseModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return baseModel.isGui3d();
    }

    @Override
    public boolean isSideLit()
    {
        return baseModel.isSideLit();
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return baseModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return baseModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return baseModel.getOverrides();
    }
}
