package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class AdvancementEvent extends EventData implements EventInterface {
    @SubscribeEvent
    public static void onAdvancementEvent(net.minecraftforge.event.entity.player.AdvancementEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getAdvancement().value().name().isEmpty()) return;
        NarratorMod.data.actualAiText.add("O jogador " + event.getEntity().getName().getString() + " obteve a conquista " + event.getAdvancement().value().name().get().getString() + ".");
        actionsPoints += 300;
    }
}