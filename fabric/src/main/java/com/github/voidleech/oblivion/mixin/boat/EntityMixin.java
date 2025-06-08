package com.github.voidleech.oblivion.mixin.boat;

import com.github.voidleech.oblivion.entities.OblivionBoat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @WrapOperation(method = "updateInWaterStateAndDoFluidPushing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateFluidHeightAndDoFluidPushing(Lnet/minecraft/tags/TagKey;D)Z"))
    private boolean oblivion_api$dontUpdateLavaHeightIfInLavaBoat(Entity instance, TagKey<Fluid> fluid, double height, Operation<Boolean> original){
        if (instance.getVehicle() instanceof OblivionBoat boat && boat.getModVariant().fireResistant()) {
            return false;
        }
        return original.call(instance, fluid, height);
    }
}
