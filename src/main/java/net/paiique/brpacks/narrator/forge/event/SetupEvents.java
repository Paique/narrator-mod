package net.paiique.brpacks.narrator.forge.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.event.server.AdvancementEvent;
import net.paiique.brpacks.narrator.forge.event.server.*;
import net.paiique.brpacks.narrator.forge.network.PacketHandler;
import net.paiique.brpacks.narrator.forge.event.interfaces.EventInterface;

import java.util.List;

@Mod.EventBusSubscriber(modid = NarratorMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents extends Data implements EventInterface {

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        List<EventInterface> modEvents = List.of(
                new LoginLogoutEvent(),
                new AdvancementEvent(),
                new GameModeChangeEvent(),
                new MobHealthEvent(),
                new NarratorTickEvent(),
                new PlayerChangeLevelEvent(),
                new SleepEvent(),
                new PlayerSendMessageEvent()
        );

        modEvents.forEach(events -> events.register(MinecraftForge.EVENT_BUS));
        event.enqueueWork(PacketHandler::register);
    }

}
