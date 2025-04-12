package com.github.voidleech.oblivion.mixin.boat;

import com.github.voidleech.oblivion.entities.OblivionBoatDispenseItemBehavior;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatDispenseItemBehavior.class)
public abstract class FabricBoatDispenseItemBehaviorMixin extends DefaultDispenseItemBehavior {
    @ModifyExpressionValue(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private boolean oblivion_api$sinkInLava(boolean original, @Local Level level, @Local BlockPos pos) {
        return original || (
                (Object)this instanceof OblivionBoatDispenseItemBehavior behavior &&
                behavior.getType().fireResistant() && level.getFluidState(pos).is(FluidTags.LAVA)
        );
    }

    @ModifyExpressionValue(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 1))
    private boolean oblivion_api$lavaBoating(boolean original, @Local Level level, @Local BlockPos pos) {
        return original || (
                (Object)this instanceof OblivionBoatDispenseItemBehavior behavior &&
                        behavior.getType().fireResistant() && level.getFluidState(pos.below()).is(FluidTags.LAVA)
        );
    }
}
