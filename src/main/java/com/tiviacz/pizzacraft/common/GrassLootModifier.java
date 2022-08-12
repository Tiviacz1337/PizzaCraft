package com.tiviacz.pizzacraft.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

public class GrassLootModifier extends LootModifier
{
    public static final Codec<GrassLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, GrassLootModifier::new));

    private final ItemStack[] possibleDrops = new ItemStack[]{
            ModItems.BROCCOLI_SEEDS.get().getDefaultInstance(),
            ModItems.CUCUMBER_SEEDS.get().getDefaultInstance(),
            ModItems.PEPPER_SEEDS.get().getDefaultInstance(),
            ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance(),
            ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
            ModItems.CORN.get().getDefaultInstance(),
            ModItems.ONION.get().getDefaultInstance(),
            Blocks.AIR.asItem().getDefaultInstance()};
      /*  private final List<ItemStack> possibleDrops = Arrays.asList(
            ModItems.BROCCOLI_SEEDS.get().getDefaultInstance(),
            ModItems.CUCUMBER_SEEDS.get().getDefaultInstance(),
            ModItems.PEPPER_SEEDS.get().getDefaultInstance(),
            ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance(),
            ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
            ModItems.ONION.get().getDefaultInstance(),
            ModItems.CORN.get().getDefaultInstance()); */

    public GrassLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if(PizzaCraftConfig.dropOliveFromJungleLeaves && context.getParamOrNull(LootContextParams.BLOCK_STATE).getBlock() == Blocks.JUNGLE_LEAVES)
        {
            generatedLoot.add(ModItems.OLIVE.get().getDefaultInstance());
        }
        else if(PizzaCraftConfig.seedDrops)
        {
            //generatedLoot.add(possibleDrops.get(context.getRandom().nextInt(possibleDrops.size() - 1)));
            generatedLoot.add(possibleDrops[context.getRandom().nextInt(possibleDrops.length)]);
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}