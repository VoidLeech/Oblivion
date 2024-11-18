package com.github.voidleech.oblivion.registry;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OblivionEntities {
    static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Oblivion.MOD_ID);
    public static final RegistryObject<EntityType<OblivionBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<OblivionBoatEntity>of(
            OblivionBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("boat"));

    public static final RegistryObject<EntityType<OblivionChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<OblivionChestBoatEntity>of(
            OblivionChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("chest_boat"));

    public static void register(IEventBus modEventBus){
        ENTITIES.register(modEventBus);
    }
}
