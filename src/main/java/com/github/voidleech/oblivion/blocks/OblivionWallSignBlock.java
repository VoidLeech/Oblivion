package com.github.voidleech.oblivion.blocks;

import com.github.voidleech.oblivion.blocks.entities.OblivionSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class OblivionWallSignBlock extends WallSignBlock implements INormalSign {
    public OblivionWallSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OblivionSignBlockEntity(pPos, pState);
    }
}
