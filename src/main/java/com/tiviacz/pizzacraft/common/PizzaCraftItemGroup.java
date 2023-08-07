package com.tiviacz.pizzacraft.common;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class PizzaCraftItemGroup extends CreativeModeTab
{
    public static final CreativeModeTab PIZZACRAFT = new PizzaCraftItemGroup(CreativeModeTab.TABS.length, PizzaCraft.MODID);

    private PizzaCraftItemGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon()
    {
        ItemStack stack = ModItems.PIZZA_SLICE.get().getDefaultInstance();
        ItemStackHandler handler = new ItemStackHandler(6);
        handler.setStackInSlot(0, ModItems.CHEESE.get().getDefaultInstance());
        handler.setStackInSlot(1, ModItems.PEPPER_SLICE.get().getDefaultInstance());
        handler.setStackInSlot(2, ModItems.WING.get().getDefaultInstance());
        handler.setStackInSlot(3, ModItems.BROCCOLI.get().getDefaultInstance());
        handler.setStackInSlot(4, ModItems.ONION_SLICE.get().getDefaultInstance());
        handler.setStackInSlot(5, ModItems.TOMATO_SLICE.get().getDefaultInstance());
        NBTUtils.saveInventoryToStack(stack, handler);
        return stack;
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> items)
    {
        items.add(ModBlocks.PIZZA.get().asItem().getDefaultInstance());
        items.add(ModBlocks.RAW_PIZZA.get().asItem().getDefaultInstance());

        items.add(ModBlocks.DOUGH.get().asItem().getDefaultInstance());
        items.add(ModBlocks.PIZZA_STATION.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OVEN.get().asItem().getDefaultInstance());

        items.add(ModBlocks.OAK_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.BIRCH_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.SPRUCE_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.JUNGLE_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.ACACIA_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.DARK_OAK_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.CRIMSON_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.WARPED_CHOPPING_BOARD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_CHOPPING_BOARD.get().asItem().getDefaultInstance());

        items.add(ModBlocks.GRANITE_BASIN.get().asItem().getDefaultInstance());
        items.add(ModBlocks.DIORITE_BASIN.get().asItem().getDefaultInstance());
        items.add(ModBlocks.ANDESITE_BASIN.get().asItem().getDefaultInstance());
        items.add(ModBlocks.BASALT_BASIN.get().asItem().getDefaultInstance());
        items.add(ModBlocks.BLACKSTONE_BASIN.get().asItem().getDefaultInstance());

        items.add(ModItems.CHEF_HAT.get().getDefaultInstance());
        items.add(ModItems.CHEF_SHIRT.get().getDefaultInstance());
        items.add(ModItems.CHEF_LEGGINGS.get().getDefaultInstance());
        items.add(ModItems.CHEF_BOOTS.get().getDefaultInstance());

        items.add(ModItems.PIZZA_DELIVERY_CAP.get().getDefaultInstance());
        items.add(ModItems.PIZZA_DELIVERY_SHIRT.get().getDefaultInstance());
        items.add(ModItems.PIZZA_DELIVERY_LEGGINGS.get().getDefaultInstance());
        items.add(ModItems.PIZZA_DELIVERY_BOOTS.get().getDefaultInstance());
        items.add(ModBlocks.RED_PIZZA_BAG.get().asItem().getDefaultInstance());

        items.add(ModItems.ROLLING_PIN.get().getDefaultInstance());

        items.add(ModItems.STONE_KNIFE.get().getDefaultInstance());
        items.add(ModItems.GOLDEN_KNIFE.get().getDefaultInstance());
        items.add(ModItems.IRON_KNIFE.get().getDefaultInstance());
        items.add(ModItems.DIAMOND_KNIFE.get().getDefaultInstance());
        items.add(ModItems.NETHERITE_KNIFE.get().getDefaultInstance());

        items.add(ModItems.STONE_PIZZA_PEEL.get().getDefaultInstance());
        items.add(ModItems.GOLDEN_PIZZA_PEEL.get().getDefaultInstance());
        items.add(ModItems.IRON_PIZZA_PEEL.get().getDefaultInstance());
        items.add(ModItems.DIAMOND_PIZZA_PEEL.get().getDefaultInstance());
        items.add(ModItems.NETHERITE_PIZZA_PEEL.get().getDefaultInstance());

        items.add(ModItems.PIZZA_SLICE.get().getDefaultInstance());
        items.add(ModItems.OLIVE_OIL.get().getDefaultInstance());
        items.add(ModItems.TOMATO_SAUCE.get().getDefaultInstance());
        items.add(ModItems.HOT_SAUCE.get().getDefaultInstance());

        items.add(ModBlocks.OLIVE_PLANKS.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_SAPLING.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_LOG.get().asItem().getDefaultInstance());
        items.add(ModBlocks.STRIPPED_OLIVE_LOG.get().asItem().getDefaultInstance());
        items.add(ModBlocks.STRIPPED_OLIVE_WOOD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_WOOD.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_LEAVES.get().asItem().getDefaultInstance());
        items.add(ModBlocks.FRUIT_OLIVE_LEAVES.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_SLAB.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_PRESSURE_PLATE.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_FENCE.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_TRAPDOOR.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_FENCE_GATE.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_BUTTON.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_STAIRS.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_DOOR.get().asItem().getDefaultInstance());
        items.add(ModBlocks.OLIVE_BOOKSHELF.get().asItem().getDefaultInstance());

        items.add(ModItems.BROCCOLI.get().getDefaultInstance());
        items.add(ModItems.CORN.get().getDefaultInstance());
        items.add(ModItems.CUCUMBER.get().getDefaultInstance());
        items.add(ModItems.ONION.get().getDefaultInstance());
        items.add(ModItems.PEPPER.get().getDefaultInstance());
        items.add(ModItems.TOMATO.get().getDefaultInstance());
        items.add(ModItems.PINEAPPLE.get().getDefaultInstance());
        items.add(ModItems.OLIVE.get().getDefaultInstance());

        items.add(ModItems.CUCUMBER_SLICE.get().getDefaultInstance());
        items.add(ModItems.ONION_SLICE.get().getDefaultInstance());
        items.add(ModItems.PEPPER_SLICE.get().getDefaultInstance());
        items.add(ModItems.PINEAPPLE_SLICE.get().getDefaultInstance());
        items.add(ModItems.TOMATO_SLICE.get().getDefaultInstance());
        items.add(ModItems.MUSHROOM_SLICE.get().getDefaultInstance());
        items.add(ModItems.HAM.get().getDefaultInstance());
        items.add(ModItems.WING.get().getDefaultInstance());
        items.add(ModItems.COOKED_WING.get().getDefaultInstance());
        items.add(ModItems.FLOUR.get().getDefaultInstance());
        items.add(ModItems.CORN_FLOUR.get().getDefaultInstance());

        items.add(ModBlocks.CHEESE_BLOCK.get().asItem().getDefaultInstance());
        items.add(ModItems.CHEESE.get().getDefaultInstance());
        items.add(ModItems.BROCCOLI_SEEDS.get().getDefaultInstance());
        items.add(ModItems.CUCUMBER_SEEDS.get().getDefaultInstance());
        items.add(ModItems.PEPPER_SEEDS.get().getDefaultInstance());
        items.add(ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance());
        items.add(ModItems.TOMATO_SEEDS.get().getDefaultInstance());
    }
}
