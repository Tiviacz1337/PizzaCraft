package com.tiviacz.pizzacraft.blockentity;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.blocks.OvenBlock;
import com.tiviacz.pizzacraft.blocks.PizzaBlock;
import com.tiviacz.pizzacraft.blocks.RawPizzaBlock;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.common.PizzaBlockCalculator;
import com.tiviacz.pizzacraft.container.PizzaMenu;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.items.SauceItem;
import com.tiviacz.pizzacraft.tags.ModTags;
import com.tiviacz.pizzacraft.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PizzaBlockEntity extends BaseBlockEntity implements MenuProvider
{
    public final ItemStackHandler inventory = createHandler();
    private Component customName = null;
    private int bakingTime = -1;
    private final int baseBakingTime = 600;
    private int selectedSlot = 0;

    private int uniqueness = 0;
    private int hunger = 0;
    private float saturation = 0.0F;
    private List<Pair<MobEffectInstance, Float>> effects = new ArrayList<>();

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String BAKING_TIME = "BakingTime";
    private final String CUSTOM_NAME = "CustomName";

    public PizzaBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.PIZZA.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(NBTUtils.TAG_INVENTORY));
        this.uniqueness = compound.getInt(NBTUtils.TAG_UNIQUENESS);
        this.hunger = compound.getInt(NBTUtils.TAG_HUNGER);
        this.saturation = compound.getFloat(NBTUtils.TAG_SATURATION);

        this.bakingTime = compound.getInt(BAKING_TIME);

        if(compound.contains(CUSTOM_NAME, 8))
        {
            this.customName = Component.Serializer.fromJson(compound.getString(CUSTOM_NAME));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put(NBTUtils.TAG_INVENTORY, this.inventory.serializeNBT());
        compound.putInt(NBTUtils.TAG_UNIQUENESS, this.uniqueness);
        compound.putInt(NBTUtils.TAG_HUNGER, this.hunger);
        compound.putFloat(NBTUtils.TAG_SATURATION, this.saturation);

        compound.putInt(BAKING_TIME, this.bakingTime);

        if(this.customName != null)
        {
            compound.putString(CUSTOM_NAME, Component.Serializer.toJson(this.customName));
        }
    }

    public InteractionResult onBlockActivated(Player player, InteractionHand hand)
    {
        if(hand == InteractionHand.MAIN_HAND)
        {
            ItemStack stack = player.getItemInHand(hand);

            if(isRaw() && !isBaking())
            {
                if(stack.isEmpty())
                {
                    //Open GUI here
                    if(player.isCrouching())
                    {
                        openGUI(player, this, getBlockPos());
                        return InteractionResult.SUCCESS;
                    }
                    else
                    {
                        //Get first not empty stack for Removal
                        for(int i = inventory.getSlots() - 1; i >= 0; i--)
                        {
                            ItemStack firstNotEmpty = inventory.getStackInSlot(i);

                            if(!firstNotEmpty.isEmpty())
                            {
                                this.selectedSlot = i;
                                break;
                            }
                        }

                        //Remove stack from slot
                        if(!inventory.getStackInSlot(this.selectedSlot).isEmpty())
                        {
                            ItemStack modifiedCopy = inventory.getStackInSlot(this.selectedSlot).copy();

                            if(this.selectedSlot != 9)
                            {
                                if(!player.getInventory().add(modifiedCopy))
                                {
                                    Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), modifiedCopy);
                                }
                            }
                            level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                            removeFromSlot(this.selectedSlot);
                            this.setChanged();
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                else
                {
                    //Get first empty or same stack
                    for(int i = 0; i < inventory.getSlots(); i++)
                    {
                        ItemStack firstEmpty = inventory.getStackInSlot(i);

                        if(firstEmpty.isEmpty())
                        {
                            this.selectedSlot = i;
                            break;
                        }
                    }

                    if(stack.getItem() instanceof PotionItem || stack.getItem() instanceof SauceItem)
                    {
                        ItemStack modifiedCopy = stack.copy();
                        modifiedCopy.setCount(1);
                        inventory.setStackInSlot(9, modifiedCopy);

                        stack.shrink(1);
                        ItemStack container = PizzaMenu.getItemStack(modifiedCopy);
                        player.addItem(container);
                        level.playSound(player, getBlockPos(), SoundEvents.AXOLOTL_SPLASH, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }

                    //Insert to selected slot or add to same stack if possible
                    if(this.selectedSlot < 9 && canAddIngredient(stack, this.selectedSlot))
                    {
                        ItemStack modifiedCopy = stack.copy();
                        modifiedCopy.setCount(1);
                        inventory.setStackInSlot(this.selectedSlot, modifiedCopy);

                        stack.shrink(1);
                        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        /*    if(!isRaw())
            {
                if(stack.isEmpty())
                {
                    if(player.isCrouching())
                    {
                        //Open Gui
                        openGUI(player, this, getBlockPos());
                        return InteractionResult.SUCCESS;
                    }
                }
            } */
        }
        return InteractionResult.FAIL;
    }

    public void writeToSliceItemStack(ItemStack stack)
    {
        NBTUtils.saveInventoryToStack(stack, this.inventory);
        NBTUtils.setUniqueness(stack, this.uniqueness);
        NBTUtils.setHunger(stack, this.hunger / 7);
        NBTUtils.setSaturation(stack, this.saturation);

        if(this.customName != null)
        {
            stack.setHoverName(this.customName);
        }
    }

    public ItemStack writeToItemStack(ItemStack stack)
    {
        NBTUtils.saveInventoryToStack(stack, this.inventory);
        NBTUtils.setUniqueness(stack, this.uniqueness);
        NBTUtils.setHunger(stack, this.hunger);
        NBTUtils.setSaturation(stack, this.saturation);

        if(this.customName != null)
        {
            stack.setHoverName(this.customName);
        }

        return stack;
    }

    public void readFromStack(ItemStack stack)
    {
        if(stack.getTag() != null)
        {
            this.inventory.deserializeNBT(stack.getTag().getCompound(NBTUtils.TAG_INVENTORY));
            this.uniqueness = stack.getTag().getInt(NBTUtils.TAG_UNIQUENESS);
            this.hunger = stack.getTag().getInt(NBTUtils.TAG_HUNGER);
            this.saturation = stack.getTag().getFloat(NBTUtils.TAG_SATURATION);

            if(stack.hasCustomHoverName())
            {
                this.customName = stack.getHoverName();
            }
        }
    }

    // ======== BAKING ========

  /*  public boolean isFresh() {
        return this.leftFreshTime > 0;
    }

    public int getLeftFreshTime()
    {
        return this.leftFreshTime;
    }

    public int getDefaultFreshTime() {
        return this.baseFreshTime;
    } */

    public boolean isBaking()
    {
        return this.bakingTime > 0;
    }

    public int getBakingTime()
    {
        return this.bakingTime;
    }

    public int getDefaultBakingTime()
    {
        return this.baseBakingTime;
    }

    public boolean isRaw()
    {
        if(level == null)
        {
            return true;
        }
        return this.level.getBlockState(getBlockPos()).getBlock() instanceof RawPizzaBlock;
    }

    /**
     * Each stack increases baking time adding 20 * stack#getCount() to baseBakingTime
     */
    public int getCalculatedBakingTime()
    {
        int bakingTime = this.getDefaultBakingTime();

        for(int i = 0; i < inventory.getSlots(); i++)
        {
            if(!inventory.getStackInSlot(i).isEmpty())
            {
                bakingTime += 20 * inventory.getStackInSlot(i).getCount();
            }
        }
        return bakingTime;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PizzaBlockEntity blockEntity)
    {
       /* if(!blockEntity.isRaw() && blockEntity.isFresh())
        {
            blockEntity.leftFreshTime--;

            if(blockEntity.leftFreshTime == 0) {
                blockEntity.leftFreshTime = -1;
            }
        } */

        if(blockEntity.isRaw())
        {
            if(blockEntity.isBaking())
            {
               // world.playSound(null, pos, ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                blockEntity.bakingTime--;

                if(blockEntity.bakingTime == 0 || !(level.getBlockState(blockEntity.getBlockPos().below()).getBlock() instanceof OvenBlock))
                {
                    if(blockEntity.bakingTime == 0)
                    {
                        if(!level.isClientSide)
                        {
                            level.setBlockAndUpdate(blockEntity.getBlockPos(), ModBlocks.PIZZA.get().defaultBlockState());
                        }
                       // blockEntity.leftFreshTime = blockEntity.baseFreshTime;
                    }
                    blockEntity.bakingTime = -1;
                }
            }

            else if(!blockEntity.isBaking() && level.getBlockState(blockEntity.getBlockPos().below()).getBlock() instanceof OvenBlock)
            {
                blockEntity.bakingTime = blockEntity.getCalculatedBakingTime();
            }
        }
    }

    public void updateFoodProperties(int hunger, float saturation, int uniqueness, List<Pair<MobEffectInstance, Float>> effects)
    {
        this.hunger = hunger;
        this.saturation = saturation;
        this.uniqueness = uniqueness;
        this.effects = effects;
    }

    public int getUniqueness()
    {
        return this.uniqueness;
    }

    public int getHunger()
    {
        return this.hunger;
    }

    public float getSaturation()
    {
        return this.saturation;
    }

    public int getHungerForSlice()
    {
        return this.hunger / 7;
    }

    public float getSaturationForSlice()
    {
        return this.saturation;
    }

    // ======== ITEMHANDLER ========

    public IItemHandlerModifiable getInventory()
    {
        return inventory;
    }

    public boolean canAddIngredient(ItemStack stack, int slot)
    {
        if(stack.isEdible() || stack.is(ModTags.INGREDIENTS))
        {
            return inventory.getStackInSlot(slot).isEmpty();
        }
        return false;
    }

    public void removeFromSlot(int slot)
    {
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(10)
        {
            final PizzaBlockCalculator calculator = new PizzaBlockCalculator(this);

            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
                if(isRaw() && !isBaking())
                {
                    return canAddIngredient(stack, slot);
                }
                return false;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
                requestModelDataUpdate();

                calculator.resetStats();
                calculator.process();
                updateFoodProperties(calculator.getHunger(), calculator.getSaturation(), calculator.getUniqueness(), calculator.getEffects());
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side)
    {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }

    // ======== CONTAINER ========

    @Override
    public Component getDisplayName()
    {
        if(this.customName != null)
        {
            return this.customName;
        }

        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player playerEntity)
    {
        return new PizzaMenu(id, playerInventory, this);
    }

    public void openGUI(Player player, MenuProvider menuSupplier, BlockPos pos)
    {
        if(!player.getLevel().isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer)player, menuSupplier, pos);
        }
    }

    // ======== MODELDATA ========

    @Nonnull
    @Override
    public ModelData getModelData()
    {
        ModelData.Builder builder = ModelData.builder();
        builder.with(PizzaBakedModel.LAYER_PROVIDERS, Optional.of(getInventory()));
        builder.with(PizzaBakedModel.INTEGER_PROPERTY, Optional.of(getBlockState().getBlock() == ModBlocks.PIZZA.get() ? getBlockState().getValue(PizzaBlock.BITES) : 0));
        builder.with(PizzaBakedModel.IS_RAW, Optional.of(isRaw()));
        ModelData modelData = builder.build();
        return modelData;
    }

    public ModelData getItemStackModelData(ItemStack stack)
    {
        readFromStack(stack);
        ModelData.Builder builder = ModelData.builder();
        builder.with(PizzaBakedModel.LAYER_PROVIDERS, Optional.of(getInventory()));
        builder.with(PizzaBakedModel.INTEGER_PROPERTY, Optional.of(0));
        builder.with(PizzaBakedModel.IS_RAW, Optional.of(stack.getItem() == ModItems.RAW_PIZZA.get()));
        return builder.build();
    }
}
