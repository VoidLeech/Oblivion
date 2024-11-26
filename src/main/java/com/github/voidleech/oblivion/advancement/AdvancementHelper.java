package com.github.voidleech.oblivion.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementHelper {

    @Deprecated(since = "2.1.0")
    public static void grantByName(String name, Player player) {
        if (player instanceof ServerPlayer serverPlayer){
            grantByName(name, serverPlayer);
        }
    }

    @Deprecated(since = "2.1.0")
    public static void grantByName(String name, ServerPlayer serverPlayer){
        grantByName(new ResourceLocation(name), serverPlayer);
    }

    public static void grantByName(ResourceLocation name, Player player) {
        if (player instanceof ServerPlayer serverPlayer){
            grantByName(name, serverPlayer);
        }
    }

    public static void grantByName(ResourceLocation name, ServerPlayer serverPlayer){
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(name);
        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        while (progress.getRemainingCriteria().iterator().hasNext()){
            serverPlayer.getAdvancements().award(advancement, progress.getRemainingCriteria().iterator().next());
        }
    }
}
