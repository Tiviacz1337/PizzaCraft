package com.tiviacz.pizzacraft.worldgen;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID)
public class TreeGenerator
{
    public static ConfiguredFeature<?,?> CONFIGURED_OLIVE_TREE;

  /*  public static void setup(final FMLCommonSetupEvent event)
    {
        WeightedStateProvider weightedBlockStateProvider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85).add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15).build());
        // WeightedBlockStateProvider weightedBlockStateProvider = new WeightedBlockStateProvider();
        //weightedBlockStateProvider.add(ModBlocks.OLIVE_LEAVES.get().defaultBlockState(), 85);
        //weightedBlockStateProvider.add(ModBlocks.FRUIT_OLIVE_LEAVES.get().defaultBlockState().setValue(OliveLeavesBlock.AGE, 1), 15);


        event.enqueueWork(() -> {
            CONFIGURED_OLIVE_TREE = Feature.TREE.configured(
                    new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ModBlocks.OLIVE_LOG.get().defaultBlockState()),
                            new StraightTrunkPlacer(4, 2, 0),
                            weightedBlockStateProvider,
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)
                    ).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(0.002F))).build());

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(PizzaCraft.MODID, "olive_tree"), CONFIGURED_OLIVE_TREE);
        });
    } */

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomesLoad(BiomeLoadingEvent event)
    {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if(types.contains(BiomeDictionary.Type.SAVANNA) && PizzaCraftConfig.COMMON.generateOliveTree.get()) {
            List<Holder<PlacedFeature>> base =
                    event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION);

            base.add(ModFeatures.OLIVE_PLACED);
        }
    /*    BiomeGenerationSettingsBuilder generation = event.getGeneration();

        if(event.getName() != null)
        {
            if(PizzaCraftConfig.COMMON.generateOliveTree.get())
            {
                if(event.getCategory() == Biome.BiomeCategory.SAVANNA)
                {
                    //generation.getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> CONFIGURED_OLIVE_TREE.placed(RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), VegetationPlacements.TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(1, 0.1F, 1), BiomeFilter.biome()));
                    generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CONFIGURED_OLIVE_TREE.placed(RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), VegetationPlacements.TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_TOP_SOLID, PlacementUtils.countExtra(1, 0.1F, 1), BiomeFilter.biome()));
                }
            }
        } */
    }
}
