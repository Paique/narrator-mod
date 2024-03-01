package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

/**
 * @author paique
 * @version 1.0
 */
public class PlayerChangeLevelEvent extends EventData implements EventInterface {

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
}
