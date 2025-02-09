package com.github.voidleech.oblivion.util;

import com.github.voidleech.oblivion.services.Services;
import net.minecraft.core.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.function.Supplier;

public class Registration {
    /**
     * Registers an object
     * @param registry
     * @param modId
     * @param name
     * @param sup
     * @return
     * @param <I>
     * @param <T>
     */
    <I, T extends I> Supplier<T> register(Registry<I> registry, String modId, String name, Supplier<T> sup) {
        return Services.PLATFORM.register(registry, modId, name, sup);
    }

    /**
     * Registers a config
     * @param spec
     * @param type Side that the config applies to
     * @param modId
     */
    void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        Services.PLATFORM.registerConfig(spec, type, modId);
    }
}
