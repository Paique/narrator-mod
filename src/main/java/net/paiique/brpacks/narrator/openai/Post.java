package net.paiique.brpacks.narrator.openai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.NarratorTickEvent;
import net.paiique.brpacks.narrator.forge.network.PacketHandler;
import net.paiique.brpacks.narrator.forge.network.SCPlaySoundPacket;
import net.paiique.brpacks.narrator.util.AudioConverter;
import net.paiique.brpacks.narrator.util.Auudio;
import net.paiique.brpacks.narrator.util.ConvertToByteArray;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

/**
 * @author paique
 * @version 1.7
 */

public class Post extends Thread {
    private final String token = "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";

    @Getter
    private LinkedList<String> actualAiText = NarratorMod.data.actualAiText;
    private StringBuilder prompt = new StringBuilder();

    public boolean lock = false;

    public boolean firstChat = true;

    @Override
    public void run() {
        if (lock) return;
        lock = true;
        playerActions();
        NarratorMod.post = new Post();
    }

    private void playerActions() {

        if (prompt.isEmpty() && firstChat) {
            prompt = new StringBuilder();
            actualAiText.forEach(last -> prompt.append("[" + last + "]").append("\n"));
            actualAiText.clear();
        } else actualAiText.forEach(last -> {
            prompt.append(last).append("\n");
        });


        //Get the message if an error occurs
        try {
            String chatUrl = "https://api.openai.com/v1/chat/completions";
            URL url = new URL(chatUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setDoOutput(true);

            JsonObject jsonObject = getJsonObject();

            String jsonString = new Gson().toJson(jsonObject);
            JsonObject respObj = getJsonObject(con, jsonString);
            String resp = respObj.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
            resp = resp.replaceAll("[\\r\\n]+", " ").replaceAll("['\\[\\](){}]", "");
            textToSpeech(resp);
        } catch (Exception e) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Erro ao se conectar com a OpenAI, por favor, verifique sua chave de API."));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("§cErro: " + e.getMessage()));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("§fAcha que isto foi um erro temporário como um erro de conexão? \n Reative seu querido narrador com o /narrator para tentar novamente"));
            NarratorTickEvent.DISABLED = true;
        }
    }

    private static JsonObject getJsonObject(HttpsURLConnection con, String jsonString) throws IOException {
        OutputStream os = con.getOutputStream();
        byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);


        StringBuilder response = new StringBuilder();

        BufferedInputStream in = new BufferedInputStream(con.getInputStream());
        byte[] dataBuffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            response.append(new String(dataBuffer, 0, bytesRead));
        }

        return (JsonObject) JsonParser.parseString(response.toString());
    }

    @NotNull
    private JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", ConfigCommon.CHAT_GPT_MODEL.get());
        JsonArray messagesArray = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "Você é um narrador de Minecraft, e deve narrar as ações do jogador, e guiar as ações dele como no jogo 'Stanley Parable' e julgue/insulte/elogie ele maneira bem sutil de vez em quando.");
        messagesArray.add(systemMessage);
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", "Esses são os eventos: \n [" + prompt.toString() + "] \n Para narrar, de 2 á 3 frases");
        messagesArray.add(userMessage);
        jsonObject.add("messages", messagesArray);
        jsonObject.addProperty("temperature", 0.2);
        return jsonObject;
    }

    @SneakyThrows
    public void textToSpeech(String text) {
        String ttsUrl = "https://api.openai.com/v1/audio/speech";
        URL url = new URL(ttsUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", ConfigCommon.VOICE_MODEL.get());
        jsonObject.addProperty("input", text);
        jsonObject.addProperty("response_format", "mp3");
        jsonObject.addProperty("voice", ConfigCommon.VOICE.get());

        String jsonString = new Gson().toJson(jsonObject);


        OutputStream os = con.getOutputStream();
        byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);


        byte[] audioData;
        BufferedInputStream in = new BufferedInputStream(con.getInputStream());
        audioData = in.readAllBytes();

        FileOutputStream fos = new FileOutputStream("output.mp3");
        fos.write(audioData);
        File filePath = new File("output.mp3");

        AudioConverter.convert(filePath.toPath());
        ConvertToByteArray soundFile = new ConvertToByteArray();
        byte[] audioBytes = soundFile.convertToByteArray(new File("output.ogg"));
        if (audioBytes == null) return;
        PacketHandler.sendToAllClients(new SCPlaySoundPacket(audioBytes));
        //new Auudio().play(new File("output.ogg").);
        lock = false;
    }
}