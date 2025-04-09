package com.github.voidleech.oblivion.mixin.sign;

import com.github.voidleech.oblivion.blocks.entities.OblivionHangingSignBlockEntity;
import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(HangingSignBlockEntity.class)
public class HangingSignBlockEntityMixin {

    @Unique
    private static final StackWalker oblivion$stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    @ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;HANGING_SIGN:Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static BlockEntityType oblivion$correctBET(BlockEntityType<HangingSignBlockEntity> original){
        // First frame that wasn't mixed into HangingSignBlockEntity (typically: subclass, except for vanilla where it's the block classes)
        Optional<StackWalker.StackFrame> stackFrame = oblivion$stackWalker.walk(s -> s.filter(f -> !f.getDeclaringClass().isAssignableFrom(HangingSignBlockEntity.class)).findFirst());
        // This optional can't be empty; for it to be someone must be doing something incredibly weird.
        Class clazz = stackFrame.get().getDeclaringClass();
        if (OblivionHangingSignBlockEntity.class.isAssignableFrom(clazz)) {
            return OblivionBlockEntities.HANGING_SIGN.get();
        }
        return original;
    }
}
