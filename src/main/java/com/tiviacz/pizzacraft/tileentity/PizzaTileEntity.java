package com.tiviacz.pizzacraft.tileentity;

import com.mojang.datafixers.util.Pair;
import com.tiviacz.pizzacraft.blocks.OvenBlock;
import com.tiviacz.pizzacraft.blocks.PizzaBlock;
import com.tiviacz.pizzacraft.blocks.RawPizzaBlock;
import com.tiviacz.pizzacraft.client.PizzaBakedModel;
import com.tiviacz.pizzacraft.container.PizzaContainer;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.init.ModTileEntityTypes;
import com.tiviacz.pizzacraft.init.PizzaLayers;
import com.tiviacz.pizzacraft.util.FoodUtils;
import com.tiviacz.pizzacraft.util.NBTUtils;
import com.tiviacz.pizzacraft.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class PizzaTileEntity extends BaseTileEntity implements INamedContainerProvider, ITickableTileEntity //#TODO sauce application has to be done
{
    private final ItemStackHandler inventory = createHandler();
    private int leftBakingTime = -1;
    private int leftFreshTime = -1;
    private final int baseBakingTime = 600;
    private final int baseFreshTime = 1800;
    private int selectedSlot = 0;
    private Pair<Integer, Float> refillment = Pair.of(0, 0.0F);
    private List<Pair<EffectInstance, Float>> effects = new ArrayList<>();
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String LEFT_BAKING_TIME = "LeftBakingTime";
    private final String LEFT_FRESH_TIME = "LeftFreshTime";
    private final String HUNGER = "Hunger";
    private final String SATURATION = "Saturation";
    private final String EFFECTS = "Effects";

    public PizzaTileEntity()
    {
        super(ModTileEntityTypes.PIZZA.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT compound)
    {
        super.load(state, compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.leftBakingTime = compound.getInt(LEFT_BAKING_TIME);
        this.leftFreshTime = compound.getInt(LEFT_FRESH_TIME);
        this.refillment = Pair.of(compound.getInt(HUNGER), compound.getFloat(SATURATION));
        this.effects = NBTUtils.readEffectsFromTag(compound);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound)
    {
        super.save(compound);
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(LEFT_BAKING_TIME, this.leftBakingTime);
        compound.putInt(LEFT_FRESH_TIME, this.leftFreshTime);
        compound.putInt(HUNGER, this.refillment.getFirst());
        compound.putFloat(SATURATION, this.refillment.getSecond());
        compound.put(EFFECTS, NBTUtils.writeEffectsToTag(this.effects));
        return compound;
    }

    /**
     * Base Ingredients can be added to this list
     */
    public static Set<Item> baseIngredients = new HashSet<>(Collections.singletonList(ModItems.CHEESE.get()));

    public ActionResultType onBlockActivated(PlayerEntity player, Hand hand)
    {
        if(hand == Hand.MAIN_HAND)
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
                        return ActionResultType.SUCCESS;
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
                            modifiedCopy.setCount(1);

                            if(!player.inventory.add(modifiedCopy))
                            {
                                level.addFreshEntity(new ItemEntity(player.level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), modifiedCopy));
                            }
                            level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                            decreaseInSlot(this.selectedSlot, 1);
                            this.setChanged();
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
                else
                {
                /*    //Adding base ingredient
                    if(inventory.getStackInSlot(0).isEmpty() && canAddIngredient(stack, this.selectedSlot))
                    {
                        player.setHeldItem(hand, inventory.insertItem(0, stack, false));
                        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + world.rand.nextFloat());
                        return ActionResultType.SUCCESS;
                    } */

                    //Get first empty or same stack
                    for(int i = 0; i < inventory.getSlots(); i++)
                    {
                        ItemStack firstEmpty = inventory.getStackInSlot(i);

                      /*  if(Utils.checkItemStacksAndCount(firstEmpty, new ItemStack(stack.getItem(), 1), PizzaLayers.getMaxStackSizeMap().get(firstEmpty.getItem()) == null ? 0 : PizzaLayers.getMaxStackSizeMap().get(firstEmpty.getItem())) || firstEmpty.isEmpty())
                        {
                            this.selectedSlot = i;
                            break;
                        } */
                        if(Utils.checkItemStacksAndCount(firstEmpty, new ItemStack(stack.getItem(), 1), PizzaLayers.getMaxStackSizeForStack(stack)) || firstEmpty.isEmpty())
                        {
                            this.selectedSlot = i;
                            break;
                        }
                    }

                    //Insert to selected slot or add to same stack if possible
                    if(this.selectedSlot < 9 && canAddIngredient(stack, this.selectedSlot))
                    {
                        if(!inventory.getStackInSlot(this.selectedSlot).isEmpty())
                        {
                            ItemStack modifiedCopy = inventory.getStackInSlot(this.selectedSlot).copy();
                            modifiedCopy.setCount(modifiedCopy.getCount() + 1);
                            inventory.setStackInSlot(this.selectedSlot, modifiedCopy);
                        }
                        else
                        {
                            ItemStack modifiedCopy = stack.copy();
                            modifiedCopy.setCount(1);
                            inventory.setStackInSlot(this.selectedSlot, modifiedCopy);
                        }
                        stack.shrink(1);
                        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
                        return ActionResultType.SUCCESS;
                    }
                }
            }

            if(!isRaw())
            {
                if(stack.isEmpty())
                {
                    if(player.isCrouching())
                    {
                        //Open Gui
                        openGUI(player, this, getBlockPos());
                        return ActionResultType.SUCCESS;
                    }
                    else
                    {
                        //#TODO item removal
                    }
                }
            }
        }
        return ActionResultType.FAIL;
    }

    public void writeToSliceItemStack(ItemStack stack, int sliceNumber)
    {
        CompoundNBT compound = new CompoundNBT();
        if(isEmpty(inventory))
        {
            return;
        }
        compound.put(INVENTORY, this.inventory.serializeNBT());

        if(FoodUtils.requiresAddition(this.refillment.getFirst(), sliceNumber))
        {
            compound.putBoolean("RequiresAddition", true);
        }
        stack.setTag(compound);
    }

    public void writeToItemStack(ItemStack stack)
    {
        CompoundNBT compound = new CompoundNBT();
        if(isEmpty(inventory))
        {
            return;
        }
        compound.put(INVENTORY, this.inventory.serializeNBT());
        compound.putInt(LEFT_FRESH_TIME, this.leftFreshTime);
        stack.setTag(compound);
    }

    public void readFromStack(ItemStack stack)
    {
        if(stack.getTag() != null)
        {
            this.inventory.deserializeNBT(stack.getTag().getCompound(INVENTORY));
            this.leftFreshTime = stack.getTag().getInt(LEFT_FRESH_TIME);
            this.setHungerAndSaturationRefillment();
        }
    }

    // ======== BAKING ========

    public boolean isFresh() {
        return this.leftFreshTime > 0;
    }

    public int getLeftFreshTime()
    {
        return this.leftFreshTime;
    }

    public int getDefaultFreshTime() {
        return this.baseFreshTime;
    }

    public boolean isBaking()
    {
        return this.leftBakingTime > 0;
    }

    public int getLeftBakingTime()
    {
        return this.leftBakingTime;
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
    public int getBakingTime()
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

    @Override
    public void tick()
    {
        if(!this.isRaw() && this.isFresh())
        {
            this.leftFreshTime--;

            if(this.leftFreshTime == 0) {
                this.leftFreshTime = -1;
            }
        }

        if(this.isRaw())
        {
            if(isBaking())
            {
               // world.playSound(null, pos, ModSounds.SIZZLING_SOUND.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                this.leftBakingTime--;

                if(this.leftBakingTime == 0 || !(level.getBlockState(getBlockPos().below()).getBlock() instanceof OvenBlock))
                {
                    if(this.leftBakingTime == 0)
                    {
                        if(!level.isClientSide)
                        {
                            level.setBlockAndUpdate(getBlockPos(), ModBlocks.PIZZA.get().defaultBlockState());
                        }
                        this.leftFreshTime = this.baseFreshTime;
                    }
                    this.leftBakingTime = -1;
                }
            }

            else if(!isBaking() && level.getBlockState(getBlockPos().below()).getBlock() instanceof OvenBlock)
            {
                this.leftBakingTime = this.getBakingTime();
            }
        }
    }

    /**
     * Probably needs tweak
     */
    public void setHungerAndSaturationRefillment()
    {
        PizzaHungerSystem instance = new PizzaHungerSystem(this.inventory);
        this.refillment = Pair.of(instance.getHunger(), instance.getSaturation());
        this.effects = instance.getEffects();
    }

   // public List<com.mojang.datafixers.util.Pair<EffectInstance, Float>> getEffects()
   // {
   //     PizzaHungerSystem instance = new PizzaHungerSystem(this.inventory);
    //    this.effects = instance.getEffects();
        //return instance.getEffects();
  //  }

    public List<Pair<EffectInstance, Float>> getEffects()
    {
        return this.effects;
    }

    public Pair<Integer, Float> getRefillmentValues()
    {
        return this.refillment;
    }

    public int getHungerForSlice(int slice)
    {
        return FoodUtils.getHungerForSlice(this.refillment.getFirst(), slice);
    }

    public float getSaturationForSlice()
    {
        return this.refillment.getSecond() / 7;
    }

    // ======== ITEMHANDLER ========

    public IItemHandlerModifiable getInventory()
    {
        return inventory;
    }

    public void dropItemStack(int slot)
    {
        InventoryHelper.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(slot));
    }

    public boolean canAddIngredient(ItemStack stack, int slot)
    {
        for(ResourceLocation tagLocation : stack.getItem().getTags())
        {
            if(PizzaLayers.VALID_TAGS.contains(tagLocation))
            {
                if(inventory.getStackInSlot(slot).isEmpty())
                {
                    return true;
                }
                else
                {
                    if(inventory.getStackInSlot(slot).getCount() + 1 <= PizzaLayers.getMaxStackSizeForStack(inventory.getStackInSlot(slot)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
  /*  public boolean canAddIngredient(ItemStack stack, int slot)
    {
        if(slot == 0)
        {
            return baseIngredients.contains(stack.getItem());
        }
        if(slot == 9)
        {
            return !isRaw();
        }
        else
        {
            if(!this.inventory.getStackInSlot(0).isEmpty() && !baseIngredients.contains(stack.getItem()))
            {
                return PizzaLayers.getItemToLayerMap().containsKey(stack.getItem());
            }
        }
        return false;
    } */

    public void decreaseInSlot(int slot, int count)
    {
        ItemStack stack = inventory.getStackInSlot(slot);
        if(stack.getCount() >= 2)
        {
            stack.setCount(stack.getCount() - count);
        }
        else
        {
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(9)
        {
            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack)
            {
                return PizzaLayers.getMaxStackSizeForStack(stack);
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return getStackInSlot(slot).isEmpty() ? 1 : PizzaLayers.getMaxStackSizeForStack(getStackInSlot(slot));
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
                setHungerAndSaturationRefillment();
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

    // ======== CONTAINER ========

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new PizzaContainer(id, playerInventory, this);
    }

    public void openGUI(PlayerEntity player, INamedContainerProvider containerSupplier, BlockPos pos)
    {
        if(!player.level.isClientSide)
        {
            NetworkHooks.openGui((ServerPlayerEntity)player, containerSupplier, pos);
        }
    }

    // ======== MODELDATA ========

    @Nonnull
    @Override
    public IModelData getModelData()
    {
        ModelDataMap modelDataMap = PizzaBakedModel.getEmptyIModelData();
        modelDataMap.setData(PizzaBakedModel.LAYER_PROVIDERS, Optional.of(getInventory()));
        modelDataMap.setData(PizzaBakedModel.INTEGER_PROPERTY, Optional.of(getBlockState().getBlock() == ModBlocks.PIZZA.get() ? getBlockState().getValue(PizzaBlock.BITES) : 0));
        modelDataMap.setData(PizzaBakedModel.IS_RAW, Optional.of(isRaw()));
        return modelDataMap;
    }

    public IModelData getItemStackModelData(ItemStack stack)
    {
        readFromStack(stack);
        ModelDataMap modelDataMap = PizzaBakedModel.getEmptyIModelData();
        modelDataMap.setData(PizzaBakedModel.LAYER_PROVIDERS, Optional.of(getInventory()));
        modelDataMap.setData(PizzaBakedModel.INTEGER_PROPERTY, Optional.of(0));
        modelDataMap.setData(PizzaBakedModel.IS_RAW, Optional.of(stack.getItem() == ModItems.RAW_PIZZA.get()));
        return modelDataMap;
    }
}
