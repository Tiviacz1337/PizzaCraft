package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.common.SeedsHarvestingModifier;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockItemColors(ColorHandlerEvent.Item event)
    {
        BlockColors blockColors = event.getBlockColors();
        ItemColors itemColors = event.getItemColors();

        blockColors.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor(), ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());

        itemColors.register((stack, tintIndex) -> {
              BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
              return blockColors.getColor(BlockState, null, null, tintIndex); }, ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        Registry.register(Registry.RECIPE_TYPE, ChoppingRecipe.Type.ID, ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, CrushingRecipe.Type.ID, CrushingRecipe.Type.CRUSHING_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, MortarRecipe.Type.ID, MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE);
    }
}