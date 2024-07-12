package net.paiique.narrator.ai.openai;

import com.mojang.logging.LogUtils;
import net.paiique.narrator.NarratorMod;
import net.paiique.narrator.ai.elevenlabs.ElevenVoicePost;
import net.paiique.narrator.data.Data;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.forge.network.PacketHandler;
import net.paiique.narrator.forge.network.SCPlaySoundPacket;
import net.paiique.narrator.util.FFmpeg;
import org.slf4j.Logger;

import java.nio.file.Path;

public class NarrationGenerator extends Data {

    private static final Logger LOGGER = LogUtils.getLogger();

    public void init() {
        if (narratorThreadLock) return;
        narratorThreadLock = true;

        //Placeholder
        String generatedText = switch (ConfigCommon.TEXT_GENERATOR_PROVIDER.get()) {
            case OPEN_AI -> openAIText();
        };

        if (generatedText.isEmpty()) return;

        Path audioPath = switch (ConfigCommon.VOICE_GENERATOR_PROVIDER.get()) {
            case ELEVEN_LABS -> new ElevenVoicePost().generate(ConfigCommon.ELEVENLABS_API_KEY.get(), generatedText);
            case OPEN_AI -> new OpenAiVoice().generate(ConfigCommon.OPENAI_API_KEY.get(), generatedText);
        };

        if (audioPath == null) return;

        actualAiText.clear();

        FFmpeg ffmpeg = new FFmpeg();
        ffmpeg.setFilePath(audioPath);
        ffmpeg.setCallback(() -> {
            Path pathCall = Path.of("output.ogg");
            byte[] audioCall = NarratorMod.fileUtil.convertToByteArray(pathCall);
            PacketHandler.sendToAllClients(new SCPlaySoundPacket(audioCall));
            narratorThreadLock = false;
        });
        ffmpeg.start();
    }

    private String openAIText() {
        String openAiToken = ConfigCommon.OPENAI_API_KEY.get();
        GenerateOpenAiText completion = new GenerateOpenAiText();
        String generatedText = completion.autoCompleteGeneration(openAiToken, actualAiText);
        LOGGER.info("Generated text: " + generatedText);
        return generatedText;
    }
}
