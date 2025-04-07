package com.github.voidleech.oblivion.entities;

import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.world.entity.vehicle.Boat;

public class OblivionBoatDispenseItemBehavior extends BoatDispenseItemBehavior {
    private final OblivionBoatType type;
    public OblivionBoatDispenseItemBehavior(OblivionBoatType type, boolean isChestBoat) {
        super(type.usesRaftModel() ? Boat.Type.BAMBOO : Boat.Type.OAK, isChestBoat);
        this.type = type;
    }

    public OblivionBoatType getType(){
        return type;
    }
}
