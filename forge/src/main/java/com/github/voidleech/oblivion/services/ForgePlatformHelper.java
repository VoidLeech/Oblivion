package com.github.voidleech.oblivion.services;

import com.github.voidleech.oblivion.Oblivion;
import com.github.voidleech.oblivion.mixin.accessor.ComposterBlockInvoker;
import com.github.voidleech.oblivion.mixin.accessor.PotionBrewingInvoker;
import com.github.voidleech.oblivion.registration.BuiltInResourcePackSource;
import com.github.voidleech.oblivion.services.services.IPlatformHelper;
import com.github.voidleech.oblivion.util.Registration;
import net.minecraft.core.Registry;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.AddPackFindersEvent;
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
    private static Set<Tuple<Supplier<? extends ItemLike>, Float>> COMPOSTING_CHANCES = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Item, Integer> FUEL_TIMES = new HashMap<>();
    private static Set<FuelTime> FUEL_TIMES_TO_REGISTER = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Registration.PackData, String> PACKS = new HashMap<>();

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
                PotionBrewingInvoker.oblivion$addMix(mix.input.get(), mix.ingredient.get(), mix.output.get());
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
    public void addMix(Supplier<? extends Potion> input, Supplier<? extends Item> ingredient, Supplier<? extends Potion> output){
        MIXES.add(new Mix(input, ingredient, output));
    }

    @Override
    public void addBrewingRecipe(Supplier<? extends Ingredient> input, Supplier<? extends Ingredient> ingredient, Supplier<? extends ItemStack> output){
        RECIPES.add(() -> new BrewingRecipe(input.get(), ingredient.get(), output.get()));
    }

    public static void addComposting(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            for (Tuple<Supplier<? extends ItemLike>, Float> pair : COMPOSTING_CHANCES){
                ComposterBlockInvoker.oblivion$add(pair.getB(), pair.getA().get());
            }
            // Now that the values are in the ComposterBlock,
            // make sure we don't point at the registry set anymore, so that the memory can get freed.
            COMPOSTING_CHANCES = null;
        });
    }

    @Override
    public void addCompostable(Supplier<? extends ItemLike> item, float chance) {
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
            if (FUEL_TIMES.put(fuelTime.item().get().asItem(), fuelTime.burnTime()) != null){
                Oblivion.LOGGER.error("Multiple mods using Oblivion set fuel ticks for {}", fuelTime.item.get().asItem().getDescriptionId());
            }
        }
        // Now that the values are in the fuel time map
        // make sure we don't point at the registry set anymore, so that the memory can get freed.
        FUEL_TIMES_TO_REGISTER = null;
    }

    @Override
    public void addFurnaceFuel(Supplier<? extends ItemLike> item, int burnTime){
        FUEL_TIMES_TO_REGISTER.add(new FuelTime(item, burnTime));
    }

    @Override
    public void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        ModLoadingContext.get().registerConfig(type, spec);
    }

    public static void registerPacks(AddPackFindersEvent event) {
        for (Map.Entry<Registration.PackData, String> entry : PACKS.entrySet()) {
            Registration.PackData data = entry.getKey();
            String modId = entry.getValue();
            Path resourcePath = Services.PLATFORM.getResourcePath(modId, "packs/" +
                    ((data.type() == PackType.CLIENT_RESOURCES ? "resource/" : "data/") + data.name()));
            Pack pack = Pack.readMetaAndCreate("builtin/" + data.name(),
                    data.display(),
                    data.required(),
                    (path) -> new PathPackResources(path, resourcePath, false),
                    data.type(),
                    Pack.Position.TOP,
                    new BuiltInResourcePackSource(data.enabledByDefault() || data.required())
            );
            event.addRepositorySource((packConsumer) -> {packConsumer.accept(pack);});
        }
    }

    @Override
    public void addPack(Registration.PackData pack, String modId) {
        PACKS.put(pack, modId);
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    private record Mix(Supplier<? extends Potion> input, Supplier<? extends Item> ingredient, Supplier<? extends Potion> output) {
    }

    private record FuelTime(Supplier<? extends ItemLike> item, int burnTime){
    }
}