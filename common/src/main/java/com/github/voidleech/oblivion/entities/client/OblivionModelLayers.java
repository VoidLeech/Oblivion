package com.github.voidleech.oblivion.entities.client;

import com.github.voidleech.oblivion.Oblivion;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class OblivionModelLayers {
    public static final ModelLayerLocation FALLBACK_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Oblivion.MOD_ID, "boat/fallback"), "main");
    public static final ModelLayerLocation FALLBACK_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Oblivion.MOD_ID, "chest_boat/fallback"), "main");
}
