package net.paiique.brpacks.narrator.forge.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.paiique.brpacks.narrator.NarratorClient;
import net.paiique.brpacks.narrator.NarratorMod;

import java.nio.file.Files;
import java.nio.file.Path;

public class SCPlaySoundPacket {
    private final byte[] fileByteArray;

    public SCPlaySoundPacket(byte[] fileByteArray) {
        this.fileByteArray = fileByteArray;
    }
    public SCPlaySoundPacket(FriendlyByteBuf buffer) {
        this(buffer.readByteArray());
    }
    public void encode(FriendlyByteBuf buffer) {
        if (fileByteArray == null) return;
        buffer.writeByteArray(fileByteArray);
    }

    public void handle(CustomPayloadEvent.Context context) {
        try {
            System.out.println("Packet recived");
            if (fileByteArray == null) return;
            Path path = Path.of("output.ogg");
            NarratorMod.fileUtil.convertByteArrayToFile(fileByteArray, path);
            if (!Files.exists(path)) return;
            NarratorClient.auudio.play(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
