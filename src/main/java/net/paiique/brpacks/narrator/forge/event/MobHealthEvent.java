package net.paiique.brpacks.narrator.forge.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.paiique.brpacks.narrator.NarratorMod;
import net.paiique.brpacks.narrator.data.EventData;
import net.paiique.brpacks.narrator.interfaces.EventInterface;

public class MobHealthEvent  extends EventData implements EventInterface {

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

}
