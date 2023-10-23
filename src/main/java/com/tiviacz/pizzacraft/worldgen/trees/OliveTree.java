package com.tiviacz.pizzacraft.worldgen.trees;

import com.tiviacz.pizzacraft.init.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class OliveTree extends AbstractTreeGrower
{
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean p_225546_2_)
    {
        return ModConfiguredFeatures.OLIVE_TREE_KEY;
    }
}
