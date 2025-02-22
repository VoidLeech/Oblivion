package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.services.ForgePlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Oblivion.MOD_ID)
public class OblivionForge {
    
    public OblivionForge() {
        Oblivion.init();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        forgeEventBus.addListener(ForgePlatformHelper::addFurnaceFuels);

        modEventBus.addListener(ForgePlatformHelper::addBrewingRecipes);
        modEventBus.addListener(ForgePlatformHelper::addComposting);
        modEventBus.addListener(ForgePlatformHelper::registerFuels);
        modEventBus.addListener(ForgePlatformHelper::registerPacks);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OblivionForgeClient.init(forgeEventBus, modEventBus));
    }
}