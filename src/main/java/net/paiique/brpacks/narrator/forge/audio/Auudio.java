package net.paiique.brpacks.narrator.forge.audio;

import de.keksuccino.auudio.audio.AudioClip;
import lombok.SneakyThrows;
import net.minecraft.sounds.SoundSource;
import net.paiique.brpacks.narrator.NarratorClient;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.util.FileUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Auudio {

    @SneakyThrows
    public void play(Path file) {
        System.out.println(file.getFileName().toString());
        AudioClip audio = AudioClip.buildExternalClip(file.getFileName().toString(), AudioClip.SoundType.EXTERNAL_LOCAL , SoundSource.VOICE);
        audio.setVolume(ConfigCommon.NARRATOR_VOLUME.get());
        audio.play();
    }
}