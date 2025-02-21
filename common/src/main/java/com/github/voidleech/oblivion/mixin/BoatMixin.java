package com.github.voidleech.oblivion.mixin;

import com.github.voidleech.oblivion.entities.OblivionBoat;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class BoatMixin extends Entity {
    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;getPlanks()Lnet/minecraft/world/level/block/Block;"))
    private Block oblivion$correctPlanksOnBreak(Block original){
        if (this instanceof OblivionBoat boat) {
            return boat.getModVariant().getPlanks();
        }
        return original;
    }
}
