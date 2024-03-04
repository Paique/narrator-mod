package net.paiique.brpacks.narrator.interfaces;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.paiique.brpacks.narrator.NarratorMod;
import org.slf4j.Logger;

public interface EventInterface {

    default Logger getLogger() {
        return LogUtils.getLogger();
    }

     default void register(IEventBus modEventBus) {
         getLogger().info("Registering event: " + this.getClass().getSimpleName());
        modEventBus.register(this.getClass());
    }
}
