package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipe;
import com.tiviacz.pizzacraft.recipes.chopping.ChoppingRecipeSerializer;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipe;
import com.tiviacz.pizzacraft.recipes.mortar.MortarRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes
{
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PizzaCraft.MODID);

    public static final IRecipeType<ChoppingRecipe> CHOPPING = IRecipeType.register(new ResourceLocation(PizzaCraft.MODID, "chopping").toString());
    public static final IRecipeType<MortarRecipe> MORTAR = IRecipeType.register(new ResourceLocation(PizzaCraft.MODID, "mortar").toString());

    public static final RegistryObject<IRecipeSerializer<?>> CHOPPING_RECIPE_SERIALIZER = SERIALIZERS.register(ChoppingRecipeSerializer.ID.getPath(), () -> ChoppingRecipeSerializer.INSTANCE);
    public static final RegistryObject<IRecipeSerializer<?>> MORTAR_RECIPE_SERIALIZER = SERIALIZERS.register(MortarRecipeSerializer.ID.getPath(), () -> MortarRecipeSerializer.INSTANCE);
}
