package com.tiviacz.pizzacraft.init;

import com.mojang.serialization.Codec;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.common.GrassLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, PizzaCraft.MODID);

    public static final RegistryObject<Codec<GrassLootModifier>> GRASS_LOOT_MODIFIER = LOOT_MODIFIERS.register("seeds_harvesting", () -> GrassLootModifier.CODEC);
}