package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PizzaCraft.MODID);

    public static final RegistryObject<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = BLOCK_ENTITY_TYPES.register("chopping_board",
            () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new,
                    ModBlocks.OAK_CHOPPING_BOARD.get(),
                    ModBlocks.BIRCH_CHOPPING_BOARD.get(),
                    ModBlocks.SPRUCE_CHOPPING_BOARD.get(),
                    ModBlocks.JUNGLE_CHOPPING_BOARD.get(),
                    ModBlocks.ACACIA_CHOPPING_BOARD.get(),
                    ModBlocks.DARK_OAK_CHOPPING_BOARD.get(),
                    ModBlocks.CRIMSON_CHOPPING_BOARD.get(),
                    ModBlocks.WARPED_CHOPPING_BOARD.get(),
                    ModBlocks.OLIVE_CHOPPING_BOARD.get()).build(null));

    public static final RegistryObject<BlockEntityType<PizzaBoardBlockEntity>> PIZZA_BOARD = BLOCK_ENTITY_TYPES.register("pizza_board",
            () -> BlockEntityType.Builder.of(PizzaBoardBlockEntity::new,
                    ModBlocks.TOMATOES.get()).build(null));

 /*   public static final RegistryObject<TileEntityType<PizzaTileEntity>> PIZZA = TILE_ENTITY_TYPES.register("pizza",
            () -> TileEntityType.Builder.create(PizzaTileEntity::new, ModBlocks.PIZZA.get()).build(null));
 */
    public static final RegistryObject<BlockEntityType<PizzaBlockEntity>> PIZZA = BLOCK_ENTITY_TYPES.register("pizza",
            () -> BlockEntityType.Builder.of(PizzaBlockEntity::new, ModBlocks.RAW_PIZZA.get(), ModBlocks.PIZZA.get()).build(null));

    public static final RegistryObject<BlockEntityType<MortarAndPestleBlockEntity>> MORTAR_AND_PESTLE = BLOCK_ENTITY_TYPES.register("mortar_and_pestle",
            () -> BlockEntityType.Builder.of(MortarAndPestleBlockEntity::new, ModBlocks.MORTAR_AND_PESTLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<BasinBlockEntity>> BASIN = BLOCK_ENTITY_TYPES.register("basin",
            () -> BlockEntityType.Builder.of(BasinBlockEntity::new,
                    ModBlocks.GRANITE_BASIN.get(),
                    ModBlocks.DIORITE_BASIN.get(),
                    ModBlocks.ANDESITE_BASIN.get(),
                    ModBlocks.BASALT_BASIN.get(),
                    ModBlocks.BLACKSTONE_BASIN.get()).build(null));

    public static final RegistryObject<BlockEntityType<PizzaBagBlockEntity>> PIZZA_BAG = BLOCK_ENTITY_TYPES.register("pizza_bag",
            () -> BlockEntityType.Builder.of(PizzaBagBlockEntity::new,
                    ModBlocks.RED_PIZZA_BAG.get()).build(null));
    //public static final RegistryObject<TileEntityType<OvenTileEntity>> OVEN = TILE_ENTITY_TYPES.register("oven",
    //        () -> TileEntityType.Builder.create(OvenTileEntity::new, ModBlocks.OVEN.get()).build(null));
}