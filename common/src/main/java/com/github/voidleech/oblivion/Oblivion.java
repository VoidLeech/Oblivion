package com.github.voidleech.oblivion;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class Oblivion {

    public static final String MOD_ID = "oblivion_api";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        OblivionBlockEntities.register(modEventBus);
        OblivionEntities.register(modEventBus);

        OblivionComposting.register(modEventBus);
        OblivionPotionRecipes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        OblivionFurnaceFuel.register(MinecraftForge.EVENT_BUS, modEventBus);
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