package com.github.voidleech.oblivion.services;

import com.github.voidleech.oblivion.services.services.IPlatformHelper;
import com.github.voidleech.oblivion.util.Registration;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.fabricators_of_create.porting_lib.brewing.BrewingRecipeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isModEarlyLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public Path getResourcePath(String modId, String resource) {
        return FabricLoader.getInstance().getModContainer(modId).get().getPath(resource);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <I, T extends I> Supplier<T> register(Registry<I> registry, String modId, String name, Supplier<T> sup) {
        T object = Registry.register(registry, new ResourceLocation(modId, name), sup.get());
        return () -> object;
    }

    @Override
    public void addMix(Supplier<Potion> input, Supplier<Item> ingredient, Supplier<Potion> output) {
        PotionBrewing.addMix(input.get(), ingredient.get(), output.get());
    }

    @Override
    public void addBrewingRecipe(Supplier<Ingredient> input, Supplier<Ingredient> ingredient, Supplier<ItemStack> output) {
        BrewingRecipeRegistry.addRecipe(input.get(), ingredient.get(), output.get());
    }

    @Override
    public void addCompostable(Supplier<ItemLike> item, float chance) {
        ComposterBlock.add(chance, item.get());
    }

    @Override
    public void addFurnaceFuel(Supplier<Item> item, int burnTime) {
        FuelRegistry.INSTANCE.add(item.get(), burnTime);
    }

    @Override
    public void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        ForgeConfigRegistry.INSTANCE.register(modId, type, spec);
    }

    @Override
    public void addPack(Registration.PackData pack, String modId) {
        ResourceManagerHelperImpl.registerBuiltinResourcePack(new ResourceLocation(modId, pack.name()),
                "packs/" + (pack.type() == PackType.CLIENT_RESOURCES ? "resource" : "data"),
                FabricLoader.getInstance().getModContainer(modId).get(), pack.display(),
                pack.required() ? ResourcePackActivationType.ALWAYS_ENABLED :
                        pack.enabledByDefault() ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL);
    }
}
