package net.paiique.brpacks.narrator.request.util;

import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.event.server.NarratorTickEvent;
import net.paiique.brpacks.narrator.forge.util.BulkPlayerMessage;
import net.paiique.brpacks.narrator.request.DataTypes;

import java.util.LinkedList;

public class ConErrorHandler extends Data {
    public static void handle(String cause, String buildReason) {
        NarratorTickEvent.DISABLED = true;
        Data.actualAiText.clear();

        LinkedList<String> messages = new LinkedList<>();
        messages.add("|----[Narrador report]----|");
        messages.add("Um erro ocorreu:");
        messages.add("[" +  cause + "]");
        messages.add("Motivo de execução: " + buildReason);
        messages.add("Verifique os tokens, e se você possui créditos.");
        messages.add("|----[Narrador report]----|");

        BulkPlayerMessage.sendMessage(messages);
    }
}
