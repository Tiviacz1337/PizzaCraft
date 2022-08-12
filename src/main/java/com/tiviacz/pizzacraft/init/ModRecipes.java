package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PizzaCraft.MODID);

    //public static final RecipeType<ChoppingRecipe> CHOPPING = RecipeType.register(new ResourceLocation(PizzaCraft.MODID, "chopping").toString());
    //public static final RecipeType<MortarRecipe> MORTAR = RecipeType.register(new ResourceLocation(PizzaCraft.MODID, "mortar").toString());
    //public static final RecipeType<CrushingRecipe> CRUSHING = RecipeType.register(new ResourceLocation(PizzaCraft.MODID, "crushing").toString());

    public static final RegistryObject<RecipeSerializer<?>> CHOPPING_RECIPE_SERIALIZER = SERIALIZERS.register(ChoppingRecipeSerializer.ID.getPath(), () -> ChoppingRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<?>> MORTAR_RECIPE_SERIALIZER = SERIALIZERS.register(MortarRecipeSerializer.ID.getPath(), () -> MortarRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<?>> CRUSHING_RECIPE_SERIALIZER = SERIALIZERS.register(CrushingRecipeSerializer.ID.getPath(), () -> CrushingRecipeSerializer.INSTANCE);
}
