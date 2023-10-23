package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PizzaCraft.MODID);

    public static final RegistryObject<SoundEvent> SIZZLING_SOUND = SOUND_EVENTS.register("sizzling", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PizzaCraft.MODID, "pizza.sizzling")));
}
