package com.tiviacz.pizzacraft.advancement;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class ChoppingBoardTrigger extends AbstractCriterionTrigger<ChoppingBoardTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "use_chopping_board");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.triggerListeners(player, Instance::test);
    }

    @Override
    protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate player, ConditionArrayParser conditionsParser) {
        return new ChoppingBoardTrigger.Instance(player);
    }

    public static class Instance extends CriterionInstance
    {
        public Instance(EntityPredicate.AndPredicate player) {
            super(ChoppingBoardTrigger.ID, player);
        }

        public static ChoppingBoardTrigger.Instance simple() {
            return new ChoppingBoardTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND);
        }

        public boolean test() {
            return true;
        }
    }
}