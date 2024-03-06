package net.paiique.brpacks.narrator.openai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.server.NarratorTickEvent;
import net.paiique.brpacks.narrator.forge.util.BulkPlayerMessage;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VoicePost {
    public Path generate(String token, String text) {
        Logger LOGGER = LogUtils.getLogger();
        try {
            URL url = new URL("https://api.openai.com/v1/audio/speech");
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

            return path;
        } catch (Exception e) {
            LOGGER.error("Error while generating voice using OpenAI.");
            List<String> messages = new ArrayList<>();
            messages.add("Erro ao gerar voz, por favor, verifique sua chave de API.");
            messages.add("Tick event do Narrador foi desativado.");
            messages.add("Erro: " + e.getMessage());
            messages.add("Acha que isto foi um erro temporário como um erro de conexão, ou corrigiu a sua chave de API?");
            messages.add("Reative o narrador com o /narrador para tentar novamente!");
            messages.add(token);
            BulkPlayerMessage.sendMessage(messages);
            NarratorTickEvent.DISABLED = true;
        }
        return null;
    }


}
