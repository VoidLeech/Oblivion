package com.github.voidleech.oblivion.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementHelper {
    /**
     * Grants an advancement by name, regardless of actual criteria completed.
     * @param name
     * @param player
     */
    public static void grantByName(ResourceLocation name, Player player) {
        if (player instanceof ServerPlayer serverPlayer){
            grantByName(name, serverPlayer);
        }
    }

    /**
     * Grants an advancement by name, regardless of actual criteria completed.
     * @param name
     * @param serverPlayer
     */
    public static void grantByName(ResourceLocation name, ServerPlayer serverPlayer){
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(name);
        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        while (progress.getRemainingCriteria().iterator().hasNext()){
            serverPlayer.getAdvancements().award(advancement, progress.getRemainingCriteria().iterator().next());
        }
    }
}
