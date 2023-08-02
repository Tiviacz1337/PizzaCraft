package com.tiviacz.pizzacraft;

import com.tiviacz.pizzacraft.client.gui.ScreenPizza;
import com.tiviacz.pizzacraft.client.gui.ScreenPizzaBag;
import com.tiviacz.pizzacraft.client.gui.ScreenPizzaStation;
import com.tiviacz.pizzacraft.client.renderer.BasinRenderer;
import com.tiviacz.pizzacraft.client.renderer.ChoppingBoardRenderer;
import com.tiviacz.pizzacraft.client.renderer.PizzaRenderer;
import com.tiviacz.pizzacraft.compat.curios.PizzaBagCurioRenderer;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
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
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod("pizzacraft")
public class PizzaCraft
{
    public static final String MODID = "pizzacraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean curiosLoaded;

    public PizzaCraft()
    {
        PizzaCraftConfig.register(ModLoadingContext.get());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onFinish);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        ModPlacedFeatures.PLACED_FEATURES.register(modEventBus);
        ModConfiguredFeatures.CONFIGURED_FEATURES.register(modEventBus);

        curiosLoaded = ModList.get().isLoaded("curios");
    }

    private void onEnqueueIMC(InterModEnqueueEvent event)
    {
        if(!curiosLoaded) return;
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModAdvancements.register();
            ModVanillaCompat.setup();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        //Screens
        MenuScreens.register(ModMenuTypes.PIZZA.get(), ScreenPizza::new);
        MenuScreens.register(ModMenuTypes.PIZZA_STATION.get(), ScreenPizzaStation::new);
        MenuScreens.register(ModMenuTypes.PIZZA_BAG.get(), ScreenPizzaBag::new);

        //BlockEntityRenderers
        BlockEntityRenderers.register(ModBlockEntityTypes.CHOPPING_BOARD.get(), ChoppingBoardRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.BASIN.get(), BasinRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.PIZZA.get(), PizzaRenderer::new);

        //RenderTypes
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PIZZA.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAW_PIZZA.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OVEN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_PIZZA_BAG.get(), RenderType.cutout());

        //Trees
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FRUIT_OLIVE_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_TRAPDOOR.get(), RenderType.cutout());

        //Crops
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BROCCOLI.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORNS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CUCUMBERS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ONIONS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEPPERS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINEAPPLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOMATOES.get(), RenderType.cutout());

        if(curiosLoaded)
        {
           registerCurioRenderer();
        }
    }

    private void registerCurioRenderer()
    {
        CuriosRendererRegistry.register(ModItems.RED_PIZZA_BAG.get(), PizzaBagCurioRenderer::new);
    }

    private void onFinish(final FMLLoadCompleteEvent event)
    {
        PizzaLayers.setMaps();
    }
}