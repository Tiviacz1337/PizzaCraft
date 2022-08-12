package com.tiviacz.pizzacraft.advancement;

import com.google.gson.JsonObject;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MortarAndPestleTrigger extends SimpleCriterionTrigger<MortarAndPestleTrigger.Instance>
{
    private static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "use_mortar_and_pestle");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Instance::test);
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext conditionsParser) {
        return new MortarAndPestleTrigger.Instance(player);
    }

    public static class Instance extends AbstractCriterionTriggerInstance
    {
        public Instance(EntityPredicate.Composite player) {
            super(MortarAndPestleTrigger.ID, player);
        }

        public static MortarAndPestleTrigger.Instance simple() {
            return new MortarAndPestleTrigger.Instance(EntityPredicate.Composite.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}