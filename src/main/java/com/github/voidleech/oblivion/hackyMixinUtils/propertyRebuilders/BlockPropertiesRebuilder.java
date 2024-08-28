package com.github.voidleech.oblivion.hackyMixinUtils.propertyRebuilders;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.CheckReturnValue;

public class BlockPropertiesRebuilder {
    private final Block block;
    private final BlockBehaviour.Properties properties;

    private BlockPropertiesRebuilder(Block block, BlockBehaviour.Properties properties){
        this.block = block;
        this.properties = properties;
    }

    @CheckReturnValue
    public static BlockPropertiesRebuilder of(Block block){
        return new BlockPropertiesRebuilder(block, block.properties);
    }

    @CheckReturnValue
    public BlockPropertiesRebuilder randomTicks(boolean randomTickingOn){
        block.isRandomlyTicking = randomTickingOn;
        properties.isRandomlyTicking = randomTickingOn;
        return this;
    }

    @CheckReturnValue
    public BlockPropertiesRebuilder collision(boolean collisionOn){
        block.hasCollision = collisionOn;
        properties.hasCollision = collisionOn;
        return this;
    }

    @CheckReturnValue
    public BlockPropertiesRebuilder soundType(SoundType soundType){
        block.soundType = soundType;
        properties.soundType = soundType;
        return this;
    }

    @CheckReturnValue
    public BlockPropertiesRebuilder pushReaction(PushReaction reaction){
        properties.pushReaction = reaction;
        return this;
    }

    @CheckReturnValue
    public BlockPropertiesRebuilder strength(float strength){
        properties.strength(strength);
        return this;
    }

    public void finalizeRebuild(){
        block.properties = properties;
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(block);
        block.createBlockStateDefinition(builder);
        block.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        block.registerDefaultState(block.getStateDefinition().any());
    }
}
