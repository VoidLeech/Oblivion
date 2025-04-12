package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.entities.client.OblivionBoatRenderer;
import com.github.voidleech.oblivion.entities.client.OblivionModelLayers;
import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import com.github.voidleech.oblivion.init.OblivionEntities;
import com.github.voidleech.oblivion.mixin.boat.ItemBlockRenderTypesAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.material.Fluids;

public class OblivionFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OblivionClient.init();

        EntityRendererRegistry.register(OblivionEntities.BOAT.get(), context -> new OblivionBoatRenderer(context, false));
        EntityRendererRegistry.register(OblivionEntities.CHEST_BOAT.get(), context -> new OblivionBoatRenderer(context, true));

        BlockEntityRenderers.register(OblivionBlockEntities.SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(OblivionBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(OblivionModelLayers.FALLBACK_BOAT_LAYER, BoatModel::createBodyModel);
        EntityModelLayerRegistry.registerModelLayer(OblivionModelLayers.FALLBACK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

        ItemBlockRenderTypesAccessor.oblivion_api$TYPE_BY_FLUID().put(Fluids.LAVA, RenderType.translucent());
        ItemBlockRenderTypesAccessor.oblivion_api$TYPE_BY_FLUID().put(Fluids.FLOWING_LAVA, RenderType.translucent());
    }
}
