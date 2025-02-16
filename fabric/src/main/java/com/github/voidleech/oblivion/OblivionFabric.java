package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.blocks.OblivionHangingSignBlock;
import com.github.voidleech.oblivion.blocks.OblivionStandingSignBlock;
import com.github.voidleech.oblivion.blocks.OblivionWallHangingSignBlock;
import com.github.voidleech.oblivion.blocks.OblivionWallSignBlock;
import com.github.voidleech.oblivion.extensions.SupportsAdditionalBlocks;
import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class OblivionFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Oblivion.init();
        RegistryEntryAddedCallback.event(BuiltInRegistries.BLOCK).register(OblivionFabric::oblivionSignAdded);
    }

    private static void oblivionSignAdded(int rawId, ResourceLocation resourceLocation, Block block){
        if (block instanceof OblivionHangingSignBlock || block instanceof OblivionWallHangingSignBlock) {
            ((SupportsAdditionalBlocks)OblivionBlockEntities.HANGING_SIGN.get()).oblivion$addSupportedBlock(block);
        }
        if (block instanceof OblivionStandingSignBlock || block instanceof OblivionWallSignBlock){
            ((SupportsAdditionalBlocks)OblivionBlockEntities.SIGN.get()).oblivion$addSupportedBlock(block);
        }
    }
}
