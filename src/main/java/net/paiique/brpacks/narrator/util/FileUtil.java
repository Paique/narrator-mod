package net.paiique.brpacks.narrator.util;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    private static Logger LOGGER;

    public FileUtil() {
        LOGGER = LogUtils.getLogger();
    }

    public byte[] convertToByteArray(Path audioFile) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(audioFile.toFile()));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            out.flush();
            in.close();
            Files.delete(audioFile);
            return out.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Failed to convert audio to byte array");
        }
        return null;
    }

    public void convertByteArrayToFile(byte[] byteArray, Path filePath) {
        try {
            File file = filePath.toFile();
            if (file.exists()) file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            LOGGER.error("Failed to convert byte array to file");
        }
    }

}
