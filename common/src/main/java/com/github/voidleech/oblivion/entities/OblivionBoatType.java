package com.github.voidleech.oblivion.entities;

import com.github.voidleech.oblivion.Oblivion;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface OblivionBoatType extends StringRepresentable{
    Map<String, OblivionBoatType> TYPES = Collections.synchronizedMap(new HashMap<>());
    default void initOBT(){
        TYPES.put(getSerializedName(), this);
    }

    @Override
    default String getSerializedName() {
        return getName() + ':' + getNamespace();
    }

    static OblivionBoatType fromSerializedName(String serializedName) {
        return TYPES.getOrDefault(serializedName, FallbackType.FALLBACK);
    }

    String getName();

    String getNamespace();
    Item getBoat();
    Item getChestBoat();
    default boolean usesRaftModel() {
        return false;
    }
    default boolean fireResistant() {
        return false;
    }
    Block getPlanks();

    enum FallbackType implements OblivionBoatType {
        FALLBACK;

        @Override
        public String getName() {
            return "fallback";
        }

        @Override
        public String getNamespace() {
            return Oblivion.MOD_ID;
        }

        // Something for the trouble of having a fallback boat in your world
        @Override
        public Item getBoat() {
            return Items.COOKIE;
        }

        @Override
        public Item getChestBoat() {
            return Items.COOKIE;
        }

        @Override
        public Block getPlanks() {
            return Blocks.CAKE;
        }
    }
}
