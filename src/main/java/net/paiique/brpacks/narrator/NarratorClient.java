package net.paiique.brpacks.narrator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.paiique.brpacks.narrator.forge.audio.Auudio;
import net.paiique.brpacks.narrator.forge.menu.ConfigMenuWarn;
import net.paiique.brpacks.narrator.util.FileUtil;


public class NarratorClient {

    public static FileUtil fileUtil;
    public static Auudio auudio;

    public void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        fileUtil = new FileUtil();
        auudio = new Auudio();
        registerScreens();
    }

    public static void registerScreens() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
            Screen parentScreen = Minecraft.getInstance().screen;
            return new ConfigMenuWarn(parentScreen);
        }));
    }
}
