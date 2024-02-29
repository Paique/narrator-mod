package net.paiique.brpacks.narrator.openai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import net.paiique.brpacks.narrator.NarratorMod;
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
    private final String token = "xxx";

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
            System.out.println(resp);
            textToSpeech(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonObject getJsonObject(HttpsURLConnection con, String jsonString) throws IOException {
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();

        try (BufferedInputStream in = new BufferedInputStream(con.getInputStream())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                response.append(new String(dataBuffer, 0, bytesRead));
            }
        }

        JsonParser respParser = new JsonParser();
        return (JsonObject) JsonParser.parseString(response.toString());
    }

    @NotNull
    private JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", "gpt-3.5-turbo");
        JsonArray messagesArray = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "Você é um narrador de Minecraft, e deve narrar as ações do jogador, e guiar as ações dele como no jogo 'Stanley Parable' e julgue/insulte/elogie ele maneira bem sutil de vez em quando.");
        messagesArray.add(systemMessage);
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", "Esses são os eventos: "+ prompt.toString() +" para narrar, de 2 á 3 frases");
        messagesArray.add(userMessage);
        jsonObject.add("messages", messagesArray);
        jsonObject.addProperty("temperature", 0.2);
        return jsonObject;
    }

    @SneakyThrows
    public void textToSpeech(String text) throws IOException {
        String ttsUrl = "https://api.openai.com/v1/audio/speech";
        URL url = new URL(ttsUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", "tts-1");
        jsonObject.addProperty("input", text);
        jsonObject.addProperty("response_format", "wav");
        jsonObject.addProperty("voice", "onyx");

        String jsonString = new Gson().toJson(jsonObject);


        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        byte[] audioData;
        try (BufferedInputStream in = new BufferedInputStream(con.getInputStream())) {
            audioData = in.readAllBytes();
        }

        try (FileOutputStream fos = new FileOutputStream("output.wav")) {
            fos.write(audioData);
        }
        NarratorMod.soundPlayer.play(new File("output.wav").toPath());
        lock = false;
    }
}