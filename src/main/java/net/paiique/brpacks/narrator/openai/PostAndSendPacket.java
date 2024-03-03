package net.paiique.brpacks.narrator.openai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.NarratorTickEvent;
import net.paiique.brpacks.narrator.forge.network.PacketHandler;
import net.paiique.brpacks.narrator.forge.network.SCPlaySoundPacket;
import net.paiique.brpacks.narrator.forge.util.BulkPlayerMessage;
import net.paiique.brpacks.narrator.util.Ffmpeg;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author paique
 * @version 1.7
 */

public class PostAndSendPacket extends Thread {
    @Getter
    private LinkedList<String> actualAiText = NarratorMod.data.actualAiText;
    private StringBuilder prompt = new StringBuilder();
    public boolean lock = false;
    public boolean firstChat = true;
    private String token;

    @Override
    public void run() {
        if (lock) return;
        lock = true;
        token = ConfigCommon.OPENAI_API_KEY.get();
        playerActions();
        NarratorMod.postPacket = new PostAndSendPacket();
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
            List<String> messages = new ArrayList<>();
            messages.add("Erro ao se conectar com a OpenAI, por favor, verifique sua chave de API.");
            messages.add("Narrator tick event foi desativado.");
            messages.add("Erro: " + e.getMessage());
            messages.add("Acha que isto foi um erro temporário como um erro de conexão, ou corrigiu a sua chave de API?");
            messages.add("Reative o narrador com o /narrador para tentar novamente!");
            messages.add(token);
            BulkPlayerMessage.sendMessage(messages);
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
        if (FMLEnvironment.dist.isClient())
            systemMessage.addProperty("content", "Você é um narrador de Minecraft, e deve narrar as ações do jogador, e guiar as ações dele como no jogo 'Stanley Parable' e julgue/insulte/elogie ele maneira bem sutil de vez em quando.");
        else
            systemMessage.addProperty("content", "Você é um narrador de Minecraft, e deve narrar as ações dos jogadores do servidor, e guiar as ações deles como no jogo 'Stanley Parable' e os julgue/insulte/elogie maneira bem sutil de vez em quando. (Todos irão lhe escutar ao mesmo tempo como uma voz onipresente)");
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

        Path path = Path.of("output.mp3");
        FileOutputStream fos = new FileOutputStream(path.toFile());
        fos.write(audioData);
        in.close();
        fos.close();

        Ffmpeg ffmpeg = new Ffmpeg();
        ffmpeg.setFilePath(path);
        ffmpeg.setCallback(() -> {
            Path pathCall = Path.of("output.ogg");
            System.out.println("Audio File" + pathCall.getFileName().toString());
            byte[] audioCall = NarratorMod.fileUtil.convertToByteArray(pathCall);

            System.out.println("audiodata: " + audioCall.length);
            PacketHandler.sendToAllClients(new SCPlaySoundPacket(audioCall));
            lock = false;
        });
        ffmpeg.start();
    }
}