package com.github.voidleech.oblivion.mixin.packs;

import com.github.voidleech.oblivion.registration.BuiltInResourcePackSource;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @ModifyExpressionValue(method = "configurePackRepository", at = @At(value = "INVOKE", target = "Ljava/util/List;contains(Ljava/lang/Object;)Z"))
    private static boolean oblivion$enabledByDefaultYetOptionalPack(boolean original, @Local String name, @Local(argsOnly = true) PackRepository packRepository) {
        return original || (packRepository.getPack(name).getPackSource() instanceof BuiltInResourcePackSource pack && !pack.enabledByDefault());
    }
}
