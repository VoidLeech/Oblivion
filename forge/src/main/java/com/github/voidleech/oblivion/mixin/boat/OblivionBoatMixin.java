package com.github.voidleech.oblivion.mixin.boat;

import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OblivionBoatEntity.class)
public abstract class OblivionBoatMixin extends Boat {

    public OblivionBoatMixin(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canBoatInFluid(FluidType type) {
        return super.canBoatInFluid(type) || (fireImmune() && type == ForgeMod.LAVA_TYPE.get());
    }

    @Override
    public boolean canBoatInFluid(FluidState state) {
        return super.canBoatInFluid(state) || (fireImmune() && state.getFluidType() == ForgeMod.LAVA_TYPE.get());
    }

    @Override
    public boolean shouldUpdateFluidWhileRiding(FluidState state, Entity rider) {
        return super.shouldUpdateFluidWhileRiding(state, rider) && !(fireImmune() && state.getFluidType() == ForgeMod.LAVA_TYPE.get());
    }
}
