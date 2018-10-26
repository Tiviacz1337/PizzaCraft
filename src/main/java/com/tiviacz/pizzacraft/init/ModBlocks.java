package com.tiviacz.pizzacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaOvenBurning;
import com.tiviacz.pizzacraft.objects.blocks.BlockRawPizza;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizza;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaBoard;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaBoardBase;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaBox;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaBoxBase;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaBurnt;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaOven;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaOven;
import com.tiviacz.pizzacraft.objects.blocks.BlockPizzaOvenBurning;
import com.tiviacz.pizzacraft.objects.blocks.plants.BroccoliPlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.CornPlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.CucumberPlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.OnionPlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.PepperPlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.PineapplePlant;
import com.tiviacz.pizzacraft.objects.blocks.plants.TomatoPlant;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
		public static final List<Block> BLOCKS = new ArrayList<Block>();
			
		//Pizza
		
		public static final Block PIZZA_0 = new BlockPizza("pizza_0", Material.CAKE, 4, 15F);
		public static final Block PIZZA_1 = new BlockPizza("pizza_1", Material.CAKE, 6, 15F);
		public static final Block PIZZA_2 = new BlockPizza("pizza_2", Material.CAKE, 6, 15F);
		public static final Block PIZZA_3 = new BlockPizza("pizza_3", Material.CAKE, 8, 15F);
		public static final Block PIZZA_4 = new BlockPizza("pizza_4", Material.CAKE, 6, 15F);
		public static final Block PIZZA_5 = new BlockPizza("pizza_5", Material.CAKE, 8, 15F);
		public static final Block PIZZA_6 = new BlockPizza("pizza_6", Material.CAKE, 6, 15F);
		public static final Block PIZZA_7 = new BlockPizza("pizza_7", Material.CAKE, 9, 15F);
		public static final Block PIZZA_8 = new BlockPizza("pizza_8", Material.CAKE, 10, 15F);
		public static final Block PIZZA_9 = new BlockPizza("pizza_9", Material.CAKE, 10, 15F);
		public static final Block PIZZA_10 = new BlockPizza("pizza_10", Material.CAKE, 10, 15F);
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
		public static final Block PIZZA_BOARD_0 = new BlockPizzaBoardBase("pizza_board_0", Material.WOOD, 4, 15F);
		public static final Block PIZZA_BOARD_1 = new BlockPizzaBoardBase("pizza_board_1", Material.WOOD, 6, 15F);
		public static final Block PIZZA_BOARD_2 = new BlockPizzaBoardBase("pizza_board_2", Material.WOOD, 6, 15F);
		public static final Block PIZZA_BOARD_3 = new BlockPizzaBoardBase("pizza_board_3", Material.WOOD, 8, 15F);
		public static final Block PIZZA_BOARD_4 = new BlockPizzaBoardBase("pizza_board_4", Material.WOOD, 6, 15F);
		public static final Block PIZZA_BOARD_5 = new BlockPizzaBoardBase("pizza_board_5", Material.WOOD, 8, 15F);
		public static final Block PIZZA_BOARD_6 = new BlockPizzaBoardBase("pizza_board_6", Material.WOOD, 6, 15F);
		public static final Block PIZZA_BOARD_7 = new BlockPizzaBoardBase("pizza_board_7", Material.WOOD, 9, 15F);
		public static final Block PIZZA_BOARD_8 = new BlockPizzaBoardBase("pizza_board_8", Material.WOOD, 10, 15F);
		public static final Block PIZZA_BOARD_9 = new BlockPizzaBoardBase("pizza_board_9", Material.WOOD, 10, 15F);
		public static final Block PIZZA_BOARD_10 = new BlockPizzaBoardBase("pizza_board_10", Material.WOOD, 10, 15F);
		
		//Boxes with Pizza
		
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
		public static final Block CORN_PLANT = new CornPlant("corn_plant");
		public static final Block BROCCOLI_PLANT = new BroccoliPlant("broccoli_plant");
		
		//Other
		
		public static final Block PIZZA_OVEN = new BlockPizzaOven("pizza_oven", Material.IRON);
		public static final Block BURNING_PIZZA_OVEN = new BlockPizzaOvenBurning("burning_pizza_oven", Material.IRON);
}
