package com.github.voidleech.oblivion.mixin.plugin;

import com.github.voidleech.oblivion.services.Services;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractOblivionMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, Condition> CONDITIONS = new HashMap<>();

    public AbstractOblivionMixinPlugin(Map<String, Condition> kvps){
        CONDITIONS.putAll(kvps);
    }

    public AbstractOblivionMixinPlugin(String prefix, Map<String, Condition> kvps){
        for (Map.Entry<String, Condition> entry : kvps.entrySet()) {
            CONDITIONS.put(prefix + entry.getKey(), entry.getValue());
        }
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
        Condition condition = CONDITIONS.get(mixinToApply);
        if (condition != null) {
            return condition.shouldLoad();
        }
        int afterPrefix = mixinToApply.indexOf("compat.") + 7;
        if (afterPrefix == 6) {
            return true;
        }
        String targetMod = mixinToApply.substring(afterPrefix, mixinToApply.indexOf('.', afterPrefix));
        return Services.PLATFORM.isModEarlyLoaded(targetMod);
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
