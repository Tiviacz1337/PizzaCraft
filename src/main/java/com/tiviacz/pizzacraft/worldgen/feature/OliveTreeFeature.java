package com.tiviacz.pizzacraft.worldgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.tiviacz.pizzacraft.blocks.OliveLeavesBlock;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class OliveTreeFeature extends TreeFeature
{
    public OliveTreeFeature(Codec<TreeConfiguration> codec)
    {
        super(codec);
    }

    public ConfiguredFeature<TreeConfiguration, ?> setConfiguration()
    {
        WeightedStateProvider weightedBlockStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85).add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15).build());
        //weightedBlockStateProvider.add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85);
        //weightedBlockStateProvider.add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15);

        return this.configured(
                new TreeConfiguration.TreeConfigurationBuilder(
                        new SimpleStateProvider(ModBlocks.OLIVE_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0),
                        weightedBlockStateProvider, new SimpleStateProvider(ModBlocks.OLIVE_SAPLING.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(0.002F))).build());
    }
}