package com.github.voidleech.oblivion.items;

import com.github.voidleech.oblivion.entities.OblivionBoatDispenseItemBehavior;
import com.github.voidleech.oblivion.entities.OblivionBoatType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

public class OblivionBoatItem extends BoatItem {
    private final OblivionBoatType type;

    public OblivionBoatItem(boolean pHasChest, OblivionBoatType pType, Item.Properties pProperties) {
        super(pHasChest, Boat.Type.OAK, pProperties);
        this.type = pType;
        DispenserBlock.registerBehavior(this, new OblivionBoatDispenseItemBehavior(pType, pHasChest));
    }

    public OblivionBoatType getModType() {
        return this.type;
    }
}
