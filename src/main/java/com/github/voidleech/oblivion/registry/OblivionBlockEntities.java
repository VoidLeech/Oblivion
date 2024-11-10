package com.github.voidleech.oblivion.registry;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.blocks.entities.OblivionHangingSignBlockEntity;
import com.github.voidleech.oblivion.blocks.entities.OblivionSignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class OblivionBlockEntities {
    static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oblivion.MOD_ID);

    private static Set<Supplier<Block>> SIGNS = Collections.synchronizedSet(new HashSet<>());
    private static Set<Supplier<Block>> HANGING_SIGNS = Collections.synchronizedSet(new HashSet<>());

    public static RegistryObject<BlockEntityType<OblivionSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () ->
            BlockEntityType.Builder.of(OblivionSignBlockEntity::new,
                    SIGNS.stream().map(Supplier::get).toList().toArray(new Block[0])
            ).build(null));

    public static RegistryObject<BlockEntityType<OblivionHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () ->
            BlockEntityType.Builder.of(OblivionHangingSignBlockEntity::new,
                    HANGING_SIGNS.stream().map(Supplier::get).toList().toArray(new Block[0])
            ).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }

    public static void addSign(Supplier<Block> sign, Supplier<Block> wallSign, Supplier<Block> hanging, Supplier<Block> wallHanging){
        SIGNS.add(sign);
        SIGNS.add(wallSign);
        HANGING_SIGNS.add(hanging);
        HANGING_SIGNS.add(wallHanging);
    }
}