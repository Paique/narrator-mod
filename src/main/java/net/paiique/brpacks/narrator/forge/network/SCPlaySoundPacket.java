package net.paiique.brpacks.narrator.forge.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.paiique.brpacks.narrator.NarratorClient;

public class SCPlaySoundPacket {
    private final byte[] soundFile;

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
            NarratorClient.auudio.play(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
