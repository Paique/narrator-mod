package net.paiique.brpacks.narrator.forge;

import net.minecraft.core.BlockPos;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;

/**
 * @author paique
 * @version 1.0
 */

public class Events {
    private static int actionsPoints = 0;

    private static BlockPos lastPlacedBlockPos = new BlockPos(0, 1256, 0);
    private static BlockPos lastBreakedBlockPos = new BlockPos(0, 1256, 0);

    private static String lastPlacedBlockName;
    private static String lastBreakedBlockName;

    private static boolean firstLogin = true;


    @SubscribeEvent
    public static void onServerStarting(PlayerEvent.PlayerLoggedInEvent event) {
        String levelName = event.getEntity().level().getServer().getWorldData().getLevelName();
        System.out.println(event.getEntity().level().getBiome(event.getEntity().blockPosition()).value());
        if (firstLogin)
            NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " criou um mundo com o nome \"" + levelName + "\", e entrou nele (Fale do nome do mundo se for muito estranho, ou diferente).");
        else
            NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " entrou novamente no mundo \"" + levelName + "\" após ser julgado por sair dele.");
        firstLogin = false;
        if (!NarratorMod.post.isAlive()) NarratorMod.post.start();
    }

    @SubscribeEvent
    public static void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " craftou um(a) " + event.getCrafting().getDisplayName().getString() + ".");
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level().isClientSide) return;
        System.out.println(lastPlacedBlockPos + " : " + event.getPos());
        System.out.println(event.getPlayer().getName().getString() + " quebrou um(a) " + event.getState().getBlock().getName().getString() + " do chão" + (lastPlacedBlockPos.equals(event.getPos()) && !lastPlacedBlockName.isBlank() ? " onde ele havia colocado o bloco " + lastPlacedBlockName + " anteriormente." : "."));
        NarratorMod.data.actualAiText.add(event.getPlayer().getName().getString() + " quebrou um(a) " + event.getState().getBlock().getName().getString() + " do chão" + (lastPlacedBlockPos.equals(event.getPos()) && !lastPlacedBlockName.isBlank() ? " onde ele havia colocado o bloco " + lastPlacedBlockName + " anteriormente." : "."));
        lastBreakedBlockPos = event.getPos();
        lastBreakedBlockName = event.getState().getBlock().getName().getString();
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;
        System.out.println(lastBreakedBlockPos + " : " + event.getPos());
        System.out.println(event.getEntity().getName().getString() + " colocou um(a) " + event.getState().getBlock().getName().getString() + " no chão" + (lastBreakedBlockPos.equals(event.getPos()) && !lastBreakedBlockName.isBlank() ? " onde ele havia quebrado o bloco " + lastBreakedBlockName + " anteriormente." : "."));
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " colocou um(a) " + event.getState().getBlock().getName().getString() + " no chão" + (lastBreakedBlockPos.equals(event.getPos()) && !lastBreakedBlockName.isBlank() ? " onde ele havia quebrado o bloco " + lastBreakedBlockName + " anteriormente." : "."));
        lastPlacedBlockPos = event.getPos();
        lastPlacedBlockName = event.getState().getBlock().getName().getString();
        actionsPoints += 5;
    }

    @SubscribeEvent
    public static void onPlayerDeathEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " morreu.");
        actionsPoints += 1;
    }

    @SubscribeEvent
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " renasceu.");
        actionsPoints += 5;
    }

    @SubscribeEvent
    public static void onCollectItemEvent(PlayerEvent.ItemPickupEvent event) {
        if (event.getEntity().level().isClientSide) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " coletou um(a) " + event.getStack().getDisplayName().getString() + ".");
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onInteractEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        Block block = event.getEntity().level().getBlockState(event.getPos()).getBlock();
        if (!NarratorMod.data.iterableBlocks.contains(block)) return;
        event.getEntity().sendSystemMessage(Component.literal(block.getName().getString()));
        NarratorMod.data.actualAiText.add("O player " + event.getEntity().getName().getString() + "interagiu com o bloco " + block.getName().getString() + ".");
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onMobDamageEvent(LivingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;
        NarratorMod.data.actualAiText.add("O player " + player.getName().getString() + " atacou um(a) " + event.getEntity().getName().getString() + (player.getMainHandItem().isEmpty() ? " usando as mãos nuas." : " usando " + player.getMainHandItem().getDisplayName().getString() + "."));
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onMobDeathEvent(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;
        System.out.println("Morte");
        if (event.getEntity() instanceof Player) return;
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;
        NarratorMod.data.actualAiText.add("O player " + player.getName().getString() + " matou um(a) " + event.getEntity().getName().getString() + (player.getMainHandItem().isEmpty() ? " usando as mãos nuas." : " usando " + player.getMainHandItem().getDisplayName().getString() + "."));
        actionsPoints += 30;
    }

    @SubscribeEvent
    public static void onAdvancementEvent(AdvancementEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getAdvancement().value().name().isEmpty()) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " obteve a conquista " + event.getAdvancement().value().name().get().getString() + ".");
        actionsPoints += 40;
    }

    @SubscribeEvent
    public static void onGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (event.getEntity().level().isClientSide) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " mudou de modo de jogo para " + event.getNewGameMode().getName() + ", estaria ele trapaceando?");
        actionsPoints += 20;
    }

    @SubscribeEvent
    public static void onSleepEvent(PlayerSleepInBedEvent event) {
        if (event.getEntity().level().isClientSide) return;
        NarratorMod.data.actualAiText.add(event.getEntity().getName().getString() + " dormiu em uma cama, e amanhaceu descansado.");
        actionsPoints += 100;
    }

    @SubscribeEvent
    public static void onClientCloseWorld(PlayerEvent.PlayerLoggedOutEvent event) {
        NarratorMod.data.actualAiText.add("O player saiu do mundo, e retornou ao menu, julgue ele por isso.");
        NarratorMod.post.start();
        actionsPoints += 0;
    }


    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {

            if (NarratorMod.post.lock) {
                event.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.sendSystemMessage(Component.literal("DEBUG: Narrador falando"), true);
                });
                return;
            }

            event.getServer().getPlayerList().getPlayers().forEach(player -> {
                player.sendSystemMessage(Component.literal("DEBUG: Próxima verificação: " + tickCounter + "/200 | Pontos de Ação: " + actionsPoints + "/300"), true);
            });

            tickCounter++;

            if (tickCounter >= 200) {
                if (actionsPoints >= 300) {
                    actionsPoints = 0;
                    NarratorMod.post.start();
                }

                // Reinicia o contador de ticks
                tickCounter = 0;
            }
        }
    }
}
