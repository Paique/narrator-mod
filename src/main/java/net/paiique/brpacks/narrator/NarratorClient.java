package net.paiique.brpacks.narrator;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.paiique.brpacks.narrator.forge.audio.Auudio;
import net.paiique.brpacks.narrator.forge.menu.ConfigMenuWarn;
import org.slf4j.Logger;


public class NarratorClient {
    public static Auudio auudio;

    private static Logger LOGGER;

    public void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        LOGGER = LogUtils.getLogger();
        auudio = new Auudio();
        registerScreens();
        LOGGER.error("Narrator Client side initialized");
    }

    public static void registerScreens() {
        LOGGER.info("Registering Screens");
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
            Screen parentScreen = Minecraft.getInstance().screen;
            return new ConfigMenuWarn(parentScreen);
        }));
    }
}
