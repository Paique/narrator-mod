package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class SleepEvent extends EventData implements EventInterface {
    @SubscribeEvent
    public static void onSleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity().level().isClientSide) return;
        System.out.println(event.getResultStatus());
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " deitou na cama.");
        actionsPoints += 100;
    }

    @SubscribeEvent
    public static void onLeaveBedEvent(PlayerWakeUpEvent event) {
        if (event.getEntity().level().isClientSide) return;
        Player player = event.getEntity();
        if (player.isSleeping()) {
            NarratorMod.data.actualAiText.add(player.getName().getString() + " acordou.");
            actionsPoints += 100;
        } else {
            NarratorMod.data.actualAiText.add(player.getName().getString() + " saiu da cama sem dormir, explique como uma noite sem dormir afeta o corpo humano, e fale que ele provavelmente faz isto na vida real.");
            actionsPoints += 5;
        }
    }
}
