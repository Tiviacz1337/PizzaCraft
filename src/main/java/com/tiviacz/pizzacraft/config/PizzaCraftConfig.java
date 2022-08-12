package com.tiviacz.pizzacraft.config;

import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = PizzaCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PizzaCraftConfig
{
    //SERVER
    public static boolean generateOliveTree;
    public static boolean dropOliveFromJungleLeaves;
    public static boolean seedDrops;

  /*  public static class Server
    {
        Server(final ForgeConfigSpec.Builder builder)
        {
            builder.comment("Server config settings")
                    .push("server");

            builder.pop();
        }
    } */

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue generateOliveTree;
        public final ForgeConfigSpec.BooleanValue dropOliveFromJungleLeaves;
        public final ForgeConfigSpec.BooleanValue seedDrops;

        Common(final ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common config settings")
                    .push("common");

            generateOliveTree = builder
                    .translation("pizzacraft.config.common.generateolivetree")
                    .define("generateOliveTree", true);

            dropOliveFromJungleLeaves = builder
                    .translation("pizzacraft.config.common.dropolivefromjungleleaves")
                    .define("dropOliveFromJungleLeaves", false);

            seedDrops = builder
                    .translation("pizzacraft.config.common.seeddrops")
                    .define("seedDrops", true);

            builder.pop();
        }
    }

   /* public static class Client
    {
        Client(final ForgeConfigSpec.Builder builder)
        {
            builder.comment("Client-only settings")
                    .push("client");

            builder.pop();
        }
    } */

    //SERVER
   /* private static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    } */

    //COMMON
    private static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
/*
    //CLIENT
    private static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    } */

    //REGISTRY
    public static void register(final ModLoadingContext context)
    {
        //context.registerConfig(ModConfig.Type.SERVER, serverSpec);
        context.registerConfig(ModConfig.Type.COMMON, commonSpec);
       // context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading configEvent)
    {
      //  if(configEvent.getConfig().getSpec() == PizzaCraftConfig.serverSpec)
      //  {
      //      bakeServerConfig();
      //  }
        if(configEvent.getConfig().getSpec() == PizzaCraftConfig.commonSpec)
        {
            bakeCommonConfig();
        }
      /*  if(configEvent.getConfig().getSpec() == PizzaCraftConfig.clientSpec)
        {
            bakeClientConfig();
        } */
    }

  //  public static void bakeServerConfig()
  //  {
  //      generateOliveTree = SERVER.generateOliveTree.get();
   // }

    public static void bakeCommonConfig()
    {
        generateOliveTree = COMMON.generateOliveTree.get();
        dropOliveFromJungleLeaves = COMMON.dropOliveFromJungleLeaves.get();
        seedDrops = COMMON.seedDrops.get();
    }
/*
    public static void bakeClientConfig()
    {

    } */
}