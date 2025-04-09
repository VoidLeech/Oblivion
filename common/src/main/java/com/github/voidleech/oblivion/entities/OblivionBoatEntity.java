package com.github.voidleech.oblivion.entities;

import com.github.voidleech.oblivion.init.OblivionEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class OblivionBoatEntity extends Boat implements OblivionBoat {
    private static final EntityDataAccessor<String> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.STRING);

    public OblivionBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public OblivionBoatEntity(Level level, double pX, double pY, double pZ) {
        this(OblivionEntities.BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public boolean fireImmune() {
        return super.fireImmune() || getModVariant().fireResistant();
    }

    @Override
    public Item getDropItem() {
        return getModVariant().getBoat();
    }

    public void setVariant(OblivionBoatType pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.getSerializedName());
    }

    public OblivionBoatType getModVariant() {
        return OblivionBoatType.fromSerializedName(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, OblivionBoatType.FallbackType.FALLBACK.getName());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(OblivionBoatType.fromSerializedName(pCompound.getString("Type")));
        }
    }
}
