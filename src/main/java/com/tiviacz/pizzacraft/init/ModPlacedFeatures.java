package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures
{
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, PizzaCraft.MODID);

    public static final RegistryObject<PlacedFeature> OLIVE_CHECKED = PLACED_FEATURES.register("olive_checked",
            () -> new PlacedFeature(ModConfiguredFeatures.OLIVE.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.OLIVE_SAPLING.get()))));

    public static final RegistryObject<PlacedFeature> OLIVE_PLACED = PLACED_FEATURES.register("olive_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.OLIVE_SPAWN.getHolder().get(), VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(1, 0.02f, 1))));
}