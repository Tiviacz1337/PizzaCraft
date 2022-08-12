package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
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


    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, PizzaCraft.MODID);

    public static final RegistryObject<RecipeType<ChoppingRecipe>> CHOPPING_RECIPE_TYPE = RECIPE_TYPES.register(ChoppingRecipe.Type.ID, () -> ChoppingRecipe.Type.CHOPPING_BOARD_RECIPE_TYPE);
    public static final RegistryObject<RecipeType<MortarRecipe>> MORTAR_RECIPE_TYPE = RECIPE_TYPES.register(MortarRecipe.Type.ID, () -> MortarRecipe.Type.MORTAR_AND_PESTLE_RECIPE_TYPE);
    public static final RegistryObject<RecipeType<CrushingRecipe>> CRUSHING_RECIPE_TYPE = RECIPE_TYPES.register(CrushingRecipe.Type.ID, () -> CrushingRecipe.Type.CRUSHING_RECIPE_TYPE);
}
