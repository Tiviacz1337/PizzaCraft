package com.tiviacz.pizzacraft.world.gen;

import java.util.Random;

import com.tiviacz.pizzacraft.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenTrees implements IWorldGenerator
{
	private final WorldGenerator OLIVE = new WorldGenOliveTree();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
			case 1:
				break;

			case 0:
				runGenerator(OLIVE, world, random, chunkX << 4, chunkZ << 4, ConfigHandler.treeGenChance, Blocks.GRASS);
				break;

			case -1:
				break;

			default:
				break;
		}
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock)
	{
		int x = chunkX + random.nextInt(8) + 4;
		int z = chunkZ + random.nextInt(8) + 4;
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x,y,z);
		
		Biome biome = world.getBiome(pos);
		boolean validBiome = (!BiomeDictionary.hasType(biome, Type.SNOWY)) && (BiomeDictionary.hasType(biome, Type.FOREST) || BiomeDictionary.hasType(biome, Type.PLAINS) || BiomeDictionary.hasType(biome, Type.MOUNTAIN));
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if(validBiome)
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
					System.out.println("generated at " + pos);
				}
			}
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;
	}
}