package net.paiique.brpacks.narrator.forge.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.paiique.brpacks.narrator.NarratorMod;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(NarratorMod.MODID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SCPlaySoundPacket.class,
                NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SCPlaySoundPacket::encode)
                .decoder(SCPlaySoundPacket::new)
                .consumerMainThread(SCPlaySoundPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }

}
