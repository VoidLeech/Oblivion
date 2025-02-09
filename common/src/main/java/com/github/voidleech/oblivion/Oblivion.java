package com.github.voidleech.oblivion;

import com.github.voidleech.oblivion.init.OblivionBlockEntities;
import com.github.voidleech.oblivion.init.OblivionEntities;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class Oblivion {

    public static final String MOD_ID = "oblivion_api";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        OblivionBlockEntities.init();
        OblivionEntities.init();
    }
}