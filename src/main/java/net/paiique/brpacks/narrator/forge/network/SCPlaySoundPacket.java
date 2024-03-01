package net.paiique.brpacks.narrator.forge.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.paiique.brpacks.narrator.util.Auudio;
import net.paiique.brpacks.narrator.util.ConvertToByteArray;
import net.paiique.brpacks.narrator.util.SoundPlayer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

public class SCPlaySoundPacket {
    private byte[] soundFile;

    public SCPlaySoundPacket(byte[] soundFile) {
        this.soundFile = soundFile;
    }
    public SCPlaySoundPacket(FriendlyByteBuf buffer) {
        this(buffer.readByteArray());
    }
    public void encode(FriendlyByteBuf buffer) {
        if (soundFile == null) return;
        buffer.writeByteArray(soundFile);
    }

    public void handle(CustomPayloadEvent.Context context) {
        try {
            if (soundFile == null) return;
            File file = new ConvertToByteArray().convertByteArrayToFile(soundFile, Path.of("output.ogg"));
            Auudio auudio = new Auudio();
            auudio.play(file);
            System.out.println(context.getDirection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
