package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.DynamicPizzaSliceModel;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(PizzaCraft.MODID, "pizza_slice_loader"), DynamicPizzaSliceModel.Loader.INSTANCE);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event)
    {
        for(BlockState blockState : ModBlocks.PIZZA.get().getStateContainer().getValidStates())
        {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
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
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for(BlockState blockState : ModBlocks.RAW_PIZZA.get().getStateContainer().getValidStates())
        {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
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
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }
    }

    @SubscribeEvent
    public static void stitcherEventPre(TextureStitchEvent.Pre event)
    {
        if(event.getMap().getTextureLocation() == PlayerContainer.LOCATION_BLOCKS_TEXTURE)
        {
            event.addSprite(PizzaLayers.PIZZA_SLICE);

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
    }
}