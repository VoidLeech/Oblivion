package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.util.Platform;

public class OblivionClient {
    public static void init(){

    }

    public static boolean shaderLoaderPresent() {
        return Platform.isModLoaded("oculus") || Platform.isModLoaded("iris");
    }
}
