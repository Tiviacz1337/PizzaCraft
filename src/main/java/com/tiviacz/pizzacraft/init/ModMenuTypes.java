package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.container.PizzaBagMenu;
import com.tiviacz.pizzacraft.container.PizzaMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, PizzaCraft.MODID);

    public static final RegistryObject<MenuType<PizzaMenu>> PIZZA = MENU_TYPES.register("pizza", () -> IForgeContainerType.create(PizzaMenu::new));
    public static final RegistryObject<MenuType<PizzaBagMenu>> PIZZA_BAG = MENU_TYPES.register("pizza_bag", () -> IForgeContainerType.create(PizzaBagMenu::new));
    //public static final RegistryObject<ContainerType<OvenContainer>> OVEN = CONTAINER_TYPES.register("oven", () -> IForgeContainerType.create(OvenContainer::new));
}