package com.github.voidleech.oblivion.init;

import net.minecraft.world.level.block.SignBlock;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApiStatus.Internal
public class OblivionInternal {
    public static final Set<SignBlock> SIGNS = Collections.synchronizedSet(new HashSet<>());
    public static final Set<SignBlock> HANGING_SIGNS = Collections.synchronizedSet(new HashSet<>());
}
