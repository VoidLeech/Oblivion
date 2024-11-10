package com.github.voidleech.oblivion.mixin;

import com.github.voidleech.oblivion.propertyUndoers.IBlockPropertyUndoerExtensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.Properties.class)
public class BlockPropertiesMixin implements IBlockPropertyUndoerExtensions {

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
