package com.github.voidleech.oblivion.registry;

import com.github.voidleech.oblivion.Oblivion;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.*;

public class OblivionFurnaceFuel {
    private static final Map<Item, Integer> FUEL_TIMES = Collections.synchronizedMap(new HashMap<>());

    private static void addFurnaceFuels(FurnaceFuelBurnTimeEvent event){
        Item eventItem = event.getItemStack().getItem();
        if (FUEL_TIMES.containsKey(eventItem)){
            event.setBurnTime(FUEL_TIMES.get(eventItem));
        }
    }

    public static void register(IEventBus eventBus){
        eventBus.addListener(OblivionFurnaceFuel::addFurnaceFuels);
    }

    /**
     * Adds an item as furnace fuel. Use only when getBurnTime somehow isn't available to override.
     * Call in Mod Constructor, after Item Registration.
     * @param item to be used as fuel
     * @param burnTime ticks to burn. A standard furnace recipe takes 200 ticks.
     */
    public static void addFurnaceFuel(Item item, int burnTime){
        if (FUEL_TIMES.put(item, burnTime) != null){
            Oblivion.LOGGER.error("Another mod using Oblivion already overrode fuel ticks for {}", item.getDescriptionId());
        }
    }
}
