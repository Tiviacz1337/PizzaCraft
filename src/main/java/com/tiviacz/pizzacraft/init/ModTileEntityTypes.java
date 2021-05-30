package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PizzaCraft.MODID);

    public static final RegistryObject<TileEntityType<ChoppingBoardTileEntity>> CHOPPING_BOARD = TILE_ENTITY_TYPES.register("chopping_board",
            () -> TileEntityType.Builder.create(ChoppingBoardTileEntity::new,
                    ModBlocks.OAK_CHOPPING_BOARD.get(),
                    ModBlocks.BIRCH_CHOPPING_BOARD.get(),
                    ModBlocks.SPRUCE_CHOPPING_BOARD.get(),
                    ModBlocks.JUNGLE_CHOPPING_BOARD.get(),
                    ModBlocks.ACACIA_CHOPPING_BOARD.get(),
                    ModBlocks.DARK_OAK_CHOPPING_BOARD.get(),
                    ModBlocks.CRIMSON_CHOPPING_BOARD.get(),
                    ModBlocks.WARPED_CHOPPING_BOARD.get(),
                    ModBlocks.OLIVE_CHOPPING_BOARD.get()).build(null));

    public static final RegistryObject<TileEntityType<PizzaBoardTileEntity>> PIZZA_BOARD = TILE_ENTITY_TYPES.register("pizza_board",
            () -> TileEntityType.Builder.create(PizzaBoardTileEntity::new,
                    ModBlocks.TOMATOES.get()).build(null));

 /*   public static final RegistryObject<TileEntityType<PizzaTileEntity>> PIZZA = TILE_ENTITY_TYPES.register("pizza",
            () -> TileEntityType.Builder.create(PizzaTileEntity::new, ModBlocks.PIZZA.get()).build(null));
 */
    public static final RegistryObject<TileEntityType<PizzaTileEntity>> PIZZA = TILE_ENTITY_TYPES.register("pizza",
            () -> TileEntityType.Builder.create(PizzaTileEntity::new, ModBlocks.RAW_PIZZA.get(), ModBlocks.PIZZA.get()).build(null));

    public static final RegistryObject<TileEntityType<MortarAndPestleTileEntity>> MORTAR_AND_PESTLE = TILE_ENTITY_TYPES.register("mortar_and_pestle",
            () -> TileEntityType.Builder.create(MortarAndPestleTileEntity::new, ModBlocks.MORTAR_AND_PESTLE.get()).build(null));

    public static final RegistryObject<TileEntityType<BasinTileEntity>> BASIN = TILE_ENTITY_TYPES.register("basin",
            () -> TileEntityType.Builder.create(BasinTileEntity::new,
                    ModBlocks.GRANITE_BASIN.get(),
                    ModBlocks.DIORITE_BASIN.get(),
                    ModBlocks.ANDESITE_BASIN.get(),
                    ModBlocks.BASALT_BASIN.get(),
                    ModBlocks.BLACKSTONE_BASIN.get()).build(null));

    //public static final RegistryObject<TileEntityType<OvenTileEntity>> OVEN = TILE_ENTITY_TYPES.register("oven",
    //        () -> TileEntityType.Builder.create(OvenTileEntity::new, ModBlocks.OVEN.get()).build(null));
}