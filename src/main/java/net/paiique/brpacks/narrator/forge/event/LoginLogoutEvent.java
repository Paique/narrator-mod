package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.forge.commands.SlashNarrator;
import net.paiique.brpacks.narrator.interfaces.EventInterface;
import net.paiique.brpacks.narrator.data.Data;

/**
 * @author paique
 * @version 1.0
 */
public class LoginLogoutEvent extends EventData implements EventInterface {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        String levelName = event.getEntity().level().getServer().getWorldData().getLevelName();
        System.out.println(event.getEntity().level().getBiome(event.getEntity().blockPosition()).value());
        if (firstLogin) {
            NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " criou um mundo com o nome \"" + levelName + "\", e entrou nele (Fale do nome do mundo se for muito estranho, ou diferente).");
        } else NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " entrou novamente no mundo \"" + levelName + "\" após ser julgado por sair dele.");
        firstLogin = false;
        if (!NarratorMod.post.isAlive()) NarratorMod.post.start();
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        new SlashNarrator().register(event.getServer().getCommands().getDispatcher());
    }

    @SubscribeEvent
    public static void onClientCloseWorld(PlayerEvent.PlayerLoggedOutEvent ignored) {
        NarratorMod.data.actualAiText.add("O player saiu do mundo, e retornou ao menu, julgue ele por isso.");
        NarratorMod.post.start();
        actionsPoints += 0;
    }
}
