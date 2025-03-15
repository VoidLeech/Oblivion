package com.github.voidleech.oblivion.mixin.accessor;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ComposterBlock.class)
public interface ComposterBlockInvoker {
    @Invoker("add")
    static void oblivion$add(float chance, ItemLike itemLike) {
        throw new AssertionError();
    }
}
