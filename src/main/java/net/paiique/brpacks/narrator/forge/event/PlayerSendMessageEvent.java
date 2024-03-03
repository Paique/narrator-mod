package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class PlayerSendMessageEvent extends EventData implements EventInterface {

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

        NarratorMod.postPacket.getActualAiText().add(username + " disse no chat: " + message);
        actionsPoints += 1;
    }

}
