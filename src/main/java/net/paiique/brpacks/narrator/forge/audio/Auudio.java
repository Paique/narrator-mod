package net.paiique.brpacks.narrator.forge.audio;

import com.mojang.logging.LogUtils;
import de.keksuccino.auudio.audio.AudioClip;
import lombok.SneakyThrows;
import net.minecraft.sounds.SoundSource;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import org.slf4j.Logger;

import java.nio.file.Path;

public class Auudio {

    private final Logger LOGGER;

    public Auudio() {
        LOGGER = LogUtils.getLogger();
    }

    @SneakyThrows
    public void play(Path file) {
        AudioClip audio = AudioClip.buildExternalClip(file.getFileName().toString(), AudioClip.SoundType.EXTERNAL_LOCAL , SoundSource.VOICE);
        audio.setVolume(ConfigCommon.NARRATOR_VOLUME.get());
        LOGGER.info("Playing audio");
        audio.play();
    }
}