package com.github.voidleech.oblivion.mixin;

import com.github.voidleech.oblivion.extensions.property.IBlockPropertyUndoer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.Properties.class)
public abstract class BlockPropertiesMixin implements IBlockPropertyUndoer {

    @Shadow
    boolean hasCollision;

    @Shadow
    boolean canOcclude;

    @Shadow
    boolean isRandomlyTicking;

    @Shadow ResourceLocation drops;

    @Override
    public BlockBehaviour.Properties oblivion$collision() {
        hasCollision = true;
        canOcclude = true;
        return (BlockBehaviour.Properties)(Object)this;
    }

    @Override
    public BlockBehaviour.Properties oblivion$occlusion() {
        canOcclude = true;
        return (BlockBehaviour.Properties)(Object)this;
    }

    @Override
    public BlockBehaviour.Properties oblivion$noRandomTicks() {
        isRandomlyTicking = false;
        return (BlockBehaviour.Properties)(Object)this;
    }

    @Override
    public BlockBehaviour.Properties oblivion$yesLootTable() {
        drops = null;
        return (BlockBehaviour.Properties)(Object)this;
    }
}
