package com.cerbon.beautiful_potions.neoforge.mixin.test;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class used to test if neoforge only mixins are being applied
@Mixin(Minecraft.class)
public abstract class TestMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void sendMessageIfWorking(GameConfig gameConfig, CallbackInfo ci) {
        BeautifulPotions.LOGGER.info("NeoForge only mixins are working for {}!",  BeautifulPotions.MOD_NAME);
    }
}
