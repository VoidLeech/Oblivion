package com.github.voidleech.oblivion.mixin.boat;

import com.github.voidleech.oblivion.entities.OblivionBoat;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class FabricBoatMixin extends Entity {
    public FabricBoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(method = {"getWaterLevelAbove", "checkInWater", "Lnet/minecraft/world/entity/vehicle/Boat;isUnderwater()Lnet/minecraft/world/entity/vehicle/Boat$Status;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean oblivion_api$lavaBoating(boolean original, @Local FluidState state) {
        return original || (this instanceof OblivionBoat boat && boat.getModVariant().fireResistant() && state.is(FluidTags.LAVA));
    }

    @ModifyExpressionValue(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean oblivion_api$fallOnLava(boolean original) {
        return original || (this instanceof OblivionBoat boat && boat.getModVariant().fireResistant() &&
                level().getFluidState(blockPosition().below()).is(FluidTags.LAVA));
    }

    @ModifyExpressionValue(method = "canAddPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean oblivion_api$lavaPassenger(boolean original) {
        return original || (this instanceof OblivionBoat boat && boat.getModVariant().fireResistant() && isEyeInFluid(FluidTags.LAVA));
    }
}
