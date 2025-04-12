package com.github.voidleech.oblivion.mixin.boat;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {
    @Accessor("TYPE_BY_FLUID")
    static Map<Fluid, RenderType> oblivion_api$TYPE_BY_FLUID() {
        throw new AssertionError();
    }
}
