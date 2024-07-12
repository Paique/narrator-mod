package net.paiique.narrator.forge.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class GeneralSettingsMenu extends Screen {

    Screen parentScreen;

    public GeneralSettingsMenu(Screen parentScreen) {
        super(Component.literal("general_settings"));
        this.parentScreen = parentScreen;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parentScreen);
    }

    private ItemStack itemStack;

    private int widthCenter;
    private int heightCenter;

    private Button button;
    private int buttonX;
    private int buttonY;

    @Override
    protected void init() {
        super.init();
        widthCenter = this.width / 2 - 100;
        heightCenter = this.height / 4 + 48 + 30;

        int buttonSize = 65;
        int buttonH = heightCenter - 130;
        int buttonW = widthCenter - 5;

        Button generalButton = this.addRenderableWidget(Button.builder(Component.literal("General"), (comp) -> {
            //this.minecraft.setScreen(new ConfigMenuWarn(this));
        }).bounds(buttonW, buttonH, buttonSize, 20).build());

        generalButton.active = false;

        buttonW += 75;

        Button elevenButton = this.addRenderableWidget(Button.builder(Component.literal("ElevenLabs"), (comp) -> {
            //this.minecraft.setScreen(new ConfigMenuWarn(this));
        }).bounds(buttonW, buttonH, buttonSize, 20).build());

        elevenButton.active = false;

        buttonW += 75;

        Button openAiButton = this.addRenderableWidget(Button.builder(Component.literal("OpenAI"), (comp) -> {
            //this.minecraft.setScreen(new ConfigMenuWarn(this));
        }).bounds(buttonW, buttonH, buttonSize, 20).build());

        openAiButton.active = false;

    }

    @Override
    public void render(GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        p_281549_.drawCenteredString(this.font, "§f§lEm desenvolvimento, configure na pasta config", this.width / 2, this.height / 2 - 20, 1);
        super.render(p_281549_, p_281550_, p_282878_, p_282465_);
    }
}
