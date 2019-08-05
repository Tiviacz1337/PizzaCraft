package com.tiviacz.pizzacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.tiviacz.pizzacraft.blocks.BlockBakeware;
import com.tiviacz.pizzacraft.blocks.BlockChoppingBoard;
import com.tiviacz.pizzacraft.blocks.BlockDough;
import com.tiviacz.pizzacraft.blocks.BlockMortarAndPestle;
import com.tiviacz.pizzacraft.blocks.BlockPizza;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBag;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBoard;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBoardBase;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBox;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBoxBase;
import com.tiviacz.pizzacraft.blocks.BlockPizzaBurnt;
import com.tiviacz.pizzacraft.blocks.BlockPizzaOven;
import com.tiviacz.pizzacraft.blocks.BlockPizzaOvenBurning;
import com.tiviacz.pizzacraft.blocks.BlockRawPizza;
import com.tiviacz.pizzacraft.blocks.plants.BroccoliPlant;
import com.tiviacz.pizzacraft.blocks.plants.CornPlantBottom;
import com.tiviacz.pizzacraft.blocks.plants.CornPlantTop;
import com.tiviacz.pizzacraft.blocks.plants.CucumberPlant;
import com.tiviacz.pizzacraft.blocks.plants.OnionPlant;
import com.tiviacz.pizzacraft.blocks.plants.PepperPlant;
import com.tiviacz.pizzacraft.blocks.plants.PineapplePlant;
import com.tiviacz.pizzacraft.blocks.plants.TomatoPlant;
import com.tiviacz.pizzacraft.blocks.trees.BlockLeavesBase;
import com.tiviacz.pizzacraft.blocks.trees.BlockLeavesGrowing;
import com.tiviacz.pizzacraft.blocks.trees.BlockLogBase;
import com.tiviacz.pizzacraft.blocks.trees.BlockPlanksBase;
import com.tiviacz.pizzacraft.blocks.trees.BlockSaplingBase;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
		public static final List<Block> BLOCKS = new ArrayList<Block>();
			
		//Pizza
		
		public static final Block PIZZA_0 = new BlockPizza("pizza_0", Material.CAKE, Reference.SHANKS_MARGHERITA, Reference.SATURATION, ModItems.SLICE_0);
		public static final Block PIZZA_1 = new BlockPizza("pizza_1", Material.CAKE, Reference.SHANKS_FUNGHI, Reference.SATURATION, ModItems.SLICE_1);
		public static final Block PIZZA_2 = new BlockPizza("pizza_2", Material.CAKE, Reference.SHANKS_PROSCIUTTO, Reference.SATURATION, ModItems.SLICE_2);
		public static final Block PIZZA_3 = new BlockPizza("pizza_3", Material.CAKE, Reference.SHANKS_MEAT, Reference.SATURATION, ModItems.SLICE_3);
		public static final Block PIZZA_4 = new BlockPizza("pizza_4", Material.CAKE, Reference.SHANKS_CLASSIC, Reference.SATURATION, ModItems.SLICE_4);
		public static final Block PIZZA_5 = new BlockPizza("pizza_5", Material.CAKE, Reference.SHANKS_CAPRICIOSA, Reference.SATURATION, ModItems.SLICE_5);
		public static final Block PIZZA_6 = new BlockPizza("pizza_6", Material.CAKE, Reference.SHANKS_HAWAIIAN, Reference.SATURATION, ModItems.SLICE_6);
		public static final Block PIZZA_7 = new BlockPizza("pizza_7", Material.CAKE, Reference.SHANKS_TOSCANA, Reference.SATURATION, ModItems.SLICE_7);
		public static final Block PIZZA_8 = new BlockPizza("pizza_8", Material.CAKE, Reference.SHANKS_RUSTICA, Reference.SATURATION, ModItems.SLICE_8);
		public static final Block PIZZA_9 = new BlockPizza("pizza_9", Material.CAKE, Reference.SHANKS_VEGETARIAN, Reference.SATURATION, ModItems.SLICE_9);
		public static final Block PIZZA_10 = new BlockPizza("pizza_10", Material.CAKE, Reference.SHANKS_POMPEA, Reference.SATURATION, ModItems.SLICE_10);
		public static final Block PIZZA_BURNT = new BlockPizzaBurnt("pizza_burnt", Material.CAKE);
		
		//Raw Pizza
		
		public static final Block RAW_PIZZA_0 = new BlockRawPizza("raw_pizza_0", Material.CAKE);
		public static final Block RAW_PIZZA_1 = new BlockRawPizza("raw_pizza_1", Material.CAKE);
		public static final Block RAW_PIZZA_2 = new BlockRawPizza("raw_pizza_2", Material.CAKE);
		public static final Block RAW_PIZZA_3 = new BlockRawPizza("raw_pizza_3", Material.CAKE);
		public static final Block RAW_PIZZA_4 = new BlockRawPizza("raw_pizza_4", Material.CAKE);
		public static final Block RAW_PIZZA_5 = new BlockRawPizza("raw_pizza_5", Material.CAKE);
		public static final Block RAW_PIZZA_6 = new BlockRawPizza("raw_pizza_6", Material.CAKE);
		public static final Block RAW_PIZZA_7 = new BlockRawPizza("raw_pizza_7", Material.CAKE);
		public static final Block RAW_PIZZA_8 = new BlockRawPizza("raw_pizza_8", Material.CAKE);
		public static final Block RAW_PIZZA_9 = new BlockRawPizza("raw_pizza_9", Material.CAKE);
		public static final Block RAW_PIZZA_10 = new BlockRawPizza("raw_pizza_10", Material.CAKE);
				
		//Pizza Board
		
		public static final Block PIZZA_BOARD = new BlockPizzaBoard("pizza_board", Material.WOOD);
		public static final Block PIZZA_BOARD_0 = new BlockPizzaBoardBase("pizza_board_0", Material.WOOD, Reference.SHANKS_MARGHERITA, Reference.SATURATION, ModItems.SLICE_0);
		public static final Block PIZZA_BOARD_1 = new BlockPizzaBoardBase("pizza_board_1", Material.WOOD, Reference.SHANKS_FUNGHI, Reference.SATURATION, ModItems.SLICE_1);
		public static final Block PIZZA_BOARD_2 = new BlockPizzaBoardBase("pizza_board_2", Material.WOOD, Reference.SHANKS_PROSCIUTTO, Reference.SATURATION, ModItems.SLICE_2);
		public static final Block PIZZA_BOARD_3 = new BlockPizzaBoardBase("pizza_board_3", Material.WOOD, Reference.SHANKS_MEAT, Reference.SATURATION, ModItems.SLICE_3);
		public static final Block PIZZA_BOARD_4 = new BlockPizzaBoardBase("pizza_board_4", Material.WOOD, Reference.SHANKS_CLASSIC, Reference.SATURATION, ModItems.SLICE_4);
		public static final Block PIZZA_BOARD_5 = new BlockPizzaBoardBase("pizza_board_5", Material.WOOD, Reference.SHANKS_CAPRICIOSA, Reference.SATURATION, ModItems.SLICE_5);
		public static final Block PIZZA_BOARD_6 = new BlockPizzaBoardBase("pizza_board_6", Material.WOOD, Reference.SHANKS_HAWAIIAN, Reference.SATURATION, ModItems.SLICE_6);
		public static final Block PIZZA_BOARD_7 = new BlockPizzaBoardBase("pizza_board_7", Material.WOOD, Reference.SHANKS_TOSCANA, Reference.SATURATION, ModItems.SLICE_7);
		public static final Block PIZZA_BOARD_8 = new BlockPizzaBoardBase("pizza_board_8", Material.WOOD, Reference.SHANKS_RUSTICA, Reference.SATURATION, ModItems.SLICE_8);
		public static final Block PIZZA_BOARD_9 = new BlockPizzaBoardBase("pizza_board_9", Material.WOOD, Reference.SHANKS_VEGETARIAN, Reference.SATURATION, ModItems.SLICE_9);
		public static final Block PIZZA_BOARD_10 = new BlockPizzaBoardBase("pizza_board_10", Material.WOOD, Reference.SHANKS_POMPEA, Reference.SATURATION, ModItems.SLICE_10);
		
		//Boxes with Pizza
		
		public static final Block PIZZA_BAG = new BlockPizzaBag("pizza_bag", Material.CLOTH);
		public static final Block PIZZA_BOX = new BlockPizzaBox("pizza_box", Material.CLOTH);
		public static final Block PIZZA_BOX_0 = new BlockPizzaBoxBase("pizza_box_w0", Material.CLOTH, ModBlocks.PIZZA_0.getDefaultState());
		public static final Block PIZZA_BOX_1 = new BlockPizzaBoxBase("pizza_box_w1", Material.CLOTH, ModBlocks.PIZZA_1.getDefaultState());
		public static final Block PIZZA_BOX_2 = new BlockPizzaBoxBase("pizza_box_w2", Material.CLOTH, ModBlocks.PIZZA_2.getDefaultState());
		public static final Block PIZZA_BOX_3 = new BlockPizzaBoxBase("pizza_box_w3", Material.CLOTH, ModBlocks.PIZZA_3.getDefaultState());
		public static final Block PIZZA_BOX_4 = new BlockPizzaBoxBase("pizza_box_w4", Material.CLOTH, ModBlocks.PIZZA_4.getDefaultState());
		public static final Block PIZZA_BOX_5 = new BlockPizzaBoxBase("pizza_box_w5", Material.CLOTH, ModBlocks.PIZZA_5.getDefaultState());
		public static final Block PIZZA_BOX_6 = new BlockPizzaBoxBase("pizza_box_w6", Material.CLOTH, ModBlocks.PIZZA_6.getDefaultState());
		public static final Block PIZZA_BOX_7 = new BlockPizzaBoxBase("pizza_box_w7", Material.CLOTH, ModBlocks.PIZZA_7.getDefaultState());
		public static final Block PIZZA_BOX_8 = new BlockPizzaBoxBase("pizza_box_w8", Material.CLOTH, ModBlocks.PIZZA_8.getDefaultState());
		public static final Block PIZZA_BOX_9 = new BlockPizzaBoxBase("pizza_box_w9", Material.CLOTH, ModBlocks.PIZZA_9.getDefaultState());
		public static final Block PIZZA_BOX_10 = new BlockPizzaBoxBase("pizza_box_w10", Material.CLOTH, ModBlocks.PIZZA_10.getDefaultState());
		
		//Plants
		
		public static final Block ONION_PLANT = new OnionPlant("onion_plant");
		public static final Block PEPPER_PLANT = new PepperPlant("pepper_plant");
		public static final Block PINEAPPLE_PLANT = new PineapplePlant("pineapple_plant");
		public static final Block TOMATO_PLANT = new TomatoPlant("tomato_plant");
		public static final Block CUCUMBER_PLANT = new CucumberPlant("cucumber_plant");
		public static final Block CORN_PLANT_BOTTOM = new CornPlantBottom("corn_plant_bottom");
		public static final Block CORN_PLANT_TOP = new CornPlantTop("corn_plant_top");
		public static final Block BROCCOLI_PLANT = new BroccoliPlant("broccoli_plant");
		
		//Trees
		public static final Block OLIVE_LOG = new BlockLogBase("olive_log");
		public static final Block OLIVE_LEAVES = new BlockLeavesBase("olive_leaves");
		public static final Block OLIVE_LEAVES_GROWING = new BlockLeavesGrowing("olive_leaves_growing");
		public static final Block OLIVE_SAPLING = new BlockSaplingBase("olive_sapling");
		public static final Block OLIVE_PLANKS = new BlockPlanksBase("olive_planks", Material.WOOD);
		
		//Other
		
		public static final Block PIZZA_OVEN = new BlockPizzaOven("pizza_oven", Material.IRON);
		public static final Block BURNING_PIZZA_OVEN = new BlockPizzaOvenBurning("burning_pizza_oven", Material.IRON);
		public static final Block CHOPPING_BOARD = new BlockChoppingBoard("chopping_board", Material.WOOD);
		public static final Block DOUGH = new BlockDough("dough", Material.CAKE);
		public static final Block MORTAR_AND_PESTLE = new BlockMortarAndPestle("mortar_and_pestle", Material.ROCK);
		public static final Block BAKEWARE = new BlockBakeware("bakeware", Material.IRON);
}