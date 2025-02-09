package com.github.voidleech.oblivion.entities.client;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.entities.OblivionBoatEntity;
import com.github.voidleech.oblivion.entities.OblivionBoatType;
import com.github.voidleech.oblivion.entities.OblivionChestBoatEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OblivionBoatRenderer extends BoatRenderer {
    private final Map<OblivionBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources = Collections.synchronizedMap(new HashMap<>());

    private static final Map<Class<? extends OblivionBoatType>, String> boatTypeClasses = Collections.synchronizedMap(new HashMap<>());

    public OblivionBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
        registerBoatResources(OblivionBoatType.FallbackType.class, Oblivion.MOD_ID, pContext, pChestBoat);
        for (Map.Entry<Class<? extends OblivionBoatType>, String> entry : boatTypeClasses.entrySet()){
            registerBoatResources(entry.getKey(), entry.getValue(), pContext, pChestBoat);
        }
    }

    public static <T extends OblivionBoatType> void registerBoatResources(Class<T> clazz, String modId){
        boatTypeClasses.put(clazz, modId);
    }

    // This was nice and type-safe with a class intersection with Enum as well but that doesn't play nice with the necessary boatTypeClasses field D:
    private <T extends OblivionBoatType> void registerBoatResources(Class<T> clazz, String modId, EntityRendererProvider.Context pContext, boolean pChestBoat){
        for (OblivionBoatType type : clazz.getEnumConstants()){
            boatResources.put(type, Pair.of(new ResourceLocation(modId, getTextureLocation(type, pChestBoat)), createBoatModel(pContext, type, pChestBoat)));
        }
    }

    private static String getTextureLocation(OblivionBoatType pType, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png" : "textures/entity/boat/" + pType.getName() + ".png";
    }

    protected ListModel<Boat> createBoatModel(EntityRendererProvider.Context pContext, OblivionBoatType pType, boolean pChestBoat) {
        ModelLayerLocation modellayerlocation = pChestBoat ? OblivionBoatRenderer.createChestBoatModelName(pType) : OblivionBoatRenderer.createBoatModelName(pType);
        ModelPart modelpart = pContext.bakeLayer(modellayerlocation);
        if (pType.usesRaftModel()){
            return pChestBoat ? new ChestRaftModel(modelpart) : new RaftModel(modelpart);
        }
        return pChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
    }

    public static ModelLayerLocation createBoatModelName(OblivionBoatType pType) {
        return createLocation("boat/" + pType.getName(), "main", pType.getNamespace());
    }

    public static ModelLayerLocation createChestBoatModelName(OblivionBoatType pType) {
        return createLocation("chest_boat/" + pType.getName(), "main", pType.getNamespace());
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel, String modId) {
        return new ModelLayerLocation(new ResourceLocation(modId, pPath), pModel);
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if(boat instanceof OblivionBoatEntity modBoat) {
            return this.boatResources.get(modBoat.getModVariant());
        } else if(boat instanceof OblivionChestBoatEntity modChestBoatEntity) {
            return this.boatResources.get(modChestBoatEntity.getModVariant());
        } else {
            return null;
        }
    }
}
