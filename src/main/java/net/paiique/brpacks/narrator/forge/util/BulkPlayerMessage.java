package net.paiique.brpacks.narrator.forge.util;

import io.netty.util.internal.UnstableApi;
import net.minecraft.network.chat.Component;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;

public class BulkPlayerMessage {
    public static void sendSystemMessage(String message, boolean actionBar) {
        if (ServerLifecycleHooks.getCurrentServer() == null) return;
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            serverPlayer.sendSystemMessage(Component.literal(message), actionBar);
        });
    }

    public static void sendMessage(List<String> messages) {
        for (String message : messages) {
            sendSystemMessage(message, false);
        }
    }

}
