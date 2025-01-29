package com.github.voidleech.oblivion.blocks.entities;

import com.github.voidleech.oblivion.registry.OblivionBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OblivionHangingSignBlockEntity extends HangingSignBlockEntity {

    public OblivionHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }
}
