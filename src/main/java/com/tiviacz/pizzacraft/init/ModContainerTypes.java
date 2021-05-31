package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.container.PizzaBagContainer;
import com.tiviacz.pizzacraft.container.PizzaContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes
{
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, PizzaCraft.MODID);

    public static final RegistryObject<ContainerType<PizzaContainer>> PIZZA = CONTAINER_TYPES.register("pizza", () -> IForgeContainerType.create(PizzaContainer::new));
    public static final RegistryObject<ContainerType<PizzaBagContainer>> PIZZA_BAG = CONTAINER_TYPES.register("pizza_bag", () -> IForgeContainerType.create(PizzaBagContainer::new));
    //public static final RegistryObject<ContainerType<OvenContainer>> OVEN = CONTAINER_TYPES.register("oven", () -> IForgeContainerType.create(OvenContainer::new));
}