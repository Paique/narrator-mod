package net.paiique.brpacks.narrator.openai;

import com.google.gson.JsonObject;
import net.paiique.brpacks.narrator.request.DataTypes;
import net.paiique.brpacks.narrator.request.HttpsRequestBuilder;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;

import java.nio.file.Path;

public class VoicePost {
    public Path generate(String token, String text) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("model", ConfigCommon.VOICE_MODEL.get());
            jsonObject.addProperty("input", text);
            jsonObject.addProperty("response_format", "mp3");
            jsonObject.addProperty("voice", ConfigCommon.VOICE.get());

            Object responseObject = new HttpsRequestBuilder()
                    .setBuildReason("generate voice")
                    .setRequestObject(jsonObject)
                    .setResponseType(DataTypes.Response.JSON)
                    .setRequestType(DataTypes.Request.POST)
                    .setOutputType(DataTypes.Output.VOICE)
                    .setToken(token)
                    .setURL("https://api.openai.com/v1/audio/speech")
                    .buildAndConnect();
            if (!(responseObject instanceof Path path)) return Path.of("");
            return path;
    }
}
