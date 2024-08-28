package com.github.voidleech.oblivion.registry;

import net.minecraft.util.Tuple;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class OblivionComposting {
    private static Set<Tuple<Supplier<ItemLike>, Float>> COMPOSTING_CHANCES = Collections.synchronizedSet(new HashSet<>());

    private static void addComposting(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            for (Tuple<Supplier<ItemLike>, Float> pair : COMPOSTING_CHANCES){
                ComposterBlock.add(pair.getB(), pair.getA().get());
            }
            // Now that the values are in the ComposterBlock,
            // make sure we don't point at the registry set anymore, so that the memory can get freed.
            COMPOSTING_CHANCES = null;
        });
    }

    public static void register(IEventBus eventBus){
        eventBus.addListener(OblivionComposting::addComposting);
    }

    /**
     * Adds an item to the composter.
     * Call in Mod Constructor, after Item Registration.
     * @param item to be composted.
     * @param chance to produce a layer in the composter.
     */
    public static void addCompostable(ItemLike item, float chance){
        COMPOSTING_CHANCES.add(new Tuple<>(() -> item, chance));
    }

    /**
     * Adds an item to the composter.
     * For adding another mod's item
     * @param item to be composted.
     * @param chance to produce a layer in the composter.
     */
    public static void addCompostable(Supplier<ItemLike> item, float chance){
        COMPOSTING_CHANCES.add(new Tuple<>(item, chance));
    }
}
