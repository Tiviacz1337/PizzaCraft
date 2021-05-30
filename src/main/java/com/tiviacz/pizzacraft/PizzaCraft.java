package com.tiviacz.pizzacraft;

import com.tiviacz.pizzacraft.client.gui.ScreenPizza;
import com.tiviacz.pizzacraft.client.renderer.BasinRenderer;
import com.tiviacz.pizzacraft.client.renderer.ChoppingBoardRenderer;
import com.tiviacz.pizzacraft.client.renderer.MortarAndPestleRenderer;
import com.tiviacz.pizzacraft.client.renderer.PizzaRenderer;
import com.tiviacz.pizzacraft.init.*;
import com.tiviacz.pizzacraft.recipes.BasinRecipeRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("pizzacraft")
public class PizzaCraft
{
    public static final String MODID = "pizzacraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public PizzaCraft()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFinish);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModAdvancements.register();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        //Screens
        ScreenManager.registerFactory(ModContainerTypes.PIZZA.get(), ScreenPizza::new);
        //ScreenManager.registerFactory(ModContainerTypes.OVEN.get(), ScreenOven::new);

        //TESRs
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.CHOPPING_BOARD.get(), ChoppingBoardRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.MORTAR_AND_PESTLE.get(), MortarAndPestleRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.BASIN.get(), BasinRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.PIZZA.get(), PizzaRenderer::new);

        //RenderTypes
        RenderTypeLookup.setRenderLayer(ModBlocks.PIZZA.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.RAW_PIZZA.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.OVEN.get(), RenderType.getCutout());
        //Trees
        RenderTypeLookup.setRenderLayer(ModBlocks.OLIVE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.OLIVE_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.FRUIT_OLIVE_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.OLIVE_TRAPDOOR.get(), RenderType.getCutout());

        //Crops
        RenderTypeLookup.setRenderLayer(ModBlocks.BROCCOLI.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORNS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CUCUMBERS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.ONIONS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.PEPPERS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.PINEAPPLE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.TOMATOES.get(), RenderType.getCutout());
    }

    private void onFinish(final FMLLoadCompleteEvent event)
    {
        BasinRecipeRegistry.addRecipesToRegistry();
        PizzaLayers.setMaps();
    }
}
