package com.github.voidleech.oblivion.blocks.entities;

import com.github.voidleech.oblivion.registry.OblivionBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OblivionSignBlockEntity extends SignBlockEntity {

    public OblivionSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OblivionBlockEntities.SIGN.get(), pPos, pBlockState);
    }
}
