package com.tiviacz.pizzacraft.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.worldgen.biomemods.TreeModifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers
{
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, PizzaCraft.MODID);

    public static final RegistryObject<Codec<TreeModifier>> TREE_MODIFIER = BIOME_MODIFIERS.register("tree_modifier", () -> RecordCodecBuilder.create(c ->
            c.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(TreeModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(TreeModifier::feature)
            ).apply(c, TreeModifier::new)));
}
