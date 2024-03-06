package net.paiique.brpacks.narrator.forge.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.event.server.NarratorTickEvent;

public class SlashNarrator extends Data {

    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("narrador").executes(this::command));
    }

    private int command(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Você precisa ser um jogador para executar este comando!"));
            return 1;
        }

        if (!NarratorTickEvent.DISABLED) {
            player.sendSystemMessage(Component.literal("O narrador já está ativado!"));
            return 1;
        }
        NarratorTickEvent.DISABLED = false;
        narratorThreadLock = false;
        actualAiText.add("O narrador foi reativado após um problema técnico (literalmente), reclame sobre o usuário não ter configurado corretamente.");
        actionsPoints += 1000;
        return 0;
    }
}
