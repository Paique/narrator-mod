package net.paiique.brpacks.narrator.forge.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.NarratorTickEvent;

import java.awt.*;
import java.net.URL;

public class ConfigMenu extends Screen {

    Screen parentScreen;

    public ConfigMenu(Screen parentScreen) {
        super(Component.literal("config_menu"));
        this.parentScreen = parentScreen;
    }

    private int widthCenter;
    private int heightCenter;

    @Override
    protected void init() {
        super.init();
        widthCenter = this.width / 2 - 100;
        heightCenter = this.height / 4 + 48 + 30;
        EditBox apiKey = new EditBox(this.font, widthCenter, heightCenter, 200, 20, Component.literal("ApiKey"));
        this.addRenderableWidget(apiKey);
        apiKey.setMaxLength(70);
        apiKey.setValue(ConfigCommon.OPENAI_API_KEY.get());
        this.addRenderableWidget(Button.builder(Component.literal("Obter uma key"),
                ConfirmLinkScreen.confirmLink(this, "https://platform.openai.com/api-keys")
        ).bounds(widthCenter, heightCenter - 50, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Salvar, e voltar"), (p_280830_) -> {
            ConfigCommon.OPENAI_API_KEY.set(apiKey.getValue());
            ConfigCommon.OPENAI_API_KEY.save();
            NarratorTickEvent.DISABLED = true;
            this.minecraft.setScreen(parentScreen);
        }).bounds(widthCenter - 1, heightCenter + 75, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Cancelar"), (p_280830_) -> {
            this.minecraft.setScreen(parentScreen);
        }).bounds(widthCenter + 101, heightCenter + 75, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        guiGraphics.drawCenteredString(this.font, "§fOpenAI Token", widthCenter + 100, heightCenter - 10, 0);
        guiGraphics.drawCenteredString(this.font, "§fNão possui uma key?", widthCenter + 100, heightCenter - 60, 0);
    }
}