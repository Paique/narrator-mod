
package net.paiique.narrator.util;

import com.mojang.logging.LogUtils;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@Setter
public class FFmpeg extends Thread {

    Path filePath;
    Runnable callback;

    private static String ffmpegExec = "";
    private static Logger LOGGER;

    public FFmpeg() {
        super("Ffmpeg");
        LOGGER = LogUtils.getLogger();
    }


    @Override
    public void run() {
        convert(filePath);
        callback.run();
    }

    public static void convert(Path filePath) {
        try {
            String operatingSys = System.getProperty("os.name").toLowerCase();
            if (operatingSys.contains("mac")) throw new Exception("MacOS it's not supported");
            if (operatingSys.contains("windows")) ffmpegExec = "ffmpeg-win-x64.exe";
            if (operatingSys.contains("linux")) ffmpegExec = "ffmpeg-linux-x64";
            System.out.println(ffmpegExec);
            if (ffmpegExec.isBlank()) throw new NoSuchFileException("Failed to get OS for FFmpeg.");

            File source = filePath.toFile();
            File target = new File("output.ogg");
            if (target.exists()) target.delete();
            File ffmpeg = new File(ffmpegExec);

            if (!ffmpeg.exists()) copyFfmpeg();

            ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg.getAbsolutePath(), "-y", "-i", source.getAbsolutePath(), "-ac", "2", "-c:a", "libvorbis", "-b:a", "64k", target.getPath());
            processBuilder.inheritIO();
            processBuilder.start().waitFor();
        } catch (Exception e) {
            LOGGER.error("Failed to convert audio file to ogg: " + e.getMessage());
        }
    }

    private static void copyFfmpeg() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("ffmpeg/" + ffmpegExec);
            OutputStream os = Files.newOutputStream(Path.of(ffmpegExec));
            if (is == null) throw new Exception("InputStream is null");
            IOUtils.copy(is, os);
            if (ffmpegExec.contains("linux")) {
                File file = Path.of(ffmpegExec).toFile();
                System.out.println(file.setExecutable(true));
            }
            os.close();
            is.close();
        } catch (Exception e) {
            LOGGER.error("Failed while getting the FFMPEG executable from resources");
        }
    }
}
