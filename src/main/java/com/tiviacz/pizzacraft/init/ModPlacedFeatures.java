package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures
{
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, PizzaCraft.MODID);

    public static final RegistryObject<PlacedFeature> OLIVE_PLACED = PLACED_FEATURES.register("olive_placed", () -> new PlacedFeature(
            (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?, ?>>)ModFeatures.OLIVE_SPAWN, VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(5))));
    //public static final Holder<PlacedFeature> OLIVE_CHECKED = PlacementUtils.register("olive_checked", ModFeatures.OLIVE,
    //        PlacementUtils.filteredByBlockSurvival(ModBlocks.OLIVE_SAPLING.get()));

  //  public static final Holder<PlacedFeature> OLIVE_PLACED = PlacementUtils.register("olive_placed", ModFeatures.OLIVE_SPAWN,
   //         VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(5))); //PlacementUtils.countExtra(1, 0.1F, 2)));
}
