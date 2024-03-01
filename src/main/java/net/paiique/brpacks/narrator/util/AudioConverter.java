package net.paiique.brpacks.narrator.util;

import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.Path;

public class AudioConverter {
    public static void convert(Path filePath) {

        File source = filePath.toFile();
        File target = new File("output.ogg");

        AudioAttributes audioAttributes = new AudioAttributes();
        audioAttributes.setCodec("libvorbis");
        audioAttributes.setBitRate(128000); // Adjust as needed
        audioAttributes.setChannels(2); // Stereo

        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("ogg");
        encodingAttributes.setAudioAttributes(audioAttributes);

        Encoder encoder = new Encoder();
        try {
            encoder.encode(new MultimediaObject(source), target, encodingAttributes);
            source.delete();
            System.out.println("Conversion successful!");
        } catch (EncoderException e) {
            System.err.println("Error during conversion: " + e.getMessage());
        }
    }
}
