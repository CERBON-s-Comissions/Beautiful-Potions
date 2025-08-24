package com.cerbon.beautiful_potions.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionUtils.class)
public abstract class PotionUtilsMixin {

    @Inject(method = "getColor(Lnet/minecraft/world/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private static void getColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (!(stack.getItem() instanceof ArrowItem) && BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals("minecraft"))
            cir.setReturnValue(0xFFFFFF);
    }
}
