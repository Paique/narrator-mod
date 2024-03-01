package net.paiique.brpacks.narrator.forge.event;

import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class SleepEvent extends EventData implements EventInterface {
    @SubscribeEvent
    public static void onSleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity().level().isClientSide) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " dormiu em uma cama, e amanhaceu descansado.");
        actionsPoints += 100;
    }
}
