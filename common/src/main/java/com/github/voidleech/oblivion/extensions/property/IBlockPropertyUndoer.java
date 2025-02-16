package com.github.voidleech.oblivion.extensions.property;

import net.minecraft.world.level.block.state.BlockBehaviour;

public interface IBlockPropertyUndoer {
    BlockBehaviour.Properties oblivion$collision();
    BlockBehaviour.Properties oblivion$occlusion();
    BlockBehaviour.Properties oblivion$noRandomTicks();
    BlockBehaviour.Properties oblivion$yesLootTable();
}
