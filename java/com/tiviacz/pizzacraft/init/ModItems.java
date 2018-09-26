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
		
		public static final Item cardboard = new ItemBase("cardboard");
		public static final Item paper_mass = new ItemBase("paper_mass");
		public static final Item red_cloth = new ItemBase("red_cloth");
		public static final Item bakeware = new ItemReturn("bakeware");
		public static final Item mortar_and_pestle = new ItemReturn("mortar_and_pestle");
		public static final Item knife = new ItemReturn("knife");
		public static final Item peel = new ItemPeel("peel", 1.0F, -3.0F, ItemPeel.ItemDurabilityMaterial);
		
		//Ingredients
		
		public static final Item milk_bottle = new ItemMilkBottle("milk_bottle");
		public static final Item cheese = new FoodBase("cheese", 2, false);
		public static final Item pizza_dough = new ItemBase("pizza_dough");
		public static final Item flour = new ItemBase("flour");
		public static final Item onion = new FoodBase("onion", 2, false);
		public static final Item pepper = new FoodBase("pepper", 2, false);
		public static final Item olive = new FoodBase("olive", 2, false);
		public static final Item black_olive = new FoodBase("black_olive", 2, false);
		public static final Item pineapple = new FoodBase("pineapple", 2, false);
		public static final Item tomato = new FoodBase("tomato", 2, false);
		public static final Item cucumber = new FoodBase("cucumber", 2, false);
		public static final Item corn = new FoodBase("corn", 2, false);
		public static final Item broccoli = new FoodBase("broccoli", 2, false);
		public static final Item meat_mix = new FoodBase("meat_mix", 6, true);
		
		//Seeds
		
		public static final Item seed_onion = new ItemSeed("seed_onion", ModBlocks.onion_plant.getDefaultState());
		public static final Item seed_pepper = new ItemSeed("seed_pepper", ModBlocks.pepper_plant.getDefaultState());
		public static final Item seed_pineapple = new ItemSeed("seed_pineapple", ModBlocks.pineapple_plant.getDefaultState());
		public static final Item seed_tomato = new ItemSeed("seed_tomato", ModBlocks.tomato_plant.getDefaultState());
		public static final Item seed_cucumber = new ItemSeed("seed_cucumber", ModBlocks.cucumber_plant.getDefaultState());
		public static final Item seed_corn = new ItemSeed("seed_corn", ModBlocks.corn_plant.getDefaultState());
		public static final Item seed_broccoli = new ItemSeed("seed_broccoli", ModBlocks.broccoli_plant.getDefaultState());
		
		//Slices
		
		public static final Item slice_0 = new FoodBase("slice_0", 4, false); //Cheese
		public static final Item slice_1 = new FoodBase("slice_1", 6, false); //Cheese / Mushrooms
		public static final Item slice_2 = new FoodBase("slice_2", 6, false); //Cheese / Porkchop
		public static final Item slice_3 = new FoodBase("slice_3", 8, false); //Cheese / Porkchop / Chicken / Beef / Onion
		public static final Item slice_4 = new FoodBase("slice_4", 6, false); //Cheese / Porkchop / Mushrooms
		public static final Item slice_5 = new FoodBase("slice_5", 10, false); //Cheese / Porkchop / Pepper / olive / Mushrooms
		public static final Item slice_6 = new FoodBase("slice_6", 6, false); //Cheese / Porkchop / Pineapple
		public static final Item slice_7 = new FoodBase("slice_7", 9, false); //Cheese / Porkchop / Tomato / Mushrooms / Cucumber
		public static final Item slice_8 = new FoodBase("slice_8", 10, false); //Cheese / Porkchop / Corn / Mushrooms / Pepper
		public static final Item slice_9 = new FoodBase("slice_9", 10, false); //Cheese / Broccoli / Corn / Pepper / Tomato
		public static final Item slice_10 = new FoodBase("slice_10", 12, false); //Cheese / Porkchop / Black Olive / Pepper / Onion / Mushrooms

		//Pizza's deliver set
		
		public static final Item pizza_deliver_hat = new ArmorBase("pizza_deliver_hat", ArmorBase.pizza_deliver_set, 1, EntityEquipmentSlot.HEAD);
		public static final Item pizza_deliver_shirt = new ArmorBase("pizza_deliver_shirt", ArmorBase.pizza_deliver_set, 1, EntityEquipmentSlot.CHEST);
		public static final Item pizza_deliver_leggings = new ArmorBase("pizza_deliver_leggings", ArmorBase.pizza_deliver_set, 2, EntityEquipmentSlot.LEGS);
		public static final Item pizza_deliver_boots = new ArmorBase("pizza_deliver_boots", ArmorBase.pizza_deliver_set, 1, EntityEquipmentSlot.FEET);
 }
