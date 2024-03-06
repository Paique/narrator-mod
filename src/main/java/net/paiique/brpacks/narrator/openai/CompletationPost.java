package net.paiique.brpacks.narrator.openai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.server.NarratorTickEvent;
import net.paiique.brpacks.narrator.forge.util.BulkPlayerMessage;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CompletationPost {

    public String generate(String token, LinkedList<String> actualAiText) {
        Logger LOGGER = LogUtils.getLogger();
        StringBuilder prompt = new StringBuilder();

            actualAiText.forEach(last -> {
                prompt.append(last + "\n");
                System.out.println(last);
            });
            actualAiText.clear();

        System.out.println(prompt);

        try {
            String chatUrl = "https://api.openai.com/v1/chat/completions";
            URL url = new URL(chatUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setDoOutput(true);

            JsonObject jsonObject = new JsonObject();
            JsonArray messagesArray = new JsonArray();
            JsonObject systemMessage = new JsonObject();
            jsonObject.addProperty("model", ConfigCommon.CHAT_GPT_MODEL.get());
            systemMessage.addProperty("role", "system");
            if (FMLEnvironment.dist.isClient())
                systemMessage.addProperty("content", "Você é um narrador de Minecraft onipresente que quebra a quarta parede de froma sutil, e deve narrar as ações do jogador, e guiar as ações dele como no jogo 'Stanley Parable' e insulte ele maneira bem sutil. - O peso dos eventos na narração é classificado no final do texto por: {pequeno}, {médio}, {grande}, {enorme}. (Somente português)");
            else systemMessage.addProperty("content", "Você é um narrador de Minecraft onipresente que quebra a quarta parede de froma sutil, e deve narrar as ações dos jogadores do servidor, e guiar as ações deles como no jogo 'Stanley Parable' e os insulte maneira bem sutil. - O peso dos eventos na narração é classificado no final do texto por: {pequeno}, {médio}, {grande}, {enorme}. (Somente português)");
            messagesArray.add(systemMessage);
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", "Esses são os eventos: \n[" + prompt + "] \n Para narrar, de 2 a 3 frases.");
            messagesArray.add(userMessage);
            jsonObject.add("messages", messagesArray);
            jsonObject.addProperty("temperature", 0.5);
            String jsonString = new Gson().toJson(jsonObject);

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

            JsonObject respObj = (JsonObject) JsonParser.parseString(response.toString());

            String resp = respObj.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
            in.close();
            return resp;
        } catch (Exception e) {
            LOGGER.error("Error while generating text using OpenAI.");
            List<String> messages = new ArrayList<>();
            messages.add("Erro ao gerar texto, por favor, verifique sua chave de API.");
            messages.add("Tick event do Narrador foi desativado.");
            messages.add("Erro: " + e.getMessage());
            messages.add("Acha que isto foi um erro temporário como um erro de conexão, ou corrigiu a sua chave de API?");
            messages.add("Reative o narrador com o /narrador para tentar novamente!");
            BulkPlayerMessage.sendMessage(messages);
            actualAiText.clear();
            NarratorTickEvent.DISABLED = true;
        }
        return "";
    }

    private JsonArray getMessagesArray(LinkedList<String> pastAiText) {
        Gson gson = new Gson();
        return gson.toJsonTree(pastAiText).getAsJsonArray();
    }
}
