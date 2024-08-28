package com.github.voidleech.oblivion.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class OblivionPotionRecipes {
    private static Set<Mix> MIXES = Collections.synchronizedSet(new HashSet<>());
    private static Set<Supplier<BrewingRecipe>> RECIPES = Collections.synchronizedSet(new HashSet<>());

    private static void addRecipes(FMLCommonSetupEvent event){
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

    public static void register(IEventBus eventBus){
        eventBus.addListener(OblivionPotionRecipes::addRecipes);
    }

    /**
     * Adds a brewing mix.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addMix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output){
        MIXES.add(new Mix(input, ingredient, output));
    }

    /**
     * Adds a brewing recipe. For brewing that has a non-potion as input or output.
     * Use {@link #addMix(Supplier input, Supplier ingredient, Supplier output) addMix} otherwise.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addRecipe(Supplier<Ingredient> input, Supplier<Ingredient> ingredient, Supplier<ItemStack> output){
        RECIPES.add(() -> new BrewingRecipe(input.get(), ingredient.get(), output.get()));
    }

    private record Mix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output) {
    }
}
