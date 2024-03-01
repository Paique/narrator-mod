package net.paiique.brpacks.narrator;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.paiique.brpacks.narrator.forge.menu.ConfigMenu;

public class NarratorClient {

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        registerScreens();
    }

    public static void registerScreens() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigMenu()));
    }

}
