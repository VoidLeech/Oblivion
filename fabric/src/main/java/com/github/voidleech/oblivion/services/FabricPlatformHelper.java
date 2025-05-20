package com.github.voidleech.oblivion.services;

import com.github.voidleech.oblivion.mixin.accessor.ComposterBlockInvoker;
import com.github.voidleech.oblivion.services.services.IPlatformHelper;
import com.github.voidleech.oblivion.util.Registration;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.fabricators_of_create.porting_lib.brewing.BrewingRecipeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.impl.FabricLoaderImpl;
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
        return isModLoaded(modId);
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
    public void addMix(Supplier<? extends Potion> input, Supplier<? extends Item> ingredient, Supplier<? extends Potion> output) {
        PotionBrewing.addMix(input.get(), ingredient.get(), output.get());
    }

    @Override
    public void addBrewingRecipe(Supplier<? extends Ingredient> input, Supplier<? extends Ingredient> ingredient, Supplier<? extends ItemStack> output) {
        BrewingRecipeRegistry.addRecipe(input.get(), ingredient.get(), output.get());
    }

    @Override
    public void addCompostable(Supplier<? extends ItemLike> item, float chance) {
        ComposterBlockInvoker.oblivion$add(chance, item.get());
    }

    @Override
    public void addFurnaceFuel(Supplier<? extends ItemLike> item, int burnTime) {
        FuelRegistry.INSTANCE.add(item.get(), burnTime);
    }

    @Override
    public void registerConfig(ForgeConfigSpec spec, ModConfig.Type type, String modId) {
        ForgeConfigRegistry.INSTANCE.register(modId, type, spec);
    }

    @Override
    public void addPack(Registration.PackData pack, String modId) {
        ResourceManagerHelperImpl.registerBuiltinResourcePack(new ResourceLocation(modId, pack.name() + (pack.type() == PackType.CLIENT_RESOURCES ? "_r" : "_d")),
                "packs/" + ((pack.type() == PackType.CLIENT_RESOURCES ? "resource/" : "data/") + pack.name()),
                FabricLoader.getInstance().getModContainer(modId).get(), pack.display(),
                pack.required() ? ResourcePackActivationType.ALWAYS_ENABLED :
                        pack.enabledByDefault() ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL);
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
