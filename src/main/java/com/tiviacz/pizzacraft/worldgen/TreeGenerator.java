package com.tiviacz.pizzacraft.worldgen;

import com.google.common.collect.ImmutableList;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blocks.OliveLeavesBlock;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID)
public class TreeGenerator
{
    public static ConfiguredFeature<?,?> CONFIGURED_OLIVE_TREE;

    public static void setup(final FMLCommonSetupEvent event)
    {
        WeightedBlockStateProvider weightedBlockStateProvider = new WeightedBlockStateProvider();
        weightedBlockStateProvider.addWeightedBlockstate(ModBlocks.OLIVE_LEAVES.get().getDefaultState(), 85);
        weightedBlockStateProvider.addWeightedBlockstate(ModBlocks.FRUIT_OLIVE_LEAVES.get().getDefaultState().with(OliveLeavesBlock.AGE, 1), 15);


        event.enqueueWork(() -> {
            CONFIGURED_OLIVE_TREE = Feature.TREE.withConfiguration(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(ModBlocks.OLIVE_LOG.get().getDefaultState()),
                            weightedBlockStateProvider,
                            new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                            new StraightTrunkPlacer(4, 2, 0),
                            new TwoLayerFeature(1, 0, 1)
                    ).setIgnoreVines().setDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).build());

            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(PizzaCraft.MODID, "olive_tree"), CONFIGURED_OLIVE_TREE);
        });
    }

    @SubscribeEvent
    public static void onBiomesLoad(BiomeLoadingEvent event)
    {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if(event.getName() != null)
        {
            if(PizzaCraftConfig.COMMON.generateOliveTree.get())
            {
                if(event.getCategory() == Biome.Category.SAVANNA)
                {
                    generation.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> CONFIGURED_OLIVE_TREE.withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)).square()));
                }
            }
        }
    }
}
