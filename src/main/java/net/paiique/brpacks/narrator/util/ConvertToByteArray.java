package net.paiique.brpacks.narrator.util;

import java.io.*;
import java.nio.file.Path;

public class ConvertToByteArray {
    public byte[] convertToByteArray(File audioFile) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(audioFile));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            out.flush();
            in.close();
            audioFile.delete();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File convertByteArrayToFile(byte[] byteArray, Path filePath) {
        try {
            File file = filePath.toFile();
            if (file.exists()) file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
