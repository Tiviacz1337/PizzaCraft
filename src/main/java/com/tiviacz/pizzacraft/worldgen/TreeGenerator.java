package com.tiviacz.pizzacraft.worldgen;

import com.google.common.collect.ImmutableList;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blocks.OliveLeavesBlock;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID)
public class TreeGenerator
{
    public static ConfiguredFeature<?,?> CONFIGURED_OLIVE_TREE;

    public static void setup(final FMLCommonSetupEvent event)
    {
        WeightedStateProvider weightedBlockStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85).add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15).build());
        // WeightedBlockStateProvider weightedBlockStateProvider = new WeightedBlockStateProvider();
        //weightedBlockStateProvider.add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85);
        //weightedBlockStateProvider.add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15);


        event.enqueueWork(() -> {
            CONFIGURED_OLIVE_TREE = Feature.TREE.configured(
                    new TreeConfiguration.TreeConfigurationBuilder(
                            new SimpleStateProvider(ModBlocks.OLIVE_LOG.get().defaultBlockState()),
                            new StraightTrunkPlacer(4, 2, 0),
                            weightedBlockStateProvider, new SimpleStateProvider(ModBlocks.OLIVE_SAPLING.get().defaultBlockState()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)
                    ).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(0.002F))).build());

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(PizzaCraft.MODID, "olive_tree"), CONFIGURED_OLIVE_TREE);
        });
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomesLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if(event.getName() != null)
        {
            if(PizzaCraftConfig.COMMON.generateOliveTree.get())
            {
                if(event.getCategory() == Biome.BiomeCategory.SAVANNA)
                {
                    generation.getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> CONFIGURED_OLIVE_TREE.decorated(Features.Decorators.HEIGHTMAP_WITH_TREE_THRESHOLD_SQUARED).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.1F, 1)).squared()));
                }
            }
        }
    }
}
