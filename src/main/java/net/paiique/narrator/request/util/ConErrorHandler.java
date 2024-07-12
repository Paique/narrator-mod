package net.paiique.narrator.request.util;

import net.paiique.narrator.data.Data;
import net.paiique.narrator.forge.event.server.NarratorTickEvent;
import net.paiique.narrator.forge.util.BulkPlayerMessage;

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
