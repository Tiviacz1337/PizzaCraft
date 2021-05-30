package com.tiviacz.pizzacraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class OliveBookshelfBlock extends Block
{
    public OliveBookshelfBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos)
    {
        return 1;
    }
}