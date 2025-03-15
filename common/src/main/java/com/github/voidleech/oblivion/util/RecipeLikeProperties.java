package com.github.voidleech.oblivion.util;

import com.github.voidleech.oblivion.services.Services;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class RecipeLikeProperties {
    /**
     * Adds a brewing mix.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addMix(Supplier<? extends Potion> input, Supplier<? extends Item> ingredient, Supplier<? extends Potion> output) {
        Services.PLATFORM.addMix(input, ingredient, output);
    }

    /**
     * Adds a brewing recipe. For brewing that has a non-potion as input or output.
     * Use {@link #addMix(Supplier input, Supplier ingredient, Supplier output) addMix} otherwise.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addBrewingRecipe(Supplier<? extends Ingredient> input, Supplier<? extends Ingredient> ingredient, Supplier<? extends ItemStack> output) {
        Services.PLATFORM.addBrewingRecipe(input, ingredient, output);
    }

    /**
     * Adds an item to the composter.
     * @param item to be composted.
     * @param chance to produce a layer in the composter.
     */
    public static void addCompostable(Supplier<? extends ItemLike> item, float chance) {
        Services.PLATFORM.addCompostable(item, chance);
    }

    /**
     * Adds an item as furnace fuel. Use only when getBurnTime somehow isn't available to override.
     * @param item to be used as fuel
     * @param burnTime ticks to burn. A standard furnace recipe takes 200 ticks.
     */
    public static void addFurnaceFuel(Supplier<? extends ItemLike> item, int burnTime){
        Services.PLATFORM.addFurnaceFuel(item, burnTime);
    }
}
