package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.renderer.PizzaItemStackRenderer;
import com.tiviacz.pizzacraft.common.PizzaCraftItemGroup;
import com.tiviacz.pizzacraft.items.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PizzaCraft.MODID);

    public static final RegistryObject<Item> PIZZA = registerBlockItem("pizza", ModBlocks.PIZZA, pizzaProperties().maxStackSize(1).setISTER(() -> PizzaItemStackRenderer::new));
    public static final RegistryObject<Item> RAW_PIZZA = registerBlockItem("raw_pizza", ModBlocks.RAW_PIZZA, pizzaProperties().maxStackSize(1).setISTER(() -> PizzaItemStackRenderer::new));
    public static final RegistryObject<Item> DOUGH = registerBlockItem("dough", ModBlocks.DOUGH, pizzaProperties().maxStackSize(16));
    public static final RegistryObject<Item> MORTAR_AND_PESTLE = registerBlockItem("mortar_and_pestle", ModBlocks.MORTAR_AND_PESTLE, pizzaProperties());
    public static final RegistryObject<Item> OVEN = registerBlockItem("oven", ModBlocks.OVEN, pizzaProperties());

    //Pizza Boards
 /*   public static final RegistryObject<Item> OAK_PIZZA_BOARD = registerBlockItem("oak_pizza_board", ModBlocks.OAK_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> BIRCH_PIZZA_BOARD = registerBlockItem("birch_pizza_board", ModBlocks.BIRCH_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> SPRUCE_PIZZA_BOARD = registerBlockItem("spruce_pizza_board", ModBlocks.SPRUCE_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> JUNGLE_PIZZA_BOARD = registerBlockItem("jungle_pizza_board", ModBlocks.JUNGLE_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> ACACIA_PIZZA_BOARD = registerBlockItem("acacia_pizza_board", ModBlocks.ACACIA_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> DARK_OAK_PIZZA_BOARD = registerBlockItem("dark_oak_pizza_board", ModBlocks.DARK_OAK_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> CRIMSON_PIZZA_BOARD = registerBlockItem("crimson_pizza_board", ModBlocks.CRIMSON_PIZZA_BOARD, pizzaProperties());
    public static final RegistryObject<Item> WARPED_PIZZA_BOARD = registerBlockItem("warped_pizza_board", ModBlocks.WARPED_PIZZA_BOARD, pizzaProperties());
 */
    //Chopping Boards
    public static final RegistryObject<Item> OAK_CHOPPING_BOARD = registerBlockItem("oak_chopping_board", ModBlocks.OAK_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> BIRCH_CHOPPING_BOARD = registerBlockItem("birch_chopping_board", ModBlocks.BIRCH_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> SPRUCE_CHOPPING_BOARD = registerBlockItem("spruce_chopping_board", ModBlocks.SPRUCE_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> JUNGLE_CHOPPING_BOARD = registerBlockItem("jungle_chopping_board", ModBlocks.JUNGLE_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> ACACIA_CHOPPING_BOARD = registerBlockItem("acacia_chopping_board", ModBlocks.ACACIA_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> DARK_OAK_CHOPPING_BOARD = registerBlockItem("dark_oak_chopping_board", ModBlocks.DARK_OAK_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> CRIMSON_CHOPPING_BOARD = registerBlockItem("crimson_chopping_board", ModBlocks.CRIMSON_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> WARPED_CHOPPING_BOARD = registerBlockItem("warped_chopping_board", ModBlocks.WARPED_CHOPPING_BOARD, pizzaProperties());

    //Basins
    public static final RegistryObject<Item> GRANITE_BASIN = registerBlockItem("granite_basin", ModBlocks.GRANITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> DIORITE_BASIN = registerBlockItem("diorite_basin", ModBlocks.DIORITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> ANDESITE_BASIN = registerBlockItem("andesite_basin", ModBlocks.ANDESITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> BASALT_BASIN = registerBlockItem("basalt_basin", ModBlocks.BASALT_BASIN, pizzaProperties());
    public static final RegistryObject<Item> BLACKSTONE_BASIN = registerBlockItem("blackstone_basin", ModBlocks.BLACKSTONE_BASIN, pizzaProperties());

    //Outfits
    public static final RegistryObject<Item> CHEF_HAT = ITEMS.register("chef_hat", () -> new ChefArmor(ArmorMaterials.CHEF, EquipmentSlotType.HEAD, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_SHIRT = ITEMS.register("chef_shirt", () -> new ChefArmor(ArmorMaterials.CHEF, EquipmentSlotType.CHEST, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_LEGGINGS = ITEMS.register("chef_leggings", () -> new ChefArmor(ArmorMaterials.CHEF, EquipmentSlotType.LEGS, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_BOOTS = ITEMS.register("chef_boots", () -> new ChefArmor(ArmorMaterials.CHEF, EquipmentSlotType.FEET, pizzaProperties()));

    //Tools
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", () -> new KnifeItem(0.5F, -2.0F, ItemTier.IRON, pizzaProperties().maxStackSize(1)));
    public static final RegistryObject<Item> ROLLING_PIN = ITEMS.register("rolling_pin", () -> new Item(pizzaProperties().maxStackSize(1).maxDamage(60)));
    public static final RegistryObject<Item> PIZZA_PEEL = ITEMS.register("pizza_peel", () -> new PizzaPeelItem(1.5F, -3.0F, ItemTier.IRON, pizzaProperties().maxStackSize(1)));
    //Pizza Slice
    public static final RegistryObject<Item> PIZZA_SLICE = ITEMS.register("pizza_slice", () -> new PizzaSliceItem(pizzaProperties()));

    //Jugs
    //public static final RegistryObject<Item> GLASS_JUG = ITEMS.register("glass_jug", () -> new Item(pizzaProperties().maxStackSize(16)));
    public static final RegistryObject<Item> OLIVE_OIL = ITEMS.register("olive_oil", () -> new Item(pizzaProperties().maxStackSize(1)));
    public static final RegistryObject<Item> TOMATO_SAUCE = ITEMS.register("tomato_sauce", () -> new SauceItem(pizzaProperties().maxStackSize(1).food(ModFoods.TOMATO_SAUCE), SauceType.TOMATO));

    //Trees
    public static final RegistryObject<Item> OLIVE_PLANKS = registerBlockItem("olive_planks", ModBlocks.OLIVE_PLANKS, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_SAPLING = registerBlockItem("olive_sapling", ModBlocks.OLIVE_SAPLING, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_LOG = registerBlockItem("olive_log", ModBlocks.OLIVE_LOG, pizzaProperties());
    public static final RegistryObject<Item> STRIPPED_OLIVE_LOG = registerBlockItem("stripped_olive_log", ModBlocks.STRIPPED_OLIVE_LOG, pizzaProperties());
    public static final RegistryObject<Item> STRIPPED_OLIVE_WOOD = registerBlockItem("stripped_olive_wood", ModBlocks.STRIPPED_OLIVE_WOOD, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_WOOD = registerBlockItem("olive_wood", ModBlocks.OLIVE_WOOD, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_LEAVES = registerBlockItem("olive_leaves", ModBlocks.OLIVE_LEAVES, pizzaProperties());
    public static final RegistryObject<Item> FRUIT_OLIVE_LEAVES = registerBlockItem("fruit_olive_leaves", ModBlocks.FRUIT_OLIVE_LEAVES, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_SLAB = registerBlockItem("olive_slab", ModBlocks.OLIVE_SLAB, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_PRESSURE_PLATE = registerBlockItem("olive_pressure_plate", ModBlocks.OLIVE_PRESSURE_PLATE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_FENCE = registerBlockItem("olive_fence", ModBlocks.OLIVE_FENCE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_TRAPDOOR = registerBlockItem("olive_trapdoor", ModBlocks.OLIVE_TRAPDOOR, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_FENCE_GATE = registerBlockItem("olive_fence_gate", ModBlocks.OLIVE_FENCE_GATE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_BUTTON = registerBlockItem("olive_button", ModBlocks.OLIVE_BUTTON, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_STAIRS = registerBlockItem("olive_stairs", ModBlocks.OLIVE_STAIRS, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_DOOR = registerBlockItem("olive_door", ModBlocks.OLIVE_DOOR, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_BOOKSHELF = registerBlockItem("olive_bookshelf", ModBlocks.OLIVE_BOOKSHELF, pizzaProperties());

    //Vegetables
    public static final RegistryObject<Item> BROCCOLI = ITEMS.register("broccoli", () -> new Item(pizzaProperties().food(ModFoods.BROCCOLI)));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new BlockNamedItem(ModBlocks.CORNS.get(), pizzaProperties().food(ModFoods.CORN)));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(pizzaProperties().food(ModFoods.CUCUMBER)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new BlockNamedItem(ModBlocks.ONIONS.get(), pizzaProperties().food(ModFoods.ONION)));
    public static final RegistryObject<Item> PEPPER = ITEMS.register("pepper", () -> new Item(pizzaProperties().food(ModFoods.PEPPER)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(pizzaProperties().food(ModFoods.TOMATO)));

    //Fruits
    public static final RegistryObject<Item> PINEAPPLE = ITEMS.register("pineapple", () -> new Item(pizzaProperties().food(ModFoods.PINEAPPLE)));
    public static final RegistryObject<Item> OLIVE = ITEMS.register("olive", () -> new Item(pizzaProperties().food(ModFoods.OLIVE)));

    //Slices
    public static final RegistryObject<Item> CUCUMBER_SLICE = ITEMS.register("cucumber_slice", () -> new Item(pizzaProperties().food(ModFoods.CUCUMBER_SLICE)));
    public static final RegistryObject<Item> ONION_SLICE = ITEMS.register("onion_slice", () -> new Item(pizzaProperties().food(ModFoods.ONION_SLICE)));
    public static final RegistryObject<Item> PEPPER_SLICE = ITEMS.register("pepper_slice", () -> new Item(pizzaProperties().food(ModFoods.PEPPER_SLICE)));
    public static final RegistryObject<Item> PINEAPPLE_SLICE = ITEMS.register("pineapple_slice", () -> new Item(pizzaProperties().food(ModFoods.PINEAPPLE_SLICE)));
    public static final RegistryObject<Item> TOMATO_SLICE = ITEMS.register("tomato_slice", () -> new Item(pizzaProperties().food(ModFoods.TOMATO_SLICE)));
    //public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register("cheese_slice", () -> new Item(pizzaProperties().food(ModFoods.CHEESE_SLICE)));

    //Meats
    public static final RegistryObject<Item> HAM = ITEMS.register("ham", () -> new Item(pizzaProperties().food(ModFoods.HAM)));
    public static final RegistryObject<Item> WING = ITEMS.register("wing", () -> new Item(pizzaProperties().food(ModFoods.WING)));
    public static final RegistryObject<Item> COOKED_WING = ITEMS.register("cooked_wing", () -> new Item(pizzaProperties().food(ModFoods.COOKED_WING)));

    //Ingredients
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new Item(pizzaProperties()));
    public static final RegistryObject<Item> CORN_FLOUR = ITEMS.register("corn_flour", () -> new Item(pizzaProperties()));
    public static final RegistryObject<Item> CHEESE_BLOCK = registerBlockItem("cheese_block", ModBlocks.CHEESE_BLOCK, pizzaProperties());
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(pizzaProperties().food(ModFoods.CHEESE)));

    //Seeds
    public static final RegistryObject<Item> BROCCOLI_SEED = ITEMS.register("broccoli_seed", () -> new BlockNamedItem(ModBlocks.BROCCOLI.get(), pizzaProperties()));
    //public static final RegistryObject<Item> CORN_SEED = ITEMS.register("corn_seed", () -> new BlockNamedItem(ModBlocks.CORN_CROP.get(), pizzaProperties()));
    public static final RegistryObject<Item> CUCUMBER_SEED = ITEMS.register("cucumber_seed", () -> new BlockNamedItem(ModBlocks.CUCUMBERS.get(), pizzaProperties()));
    //public static final RegistryObject<Item> ONION_SEED = ITEMS.register("onion_seed", () -> new BlockNamedItem(ModBlocks.ONION_CROP.get(), pizzaProperties()));
    public static final RegistryObject<Item> PEPPER_SEED = ITEMS.register("pepper_seed", () -> new BlockNamedItem(ModBlocks.PEPPERS.get(), pizzaProperties()));
    public static final RegistryObject<Item> PINEAPPLE_SEED = ITEMS.register("pineapple_seed", () -> new BlockNamedItem(ModBlocks.PINEAPPLE.get(), pizzaProperties()));
    public static final RegistryObject<Item> TOMATO_SEED = ITEMS.register("tomato_seed", () -> new BlockNamedItem(ModBlocks.TOMATOES.get(), pizzaProperties()));

    public static RegistryObject<Item> registerBlockItem(final String name, RegistryObject<Block> block, Item.Properties properties)
    {
        return ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    public static Item.Properties pizzaProperties()
    {
        return new Item.Properties().group(PizzaCraftItemGroup.PIZZACRAFT);
    }
}