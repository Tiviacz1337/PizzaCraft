package com.tiviacz.pizzacraft.init;

import com.google.common.collect.ImmutableList;
import com.tiviacz.pizzacraft.blocks.OliveLeavesBlock;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModFeatures
{
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> OLIVE = FeatureUtils.register("olive", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.OLIVE_LOG.get().defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85).add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15).build()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(0.002F))).build());

    public static final Holder<PlacedFeature> OLIVE_CHECKED = PlacementUtils.register("olive_checked", OLIVE,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.OLIVE_SAPLING.get()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> OLIVE_SPAWN = FeatureUtils.register("olive_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(OLIVE_CHECKED, 0.01F)), OLIVE_CHECKED));

    public static final Holder<PlacedFeature> OLIVE_PLACED = PlacementUtils.register("olive_placed", OLIVE_SPAWN,
            VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(5))); //PlacementUtils.countExtra(1, 0.1F, 2)));
}