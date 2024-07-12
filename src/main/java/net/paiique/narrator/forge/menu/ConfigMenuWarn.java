package net.paiique.narrator.forge.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.paiique.narrator.NarratorMod;

import java.awt.*;
import java.util.Random;

public class ConfigMenuWarn extends Screen {

    Screen parentScreen;

    public ConfigMenuWarn(Screen parentScreen) {
        super(Component.literal("config_menu_warn"));
        this.parentScreen = parentScreen;
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
        heightCenter = this.height / 4 + 48;
        button = this.addRenderableWidget(Button.builder(Component.literal("Exibir"), (p_280830_) -> {
            this.minecraft.setScreen(new OpenAiConfigScreen(parentScreen));
        }).bounds(widthCenter, heightCenter + 50, 100, 20).build());
        button.setFGColor(Color.RED.getRGB());
        buttonX = button.getX();
        buttonY = button.getY();

        this.addRenderableWidget(Button.builder(Component.literal("Cancelar"), (p_280830_) -> {
            this.minecraft.setScreen(parentScreen);
        }).bounds(widthCenter + 100, heightCenter + 50, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);

        if (itemStack == null) {
            itemStack = new ItemStack(net.minecraft.world.item.Items.BARRIER);
        }

        guiGraphics.renderFakeItem(itemStack, widthCenter + 94, heightCenter - 50);
        guiGraphics.drawCenteredString(this.font, "§4§lAtenção!§f", widthCenter + 100, heightCenter - 20, 0);
        guiGraphics.drawCenteredString(this.font, "§f§nNão continue se o Minecraft estiver sendo stremado", widthCenter + 100, heightCenter - 5, 0);
        guiGraphics.drawCenteredString(this.font, "§fA sua key da OpenAi será §c§nexposta§f a quem estiver assistindo.", widthCenter + 100, heightCenter + 5, 0);

        if (button.isHovered()) {
            Random rand = NarratorMod.random;
            int x = rand.nextInt(3);
            int y = rand.nextInt(3);
            button.setX(button.getX() > buttonX ? button.getX() - x : button.getX() + x);
            button.setY(button.getY() > buttonY ? button.getY() - y : button.getY() + y);
        }

        if (!button.isHovered() && (button.getX() != buttonX || button.getY() != buttonY)) {
            button.setX(buttonX);
            button.setY(buttonY);
        }

    }
}