package com.github.voidleech.oblivion.services.services;

import com.github.voidleech.oblivion.util.Registration;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.function.Supplier;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Checks if a mod with the given id is loaded early.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModEarlyLoaded(String modId);

    Path getResourcePath(String modId, String resource);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

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
    <I, T extends I> Supplier<T> register(Registry<I> registry, String modId, String name, Supplier<T> sup);

    /**
     * Adds a brewing mix.
     * @param input
     * @param ingredient
     * @param output
     */
    void addMix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output);

    /**
     * Adds a brewing recipe. For brewing that has a non-potion as input or output.
     * Use {@link #addMix(Supplier input, Supplier ingredient, Supplier output) addMix} otherwise.
     * @param input
     * @param ingredient
     * @param output
     */
    void addBrewingRecipe(Supplier<Ingredient> input, Supplier<Ingredient> ingredient, Supplier<ItemStack> output);

    /**
     * Adds an item to the composter.
     * @param item to be composted.
     * @param chance to produce a layer in the composter.
     */
    void addCompostable(Supplier<ItemLike> item, float chance);

    /**
     * Adds an item as furnace fuel. Use only when getBurnTime somehow isn't available to override.
     * @param item to be used as fuel
     * @param burnTime ticks to burn. A standard furnace recipe takes 200 ticks.
     */
    void addFurnaceFuel(Supplier<Item> item, int burnTime);

    /**
     * Registers a config
     * @param spec
     * @param type Side that the config applies to
     * @param modId
     */
    void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId);

    /**
     * Registers an addition resource/data pack
     * @param pack
     * @param modId
     */
    void addPack(Registration.PackData pack, String modId);
}