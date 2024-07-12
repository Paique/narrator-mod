package net.paiique.narrator.ai.openai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.request.DataTypes;
import net.paiique.narrator.request.HttpsRequestBuilder;

import java.util.LinkedList;

public class GenerateOpenAiText {

    @Deprecated(forRemoval = true)
    public String autoCompleteGeneration(String token, LinkedList<String> actualAiText) {
         StringBuilder prompt = new StringBuilder();


        actualAiText.forEach(last -> {
            prompt.append(last + "\n");
            System.out.println(last);
        });
        actualAiText.clear();

        JsonObject jsonObject = new JsonObject();
        JsonArray messagesArray = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        jsonObject.addProperty("model", ConfigCommon.OPENAI_TEXT_MODEL.get());
        jsonObject.addProperty("max_tokens", ConfigCommon.OPENAI_MAX_TEXT_TOKENS.get());
        systemMessage.addProperty("role", "system");
        if (FMLEnvironment.dist.isClient())
            systemMessage.addProperty("content", "Você é um narrador de Minecraft onipresente que quebra a quarta parede de froma sutil, e deve narrar as ações do jogador, e guiar as ações dele como no jogo 'Stanley Parable' e insulte ele maneira bem sutil. (Somente português)");
        else systemMessage.addProperty("content", "Você é um narrador de Minecraft onipresente que quebra a quarta parede de froma sutil, e deve narrar as ações dos jogadores do servidor, e guiar as ações deles como no jogo 'Stanley Parable' e os insulte maneira bem sutil. (Somente português)");
        messagesArray.add(systemMessage);
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", "Esses são os eventos: \n[" + prompt + "] \n Para narrar, de 2 a 5 frases.");
        messagesArray.add(userMessage);
        jsonObject.add("messages", messagesArray);
        jsonObject.addProperty("temperature", ConfigCommon.NARRATOR_TEMPERATURE.get());

        Object responseObject = new HttpsRequestBuilder()
                .setBuildReason("gerar texto")
                .contentType(DataTypes.Response.JSON)
                .setRequestType(DataTypes.Request.POST)
                .setOutputType(DataTypes.Output.TEXT)
                .setURL("https://api.openai.com/v1/chat/completions")
                .setToken(token)
                .setRequestObject(jsonObject)
                .buildAndConnect();

        if (!(responseObject instanceof String response)) return "";
        return response;
    }

    public String generateText(String token, LinkedList<String> actualAiText) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Ações realizadas: ");
        actualAiText.forEach(last -> prompt.append(last).append("\n"));
        actualAiText.clear();

        return prompt.toString();

    }

}
