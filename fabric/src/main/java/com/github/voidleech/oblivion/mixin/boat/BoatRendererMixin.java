package com.github.voidleech.oblivion.mixin.boat;

import com.github.voidleech.oblivion.entities.client.OblivionBoatRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
    at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object oblivion$correctBoatTexture(Object original, @Local(argsOnly = true) Boat boat){
        if ((Object)this instanceof OblivionBoatRenderer renderer) {
            return renderer.getModelWithLocation(boat);
        }
        return original;
    }
}
