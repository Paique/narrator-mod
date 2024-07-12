package net.paiique.narrator.ai.elevenlabs;

import com.google.gson.JsonObject;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.request.DataTypes;
import net.paiique.narrator.request.HttpsRequestBuilder;

import java.nio.file.Path;

public class ElevenVoicePost {
    public Path generate(String token, String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model_id", ConfigCommon.ELEVENLABS_MODEL_ID.get());
        jsonObject.addProperty("text", text);
        jsonObject.addProperty("output_format", "mp3_44100_32");

        JsonObject voiceSettings = new JsonObject();
        voiceSettings.addProperty("similarity_boost", ConfigCommon.ELEVENLABS_SIMILARITY_BOOST.get());
        voiceSettings.addProperty("stability", ConfigCommon.ELEVENLABS_STABILITY.get());
        voiceSettings.addProperty("style", ConfigCommon.ELEVENLABS_STYLE.get());
        voiceSettings.addProperty("use_speaker_boost", ConfigCommon.ELEVENLABS_SPEAKER_BOOST.get());

        jsonObject.add("voice_settings", voiceSettings);

        Object responseObject = new HttpsRequestBuilder()
                .setBuildReason("generate voice")
                .setRequestObject(jsonObject)
                .contentType(DataTypes.Response.JSON)
                .setRequestType(DataTypes.Request.POST)
                .setOutputType(DataTypes.Output.VOICE)
                .setToken(token)
                .setURL("https://api.elevenlabs.io/v1/text-to-speech/" + ConfigCommon.ELEVENLABS_VOICE_ID.get())
                .buildAndConnect();
        if (!(responseObject instanceof Path path)) return Path.of("");
        return path;
    }

}
