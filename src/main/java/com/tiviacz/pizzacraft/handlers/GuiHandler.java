package com.tiviacz.pizzacraft.handlers;

import com.tiviacz.pizzacraft.gui.GuiBakeware;
import com.tiviacz.pizzacraft.gui.GuiPizzaBag;
import com.tiviacz.pizzacraft.gui.container.ContainerBakeware;
import com.tiviacz.pizzacraft.gui.container.ContainerPizzaBag;
import com.tiviacz.pizzacraft.tileentity.TileEntityBakeware;
import com.tiviacz.pizzacraft.tileentity.TileEntityPizzaBag;
import com.tiviacz.pizzacraft.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.GUI_BAKEWARE)
		{
			return new ContainerBakeware(world, player, (TileEntityBakeware)world.getTileEntity(new BlockPos(x,y,z)));
		}
		if(ID == Reference.GUI_PIZZA_BAG)
		{
			return new ContainerPizzaBag(player, (TileEntityPizzaBag)world.getTileEntity(new BlockPos(x,y,z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_BAKEWARE)
		{
			return new GuiBakeware(world, player, (TileEntityBakeware)world.getTileEntity(new BlockPos(x,y,z)));
		}
		if(ID == Reference.GUI_PIZZA_BAG)
		{
			return new GuiPizzaBag(player, (TileEntityPizzaBag)world.getTileEntity(new BlockPos(x,y,z)));
		}
		return null;
	}

}