package com.tiviacz.pizzacraft.network;

import com.tiviacz.pizzacraft.container.PizzaStationMenu;
import net.minecraft.SharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundRenamePizzaPacket
{
    private String name;

    public ServerboundRenamePizzaPacket(String name)
    {
        this.name = name;
    }

    public static ServerboundRenamePizzaPacket decode(final FriendlyByteBuf buffer)
    {
        final String name = buffer.readUtf();

        return new ServerboundRenamePizzaPacket(name);
    }

    public static void encode(final ServerboundRenamePizzaPacket message, final FriendlyByteBuf buffer)
    {
        buffer.writeUtf(message.name);
    }

    public static void handle(final ServerboundRenamePizzaPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            final ServerPlayer serverPlayer = ctx.get().getSender();

            AbstractContainerMenu menu = serverPlayer.containerMenu;
            if(menu instanceof PizzaStationMenu stationMenu)
            {
                if(!stationMenu.stillValid(serverPlayer))
                {
                    return;
                }

                String s = SharedConstants.filterText(message.name);
                if (s.length() <= 50) {
                    stationMenu.setItemName(s);
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
