package com.cerbon.beautiful_potions.mixin;

import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionUtils.class)
public abstract class PotionUtilsMixin {

    @Inject(method = "getColor(Ljava/util/Collection;)I", at = @At("HEAD"), cancellable = true)
    private static void getColor(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0xFFFFFF);
    }
}
