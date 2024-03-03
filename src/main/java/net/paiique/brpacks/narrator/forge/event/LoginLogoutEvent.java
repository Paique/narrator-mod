package net.paiique.brpacks.narrator.forge.event;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.forge.commands.SlashNarrator;
import net.paiique.brpacks.narrator.interfaces.EventInterface;
import net.paiique.brpacks.narrator.util.DateUtil;

/**
 * @author paique
 * @version 1.0
 */
public class LoginLogoutEvent extends EventData implements EventInterface {


    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (FMLEnvironment.dist.isDedicatedServer()) {
            int players = ServerLifecycleHooks.getCurrentServer().getPlayerCount();
            if (disconnectedPlayers.containsKey(event.getEntity().getUUID())) {
                NarratorMod.data.actualAiText.add(disconnectedPlayers.get(event.getEntity().getUUID()) + "O jogador retornou as " + DateUtil.getActualDate() + "o julgue dependendo do tempo que ele demorou para retornar (Se foi muito rápido provavelmente ele só caiu do servidor). (" + (players - 1) + " jogadores conectados no servidor além dele).");
            } else
                NarratorMod.data.actualAiText.add("O jogador " + event.getEntity().getName().getString() + " entrou no servidor pela a primeira vez, ou após o servidor reiniciar, você não sabe quais das situações ocorreu, mas dê boas vindas a ele. (Existem" + (players - 1) + " jogadores no servidor além dele).");
            actionsPoints += 300;
            return;
        }

        String levelName = event.getEntity().level().getServer().getWorldData().getLevelName();
        if (!disconnectedPlayers.containsKey(event.getEntity().getUUID())) {
            NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " criou um mundo com o nome \"" + levelName + "\", e entrou nele (Fale do nome do mundo se for muito estranho, ou diferente).");
        } else
            NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " entrou novamente no mundo \"" + levelName + "\" após ser julgado por sair dele.");
        if (!NarratorMod.postPacket.lock) NarratorMod.postPacket.start();
        else actionsPoints += 300;
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        new SlashNarrator().register(event.getServer().getCommands().getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (FMLEnvironment.dist.isDedicatedServer()) {
            disconnectedPlayers.put(event.getEntity().getUUID(), "O jogador " + event.getEntity().getName().getString() + " se desconectou do servidor as " + DateUtil.getActualDate() + ", e se reconectou agora as ");
            return;
        }
        NarratorMod.postPacket.start();
        actionsPoints = 0;
    }
}
