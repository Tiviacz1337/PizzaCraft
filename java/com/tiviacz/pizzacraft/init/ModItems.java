package com.tiviacz.pizzacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.tiviacz.pizzacraft.init.base.ArmorBase;
import com.tiviacz.pizzacraft.init.base.DrinkBase;
import com.tiviacz.pizzacraft.init.base.FoodBase;
import com.tiviacz.pizzacraft.init.base.ItemBase;
import com.tiviacz.pizzacraft.objects.items.ItemMilkBottle;
import com.tiviacz.pizzacraft.objects.items.ItemPeel;
import com.tiviacz.pizzacraft.objects.items.ItemReturn;
import com.tiviacz.pizzacraft.objects.items.ItemSeed;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public class ModItems 
{
		public static final List<Item> ITEMS = new ArrayList<Item>();
		
		//Other
		
		public static final Item CARDBOARD = new ItemBase("cardboard");
		public static final Item PAPER_MASS = new ItemBase("paper_mass");
		public static final Item RED_CLOTH = new ItemBase("red_cloth");
		public static final Item BAKEWARE = new ItemReturn("bakeware");
		public static final Item MORTAR_AND_PESTLE = new ItemReturn("mortar_and_pestle");
		public static final Item KNIFE = new ItemReturn("knife");
		public static final Item PEEL = new ItemPeel("peel", 1.0F, -3.0F, ItemPeel.PEEL_MATERIAL);
		
		//Ingredients
		
		public static final Item MILK_BOTTLE = new ItemMilkBottle("milk_bottle");
		public static final Item CHEESE = new FoodBase("cheese", 2, 5, false);
		public static final Item PIZZA_DOUGH = new ItemBase("pizza_dough");
		public static final Item FLOUR = new ItemBase("flour");
		public static final Item ONION = new FoodBase("onion", 2, 5, false);
		public static final Item PEPPER = new FoodBase("pepper", 2, 5, false);
		public static final Item OLIVE = new FoodBase("olive", 2, 5, false);
		public static final Item BLACK_OLIVE = new FoodBase("black_olive", 2, 5, false);
		public static final Item PINEAPPLE = new FoodBase("pineapple", 2, 5, false);
		public static final Item TOMATO = new FoodBase("tomato", 2, 5, false);
		public static final Item CUCUMBER = new FoodBase("cucumber", 2, 5, false);
		public static final Item CORN = new FoodBase("corn", 2, 5, false);
		public static final Item BROCCOLI = new FoodBase("broccoli", 2, 5, false);
		
		//Seeds
		
		public static final Item SEED_ONION = new ItemSeed("seed_onion", ModBlocks.ONION_PLANT.getDefaultState());
		public static final Item SEED_PEPPER = new ItemSeed("seed_pepper", ModBlocks.PEPPER_PLANT.getDefaultState());
		public static final Item SEED_PINEAPPLE = new ItemSeed("seed_pineapple", ModBlocks.PINEAPPLE_PLANT.getDefaultState());
		public static final Item SEED_TOMATO = new ItemSeed("seed_tomato", ModBlocks.TOMATO_PLANT.getDefaultState());
		public static final Item SEED_CUCUMBER = new ItemSeed("seed_cucumber", ModBlocks.CUCUMBER_PLANT.getDefaultState());
		public static final Item SEED_CORN = new ItemSeed("seed_corn", ModBlocks.CORN_PLANT.getDefaultState());
		public static final Item SEED_BROCCOLI = new ItemSeed("seed_broccoli", ModBlocks.BROCCOLI_PLANT.getDefaultState());
		
		//Slices
		
		public static final Item SLICE_0 = new FoodBase("slice_0", 4, 15, false); //Cheese  											//Margherita
		public static final Item SLICE_1 = new FoodBase("slice_1", 6, 15, false); //Cheese / Mushrooms									//Funghi
		public static final Item SLICE_2 = new FoodBase("slice_2", 6, 15, true); //Cheese / Porkchop									//Prosciutto
		public static final Item SLICE_3 = new FoodBase("slice_3", 8, 15, true); //Cheese / Porkchop / Chicken / Beef / Onion			//Meat
		public static final Item SLICE_4 = new FoodBase("slice_4", 6, 15, true); //Cheese / Porkchop / Mushrooms 						//Classic
		public static final Item SLICE_5 = new FoodBase("slice_5", 10, 15, true); //Cheese / Porkchop / Pepper / olive / Mushrooms		//Capriciosa
		public static final Item SLICE_6 = new FoodBase("slice_6", 6, 15, true); //Cheese / Porkchop / Pineapple						//Hawaiian
		public static final Item SLICE_7 = new FoodBase("slice_7", 9, 15, true); //Cheese / Porkchop / Tomato / Mushrooms / Cucumber	//Toscana
		public static final Item SLICE_8 = new FoodBase("slice_8", 10, 15, true); //Cheese / Porkchop / Corn / Mushrooms / Pepper		//Rustica
		public static final Item SLICE_9 = new FoodBase("slice_9", 10, 15, false); //Cheese / Broccoli / Corn / Pepper / Tomato			//Vegetarian
		public static final Item SLICE_10 = new FoodBase("slice_10", 12, 15, true); //Cheese / Porkchop / Black Olive / Pepper / Onion / Mushrooms //Pompea

		//Pizza's deliver set
		
		public static final Item PIZZA_DELIVER_HAT = new ArmorBase("pizza_deliver_hat", ArmorBase.PIZZA_DELIVER_SET, 1, EntityEquipmentSlot.HEAD);
		public static final Item PIZZA_DELIVER_SHIRT = new ArmorBase("pizza_deliver_shirt", ArmorBase.PIZZA_DELIVER_SET, 1, EntityEquipmentSlot.CHEST);
		public static final Item PIZZA_DELIVER_LEGGINGS = new ArmorBase("pizza_deliver_leggings", ArmorBase.PIZZA_DELIVER_SET, 2, EntityEquipmentSlot.LEGS);
		public static final Item PIZZA_DELIVER_BOOTS = new ArmorBase("pizza_deliver_boots", ArmorBase.PIZZA_DELIVER_SET, 1, EntityEquipmentSlot.FEET);
 }
