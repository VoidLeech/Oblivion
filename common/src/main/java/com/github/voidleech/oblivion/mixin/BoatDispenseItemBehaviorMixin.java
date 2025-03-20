package com.github.voidleech.oblivion.mixin;

import com.github.voidleech.oblivion.entities.OblivionBoatDispenseItemBehavior;
import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatDispenseItemBehavior.class)
public abstract class BoatDispenseItemBehaviorMixin {

    @Shadow @Final private boolean isChestBoat;

    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean oblivion$dispenseOblivionBoat(Level instance, Entity entity, Operation<Boolean> original,
    /* ugly locals cause Forge has a patch here */ @Local(ordinal = 1) double d1, @Local(ordinal = 2) double d2, @Local(ordinal = 3) double d3, @Local(ordinal = 4) double d4) {
        if ((Object)this instanceof OblivionBoatDispenseItemBehavior behavior) {
            var boat = isChestBoat ? new OblivionChestBoatEntity(instance, entity.xo, entity.yo, entity.zo) :
                    new OblivionBoatEntity(instance, entity.xo, entity.yo, entity.zo);
            boat.setVariant(behavior.getType());
            boat.setYRot(entity.getYRot());
            boat.setPos(d1, d2 + d4, d3);
            return original.call(instance, boat);
        }
        return original.call(instance, entity);
    }
}
