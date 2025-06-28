package com.cerbon.beautiful_potions.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum PotionType {
    NORMAL,
    SPLASH,
    LINGER,
    UNKNOWN;

    public static PotionType get(ItemStack stack) {
        if (stack.is(Items.POTION))
            return PotionType.NORMAL;

        else if (stack.is(Items.SPLASH_POTION))
            return PotionType.SPLASH;

        else if (stack.is(Items.LINGERING_POTION))
            return PotionType.LINGER;

        else return PotionType.UNKNOWN;
    }
}
