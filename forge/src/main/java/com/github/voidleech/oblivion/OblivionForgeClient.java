package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.entities.client.OblivionBoatRenderer;
import com.github.voidleech.oblivion.entities.client.OblivionModelLayers;
import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import com.github.voidleech.oblivion.init.OblivionEntities;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class OblivionForgeClient {
    public static void init(IEventBus forgeEventBus, IEventBus modEventBus){
        OblivionClient.init();
        modEventBus.addListener(OblivionForgeClient::onClientSetup);
        modEventBus.addListener(OblivionForgeClient::registerBER);
        modEventBus.addListener(OblivionForgeClient::registerModelLayers);
    }

    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            EntityRenderers.register(OblivionEntities.BOAT.get(), context -> new OblivionBoatRenderer(context, false));
            EntityRenderers.register(OblivionEntities.CHEST_BOAT.get(), context -> new OblivionBoatRenderer(context, true));
        });
    }

    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(OblivionBlockEntities.SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(OblivionBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(OblivionModelLayers.FALLBACK_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(OblivionModelLayers.FALLBACK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
    }
}
