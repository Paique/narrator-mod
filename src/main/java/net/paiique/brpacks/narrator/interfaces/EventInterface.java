package net.paiique.brpacks.narrator.interfaces;

import net.minecraftforge.eventbus.api.IEventBus;
import net.paiique.brpacks.narrator.NarratorMod;

public interface EventInterface {
     default void register(IEventBus modEventBus) {
         NarratorMod.LOGGER.info("Registering event: " + this.getClass().getSimpleName());
        modEventBus.register(this.getClass());
    }
}
