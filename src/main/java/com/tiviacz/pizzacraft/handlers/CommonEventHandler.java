package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.common.SeedsHarvestingModifier;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEventHandler
{
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
    {
        event.getRegistry().register(new SeedsHarvestingModifier.SeedsHarvestingSerializer().setRegistryName(new ResourceLocation(PizzaCraft.MODID,"seeds_harvesting")));
    }

    @SubscribeEvent
    public static void registerBlockItemColors(ColorHandlerEvent.Item event)
    {
        BlockColors blockColors = event.getBlockColors();
        ItemColors itemColors = event.getItemColors();

        blockColors.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefault(), ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());

        itemColors.register((stack, tintIndex) -> {
              BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
              return blockColors.getColor(BlockState, null, null, tintIndex); }, ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());
    }
}