package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs
{
    public static CreativeModeTab PIZZACRAFT;

    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event)
    {
        PIZZACRAFT = event.registerCreativeModeTab(new ResourceLocation(PizzaCraft.MODID, ""), builder -> builder
                .icon(ModCreativeTabs::createIcon)
                        .title(Component.translatable("itemGroup.pizzacraft")).build());
    }

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

    public static void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if(event.getTab() == PIZZACRAFT)
        {
            event.accept(ModBlocks.PIZZA);
            event.accept(ModBlocks.RAW_PIZZA);

            event.accept(ModBlocks.DOUGH);
            event.accept(ModBlocks.PIZZA_STATION);
            event.accept(ModBlocks.OVEN);

            event.accept(ModBlocks.OAK_CHOPPING_BOARD);
            event.accept(ModBlocks.BIRCH_CHOPPING_BOARD);
            event.accept(ModBlocks.SPRUCE_CHOPPING_BOARD);
            event.accept(ModBlocks.JUNGLE_CHOPPING_BOARD);
            event.accept(ModBlocks.ACACIA_CHOPPING_BOARD);
            event.accept(ModBlocks.DARK_OAK_CHOPPING_BOARD);
            event.accept(ModBlocks.CRIMSON_CHOPPING_BOARD);
            event.accept(ModBlocks.WARPED_CHOPPING_BOARD);
            event.accept(ModBlocks.OLIVE_CHOPPING_BOARD);

            event.accept(ModBlocks.GRANITE_BASIN);
            event.accept(ModBlocks.DIORITE_BASIN);
            event.accept(ModBlocks.ANDESITE_BASIN);
            event.accept(ModBlocks.BASALT_BASIN);
            event.accept(ModBlocks.BLACKSTONE_BASIN);

            event.accept(ModItems.CHEF_HAT);
            event.accept(ModItems.CHEF_SHIRT);
            event.accept(ModItems.CHEF_LEGGINGS);
            event.accept(ModItems.CHEF_BOOTS);

            event.accept(ModItems.PIZZA_DELIVERY_CAP);
            event.accept(ModItems.PIZZA_DELIVERY_SHIRT);
            event.accept(ModItems.PIZZA_DELIVERY_LEGGINGS);
            event.accept(ModItems.PIZZA_DELIVERY_BOOTS);
            event.accept(ModBlocks.RED_PIZZA_BAG);

            event.accept(ModItems.ROLLING_PIN);

            event.accept(ModItems.STONE_KNIFE);
            event.accept(ModItems.GOLDEN_KNIFE);
            event.accept(ModItems.IRON_KNIFE);
            event.accept(ModItems.DIAMOND_KNIFE);
            event.accept(ModItems.NETHERITE_KNIFE);

            event.accept(ModItems.STONE_PIZZA_PEEL);
            event.accept(ModItems.GOLDEN_PIZZA_PEEL);
            event.accept(ModItems.IRON_PIZZA_PEEL);
            event.accept(ModItems.DIAMOND_PIZZA_PEEL);
            event.accept(ModItems.NETHERITE_PIZZA_PEEL);

            event.accept(ModItems.PIZZA_SLICE);
            event.accept(ModItems.OLIVE_OIL);
            event.accept(ModItems.TOMATO_SAUCE);
            event.accept(ModItems.HOT_SAUCE);

            event.accept(ModBlocks.OLIVE_PLANKS);
            event.accept(ModBlocks.OLIVE_SAPLING);
            event.accept(ModBlocks.OLIVE_LOG);
            event.accept(ModBlocks.STRIPPED_OLIVE_LOG);
            event.accept(ModBlocks.STRIPPED_OLIVE_WOOD);
            event.accept(ModBlocks.OLIVE_WOOD);
            event.accept(ModBlocks.OLIVE_LEAVES);
            event.accept(ModBlocks.FRUIT_OLIVE_LEAVES);
            event.accept(ModBlocks.OLIVE_SLAB);
            event.accept(ModBlocks.OLIVE_PRESSURE_PLATE);
            event.accept(ModBlocks.OLIVE_FENCE);
            event.accept(ModBlocks.OLIVE_TRAPDOOR);
            event.accept(ModBlocks.OLIVE_FENCE_GATE);
            event.accept(ModBlocks.OLIVE_BUTTON);
            event.accept(ModBlocks.OLIVE_STAIRS);
            event.accept(ModBlocks.OLIVE_DOOR);
            event.accept(ModBlocks.OLIVE_BOOKSHELF);

            event.accept(ModItems.BROCCOLI);
            event.accept(ModItems.CORN);
            event.accept(ModItems.CUCUMBER);
            event.accept(ModItems.ONION);
            event.accept(ModItems.PEPPER);
            event.accept(ModItems.TOMATO);
            event.accept(ModItems.PINEAPPLE);
            event.accept(ModItems.OLIVE);

            event.accept(ModItems.CUCUMBER_SLICE);
            event.accept(ModItems.ONION_SLICE);
            event.accept(ModItems.PEPPER_SLICE);
            event.accept(ModItems.PINEAPPLE_SLICE);
            event.accept(ModItems.TOMATO_SLICE);
            event.accept(ModItems.MUSHROOM_SLICE);
            event.accept(ModItems.HAM);
            event.accept(ModItems.WING);
            event.accept(ModItems.COOKED_WING);
            event.accept(ModItems.FLOUR);
            event.accept(ModItems.CORN_FLOUR);

            event.accept(ModBlocks.CHEESE_BLOCK);
            event.accept(ModItems.CHEESE);
            event.accept(ModItems.BROCCOLI_SEEDS);
            event.accept(ModItems.CUCUMBER_SEEDS);
            event.accept(ModItems.PEPPER_SEEDS);
            event.accept(ModItems.PINEAPPLE_SEEDS);
            event.accept(ModItems.TOMATO_SEEDS);
        }
    }
}