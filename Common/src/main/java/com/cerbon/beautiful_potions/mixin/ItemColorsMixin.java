package com.cerbon.beautiful_potions.mixin;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {

    @Redirect(method = "createDefault", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;register(Lnet/minecraft/client/color/item/ItemColor;[Lnet/minecraft/world/level/ItemLike;)V", ordinal = 4))
    private static void removeColorFromPotions(ItemColors instance, ItemColor itemColor, ItemLike[] items) {
        instance.register(
                (itemStack, i) -> i > 0 ? -1 : FastColor.ARGB32.opaque(itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()),
                Items.TIPPED_ARROW
        );
    }
}
