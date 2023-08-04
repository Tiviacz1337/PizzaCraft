package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.network.ServerboundRenamePizzaPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork
{
    public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(PizzaCraft.MODID, "network");
    public static final String NETWORK_VERSION = new ResourceLocation(PizzaCraft.MODID, "1").toString();

    public static SimpleChannel registerNetworkChannel()
    {
        final SimpleChannel channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(() -> NETWORK_VERSION)
                .simpleChannel();

        PizzaCraft.NETWORK = channel;

        channel.messageBuilder(ServerboundRenamePizzaPacket.class, 0)
                .decoder(ServerboundRenamePizzaPacket::decode)
                .encoder(ServerboundRenamePizzaPacket::encode)
                .consumerMainThread(ServerboundRenamePizzaPacket::handle)
                .add();

        return channel;
    }
}