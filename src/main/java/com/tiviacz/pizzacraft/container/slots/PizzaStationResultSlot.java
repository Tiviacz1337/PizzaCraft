package com.tiviacz.pizzacraft.container.slots;

import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class PizzaStationResultSlot extends SlotItemHandler
{
    private final PizzaStationMenu menu;

    public PizzaStationResultSlot(PizzaStationMenu menu, IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);

        this.menu = menu;
    }

    @Override
    public void setChanged()
    {
        menu.updateOutput();
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack)
    {
        return false;
    }

    @Override
    public void onTake(@Nonnull Player player, @Nonnull ItemStack stack)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            //CraftFoodTrigger.INSTANCE.trigger(serverPlayer);
        }

        for(int i = 0; i < menu.blockEntity.getInventory().getSlots() - 1; i++)
        {
            ItemStack itemStack = menu.blockEntity.getInventory().getStackInSlot(i);

            if(!itemStack.isEmpty())
            {
                boolean isPotion = itemStack.getItem() instanceof PotionItem;
                boolean isSoup = itemStack.getItem() instanceof BowlFoodItem;

                ItemStack container = itemStack.getCraftingRemainingItem();
                itemStack.shrink(1);

                if(container.isEmpty())
                {
                    if(isPotion)
                    {
                        container = new ItemStack(Items.GLASS_BOTTLE);
                    }
                    else if(isSoup)
                    {
                        container = new ItemStack(Items.BOWL);
                    }
                }

                if(!container.isEmpty())
                {
                    if(itemStack.isEmpty())
                    {
                        menu.blockEntity.getInventory().setStackInSlot(i, container);
                    }
                    else
                    {
                        ItemHandlerHelper.giveItemToPlayer(player, stack);
                    }
                }
            }
        }
        menu.updateOutput();
    }
}