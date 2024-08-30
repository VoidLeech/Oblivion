package com.github.voidleech.oblivion.hackyMixinUtils.propertyRebuilders;

import net.minecraft.world.item.Item;

import javax.annotation.CheckReturnValue;

public class ItemPropertiesRebuilder {
    private final Item item;

    private ItemPropertiesRebuilder(Item item) {
        this.item = item;
    }

    @CheckReturnValue
    public static ItemPropertiesRebuilder of(Item item){
        return new ItemPropertiesRebuilder(item);
    }

    @CheckReturnValue
    public ItemPropertiesRebuilder craftingRemainder(Item remainder){
        item.craftingRemainingItem = remainder;
        return this;
    }

    public void finalizeRebuild(){
        // Not actually strictly required due to what Item constructor does with the properties.
    }
}
