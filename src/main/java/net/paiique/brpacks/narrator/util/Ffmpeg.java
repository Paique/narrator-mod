
package net.paiique.brpacks.narrator.util;

import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ffmpeg extends Thread {

    @Setter
    Path filePath;

    @Setter
    Runnable callback;

    @Override
    public void run() {
        convert(filePath);
        callback.run();
    }

    @SneakyThrows
    public static void convert(Path filePath) {
        try {

            File source = filePath.toFile();
            File target = new File("output.ogg");
            if (target.exists()) target.delete();
            File ffmpeg = new File("ffmpeg.exe");

            if (!ffmpeg.exists()) copyFFmpeg();

            ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg.getAbsolutePath(), "-i", source.getAbsolutePath() ,"-c:a", "libvorbis", "-b:a","64k", target.getPath());
            processBuilder.inheritIO();
            processBuilder.start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void copyFFmpeg() {

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("ffmpeg/ffmpeg.exe");
            OutputStream os = Files.newOutputStream(Path.of("ffmpeg.exe"));
            if (is == null) throw new Exception("is is null");
            IOUtils.copy(is, os);
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
