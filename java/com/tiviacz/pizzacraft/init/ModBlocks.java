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
		
		public static final Block pizza_0 = new BlockPizza("pizza_0", Material.CAKE, 4, 15F);
		public static final Block pizza_1 = new BlockPizza("pizza_1", Material.CAKE, 6, 15F);
		public static final Block pizza_2 = new BlockPizza("pizza_2", Material.CAKE, 6, 15F);
		public static final Block pizza_3 = new BlockPizza("pizza_3", Material.CAKE, 8, 15F);
		public static final Block pizza_4 = new BlockPizza("pizza_4", Material.CAKE, 6, 15F);
		public static final Block pizza_5 = new BlockPizza("pizza_5", Material.CAKE, 8, 15F);
		public static final Block pizza_6 = new BlockPizza("pizza_6", Material.CAKE, 6, 15F);
		public static final Block pizza_7 = new BlockPizza("pizza_7", Material.CAKE, 9, 15F);
		public static final Block pizza_8 = new BlockPizza("pizza_8", Material.CAKE, 10, 15F);
		public static final Block pizza_9 = new BlockPizza("pizza_9", Material.CAKE, 10, 15F);
		public static final Block pizza_10 = new BlockPizza("pizza_10", Material.CAKE, 10, 15F);
		public static final Block pizza_burnt = new BlockPizzaBurnt("pizza_burnt", Material.CAKE);
		
		//Raw Pizza
		
		public static final Block raw_pizza_0 = new BlockRawPizza("raw_pizza_0", Material.CAKE);
		public static final Block raw_pizza_1 = new BlockRawPizza("raw_pizza_1", Material.CAKE);
		public static final Block raw_pizza_2 = new BlockRawPizza("raw_pizza_2", Material.CAKE);
		public static final Block raw_pizza_3 = new BlockRawPizza("raw_pizza_3", Material.CAKE);
		public static final Block raw_pizza_4 = new BlockRawPizza("raw_pizza_4", Material.CAKE);
		public static final Block raw_pizza_5 = new BlockRawPizza("raw_pizza_5", Material.CAKE);
		public static final Block raw_pizza_6 = new BlockRawPizza("raw_pizza_6", Material.CAKE);
		public static final Block raw_pizza_7 = new BlockRawPizza("raw_pizza_7", Material.CAKE);
		public static final Block raw_pizza_8 = new BlockRawPizza("raw_pizza_8", Material.CAKE);
		public static final Block raw_pizza_9 = new BlockRawPizza("raw_pizza_9", Material.CAKE);
		public static final Block raw_pizza_10 = new BlockRawPizza("raw_pizza_10", Material.CAKE);
				
		//Pizza Board
		
		public static final Block pizza_board = new BlockPizzaBoard("pizza_board", Material.WOOD);
		public static final Block pizza_board_w0 = new BlockPizzaBoardBase("pizza_board_w0", Material.WOOD, 4, 15F);
		public static final Block pizza_board_w1 = new BlockPizzaBoardBase("pizza_board_w1", Material.WOOD, 6, 15F);
		public static final Block pizza_board_w2 = new BlockPizzaBoardBase("pizza_board_w2", Material.WOOD, 6, 15F);
		public static final Block pizza_board_w3 = new BlockPizzaBoardBase("pizza_board_w3", Material.WOOD, 8, 15F);
		public static final Block pizza_board_w4 = new BlockPizzaBoardBase("pizza_board_w4", Material.WOOD, 6, 15F);
		public static final Block pizza_board_w5 = new BlockPizzaBoardBase("pizza_board_w5", Material.WOOD, 8, 15F);
		public static final Block pizza_board_w6 = new BlockPizzaBoardBase("pizza_board_w6", Material.WOOD, 6, 15F);
		public static final Block pizza_board_w7 = new BlockPizzaBoardBase("pizza_board_w7", Material.WOOD, 9, 15F);
		public static final Block pizza_board_w8 = new BlockPizzaBoardBase("pizza_board_w8", Material.WOOD, 10, 15F);
		public static final Block pizza_board_w9 = new BlockPizzaBoardBase("pizza_board_w9", Material.WOOD, 10, 15F);
		public static final Block pizza_board_w10 = new BlockPizzaBoardBase("pizza_board_w10", Material.WOOD, 10, 15F);
		
		//Boxes with Pizza
		
		public static final Block pizza_box = new BlockPizzaBox("pizza_box", Material.CLOTH);
		public static final Block pizza_box_w0 = new BlockPizzaBoxBase("pizza_box_w0", Material.CLOTH, ModBlocks.pizza_0.getDefaultState());
		public static final Block pizza_box_w1 = new BlockPizzaBoxBase("pizza_box_w1", Material.CLOTH, ModBlocks.pizza_1.getDefaultState());
		public static final Block pizza_box_w2 = new BlockPizzaBoxBase("pizza_box_w2", Material.CLOTH, ModBlocks.pizza_2.getDefaultState());
		public static final Block pizza_box_w3 = new BlockPizzaBoxBase("pizza_box_w3", Material.CLOTH, ModBlocks.pizza_3.getDefaultState());
		public static final Block pizza_box_w4 = new BlockPizzaBoxBase("pizza_box_w4", Material.CLOTH, ModBlocks.pizza_4.getDefaultState());
		public static final Block pizza_box_w5 = new BlockPizzaBoxBase("pizza_box_w5", Material.CLOTH, ModBlocks.pizza_5.getDefaultState());
		public static final Block pizza_box_w6 = new BlockPizzaBoxBase("pizza_box_w6", Material.CLOTH, ModBlocks.pizza_6.getDefaultState());
		public static final Block pizza_box_w7 = new BlockPizzaBoxBase("pizza_box_w7", Material.CLOTH, ModBlocks.pizza_7.getDefaultState());
		public static final Block pizza_box_w8 = new BlockPizzaBoxBase("pizza_box_w8", Material.CLOTH, ModBlocks.pizza_8.getDefaultState());
		public static final Block pizza_box_w9 = new BlockPizzaBoxBase("pizza_box_w9", Material.CLOTH, ModBlocks.pizza_9.getDefaultState());
		public static final Block pizza_box_w10 = new BlockPizzaBoxBase("pizza_box_w10", Material.CLOTH, ModBlocks.pizza_10.getDefaultState());
		
		//Plants
		
		public static final Block onion_plant = new OnionPlant("onion_plant");
		public static final Block pepper_plant = new PepperPlant("pepper_plant");
		public static final Block pineapple_plant = new PineapplePlant("pineapple_plant");
		public static final Block tomato_plant = new TomatoPlant("tomato_plant");
		public static final Block cucumber_plant = new CucumberPlant("cucumber_plant");
		public static final Block corn_plant = new CornPlant("corn_plant");
		public static final Block broccoli_plant = new BroccoliPlant("broccoli_plant");
		
		//Other
		
		public static final Block pizza_oven = new BlockPizzaOven("pizza_oven", Material.IRON);
		public static final Block burning_pizza_oven = new BlockPizzaOvenBurning("burning_pizza_oven", Material.IRON);	
				
}
