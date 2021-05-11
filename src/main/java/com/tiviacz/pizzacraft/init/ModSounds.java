package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PizzaCraft.MODID);

    public static final RegistryObject<SoundEvent> SIZZLING_SOUND = SOUND_EVENTS.register("sizzling", () -> new SoundEvent(new ResourceLocation(PizzaCraft.MODID, "pizza.sizzling")));
}
