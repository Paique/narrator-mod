package net.paiique.brpacks.narrator.forge.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.openai.Post;

public class SlashNarrator {

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("narrador").executes(this::command));
    }


    private int command(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();

        if (player == null) return 0;
        player.sendSystemMessage(Component.literal("Narrador reativado"), false);

        Post post = new Post();
        NarratorMod.data.actualAiText.add("O narrador foi reativado após um problema técnico (literalmente), reclame sobre a internet do jogador.");
        post.start();
        return 0;
    }

}
