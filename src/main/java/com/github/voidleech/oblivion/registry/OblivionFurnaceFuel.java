package com.github.voidleech.oblivion.registry;

import com.github.voidleech.oblivion.Oblivion;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class OblivionFurnaceFuel {
    private static final Map<Item, Integer> FUEL_TIMES = new HashMap<>();
    private static Set<FuelTime> FUEL_TIMES_TO_REGISTER = Collections.synchronizedSet(new HashSet<>());

    private static void addFurnaceFuels(FurnaceFuelBurnTimeEvent event){
        Item eventItem = event.getItemStack().getItem();
        if (FUEL_TIMES.containsKey(eventItem)){
            event.setBurnTime(FUEL_TIMES.get(eventItem));
        }
    }

    private static void registerFuels(FMLCommonSetupEvent event) {
        for (FuelTime fuelTime : FUEL_TIMES_TO_REGISTER){
            if (FUEL_TIMES.put(fuelTime.item().get(), fuelTime.burnTime()) != null){
                Oblivion.LOGGER.error("Multiple mods using Oblivion set fuel ticks for {}", fuelTime.item.get().getDescriptionId());
            }
        }
        // Now that the values are in the fuel time map
        // make sure we don't point at the registry set anymore, so that the memory can get freed.
        FUEL_TIMES_TO_REGISTER = null;
    }

    public static void register(IEventBus forgeBus, IEventBus modBus){
        forgeBus.addListener(OblivionFurnaceFuel::addFurnaceFuels);
        modBus.addListener(OblivionFurnaceFuel::registerFuels);
    }

    /**
     * Adds an item as furnace fuel. Use only when getBurnTime somehow isn't available to override.
     * @param item to be used as fuel
     * @param burnTime ticks to burn. A standard furnace recipe takes 200 ticks.
     */
    public static void addFurnaceFuel(Supplier<Item> item, int burnTime){
        FUEL_TIMES_TO_REGISTER.add(new FuelTime(item, burnTime));
    }

    private record FuelTime(Supplier<Item> item, int burnTime){

    }
}
