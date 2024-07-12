package net.paiique.narrator.ai.openai;

import com.google.gson.JsonObject;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.request.DataTypes;
import net.paiique.narrator.request.HttpsRequestBuilder;

import java.nio.file.Path;

public class OpenAiVoice {
    public Path generate(String token, String text) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("model", ConfigCommon.OPENAI_VOICE_MODEL.get());
            jsonObject.addProperty("input", text);
            jsonObject.addProperty("response_format", "mp3");
            jsonObject.addProperty("voice", ConfigCommon.OPENAI_VOICE_TYPE.get());

            Object responseObject = new HttpsRequestBuilder()
                    .setBuildReason("generate voice")
                    .setRequestObject(jsonObject)
                    .contentType(DataTypes.Response.JSON)
                    .setRequestType(DataTypes.Request.POST)
                    .setOutputType(DataTypes.Output.VOICE)
                    .setToken(token)
                    .setURL("https://api.openai.com/v1/audio/speech")
                    .buildAndConnect();
            if (!(responseObject instanceof Path path)) return Path.of("");
            return path;
    }
}
