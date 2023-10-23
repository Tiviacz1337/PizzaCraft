package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PizzaCraft.MODID);

    public static RegistryObject<CreativeModeTab> TRAVELERS_BACKPACK = CREATIVE_MODE_TABS.register("travelersbackpack", () -> CreativeModeTab.builder()
            .icon(ModCreativeTabs::createIcon)
            .title(Component.translatable("itemGroup.pizzacraft")).displayItems(ModCreativeTabs::displayItems).build());

    public static ItemStack createIcon()
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

    public static void displayItems(CreativeModeTab.ItemDisplayParameters displayParameters, CreativeModeTab.Output output)
    {
        output.accept(ModBlocks.PIZZA.get());
        output.accept(ModBlocks.RAW_PIZZA.get());

        output.accept(ModBlocks.DOUGH.get());
        output.accept(ModBlocks.PIZZA_STATION.get());
        output.accept(ModBlocks.OVEN.get());

        output.accept(ModBlocks.OAK_CHOPPING_BOARD.get());
        output.accept(ModBlocks.BIRCH_CHOPPING_BOARD.get());
        output.accept(ModBlocks.SPRUCE_CHOPPING_BOARD.get());
        output.accept(ModBlocks.JUNGLE_CHOPPING_BOARD.get());
        output.accept(ModBlocks.ACACIA_CHOPPING_BOARD.get());
        output.accept(ModBlocks.DARK_OAK_CHOPPING_BOARD.get());
        output.accept(ModBlocks.CRIMSON_CHOPPING_BOARD.get());
        output.accept(ModBlocks.WARPED_CHOPPING_BOARD.get());
        output.accept(ModBlocks.OLIVE_CHOPPING_BOARD.get());

        output.accept(ModBlocks.GRANITE_BASIN.get());
        output.accept(ModBlocks.DIORITE_BASIN.get());
        output.accept(ModBlocks.ANDESITE_BASIN.get());
        output.accept(ModBlocks.BASALT_BASIN.get());
        output.accept(ModBlocks.BLACKSTONE_BASIN.get());

        output.accept(ModItems.CHEF_HAT.get());
        output.accept(ModItems.CHEF_SHIRT.get());
        output.accept(ModItems.CHEF_LEGGINGS.get());
        output.accept(ModItems.CHEF_BOOTS.get());

        output.accept(ModItems.PIZZA_DELIVERY_CAP.get());
        output.accept(ModItems.PIZZA_DELIVERY_SHIRT.get());
        output.accept(ModItems.PIZZA_DELIVERY_LEGGINGS.get());
        output.accept(ModItems.PIZZA_DELIVERY_BOOTS.get());
        output.accept(ModBlocks.RED_PIZZA_BAG.get());

        output.accept(ModItems.ROLLING_PIN.get());

        output.accept(ModItems.STONE_KNIFE.get());
        output.accept(ModItems.GOLDEN_KNIFE.get());
        output.accept(ModItems.IRON_KNIFE.get());
        output.accept(ModItems.DIAMOND_KNIFE.get());
        output.accept(ModItems.NETHERITE_KNIFE.get());

        output.accept(ModItems.STONE_PIZZA_PEEL.get());
        output.accept(ModItems.GOLDEN_PIZZA_PEEL.get());
        output.accept(ModItems.IRON_PIZZA_PEEL.get());
        output.accept(ModItems.DIAMOND_PIZZA_PEEL.get());
        output.accept(ModItems.NETHERITE_PIZZA_PEEL.get());

        output.accept(ModItems.PIZZA_SLICE.get());
        output.accept(ModItems.OLIVE_OIL.get());
        output.accept(ModItems.TOMATO_SAUCE.get());
        output.accept(ModItems.HOT_SAUCE.get());

        output.accept(ModBlocks.OLIVE_PLANKS.get());
        output.accept(ModBlocks.OLIVE_SAPLING.get());
        output.accept(ModBlocks.OLIVE_LOG.get());
        output.accept(ModBlocks.STRIPPED_OLIVE_LOG.get());
        output.accept(ModBlocks.STRIPPED_OLIVE_WOOD.get());
        output.accept(ModBlocks.OLIVE_WOOD.get());
        output.accept(ModBlocks.OLIVE_LEAVES.get());
        output.accept(ModBlocks.FRUIT_OLIVE_LEAVES.get());
        output.accept(ModBlocks.OLIVE_SLAB.get());
        output.accept(ModBlocks.OLIVE_PRESSURE_PLATE.get());
        output.accept(ModBlocks.OLIVE_FENCE.get());
        output.accept(ModBlocks.OLIVE_TRAPDOOR.get());
        output.accept(ModBlocks.OLIVE_FENCE_GATE.get());
        output.accept(ModBlocks.OLIVE_BUTTON.get());
        output.accept(ModBlocks.OLIVE_STAIRS.get());
        output.accept(ModBlocks.OLIVE_DOOR.get());
        output.accept(ModBlocks.OLIVE_BOOKSHELF.get());

        output.accept(ModItems.BROCCOLI.get());
        output.accept(ModItems.CORN.get());
        output.accept(ModItems.CUCUMBER.get());
        output.accept(ModItems.ONION.get());
        output.accept(ModItems.PEPPER.get());
        output.accept(ModItems.TOMATO.get());
        output.accept(ModItems.PINEAPPLE.get());
        output.accept(ModItems.OLIVE.get());

        output.accept(ModItems.CUCUMBER_SLICE.get());
        output.accept(ModItems.ONION_SLICE.get());
        output.accept(ModItems.PEPPER_SLICE.get());
        output.accept(ModItems.PINEAPPLE_SLICE.get());
        output.accept(ModItems.TOMATO_SLICE.get());
        output.accept(ModItems.MUSHROOM_SLICE.get());
        output.accept(ModItems.HAM.get());
        output.accept(ModItems.WING.get());
        output.accept(ModItems.COOKED_WING.get());
        output.accept(ModItems.FLOUR.get());
        output.accept(ModItems.CORN_FLOUR.get());

        output.accept(ModBlocks.CHEESE_BLOCK.get());
        output.accept(ModItems.CHEESE.get());
        output.accept(ModItems.BROCCOLI_SEEDS.get());
        output.accept(ModItems.CUCUMBER_SEEDS.get());
        output.accept(ModItems.PEPPER_SEEDS.get());
        output.accept(ModItems.PINEAPPLE_SEEDS.get());
        output.accept(ModItems.TOMATO_SEEDS.get());
    }
}