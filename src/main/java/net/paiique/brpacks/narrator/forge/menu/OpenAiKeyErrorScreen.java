package net.paiique.brpacks.narrator.forge.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class OpenAiKeyErrorScreen extends Screen {
    public OpenAiKeyErrorScreen() {
        super(Component.literal("openai_key_error_screen"));
    }

    @Override
    public void render(GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        p_281549_.drawCenteredString(this.font, "§4Atenção:§f", this.width / 2, this.height / 2 - 20, 0);
        p_281549_.drawCenteredString(this.font, "§fPor favor defina a sua key da OpenAI indo em:", this.width / 2, this.height / 2, 0);
        p_281549_.drawCenteredString(this.font, "§eMods§f -> §eNarrator§f -> §eConfig§f -> §ekey§f", this.width / 2, this.height / 2 + 10, 0);
        p_281549_.drawCenteredString(this.font, "§fPressione §cesc§f para retornar ao menu.", this.width / 2, this.height / 2 + 20, 0);
        super.render(p_281549_, p_281550_, p_282878_, p_282465_);
    }
}