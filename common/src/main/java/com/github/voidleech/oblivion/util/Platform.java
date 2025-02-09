package com.github.voidleech.oblivion.util;

import com.github.voidleech.oblivion.services.Services;

import java.nio.file.Path;

public class Platform {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    public static String getPlatformName() {
        return Services.PLATFORM.getPlatformName();
    }

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    public static boolean isModLoaded(String modId) {
        return Services.PLATFORM.isModLoaded(modId);
    }

    /**
     * Checks if a mod with the given id is loaded early.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    public static boolean isModEarlyLoaded(String modId) {
        return Services.PLATFORM.isModEarlyLoaded(modId);
    }

    public static Path getResourcePath(String modId, String resource) {
        return Services.PLATFORM.getResourcePath(modId, resource);
    }

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    public static boolean isDevelopmentEnvironment() {
        return Services.PLATFORM.isDevelopmentEnvironment();
    }

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    public static String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }
}
