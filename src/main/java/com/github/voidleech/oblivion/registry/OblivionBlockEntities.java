package com.github.voidleech.oblivion.registry;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.blocks.entities.OblivionHangingSignBlockEntity;
import com.github.voidleech.oblivion.blocks.entities.OblivionSignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OblivionBlockEntities {
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oblivion.MOD_ID);

    private static final Set<SignBlock> SIGNS = Collections.synchronizedSet(new HashSet<>());
    private static final Set<SignBlock> HANGING_SIGNS = Collections.synchronizedSet(new HashSet<>());

    public static RegistryObject<BlockEntityType<OblivionSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () ->
            BlockEntityType.Builder.of(OblivionSignBlockEntity::new,
                    SIGNS.toArray(new Block[0])
            ).build(null));

    public static RegistryObject<BlockEntityType<OblivionHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () ->
            BlockEntityType.Builder.of(OblivionHangingSignBlockEntity::new,
                    HANGING_SIGNS.toArray(new Block[0])
            ).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }

    @ApiStatus.Internal
    public static void addSign(SignBlock sign){
        SIGNS.add(sign);
    }

    @ApiStatus.Internal
    public static void addHangingSign(SignBlock hangingSign){
        HANGING_SIGNS.add(hangingSign);
    }
}