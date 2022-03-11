package com.tiviacz.pizzacraft.worldgen.trees;

import com.tiviacz.pizzacraft.init.ModFeatures;
import com.tiviacz.pizzacraft.worldgen.feature.OliveTreeFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class OliveTree extends Tree
{
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean p_225546_2_)
    {
        return ((OliveTreeFeature) ModFeatures.OLIVE_TREE.get()).setConfiguration();
    }
}
