package net.paiique.brpacks.narrator.forge.event.server;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.commands.SlashNarrator;
import net.paiique.brpacks.narrator.interfaces.EventInterface;
import net.paiique.brpacks.narrator.util.DateUtil;

/**
 * @author paique
 * @version 1.0
 */
public class LoginLogoutEvent extends Data implements EventInterface {


    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;

        if (FMLEnvironment.dist.isDedicatedServer()) {
            int players = ServerLifecycleHooks.getCurrentServer().getPlayerCount();
            if (disconnectedPlayers.containsKey(event.getEntity().getUUID())) {
                actualAiText.add(disconnectedPlayers.get(event.getEntity().getUUID()) + "O jogador retornou as " + DateUtil.getActualDate() + "(o julgue dependendo do tempo que ele demorou para retornar, se foi muito rápido provavelmente ele só caiu do servidor, qualquer coisa além de 20 minutos é bastante. Existem " + (players - 1) + " jogadores conectados no servidor além dele) {médio}.");
            } else {
                actualAiText.add("O jogador " + event.getEntity().getName().getString() + " entrou no servidor pela a primeira vez, ou após o servidor reiniciar, você não sabe quais das situações ocorreu, mas dê boas vindas a ele. (Existem" + (players - 1) + " jogadores no servidor além dele) {médio}.");
            }
            disconnectedPlayers.remove(event.getEntity().getUUID());
            actionsPoints += 30;
            return;
        }

        String levelName = event.getEntity().level().getServer().getWorldData().getLevelName();
        actualAiText.add(event.getEntity().getName().getString() + " criou um mundo com o nome \"" + levelName + "\", e entrou nele (Fale do nome do mundo se for muito estranho, ou diferente) {enorme}.");
        actionsPoints += 300;
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        new SlashNarrator().register(event.getServer().getCommands().getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (FMLEnvironment.dist.isDedicatedServer()) disconnectedPlayers.put(event.getEntity().getUUID(), "O jogador " + event.getEntity().getName().getString() + " se desconectou do servidor as " + DateUtil.getActualDate() + ", e se reconectou agora as ");
    }
}
