package net.paiique.brpacks.narrator.forge.network;

import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.util.BulkPlayerMessage;
import org.slf4j.Logger;

public class CSSetupOpenAIKey {
    private final byte[] stringByteArray;
    private static Logger LOGGER;

    public CSSetupOpenAIKey(byte[] stringByteArray) {
        LOGGER = LogUtils.getLogger();
        this.stringByteArray = stringByteArray;
    }
    public CSSetupOpenAIKey(FriendlyByteBuf buffer) {
        this(buffer.readByteArray());
    }
    public void encode(FriendlyByteBuf buffer) {
        if (stringByteArray == null) {
            LOGGER.error("Failed to encode packet");
            return;
        }
        buffer.writeByteArray(stringByteArray);
    }

    public void handle(CustomPayloadEvent.Context context) {
        try {
            LOGGER.info("Api packet received");
            System.out.println(context.getDirection());
            if (stringByteArray == null) return;
            ConfigCommon.OPENAI_API_KEY.set(new String(stringByteArray));
            ConfigCommon.OPENAI_API_KEY.save();
            BulkPlayerMessage.sendMessage("Chave de API configurada com sucesso!", false);
        } catch (Exception e) {
            LOGGER.error("Failed to handle packet:" + e.getMessage());
        }
    }
}
