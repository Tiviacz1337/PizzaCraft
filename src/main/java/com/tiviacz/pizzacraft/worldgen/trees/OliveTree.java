package com.tiviacz.pizzacraft.worldgen.trees;

import com.tiviacz.pizzacraft.init.ModConfiguredFeatures;
import com.tiviacz.pizzacraft.init.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class OliveTree extends AbstractTreeGrower
{
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean p_225546_2_)
    {
        return ModConfiguredFeatures.OLIVE.getHolder().get();
    }
}
