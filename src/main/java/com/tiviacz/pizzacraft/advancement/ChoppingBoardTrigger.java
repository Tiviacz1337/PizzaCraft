package com.tiviacz.pizzacraft.advancement;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ChoppingBoardTrigger extends SimpleCriterionTrigger<ChoppingBoardTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "use_chopping_board");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Instance::test);
    }

    @Override
    protected Instance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext)
    {
        return new ChoppingBoardTrigger.Instance(pPredicate);
    }

    public static class Instance extends AbstractCriterionTriggerInstance
    {
        public Instance(ContextAwarePredicate player) {
            super(ChoppingBoardTrigger.ID, player);
        }

        public static ChoppingBoardTrigger.Instance simple() {
            return new ChoppingBoardTrigger.Instance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}