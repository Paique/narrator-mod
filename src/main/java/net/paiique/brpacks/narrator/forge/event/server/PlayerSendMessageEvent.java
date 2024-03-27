package net.paiique.brpacks.narrator.forge.event.server;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.event.interfaces.EventInterface;

public class PlayerSendMessageEvent extends Data implements EventInterface {

    @SubscribeEvent
    public static void onPlayerSendMessageEvent(ServerChatEvent event) {
        if (event.getPlayer().getCommandSenderWorld().isClientSide) return;
        String message = event.getMessage().getString();
        String username = event.getUsername();
        if (message.startsWith("/")) return;

        if (message.length() > 200) {
            event.getPlayer().sendSystemMessage(Component.literal("Mensagem muito longa, minimo de 200 caracteres para o Narrador registrar!").withStyle(ChatFormatting.RED));
            return;
        }
        actualAiText.add("O jogador " + username + " disse: " + message + ". {enorme}");
        actionsPoints += 300;
    }

}
