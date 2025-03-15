package com.github.voidleech.oblivion.mixin.accessor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PotionBrewing.class)
public interface PotionBrewingInvoker {
    @Invoker("addMix")
    static void oblivion$addMix(Potion potion, Item ingredient, Potion result) {
        throw new AssertionError();
    }
}
