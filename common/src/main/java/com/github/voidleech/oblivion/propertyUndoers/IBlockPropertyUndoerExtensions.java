package com.github.voidleech.oblivion.propertyUndoers;

import net.minecraft.world.level.block.state.BlockBehaviour;

public interface IBlockPropertyUndoerExtensions {
    BlockBehaviour.Properties oblivion$collision();
    BlockBehaviour.Properties oblivion$occlusion();
    BlockBehaviour.Properties oblivion$noRandomTicks();
    BlockBehaviour.Properties oblivion$yesLootTable();
}
