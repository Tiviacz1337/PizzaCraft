    @SubscribeEvent
    public static void registerBlockItemColors(ColorHandlerEvent.Item event)
    {
        BlockColors blockColors = event.getBlockColors();
        ItemColors itemColors = event.getItemColors();

        blockColors.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefault(), ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());

        itemColors.register((stack, tintIndex) -> {
              BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
              return blockColors.getColor(BlockState, null, null, tintIndex); }, ModBlocks.OLIVE_LEAVES.get(), ModBlocks.FRUIT_OLIVE_LEAVES.get());
    }
}