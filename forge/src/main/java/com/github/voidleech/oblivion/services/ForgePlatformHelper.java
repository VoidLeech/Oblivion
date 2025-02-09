package com.github.voidleech.oblivion.services;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.services.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.registries.DeferredRegister;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {
    public static Map<String, Map<Registry<?>, DeferredRegister<?>>> REGISTRIES = new HashMap<>();

    private static Set<Mix> MIXES = Collections.synchronizedSet(new HashSet<>());
    private static Set<Supplier<BrewingRecipe>> RECIPES = Collections.synchronizedSet(new HashSet<>());
    private static Set<Tuple<Supplier<ItemLike>, Float>> COMPOSTING_CHANCES = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Item, Integer> FUEL_TIMES = new HashMap<>();
    private static Set<FuelTime> FUEL_TIMES_TO_REGISTER = Collections.synchronizedSet(new HashSet<>());

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isModEarlyLoaded(String modId) {
        return LoadingModList.get().getModFileById(modId) != null;
    }

    @Override
    public Path getResourcePath(String modId, String resource) {
        return ModList.get().getModFileById(modId).getFile().findResource(resource);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <I, T extends I> Supplier<T> register(Registry<I> registry, String modId, String name, Supplier<T> sup) {
        int count = 0;
        if (REGISTRIES.get(modId) == null){
            REGISTRIES.put(modId, new HashMap<>());
        }
        else {
            count = REGISTRIES.get(modId).size();
        }
        DeferredRegister<I> deferredRegister = (DeferredRegister<I>) REGISTRIES.get(modId).computeIfAbsent(registry, reg -> DeferredRegister.create(reg.key(), modId));
        if (count < REGISTRIES.get(modId).size()){
            deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
        return deferredRegister.register(name, sup);
    }

    public static void addBrewingRecipes(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            for (Mix mix : MIXES){
                PotionBrewing.addMix(mix.input.get(), mix.ingredient.get(), mix.output.get());
            }
            for (Supplier<BrewingRecipe> recipe : RECIPES){
                BrewingRecipeRegistry.addRecipe(recipe.get());
            }
            // Now that the values are in the brewing recipe registry or potion brewing mixes,
            // make sure we don't point at the registry sets anymore, so that the memory can get freed.
            MIXES = null;
            RECIPES = null;
        });
    }

    @Override
    public void addMix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output){
        MIXES.add(new Mix(input, ingredient, output));
    }

    @Override
    public void addBrewingRecipe(Supplier<Ingredient> input, Supplier<Ingredient> ingredient, Supplier<ItemStack> output){
        RECIPES.add(() -> new BrewingRecipe(input.get(), ingredient.get(), output.get()));
    }

    public static void addComposting(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            for (Tuple<Supplier<ItemLike>, Float> pair : COMPOSTING_CHANCES){
                ComposterBlock.add(pair.getB(), pair.getA().get());
            }
            // Now that the values are in the ComposterBlock,
            // make sure we don't point at the registry set anymore, so that the memory can get freed.
            COMPOSTING_CHANCES = null;
        });
    }

    @Override
    public void addCompostable(Supplier<ItemLike> item, float chance) {
        COMPOSTING_CHANCES.add(new Tuple<>(item, chance));
    }

    public static void addFurnaceFuels(FurnaceFuelBurnTimeEvent event){
        Item eventItem = event.getItemStack().getItem();
        if (FUEL_TIMES.containsKey(eventItem)){
            event.setBurnTime(FUEL_TIMES.get(eventItem));
        }
    }

    public static void registerFuels(FMLCommonSetupEvent event) {
        for (FuelTime fuelTime : FUEL_TIMES_TO_REGISTER){
            if (FUEL_TIMES.put(fuelTime.item().get(), fuelTime.burnTime()) != null){
                Oblivion.LOGGER.error("Multiple mods using Oblivion set fuel ticks for {}", fuelTime.item.get().getDescriptionId());
            }
        }
        // Now that the values are in the fuel time map
        // make sure we don't point at the registry set anymore, so that the memory can get freed.
        FUEL_TIMES_TO_REGISTER = null;
    }

    @Override
    public void addFurnaceFuel(Supplier<Item> item, int burnTime){
        FUEL_TIMES_TO_REGISTER.add(new FuelTime(item, burnTime));
    }

    @Override
    public void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        ModLoadingContext.get().registerConfig(type, spec);
    }

    private record Mix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output) {
    }

    private record FuelTime(Supplier<Item> item, int burnTime){
    }
}