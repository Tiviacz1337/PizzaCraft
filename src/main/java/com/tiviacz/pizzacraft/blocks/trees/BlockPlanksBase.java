package com.tiviacz.pizzacraft.blocks.trees;

import com.tiviacz.pizzacraft.items.BlockBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPlanksBase extends BlockBase
{
	public BlockPlanksBase(String name, Material material) 
	{
		super(name, material);
		
		setSoundType(SoundType.WOOD);
		setResistance(15.0F);
		setHardness(2.0F);
	}
}