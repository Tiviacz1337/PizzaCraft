package com.tiviacz.pizzacraft.worldgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.tiviacz.pizzacraft.blocks.OliveLeavesBlock;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class OliveTreeFeature extends TreeFeature
{
    public OliveTreeFeature(Codec<BaseTreeFeatureConfig> codec)
    {
        super(codec);
    }

    public ConfiguredFeature<BaseTreeFeatureConfig, ?> setConfiguration()
    {
        WeightedBlockStateProvider weightedBlockStateProvider = new WeightedBlockStateProvider();
        weightedBlockStateProvider.addWeightedBlockstate(ModBlocks.OLIVE_LEAVES.get().getDefaultState(), 85);
        weightedBlockStateProvider.addWeightedBlockstate(ModBlocks.FRUIT_OLIVE_LEAVES.get().getDefaultState().with(OliveLeavesBlock.AGE, 1), 15);

        return this.withConfiguration(
                new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(ModBlocks.OLIVE_LOG.get().getDefaultState()),
                        weightedBlockStateProvider,
                        new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                        new StraightTrunkPlacer(4, 2, 0),
                        new TwoLayerFeature(1, 0, 1)
                ).setIgnoreVines().setDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).build());
    }
}