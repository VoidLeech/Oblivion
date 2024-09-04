package com.github.voidleech.oblivion.registry;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.text.WordUtils;

import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractOblivionPacks {
    private final Map<String, Tuple<Component, Boolean>> RESOURCE_PACKS;
    private final String MOD_ID;
    private final String ABBREVIATION;

    protected AbstractOblivionPacks(Map<String, Tuple<Component, Boolean>> resourcePacks, String modId, String abrv) {
        RESOURCE_PACKS = resourcePacks;
        MOD_ID = modId;
        ABBREVIATION = abrv;
    }

    /**
     * @param modEventBus mod event bus
     */
    public void register(IEventBus modEventBus){
        modEventBus.addListener(this::addResourcePacks);
    }

    private void addResourcePacks(AddPackFindersEvent event){
        if (event.getPackType() == PackType.CLIENT_RESOURCES){
            for (Map.Entry<String, Tuple<Component, Boolean>> pack : RESOURCE_PACKS.entrySet()){
                addResourcePack(event, pack.getKey(), pack.getValue().getB());
            }
        }
    }

    private void addResourcePack(AddPackFindersEvent event, String packName, boolean required){
        Path resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("packs/resource/" + packName);
        Pack pack = Pack.create("builtin/" + packName,
                Component.literal("(" + ABBREVIATION + (!required ? " Alt) " : ") ") + WordUtils.capitalize(packName.replace('_', ' ').toLowerCase())),
                required,
                (path) -> new PathPackResources(path, resourcePath, true),
                new Pack.Info(RESOURCE_PACKS.getOrDefault(packName, new Tuple<>(Component.empty(), false)).getA(), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), FeatureFlagSet.of()),
                PackType.CLIENT_RESOURCES,
                Pack.Position.TOP,
                false,
                PackSource.BUILT_IN
        );
        event.addRepositorySource((packConsumer) -> {packConsumer.accept(pack);});
    }
}
