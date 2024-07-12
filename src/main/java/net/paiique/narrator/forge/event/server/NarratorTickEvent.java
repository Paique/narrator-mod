package net.paiique.narrator.forge.event.server;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.narrator.ai.openai.NarrationThread;
import net.paiique.narrator.data.Data;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.forge.event.interfaces.EventInterface;

public class NarratorTickEvent extends Data implements EventInterface {
    private static int tickCounter = 0;
    public static boolean DISABLED = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {

             int REQUIRED_ACTIONS_POINTS = ConfigCommon.REQUIRED_ACTIONS_POINTS.get();
             int COOLDOWN = ConfigCommon.CHECK_COOLDOWN.get();

            if (DISABLED) {
                event.getServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(Component.literal("Verifique a key em: mods > narrator > configurações"), true));
                return;
            }

            if (narratorThreadLock) {
                if (ConfigCommon.DEBUG.get()) event.getServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(Component.literal("DEBUG: Thread do narrador executando"), true));
                return;
            }

            event.getServer().getPlayerList().getPlayers().forEach(player -> {
               if (ConfigCommon.DEBUG.get()) player.sendSystemMessage(Component.literal("DEBUG: Próxima verificação: " + tickCounter + "/ " + COOLDOWN + " | Pontos de Ação: " + actionsPoints + "/" + REQUIRED_ACTIONS_POINTS), true);
            });

            tickCounter++;

            if (tickCounter >= COOLDOWN) {
                if (actionsPoints >= REQUIRED_ACTIONS_POINTS) {
                    actionsPoints = 0;
                    new NarrationThread().start();
                }
                tickCounter = 0;
            }
        }
    }
}
