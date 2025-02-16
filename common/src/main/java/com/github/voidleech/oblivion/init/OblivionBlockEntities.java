package com.github.voidleech.oblivion.init;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.blocks.entities.OblivionHangingSignBlockEntity;
import com.github.voidleech.oblivion.blocks.entities.OblivionSignBlockEntity;
import com.github.voidleech.oblivion.services.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class OblivionBlockEntities {

    public static Supplier<BlockEntityType<OblivionSignBlockEntity>> SIGN = register("sign", () ->
            BlockEntityType.Builder.of(OblivionSignBlockEntity::new,
                    OblivionInternal.SIGNS.toArray(new Block[0])
            ).build(null));

    public static Supplier<BlockEntityType<OblivionHangingSignBlockEntity>> HANGING_SIGN = register("hanging_sign", () ->
            BlockEntityType.Builder.of(OblivionHangingSignBlockEntity::new,
                    OblivionInternal.HANGING_SIGNS.toArray(new Block[0])
            ).build(null));

    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> register(String name, Supplier<T> sup){
        return Services.PLATFORM.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Oblivion.MOD_ID, name, sup);
    }

    public static void init() {
    }
}