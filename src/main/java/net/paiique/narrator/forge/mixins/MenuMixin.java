package net.paiique.narrator.forge.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.paiique.narrator.forge.config.ConfigCommon;
import net.paiique.narrator.forge.menu.OpenAiKeyErrorScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class)
public abstract class MenuMixin extends Screen {

    @Shadow
    protected abstract void createNormalMenuOptions(int p_96764_, int p_96765_);
    @Shadow
    protected abstract void init();

    @Shadow(remap = false)
    private TitleScreenModUpdateIndicator modUpdateNotification = null;

    protected MenuMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Unique
    SplashRenderer narratorMod$splash = Minecraft.getInstance().getSplashManager().getSplash();
    @Unique
    Component narratorMod$copyText = Component.literal("Copyright da Mojang AB, e BrPacks. Não distribuir!");

    @Unique
    int widthCenter;
    @Unique
    int heightCenter;
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();
        if (this.narratorMod$splash == null) {
            this.narratorMod$splash = this.minecraft.getSplashManager().getSplash();
        }

        int i = this.font.width(narratorMod$copyText);
        int j = this.width - i - 2;

        widthCenter = this.width / 2 - 100;
        heightCenter = this.height / 4 + 40;

        Button modButton = null;

        modUpdateNotification = TitleScreenModUpdateIndicator.init(new TitleScreen(), modButton);
        this.createNormalMenuOptions(heightCenter + 8, 24);
        this.addRenderableWidget(Button.builder(Component.translatable("fml.menu.mods"), (p_280830_) -> {
            this.minecraft.setScreen(new ModListScreen(this));
        }).bounds(widthCenter, heightCenter + 42, 200, 20).build());


        int quitAndOptionsOffset = 86;
        this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), (p_280831_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(widthCenter, heightCenter + quitAndOptionsOffset, 98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (p_280835_) -> {
            this.minecraft.stop();
        }).bounds(widthCenter + 102, heightCenter + quitAndOptionsOffset, 98, 20).build());
        this.addRenderableWidget(new PlainTextButton(j, this.height - 10, i, 10, narratorMod$copyText, (p_280834_) -> {
            this.minecraft.setScreen(new CreditsAndAttributionScreen(this));
        }, this.font));
    }
    @Unique
    Tooltip narratorMod$apiKeytooltip = Tooltip.create(Component.literal("§cVocê precisa definir a sua key da OpenAI.§f"));
    @Unique
    Tooltip narratorMod$apiKeytooltip2 = Tooltip.create(Component.literal("§cVocê precisa definir a sua key da OpenAI.§f"));

    @Inject(method = "createNormalMenuOptions", at = @At("HEAD"), cancellable = true)
    private void createNormalMenuOptions(int p_96764_, int p_96765_, CallbackInfo ci) {
        
        ci.cancel();

        if (ConfigCommon.OPENAI_API_KEY.get().equals("CHANGE_ME")) {
            this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (p_280833_) -> {
                this.minecraft.setScreen(new OpenAiKeyErrorScreen());
            }).bounds(widthCenter, heightCenter - 2, 200, 20).build()).setTooltip(narratorMod$apiKeytooltip);

        } else {
            this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (p_280833_) -> {
                this.minecraft.setScreen(new SelectWorldScreen(this));
            }).bounds(widthCenter, heightCenter - 2, 200, 20).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (p_280833_) -> {
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }).bounds(widthCenter, heightCenter + 20, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("options.language"), (p_280833_) -> {
            this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }).bounds(widthCenter, heightCenter + 64, 200, 20).build());

    }
}
