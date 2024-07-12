package net.paiique.narrator.forge.util;

import de.keksuccino.melody.Melody;
import de.keksuccino.melody.resources.audio.MelodyAudioException;
import de.keksuccino.melody.resources.audio.SimpleAudioFactory;
import de.keksuccino.melody.resources.audio.openal.ALAudioClip;
import de.keksuccino.melody.resources.audio.openal.ALException;
import net.minecraft.sounds.SoundSource;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class Audio {

    public static void playAudio(Path path) throws MelodyAudioException {
       CompletableFuture<ALAudioClip> completableFuture = SimpleAudioFactory.ogg(path.toString(), SimpleAudioFactory.SourceType.LOCAL_FILE);
       completableFuture.thenAccept(audioClip -> {
           try {
               audioClip.setSoundChannel(SoundSource.MASTER);
               audioClip.play();
               audioClip.setVolume(1.0f);
               audioClip.tryUpdateVolume();
           } catch (ALException e) {
               throw new RuntimeException(e);
           }
       });

    }

}
