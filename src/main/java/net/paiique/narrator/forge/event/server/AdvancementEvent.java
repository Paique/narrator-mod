package net.paiique.narrator.forge.event.server;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.narrator.data.Data;
import net.paiique.narrator.forge.event.interfaces.EventInterface;

public class AdvancementEvent extends Data implements EventInterface {
    @SubscribeEvent
    public static void onAdvancementEvent(net.minecraftforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent event) {
        if (event.getAdvancement().value().name().isEmpty()) return;
        String advancementName = event.getAdvancement().value().name().get().getString();
        if (advancementName.isEmpty()) return;
        actualAiText.add(event.getEntity().getName().getString() + " obteve a conquista " + advancementName + ".");
        actionsPoints += 50;
    }
}