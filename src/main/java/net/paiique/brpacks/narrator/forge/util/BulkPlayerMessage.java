package net.paiique.brpacks.narrator.forge.util;

import net.minecraft.network.chat.Component;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.LinkedList;
import java.util.List;

public class BulkPlayerMessage {
    public static void sendMessage(String message, boolean actionBar) {
        if (ServerLifecycleHooks.getCurrentServer() == null) return;
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            serverPlayer.sendSystemMessage(Component.literal(message), actionBar);
        });
    }

    public static void sendMessage(LinkedList<String> messages) {
        for (String message : messages) {
            sendMessage(message, false);
        }
    }

}
