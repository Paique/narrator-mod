package net.paiique.brpacks.narrator.forge.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ModListScreen;
import net.paiique.brpacks.narrator.forge.config.ConfigCommon;
import net.paiique.brpacks.narrator.forge.event.NarratorTickEvent;

public class ConfigMenu extends Screen {
    public ConfigMenu() {
        super(Component.literal("config_menu"));
    }

    private int widthCenter;
    private int heightCenter;
    @Override
    protected void init() {
        super.init();
        widthCenter = this.width / 2 - 100;
        heightCenter = this.height / 4 + 48;
        EditBox apiKey = new EditBox(this.font, widthCenter, heightCenter, 200, 20, Component.literal("ApiKey"));
        this.addRenderableWidget(apiKey);
        apiKey.setValue(ConfigCommon.OPENAI_API_KEY.get());
        System.out.println(ConfigCommon.OPENAI_API_KEY.get());
        this.addRenderableWidget(Button.builder(Component.literal("Salvar, e voltar"), (p_280830_) -> {
            ConfigCommon.OPENAI_API_KEY.set(apiKey.getValue());
            ConfigCommon.OPENAI_API_KEY.save();
            NarratorTickEvent.DISABLED = true;
            this.minecraft.setScreen(new ModListScreen(new TitleScreen()));
        }).bounds(widthCenter, heightCenter + 100, 200, 20).build());

    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        guiGraphics.drawCenteredString(this.font, "§fOpenAI Token", widthCenter + 100, heightCenter - 10, 0);
    }
}