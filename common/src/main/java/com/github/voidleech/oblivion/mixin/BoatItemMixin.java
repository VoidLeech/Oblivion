package com.github.voidleech.oblivion.mixin;

import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import com.github.voidleech.oblivion.items.OblivionBoatItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatItem.class)
public abstract class BoatItemMixin extends Item {
    @Final
    @Shadow
    private boolean hasChest;

    public BoatItemMixin(Properties properties) {
        super(properties);
    }

    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BoatItem;getBoat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/vehicle/Boat;"))
    private Boat oblivion$getBoat(Boat original, @Local(argsOnly = true) Level level, @Local HitResult hitResult) {
        if ((Object)this instanceof OblivionBoatItem boatItem) {
            var ret = hasChest ? new OblivionChestBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) :
                    new OblivionBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
            ret.setVariant(boatItem.getModType());
            return ret;
        }
        return original;
    }
}
