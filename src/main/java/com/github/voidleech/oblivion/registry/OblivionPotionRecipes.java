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

public class OblivionPotionRecipes {
    private static Set<Mix> MIXES = Collections.synchronizedSet(new HashSet<>());
    private static Set<BrewingRecipe> RECIPES = Collections.synchronizedSet(new HashSet<>());

    private static void addRecipes(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            for (Mix mix : MIXES){
                PotionBrewing.addMix(mix.input, mix.ingredient, mix.output);
            }
            for (BrewingRecipe recipe : RECIPES){
                BrewingRecipeRegistry.addRecipe(recipe);
            }
            // Now that the values are in the brewing recipe registry or potion brewing mixes,
            // make sure we don't point at it anymore, so that the memory can get freed.
            MIXES = null;
            RECIPES = null;
        });
    }

    public static void register(IEventBus eventBus){
        eventBus.addListener(OblivionPotionRecipes::addRecipes);
    }

    /**
     * Adds a brewing mix.
     * Call in Mod Constructor, after Item Registration.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addMix(Potion input, Item ingredient, Potion output){
        MIXES.add(new Mix(input, ingredient, output));
    }

    /**
     * Adds a brewing recipe. For brewing that has a non-potion as input or output.
     * Use {@link #addMix(Potion input, Item ingredient, Potion output) addMix} otherwise.
     * Call in Mod Constructor, after Item Registration.
     * @param input
     * @param ingredient
     * @param output
     */
    public static void addRecipe(Ingredient input, Ingredient ingredient, ItemStack output){
        RECIPES.add(new BrewingRecipe(input, ingredient, output));
    }

    private record Mix(Potion input, Item ingredient, Potion output) {
    }
}
