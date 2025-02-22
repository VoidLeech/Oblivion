package com.github.voidleech.oblivion.items;

import com.github.voidleech.oblivion.entities.OblivionBoat;
import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionBoatType;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class OblivionBoatItem extends BoatItem {
    private final OblivionBoatType type;

    public OblivionBoatItem(boolean pHasChest, OblivionBoatType pType, Item.Properties pProperties) {
        super(pHasChest, Boat.Type.OAK, pProperties);
        this.type = pType;
    }

    public OblivionBoatType getModType() {
        return this.type;
    }
}
