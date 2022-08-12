package com.tiviacz.pizzacraft.worldgen.trees;

import com.tiviacz.pizzacraft.init.ModFeatures;
import com.tiviacz.pizzacraft.worldgen.feature.OliveTreeFeature;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nullable;
import java.util.Random;

public class OliveTree extends AbstractTreeGrower
{
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random randomIn, boolean p_225546_2_)
    {
        return ((OliveTreeFeature) ModFeatures.OLIVE_TREE.get()).setConfiguration();
    }
}
