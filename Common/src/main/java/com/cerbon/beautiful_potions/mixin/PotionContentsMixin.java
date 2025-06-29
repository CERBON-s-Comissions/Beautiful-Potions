package com.cerbon.beautiful_potions.mixin;

import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionContents.class)
public abstract class PotionContentsMixin {

    @Inject(method = "getColor()I", at = @At("RETURN"), cancellable = true)
    private void getColor(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0xFFFFFF);
    }
}
