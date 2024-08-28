package com.github.voidleech.oblivion.mixin.plugin;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class AbstractOblivionMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, String> REQUIRED_MODS_FOR_MIXINS = new HashMap<>();

    public AbstractOblivionMixinPlugin(Map<String, String> kvps){
        REQUIRED_MODS_FOR_MIXINS.putAll(kvps);
    }

    @Override
    public void onLoad(String s) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String target, String mixinToApply) {
        String targetMod = REQUIRED_MODS_FOR_MIXINS.get(mixinToApply);
        return targetMod == null || LoadingModList.get().getModFileById(targetMod) != null;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }
}
