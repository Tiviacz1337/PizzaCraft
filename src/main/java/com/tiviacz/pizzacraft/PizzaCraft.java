package com.tiviacz.pizzacraft;

import com.tiviacz.pizzacraft.client.gui.ScreenPizza;
import com.tiviacz.pizzacraft.client.gui.ScreenPizzaBag;
import com.tiviacz.pizzacraft.client.renderer.BasinRenderer;
import com.tiviacz.pizzacraft.client.renderer.ChoppingBoardRenderer;
import com.tiviacz.pizzacraft.client.renderer.MortarAndPestleRenderer;
import com.tiviacz.pizzacraft.client.renderer.PizzaRenderer;
import com.tiviacz.pizzacraft.init.*;
import com.tiviacz.pizzacraft.recipes.BasinRecipeRegistry;
import com.tiviacz.pizzacraft.worldgen.TreeGenerator;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod("pizzacraft")
public class PizzaCraft
{
    public static final String MODID = "pizzacraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean curiosLoaded;

    public PizzaCraft()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFinish);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);

        curiosLoaded = ModList.get().isLoaded("curios");
    }

    private void onEnqueueIMC(InterModEnqueueEvent event)
    {
        if(!curiosLoaded) return;
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModVanillaCompat::setup);

        ModAdvancements.register();
        TreeGenerator.setup(event);
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        //Screens
        ScreenManager.registerFactory(ModContainerTypes.PIZZA.get(), ScreenPizza::new);
        ScreenManager.registerFactory(ModContainerTypes.PIZZA_BAG.get(), ScreenPizzaBag::new);
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
        RenderTypeLookup.setRenderLayer(ModBlocks.RED_PIZZA_BAG.get(), RenderType.getCutout());

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
