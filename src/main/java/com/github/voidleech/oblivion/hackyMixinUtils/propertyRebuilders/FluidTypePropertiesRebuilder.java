package com.github.voidleech.oblivion.hackyMixinUtils.propertyRebuilders;

import net.minecraftforge.fluids.FluidType;

import javax.annotation.CheckReturnValue;

public class FluidTypePropertiesRebuilder {
    private final FluidType fluidType;

    private FluidTypePropertiesRebuilder(FluidType fluidType) {
        this.fluidType = fluidType;
    }

    @CheckReturnValue
    public static FluidTypePropertiesRebuilder of(FluidType fluidType){
        return new FluidTypePropertiesRebuilder(fluidType);
    }

    @CheckReturnValue
    public FluidTypePropertiesRebuilder canSwim(boolean canSwim){
        fluidType.canSwim = canSwim;
        return this;
    }

    @CheckReturnValue
    public FluidTypePropertiesRebuilder canConvertToSource(boolean canConvertToSource){
        fluidType.canConvertToSource = canConvertToSource;
        return this;
    }

    @CheckReturnValue
    public FluidTypePropertiesRebuilder viscosity(int viscosity){
        fluidType.viscosity = viscosity;
        return this;
    }

    public void finalizeRebuild(){
        // Not actually strictly required due to what FluidType constructor does with the properties.
    }
}
