package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PizzaCraft.MODID);

    public static final RegistryObject<RecipeSerializer<?>> CHOPPING_RECIPE_SERIALIZER = SERIALIZERS.register(ChoppingRecipeSerializer.ID.getPath(), () -> ChoppingRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<?>> MORTAR_RECIPE_SERIALIZER = SERIALIZERS.register(MortarRecipeSerializer.ID.getPath(), () -> MortarRecipeSerializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<?>> CRUSHING_RECIPE_SERIALIZER = SERIALIZERS.register(CrushingRecipeSerializer.ID.getPath(), () -> CrushingRecipeSerializer.INSTANCE);
}
