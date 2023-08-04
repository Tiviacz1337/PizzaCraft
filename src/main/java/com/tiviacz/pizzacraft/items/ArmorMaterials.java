package com.tiviacz.pizzacraft.items;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ArmorMaterials implements ArmorMaterial
{
    public static final ArmorMaterials CHEF = new ArmorMaterials("chef", 5, new int[]{1, 2, 3, 1}, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);
    public static final ArmorMaterials PIZZA_DELIVERY = new ArmorMaterials("pizza_delivery", 5, new int[]{1, 2, 3, 1}, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private SoundEvent equipSound;
    private String name;
    private int durability, enchantability;
    private final int[] defense;
    private float toughness;
    private float knockbackResistance;
    private LazyLoadedValue<Ingredient> material;

    private ArmorMaterials(String name, int durability, int[] defense, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> material)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.defense = defense;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.material = new LazyLoadedValue(material);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType)
    {
        return MAX_DAMAGE_ARRAY[pType.getSlot().getIndex()] * this.durability;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType)
    {
        return this.defense[pType.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return this.material.get();
    }

    @Override
    public String getName()
    {
        return PizzaCraft.MODID + ":" + this.name;
    }

    @Override
    public float getToughness()
    {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance()
    {
        return this.knockbackResistance;
    }
}