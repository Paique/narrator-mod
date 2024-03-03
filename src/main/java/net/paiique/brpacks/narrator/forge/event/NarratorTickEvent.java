package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class NarratorTickEvent extends EventData implements EventInterface {
    private static int tickCounter = 0;
    private static final int REQUIRED_ACTIONS_POINTS = ConfigCommon.REQUIRED_ACTIONS_POINTS.get();
    private static final int COOLDOWN = ConfigCommon.CHECK_COOLDOWN.get();

    public static boolean DISABLED = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {

            if (DISABLED) {
                event.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.sendSystemMessage(Component.literal("Verifique a key em: mods > narrator > configurações"), true);
                });
                return;
            }

            if (NarratorMod.postPacket.lock) {
                event.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.sendSystemMessage(Component.literal("DEBUG: Narrador falando"), true);
                });
                return;
            }

            event.getServer().getPlayerList().getPlayers().forEach(player -> {
                player.sendSystemMessage(Component.literal("DEBUG: Próxima verificação: " + tickCounter + "/ " + COOLDOWN + " | Pontos de Ação: " + actionsPoints + "/" + REQUIRED_ACTIONS_POINTS), true);
            });

            tickCounter++;

            if (tickCounter >= COOLDOWN) {
                if (actionsPoints >= REQUIRED_ACTIONS_POINTS) {
                    actionsPoints = 0;
                    NarratorMod.postPacket.start();
                }
                tickCounter = 0;
            }
        }
    }
}
