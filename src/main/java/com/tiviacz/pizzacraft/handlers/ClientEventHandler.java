package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler
{

    //Cooked
   // public static final ResourceLocation COOKED_CHEESE_LAYER = new ResourceLocation(PizzaCraft.MODID, "block/cooked_cheese_layer");

 /*   @SubscribeEvent
    public static void onModelBakery(ModelBakeEvent event)
    {
        IUnbakedModel unbakedModel = event.getModelLoader().getModelOrLogError(CRUST_MODEL, "Didn't fetch the model!");
        IBakedModel bakedModel = unbakedModel.bakeModel(event.getModelLoader(), RenderMaterial::getSprite, SimpleModelTransform.IDENTITY, CRUST_MODEL);
        event.getModelRegistry().put(CRUST_MODEL, bakedModel);
        //IBakedModel bakedModel = unbakedModel.bakeModel(Minecraft.getInstance().getModelManager().getModel())
        //IBakedModel bakedModel = event.getModelManager().getModel(CRUST_MODEL);
        //event.getModelRegistry().put();
    } */

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

/*    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event)
    {
        ModelLoader.addSpecialModel(CRUST_MODEL);
    } */

    @SubscribeEvent
    public static void stitcherEventPre(TextureStitchEvent.Pre event)
    {
        if(event.getMap().getTextureLocation() == PlayerContainer.LOCATION_BLOCKS_TEXTURE)
        {
            event.addSprite(PizzaLayers.CHEESE_LAYER);
            event.addSprite(PizzaLayers.RAW_CHEESE_LAYER);

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

            event.addSprite(PizzaLayers.PINEAPPLE_LAYER);
            event.addSprite(PizzaLayers.OLIVE_LAYER);

            event.addSprite(PizzaLayers.RAW_PINEAPPLE_LAYER);
            event.addSprite(PizzaLayers.RAW_OLIVE_LAYER);

            event.addSprite(PizzaLayers.MUSHROOM_LAYER);
            event.addSprite(PizzaLayers.RAW_MUSHROOM_LAYER);

            event.addSprite(PizzaLayers.HAM_LAYER);
            event.addSprite(PizzaLayers.BEEF_LAYER);
            event.addSprite(PizzaLayers.CHICKEN_LAYER);

            event.addSprite(PizzaLayers.RAW_HAM_LAYER);
            event.addSprite(PizzaLayers.RAW_BEEF_LAYER);
            event.addSprite(PizzaLayers.RAW_CHICKEN_LAYER);

            event.addSprite(PizzaLayers.TOMATO_SAUCE_LAYER);
        }
    }
}