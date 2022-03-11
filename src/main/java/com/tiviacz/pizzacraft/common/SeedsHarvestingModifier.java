package com.tiviacz.pizzacraft.common;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class SeedsHarvestingModifier
{
    public static class SeedsHarvestingSerializer extends GlobalLootModifierSerializer<SeedsModifier>
    {
        @Override
        public SeedsHarvestingModifier.SeedsModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
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
      /*  private final List<ItemStack> possibleDrops = Arrays.asList(
            ModItems.BROCCOLI_SEEDS.get().getDefaultInstance(),
            ModItems.CUCUMBER_SEEDS.get().getDefaultInstance(),
            ModItems.PEPPER_SEEDS.get().getDefaultInstance(),
            ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance(),
            ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
            ModItems.ONION.get().getDefaultInstance(),
            ModItems.CORN.get().getDefaultInstance()); */

        protected SeedsModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
        {
            if(PizzaCraftConfig.dropOliveFromJungleLeaves && context.getParamOrNull(LootParameters.BLOCK_STATE).getBlock() == Blocks.JUNGLE_LEAVES)
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
    }
}