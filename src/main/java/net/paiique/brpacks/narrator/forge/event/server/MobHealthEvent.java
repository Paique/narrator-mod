package net.paiique.brpacks.narrator.forge.event.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.data.Data;
import net.paiique.brpacks.narrator.forge.event.interfaces.EventInterface;

public class MobHealthEvent extends Data implements EventInterface {

    @SubscribeEvent
    public static void onMobDamageEvent(LivingDamageEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;
        actualAiText.add("O player " + player.getName().getString() + " atacou um(a) " + event.getEntity().getName().getString() + (player.getMainHandItem().isEmpty() ? " usando as mãos sem items." : " usando " + player.getMainHandItem().getDisplayName().getString() + ".") + " {médio}");
        actionsPoints += 10;
    }

    @SubscribeEvent
    public static void onMobDeathEvent(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (event.getEntity() instanceof Player) return;
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;
        actualAiText.add("O player " + player.getName().getString() + " matou um(a) " + event.getEntity().getName().getString() + (player.getMainHandItem().isEmpty() ? " usando as mãos sem items." : " usando " + player.getMainHandItem().getDisplayName().getString() + ".") + "{médio}");
        actionsPoints += 30;
    }
}
