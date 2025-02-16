package com.github.voidleech.oblivion.blocks;

import com.github.voidleech.oblivion.blocks.entities.OblivionSignBlockEntity;
import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import com.github.voidleech.oblivion.init.OblivionInternal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class OblivionStandingSignBlock extends StandingSignBlock {
    public OblivionStandingSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
        OblivionInternal.SIGNS.add(this);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OblivionSignBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type){
        return createTickerHelper(type, OblivionBlockEntities.SIGN.get(), SignBlockEntity::tick);
    }
}
