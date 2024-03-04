package net.paiique.brpacks.narrator.forge.event;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

/**
 * @author paique
 * @version 1.0
 */
public class GameModeChangeEvent extends EventData implements EventInterface {
    @SubscribeEvent
    public static void onGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        NarratorMod.data.actualAiText.add("O jogador " + event.getEntity().getName().getString() + " mudou de modo de jogo para " + event.getNewGameMode().getName() + ", estaria ele trapaceando?");
        actionsPoints += 20;
    }
}
