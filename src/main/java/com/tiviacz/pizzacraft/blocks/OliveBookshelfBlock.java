package com.tiviacz.pizzacraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class OliveBookshelfBlock extends Block
{
    public OliveBookshelfBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos)
    {
        return 1;
    }
}