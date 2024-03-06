package net.paiique.brpacks.narrator.forge.event.server;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class AdvancementEvent extends Data implements EventInterface {
    @SubscribeEvent
    public static void onAdvancementEvent(net.minecraftforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent event) {
        if (event.getAdvancement().value().name().isEmpty()) return;
        String advancementName = event.getAdvancement().value().name().get().getString().toLowerCase();
        if (advancementName.isEmpty()) return;
        actualAiText.add(event.getEntity().getName().getString() + " obteve a conquista " + event.getAdvancement().value().name().get().getString() + ". {enorme}");
        actionsPoints += 50;
    }
}