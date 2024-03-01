package net.paiique.brpacks.narrator.util;

import de.keksuccino.auudio.audio.AudioClip;
import de.keksuccino.auudio.audio.exceptions.InvalidAudioException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

import java.io.File;
import java.nio.file.Path;

public class Auudio {
    public void play(File file) throws InvalidAudioException {
        System.out.println(file.getName());
        AudioClip audio = AudioClip.buildExternalClip(file.getPath(), AudioClip.SoundType.EXTERNAL_LOCAL ,SoundSource.VOICE);
        audio.play();
    }
}
