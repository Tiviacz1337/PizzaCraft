package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.DynamicPizzaSliceModel;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.client.renderer.BasinRenderer;
import com.tiviacz.pizzacraft.client.renderer.ChefHatModel;
import com.tiviacz.pizzacraft.client.renderer.PizzaDeliveryCapModel;
import com.tiviacz.pizzacraft.client.tooltip.ClientPizzaTooltipComponent;
import com.tiviacz.pizzacraft.client.tooltip.PizzaTooltipComponent;
import com.tiviacz.pizzacraft.init.ModBlocks;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void registerTooltipComponent(RegisterClientTooltipComponentFactoriesEvent event)
    {
        event.register(PizzaTooltipComponent.class, ClientPizzaTooltipComponent::new);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterGeometryLoaders event)
    {
        event.register("pizza_slice_loader", DynamicPizzaSliceModel.Loader.INSTANCE);
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(BasinRenderer.ContentModel.CONTENT_LAYER, BasinRenderer.ContentModel::createModelData);
        event.registerLayerDefinition(BasinRenderer.SauceModel.SAUCE_LAYER, BasinRenderer.SauceModel::createModelData);
        event.registerLayerDefinition(PizzaDeliveryCapModel.CAP, PizzaDeliveryCapModel::createModelData);
        event.registerLayerDefinition(ChefHatModel.CHEF_HAT, ChefHatModel::createModelData);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event)
    {
        for(BlockState blockState : ModBlocks.PIZZA.get().getStateDefinition().getPossibleStates())
        {
            ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
            BakedModel existingModel = event.getModels().get(variantMRL);
            if(existingModel == null)
            {
                //LOGGER.warn("Did not find the expected vanilla baked model(s) for blockAltimeter in registry");
            }
            else if(existingModel instanceof PizzaBakedModel)
            {
               // LOGGER.warn("Tried to replace AltimeterBakedModel twice");
            }
            else
            {
                PizzaBakedModel customModel = new PizzaBakedModel(existingModel);
                event.getModels().put(variantMRL, customModel);
            }
        }

        for(BlockState blockState : ModBlocks.RAW_PIZZA.get().getStateDefinition().getPossibleStates())
        {
            ModelResourceLocation variantMRL = BlockModelShaper.stateToModelLocation(blockState);
            BakedModel existingModel = event.getModels().get(variantMRL);
            if(existingModel == null)
            {
                //LOGGER.warn("Did not find the expected vanilla baked model(s) for blockAltimeter in registry");
            }
            else if(existingModel instanceof PizzaBakedModel)
            {
                // LOGGER.warn("Tried to replace AltimeterBakedModel twice");
            }
            else
            {
                PizzaBakedModel customModel = new PizzaBakedModel(existingModel);
                event.getModels().put(variantMRL, customModel);
            }
        }
    }

   /* @SubscribeEvent
    public static void stitcherEventPre(TextureStitchEvent event)
    {
        if(event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS)
        {
            event..addSprite(PizzaLayers.PIZZA_SLICE);

            //Base
            event.addSprite(PizzaLayers.CHEESE_LAYER);
            event.addSprite(PizzaLayers.RAW_CHEESE_LAYER);

            //Vegetables
            event.addSprite(PizzaLayers.BROCCOLI_LAYER);
            event.addSprite(PizzaLayers.CORN_LAYER);
            event.addSprite(PizzaLayers.CUCUMBER_LAYER);
            event.addSprite(PizzaLayers.ONION_LAYER);
            event.addSprite(PizzaLayers.PEPPER_LAYER);
            event.addSprite(PizzaLayers.TOMATO_LAYER);

            event.addSprite(PizzaLayers.RAW_BROCCOLI_LAYER);
            event.addSprite(PizzaLayers.RAW_CORN_LAYER);
            event.addSprite(PizzaLayers.RAW_CUCUMBER_LAYER);
            event.addSprite(PizzaLayers.RAW_ONION_LAYER);
            event.addSprite(PizzaLayers.RAW_PEPPER_LAYER);
            event.addSprite(PizzaLayers.RAW_TOMATO_LAYER);

            event.addSprite(PizzaLayers.BROCCOLI_ITEM_LAYER);
            event.addSprite(PizzaLayers.CORN_ITEM_LAYER);
            event.addSprite(PizzaLayers.CUCUMBER_ITEM_LAYER);
            event.addSprite(PizzaLayers.ONION_ITEM_LAYER);
            event.addSprite(PizzaLayers.PEPPER_ITEM_LAYER);
            event.addSprite(PizzaLayers.TOMATO_ITEM_LAYER);

            //Fruits
            event.addSprite(PizzaLayers.PINEAPPLE_LAYER);
            event.addSprite(PizzaLayers.OLIVE_LAYER);

            event.addSprite(PizzaLayers.RAW_PINEAPPLE_LAYER);
            event.addSprite(PizzaLayers.RAW_OLIVE_LAYER);

            event.addSprite(PizzaLayers.PINEAPPLE_ITEM_LAYER);
            event.addSprite(PizzaLayers.OLIVE_ITEM_LAYER);

            //Mushrooms
            event.addSprite(PizzaLayers.MUSHROOM_LAYER);

            event.addSprite(PizzaLayers.RAW_MUSHROOM_LAYER);

            event.addSprite(PizzaLayers.MUSHROOM_ITEM_LAYER);

            //Meats
            event.addSprite(PizzaLayers.HAM_LAYER);
            event.addSprite(PizzaLayers.BEEF_LAYER);
            event.addSprite(PizzaLayers.CHICKEN_LAYER);

            event.addSprite(PizzaLayers.RAW_HAM_LAYER);
            event.addSprite(PizzaLayers.RAW_BEEF_LAYER);
            event.addSprite(PizzaLayers.RAW_CHICKEN_LAYER);

            event.addSprite(PizzaLayers.HAM_ITEM_LAYER);
            event.addSprite(PizzaLayers.BEEF_ITEM_LAYER);
            event.addSprite(PizzaLayers.CHICKEN_ITEM_LAYER);

            //Sauces
            event.addSprite(PizzaLayers.TOMATO_SAUCE_LAYER);
            event.addSprite(PizzaLayers.TOMATO_SAUCE_ITEM_LAYER);
        }
    } */
}