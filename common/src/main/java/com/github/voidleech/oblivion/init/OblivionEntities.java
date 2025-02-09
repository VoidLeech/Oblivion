package com.github.voidleech.oblivion.init;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import com.github.voidleech.oblivion.services.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class OblivionEntities {
    public static final Supplier<EntityType<OblivionBoatEntity>> BOAT = register("boat", () -> EntityType.Builder.<OblivionBoatEntity>of(
            OblivionBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("boat"));

    public static final Supplier<EntityType<OblivionChestBoatEntity>> CHEST_BOAT = register("chest_boat", () -> EntityType.Builder.<OblivionChestBoatEntity>of(
            OblivionChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("chest_boat"));

    public static <T extends EntityType<E>, E extends Entity> Supplier<T> register(String name, Supplier<T> sup){
        return Services.PLATFORM.register(BuiltInRegistries.ENTITY_TYPE, Oblivion.MOD_ID, name, sup);
    }

    public static void init(){
    }
}
