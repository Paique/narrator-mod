package net.paiique.brpacks.narrator;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.paiique.brpacks.narrator.forge.Events;
import net.paiique.brpacks.narrator.openai.Data;
import net.paiique.brpacks.narrator.openai.Post;
import net.paiique.brpacks.narrator.util.SoundPlayer;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

/**
 * @author paique
 * @version 1.1
 */

@Mod(NarratorMod.MODID)
public class NarratorMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "narrator";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Data data;

    public static Post post;

    public static SoundPlayer soundPlayer;

    public NarratorMod() {
        data = new Data();
        post = new Post();
        soundPlayer = new SoundPlayer();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(Events.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
