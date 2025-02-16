package com.github.voidleech.oblivion.util;

import com.github.voidleech.oblivion.services.Services;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;
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
    public static <I, T extends I> Supplier<T> register(Registry<I> registry, String modId, String name, Supplier<T> sup) {
        return Services.PLATFORM.register(registry, modId, name, sup);
    }

    /**
     * Registers a config
     * @param spec
     * @param type Side that the config applies to
     * @param modId
     */
    public static void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        Services.PLATFORM.registerConfig(spec, type, modId);
    }

    /**
     * Registers additional resource/data packs
     * @param packs
     * @param modId
     */
    public static void registerPacks(List<PackData> packs, String modId) {
        for (PackData data : packs) {
            Services.PLATFORM.addPack(data, modId);
        }
    }

    /**
     * Metadata necessary for finding or adding an additional resource/data pack.
     * @param name File name of the pack: save your pack, along with a pack.mcmeta file, in src/main/resources/packs/{resource/data}/name
     * @param display How you want your pack's name to be displayed.
     * @param required Whether the pack should always be on
     * @param enabledByDefault Whether the pack should be on by default
     * @param type Whether the pack is a resource or a data pack
     */
    public record PackData(String name, Component display, boolean required, boolean enabledByDefault, PackType type){

    }
}
