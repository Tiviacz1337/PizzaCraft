package com.tiviacz.pizzacraft.common;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class SeedsHarvestingModifier
{
    public static class SeedsHarvestingSerializer extends GlobalLootModifierSerializer<SeedsModifier>
    {
        @Override
        public SeedsHarvestingModifier.SeedsModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            return new SeedsHarvestingModifier.SeedsModifier(ailootcondition);
        }

        @Override
        public JsonObject write(SeedsModifier instance) {
            return new JsonObject();
        }
    }

    public static class SeedsModifier extends LootModifier
    {
        private final ItemStack[] possibleDrops = new ItemStack[]{
                ModItems.BROCCOLI_SEEDS.get().getDefaultInstance(),
                ModItems.CUCUMBER_SEEDS.get().getDefaultInstance(),
                ModItems.PEPPER_SEEDS.get().getDefaultInstance(),
                ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance(),
                ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
                ModItems.CORN.get().getDefaultInstance(),
                ModItems.ONION.get().getDefaultInstance(),
                Blocks.AIR.asItem().getDefaultInstance()};

        protected SeedsModifier(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
        {
            if(PizzaCraftConfig.dropOliveFromJungleLeaves && context.getParamOrNull(LootContextParams.BLOCK_STATE).getBlock() == Blocks.JUNGLE_LEAVES)
            {
                generatedLoot.add(ModItems.OLIVE.get().getDefaultInstance());
            }
            else if(PizzaCraftConfig.seedDrops)
            {
                generatedLoot.add(possibleDrops[context.getRandom().nextInt(possibleDrops.length)]);
            }
            return generatedLoot;
        }
    }
}