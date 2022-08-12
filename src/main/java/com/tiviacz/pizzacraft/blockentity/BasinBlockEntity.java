package com.tiviacz.pizzacraft.blockentity;

import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.mojang.math.Vector3f;
import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.*;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class BasinBlockEntity extends BaseBlockEntity
{
    private final ItemStackHandler inventory = createHandler();
    private BasinContent content = BasinContent.AIR;
    private ItemStack squashedStack = ItemStack.EMPTY;
    private int fermentProgress = 0;
    private final int defaultFermentTime = 1200;
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String BASIN_CONTENT = "BasinContent";
    private final String SQUASHED_STACK = "SquashedStack";
    private final String FERMENT_PROGRESS = "FermentProgress";

    public BasinBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.BASIN.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.content = SauceRegistry.INSTANCE.basinContentFromString(compound.getString(BASIN_CONTENT));
        this.squashedStack = ItemStack.of(compound.getCompound(SQUASHED_STACK));
        if(this.getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK) this.fermentProgress = compound.getInt(FERMENT_PROGRESS);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        super.save(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putString(BASIN_CONTENT, content.toString());
        CompoundTag squashedStack = new CompoundTag();
        this.squashedStack.save(squashedStack);
        compound.put(SQUASHED_STACK, squashedStack);
        if(this.getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK) compound.putInt(FERMENT_PROGRESS, this.fermentProgress);
        return compound;
    }

 /*   public boolean canRender()
    {
        PlayerEntity player = getWorld().getClosestPlayer(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, 5.0D, false);

        if(player != null && getWorld() != null)
        {
            BlockRayTraceResult result = RenderUtils.getBlockRayTraceResult(player, getWorld());
            BlockPos resultPos = result.getPos();

            if(result.getType() == RayTraceResult.Type.BLOCK && player.isSneaking())
            {
                return world.getBlockState(resultPos).getBlockState() == world.getBlockState(getPos()) && world.getTileEntity(resultPos) == this;
            }
        }
        return false;
    } */

  /*  public List<ITextComponent> getTextComponentsForRenderer()
    {
        List<ITextComponent> textComponents = new ArrayList<>();

        if(getBasinContent().getContentType() == BasinContentType.EMPTY)
        {
            return textComponents;
        }
        else
        {
            textComponents.add(0, new TranslationTextComponent(getBasinContent().getTranslationKey()));
        }

        StringBuilder b = new StringBuilder();

        if(getBasinContent().getContentType() == BasinContentType.MILK || getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK || getBasinContent().getContentType() == BasinContentType.CHEESE)
        {
            int fermentSeconds = getFermentProgress() / 20;
            b.append("Ferment Progress: ").append(fermentSeconds);
        }

        else if(!getSquashedStack().isEmpty())
        {
            b.append("Squashed ").append(getSquashedStack().getDisplayName().getString()).append(": ").append(getSquashedStackCount());
        }

        textComponents.add(1, new StringTextComponent(b.toString()));
        return textComponents;
    } */

    public float getAmount()
    {
        return getSquashedStackCount() * 0.75F;
    }

    public BasinContent getBasinContent()
    {
        return this.content;
    }

    public void updateBasinContent()
    {
        if(getSquashedStackCount() <= 0)
        {
            this.squashedStack = ItemStack.EMPTY;
            this.content = BasinContent.AIR;
        }
        setChanged();
    }

    public static Map<BasinContent, ItemStack> basinContentToItemStack()
    {
        Map<BasinContent, ItemStack> map = Maps.newHashMap();
        map.put(BasinContent.AIR, ItemStack.EMPTY);
        map.put(BasinContent.MILK, ItemStack.EMPTY);
        map.put(BasinContent.FERMENTING_MILK, ItemStack.EMPTY);
        map.put(BasinContent.CHEESE, new ItemStack(ModBlocks.CHEESE_BLOCK.get()));
        map.put(BasinContent.TOMATO_SAUCE, new ItemStack(ModItems.TOMATO_SAUCE.get()));
        map.put(BasinContent.OLIVE_OIL, new ItemStack(ModItems.OLIVE_OIL.get()));
        return map;
    }

    public static Map<BasinContent, Integer> basinContentExtractSize()
    {
        Map<BasinContent, Integer> map = Maps.newHashMap();
        map.put(BasinContent.AIR, 999);
        map.put(BasinContent.MILK, 999);
        map.put(BasinContent.FERMENTING_MILK, 999);
        map.put(BasinContent.CHEESE, 1);
        map.put(BasinContent.TOMATO_SAUCE, 4);
        map.put(BasinContent.OLIVE_OIL, 4);
        return map;
    }

    public static final ResourceLocation FERMENTING_ITEMS_TAG = new ResourceLocation(PizzaCraft.MODID, "fermenting_items");

    public InteractionResult onBlockActivated(Player player, InteractionHand hand)
    {
        ItemStack itemHeld = player.getItemInHand(hand);
        BasinContentType basinContentType = getBasinContent().getContentType();
        ItemStack stackInSlot = getInventory().getStackInSlot(0);

        //If cheese, just extract
        if(basinContentType == BasinContentType.CHEESE)
        {
            level.addFreshEntity(new ItemEntity(level, getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D, new ItemStack(ModBlocks.CHEESE_BLOCK.get())));
            this.content = BasinContent.AIR;
            this.squashedStack = ItemStack.EMPTY;
            level.playSound(player, getBlockPos(), SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
            this.setChanged();
            return InteractionResult.SUCCESS;
        }
        if(hand == InteractionHand.MAIN_HAND)
        {
            if(basinContentType == BasinContentType.MILK)
            {
                //Check if player holds acceptable fermenting item, if so start fermenting process.
                if(itemHeld.is(ItemTags.getAllTags().getTag(FERMENTING_ITEMS_TAG)))
                {
                    this.content = BasinContent.FERMENTING_MILK;
                    level.playSound(player, getBlockPos(), SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if(!player.isCreative())
                    {
                        itemHeld.shrink(1);
                       // if(player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1)))
                       // {
                       //     world.addEntity(new ItemEntity(world, getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, new ItemStack(Items.GLASS_BOTTLE, 1)));
                       // }
                    }
                    this.setChanged();
                    return InteractionResult.SUCCESS;
                }

                //Extract Milk
                else if(itemHeld.getItem() instanceof BucketItem)
                {
                    this.content = BasinContent.AIR;
                    itemHeld.shrink(1);

                    if(!player.addItem(new ItemStack(Items.MILK_BUCKET)))
                    {
                        level.addFreshEntity(new ItemEntity(player.level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), new ItemStack(Items.MILK_BUCKET)));
                    }

                    level.playSound(player, getBlockPos(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8F, 1.0F);
                    this.setChanged();
                    return InteractionResult.SUCCESS;
                }
            }

            if(basinContentType == BasinContentType.EMPTY || getBasinContent().getSauceType() != SauceType.NONE || basinContentType == BasinContentType.OIL) //#TODO IF NOT FIX OLIVE, ADD LINE HERE
            {
                if(basinContentType == BasinContentType.EMPTY)
                {
                    if(itemHeld.getItem() == ModItems.CHEESE_BLOCK.get() && stackInSlot.isEmpty())
                    {
                        this.content = BasinContent.CHEESE;
                        this.squashedStack = new ItemStack(ModItems.CHEESE.get(), getInventory().getSlotLimit(0));

                        if(!player.isCreative())
                        {
                            itemHeld.shrink(2);
                        }

                        level.playSound(player, getBlockPos(), SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }

                    else if(itemHeld.getItem() instanceof MilkBucketItem && stackInSlot.isEmpty())
                    {
                        this.content = BasinContent.MILK;
                        this.squashedStack = new ItemStack(Items.MILK_BUCKET, getInventory().getSlotLimit(0));

                        if(!player.isCreative())
                        {
                            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                        }

                        level.playSound(player, getBlockPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.8F, 1.0F);
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }

                    else if(canInsert(itemHeld))
                    {
                        insertStack(player, hand);
                        return InteractionResult.SUCCESS;
                    }
                    else if(canExtract(player, hand))
                    {
                        extractStack(player);
                        return InteractionResult.SUCCESS;
                    }
                }
                else if(getBasinContent().getSauceType() != SauceType.NONE || basinContentType == BasinContentType.OIL) //#TODO IF NOT FIX OLIVE, ADD LINE HERE
                {
                    //if(itemHeld.getItem() == Items.GLASS_BOTTLE)
                    //{
                        if(getSquashedStackCount() >= basinContentExtractSize().get(getBasinContent()))
                        {
                            //itemHeld.shrink(1);
                            ItemStack result = basinContentToItemStack().get(getBasinContent());

                            if(!player.getInventory().add(result))
                            {
                                level.addFreshEntity(new ItemEntity(player.level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), result));
                            }
                            level.playSound(player, getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat());
                            setSquashedStackCount(getSquashedStackCount() - basinContentExtractSize().get(getBasinContent()));
                            return InteractionResult.SUCCESS;
                        }
                    //}

                    else if(canInsert(itemHeld))
                    {
                        insertStack(player, hand);
                        return InteractionResult.SUCCESS;
                    }
                    else if(canExtract(player, hand))
                    {
                        extractStack(player);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    // ======== CRUSHING ========

    public ItemStack getSquashedStack()
    {
        return this.squashedStack;
    }

    public int getSquashedStackCount()
    {
        return this.squashedStack.getCount();
    }

    public void setSquashedStackCount(int count)
    {
        this.squashedStack.setCount(count);
        updateBasinContent();
    }

    /**
     * Called when player jumps on basin block
     * @param player
     */
    public void crush(Player player)
    {
        if(!isEmpty(inventory) && getSquashedStackCount() + 1 <= getInventory().getSlotLimit(0))
        {
            ItemStack stack = getInventory().getStackInSlot(0);

            Optional<CrushingRecipe> match = level.getRecipeManager().getRecipeFor(CrushingRecipe.Type.CRUSHING_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);

            if(match.isPresent())
            {
                if(SauceRegistry.INSTANCE.basinContentFromString(match.get().getContentOutput()) == null)
                {
                    throw new JsonSyntaxException(String.format("Content in %s recipe does not exist", match.get()));
                }

                if(getBasinContent() == BasinContent.AIR || getBasinContent() == SauceRegistry.INSTANCE.basinContentFromString(match.get().getContentOutput()))
                {
                    if(getSquashedStack().isEmpty())
                    {
                        this.squashedStack = new ItemStack(stack.getItem(), 1);
                    }
                    else
                    {
                        setSquashedStackCount(getSquashedStackCount() + 1);
                    }

                    this.content = SauceRegistry.INSTANCE.basinContentFromString(match.get().getContentOutput());
                    decrStackSize(inventory, 0, 1);
                    level.playSound(player, getBlockPos(), SoundEvents.SLIME_BLOCK_FALL, SoundSource.BLOCKS, 0.7F, 0.9F + (0.1F * level.random.nextFloat()));
                }
            }
            //Check for possible recipes
       /*     for(Map<ResourceLocation, String> recipe : BasinRecipeRegistry.RECIPES_REGISTRY2)
            {
                if(Utils.matchRecipeTag(recipe, stack) != null)
                {
                    ResourceLocation tagLocation = Utils.matchRecipeTag(recipe, stack);

                    if(getBasinContent() == BasinContent.AIR || getBasinContent() == SauceRegistry.INSTANCE.basinContentFromString(recipe.get(tagLocation)))
                    {
                        if(getSquashedStack().isEmpty())
                        {
                            this.squashedStack = new ItemStack(stack.getItem(), 1);
                        }
                        else
                        {
                            setSquashedStackCount(getSquashedStackCount() + 1);
                        }

                        this.content = SauceRegistry.INSTANCE.basinContentFromString(recipe.get(tagLocation));
                        decrStackSize(inventory, 0, 1);
                        world.playSound(player, pos, SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.BLOCKS, 0.7F, 0.9F + (0.1F * world.rand.nextFloat()));
                    }
                } 8/
            /*    if(Utils.recipeContainsTag(recipe, stack) && (getBasinContent() == BasinContent.AIR || getBasinContent() == SauceRegistry.INSTANCE.basinContentFromString(recipe.get(Ingredient.fromStacks(stack)))));
                {
                    if(getSquashedStack().isEmpty())
                    {
                        this.squashedStack = new ItemStack(stack.getItem(), 1);
                    }
                    else
                    {
                        setSquashedStackCount(getSquashedStackCount() + 1);
                    }

                    this.content = SauceRegistry.INSTANCE.basinContentFromString(recipe.get(Ingredient.fromStacks(stack)));
                    decrStackSize(inventory, 0, 1);
                    world.playSound(player, pos, SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.BLOCKS, 0.7F, 0.9F + (0.1F * world.rand.nextFloat()));
                } */
            }

        /*    for(Map<Item, String> map : BasinRecipeRegistry.RECIPES_REGISTRY)
            {
                if(map.containsKey(stack.getItem()) && (getBasinContent() == BasinContent.AIR || getBasinContent() == SauceRegistry.INSTANCE.basinContentFromString(map.get(stack.getItem()))))
                {
                    if(getSquashedStack().isEmpty())
                    {
                        this.squashedStack = new ItemStack(stack.getItem(), 1);
                    }
                    else
                    {
                        setSquashedStackCount(getSquashedStackCount() + 1);
                    }

                    this.content = SauceRegistry.INSTANCE.basinContentFromString(map.get(stack.getItem()));
                    decrStackSize(inventory, 0, 1);
                    world.playSound(player, pos, SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.BLOCKS, 0.7F, 0.9F + (0.1F * world.rand.nextFloat()));
                }
            } */
       // }
    }

    // ======== FERMENTING ========

    public int getFermentProgress()
    {
        return this.fermentProgress;
    }

    public int getDefaultFermentTime()
    {
        return this.defaultFermentTime;
    }

    public int getComparatorOutput()
    {
        float f = (float)this.fermentProgress / defaultFermentTime;
        return (int)(f*15);
    }

    public void finishFermenting()
    {
        if(getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK)
        {
            this.fermentProgress = 0;
            this.content = BasinContent.CHEESE;
        }
        setChanged();
    }

  /*  @Override
    public void setChanged()
    {
        super.setChanged();
        notifyBlockUpdate();
    }

    private void notifyBlockUpdate()
    {
        BlockState blockstate = getLevel().getBlockState(getBlockPos());
        level.setBlocksDirty(getBlockPos(), blockstate, blockstate);
        level.sendBlockUpdated(getBlockPos(), blockstate, blockstate, Block.UPDATE_CLIENTS);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), 3, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        super.onDataPacket(net, pkt);
        this.handleUpdateTag(pkt.getTag());
    } */

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        if(getBasinContent() == BasinContent.FERMENTING_MILK)
        {
            this.fermentProgress = tag.getInt(FERMENT_PROGRESS);
        }
        else super.handleUpdateTag(tag);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        if(getBasinContent() == BasinContent.FERMENTING_MILK)
        {
            CompoundTag tag = new CompoundTag();
            tag.putInt(FERMENT_PROGRESS, this.fermentProgress);
            return tag;
        }
        return save(new CompoundTag());
    }

    private int tick = 0;

    public static void tick(Level level, BlockPos pos, BlockState state, BasinBlockEntity blockEntity)
    {
        if(blockEntity.getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK)
        {
            blockEntity.fermentProgress++;
            //blockEntity.setChanged();

            if(blockEntity.fermentProgress % 60 == 0)
            {
                //if(tick == 59)
                //{
                level.playSound(null, pos, SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
                //}
            }
        }
        if(blockEntity.fermentProgress >= blockEntity.defaultFermentTime)
        {
            blockEntity.finishFermenting();
        }

        if(blockEntity.getBasinContent().getContentType() == BasinContentType.CHEESE)
        {
            if(blockEntity.tick % 20 == 0)
            {
                blockEntity.createCheeseParticle();
            }
            if(blockEntity.tick < 60)
            {
                blockEntity.tick++;
            }
            else if(blockEntity.tick == 60)
            {
                blockEntity.tick = 0;
            }
        }
    }
/*    @Override
    public void tick()
    {
        if(getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK)
        {
            this.fermentProgress++;

            if(fermentProgress % 60 == 0) {
                //if(tick == 59)
                //{
                level.playSound(null, getBlockPos(), SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
                //}
            }
        }
        if(this.fermentProgress >= this.defaultFermentTime)
        {
            finishFermenting();
        }

        if(getBasinContent().getContentType() == BasinContentType.CHEESE)
        {
            if(this.tick % 20 == 0)
            {
                createCheeseParticle();
            }
            if(tick < 60)
            {
                tick++;
            }
            else if(tick == 60)
            {
                tick = 0;
            }
        }
    } */

    private void createCheeseParticle()
    {
        double x = ((double)level.random.nextInt(12) / 16);
        double z = ((double)level.random.nextInt(12) / 16);
        level.addParticle(new DustParticleOptions(new Vector3f(0.91F, 0.76F, 0.31F), 1.0F), getBlockPos().getX() + x + 0.2D, getBlockPos().getY() + 0.6D, getBlockPos().getZ() + z + 0.2D, 0.0D, 0.09D, 0.0D);
    }

    // ======== ITEMHANDLER ========

    public boolean isEmpty()
    {
        return getInventory().getStackInSlot(0).isEmpty();
    }

    public boolean canInsert(ItemStack stack)
    {
        return !stack.isEmpty() && isEmpty();
    }

    public boolean canExtract(Player player, InteractionHand handIn)
    {
        return player.getItemInHand(handIn).isEmpty() && !isEmpty();
    }

    public void insertStack(Player player, InteractionHand hand)
    {
        if(!player.isCreative())
        {
            player.setItemInHand(hand, getInventory().insertItem(0, player.getItemInHand(hand), false));
        }
        else
        {
            getInventory().insertItem(0, player.getItemInHand(hand).copy(), false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    public void extractStack(Player player)
    {
        if(!player.isCreative())
        {
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), getInventory().extractItem(0, 64, false));
        }
        else
        {
            getInventory().extractItem(0, 64, false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    public IItemHandlerModifiable getInventory()
    {
        return inventory;
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler()
        {
            @Override
            public int getSlotLimit(int slot)
            {
                return 8;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side)
    {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }
}