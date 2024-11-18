package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.entities.client.OblivionBoatRenderer;
import com.github.voidleech.oblivion.entities.client.OblivionModelLayers;
import com.github.voidleech.oblivion.registry.OblivionBlockEntities;
import com.github.voidleech.oblivion.registry.OblivionComposting;
import com.github.voidleech.oblivion.registry.OblivionEntities;
import com.github.voidleech.oblivion.registry.OblivionFurnaceFuel;
import com.github.voidleech.oblivion.registry.OblivionPotionRecipes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Oblivion.MOD_ID)
public class Oblivion
{
    public static final String MOD_ID = "oblivion_api";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Oblivion()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        OblivionBlockEntities.register(modEventBus);
        OblivionEntities.register(modEventBus);

        OblivionComposting.register(modEventBus);
        OblivionPotionRecipes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        OblivionFurnaceFuel.register(MinecraftForge.EVENT_BUS, modEventBus);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OblivionConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                EntityRenderers.register(OblivionEntities.BOAT.get(), context -> new OblivionBoatRenderer(context, false));
                EntityRenderers.register(OblivionEntities.CHEST_BOAT.get(), context -> new OblivionBoatRenderer(context, true));
            });
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(OblivionBlockEntities.SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(OblivionBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        }

        @SubscribeEvent
        public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(OblivionModelLayers.FALLBACK_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(OblivionModelLayers.FALLBACK_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        }
    }
}
