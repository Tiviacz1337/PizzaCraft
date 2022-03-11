package com.tiviacz.pizzacraft.advancement;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class MortarAndPestleTrigger extends AbstractCriterionTrigger<MortarAndPestleTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "use_mortar_and_pestle");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, Instance::test);
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.AndPredicate player, ConditionArrayParser conditionsParser) {
        return new MortarAndPestleTrigger.Instance(player);
    }

    public static class Instance extends CriterionInstance
    {
        public Instance(EntityPredicate.AndPredicate player) {
            super(MortarAndPestleTrigger.ID, player);
        }

        public static MortarAndPestleTrigger.Instance simple() {
            return new MortarAndPestleTrigger.Instance(EntityPredicate.AndPredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}