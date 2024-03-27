package net.paiique.brpacks.narrator.openai;

import com.mojang.logging.LogUtils;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.network.PacketHandler;
import net.paiique.brpacks.narrator.forge.network.SCPlaySoundPacket;
import net.paiique.brpacks.narrator.util.FFmpeg;
import org.slf4j.Logger;

import java.nio.file.Path;

public class NarrationGenerator extends Data {

    private static final Logger LOGGER = LogUtils.getLogger();

    private CompletationPost completation;
    private VoicePost voicePost;
    private FFmpeg ffmpeg;

    public void generate() {
        if (narratorThreadLock) return;
        narratorThreadLock = true;
        String openAiToken = ConfigCommon.OPENAI_API_KEY.get();

        if (completation == null) completation = new CompletationPost();
        String generatedText = completation.generate(openAiToken, actualAiText);
        if (generatedText.isEmpty()) return;
        LOGGER.info("Generated text: " + generatedText);


        if (voicePost == null) voicePost = new VoicePost();
        Path narratorFile = voicePost.generate(openAiToken, generatedText);
        actualAiText.clear();
        if (narratorFile == null) return;

        if (ffmpeg == null) ffmpeg = new FFmpeg();
        ffmpeg.setFilePath(narratorFile);
        ffmpeg.setCallback(() -> {
            Path pathCall = Path.of("output.ogg");
            byte[] audioCall = NarratorMod.fileUtil.convertToByteArray(pathCall);
            PacketHandler.sendToAllClients(new SCPlaySoundPacket(audioCall));
            narratorThreadLock = false;
        });
        ffmpeg.start();
    }

}
