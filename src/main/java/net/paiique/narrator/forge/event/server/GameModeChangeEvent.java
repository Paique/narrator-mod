package net.paiique.narrator.forge.event.server;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.narrator.data.Data;
import net.paiique.narrator.forge.event.interfaces.EventInterface;

/**
 * @author paique
 * @version 1.0
 */
public class GameModeChangeEvent extends Data implements EventInterface {
    @SubscribeEvent
    public static void onGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        actualAiText.add("O jogador " + event.getEntity().getName().getString() + " mudou de modo de jogo para " + event.getNewGameMode().getName() + ", estaria ele trapaceando? {enorme}");
        actionsPoints += 20;
    }
}
