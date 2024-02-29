package net.paiique.brpacks.narrator.util;

import lombok.Setter;
import net.paiique.brpacks.narrator.NarratorMod;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author paique
 * @version 1.2
 */


public class SoundPlayer {

    public void play(Path filePath) {
        try {
            if (filePath == null) {
                NarratorMod.LOGGER.error("Audio path is null.");
                return;
            }

            File audioFile = filePath.toFile();

            if (!audioFile.exists()) {
                NarratorMod.LOGGER.error("Audio file not found.");
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();

            byte[] bytesBuffer = new byte[1024];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();

            NarratorMod.LOGGER.info("Audio file " + audioFile.getName() + " played successfully.");

        } catch (UnsupportedAudioFileException ex) {
            NarratorMod.LOGGER.error("The specified audio file is not supported.");
        } catch (LineUnavailableException ex) {
            NarratorMod.LOGGER.error("Audio line for playing back is unavailable.");
        } catch (IOException ex) {
            NarratorMod.LOGGER.error("Error playing the audio file.");
        }
    }
}

