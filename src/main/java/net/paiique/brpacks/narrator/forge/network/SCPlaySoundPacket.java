package net.paiique.brpacks.narrator.forge.network;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.paiique.brpacks.narrator.NarratorClient;
import net.paiique.brpacks.narrator.NarratorMod;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public class SCPlaySoundPacket {
    private final byte[] fileByteArray;
    private static Logger LOGGER;

    public SCPlaySoundPacket(byte[] fileByteArray) {
        LOGGER = LogUtils.getLogger();
        this.fileByteArray = fileByteArray;
    }
    public SCPlaySoundPacket(FriendlyByteBuf buffer) {
        this(buffer.readByteArray());
    }
    public void encode(FriendlyByteBuf buffer) {
        if (fileByteArray == null) {
            LOGGER.error("Failed to encode packet");
            return;
        }
        buffer.writeByteArray(fileByteArray);
    }

    public void handle(CustomPayloadEvent.Context context) {
        try {
            LOGGER.info("Audio packet received");
            if (fileByteArray == null) return;
            Path path = Path.of("output.ogg");
            NarratorMod.fileUtil.convertByteArrayToFile(fileByteArray, path);
            if (!Files.exists(path)) return;
            NarratorClient.auudio.play(path);
        } catch (Exception e) {
            LOGGER.error("Failed to handle packet:" + e.getMessage());
        }
    }
}
