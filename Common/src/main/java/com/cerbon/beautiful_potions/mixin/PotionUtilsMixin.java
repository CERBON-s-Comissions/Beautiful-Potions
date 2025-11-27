package com.cerbon.beautiful_potions.mixin;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionUtils.class)
public abstract class PotionUtilsMixin {

    @Inject(method = "getColor(Lnet/minecraft/world/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private static void getColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (!(stack.getItem() instanceof ArrowItem)) {
            Potion potion = PotionUtils.getPotion(stack);
            ResourceLocation potionRL = BuiltInRegistries.POTION.getKey(potion);
            if (potionRL == null) return;

            String potionNamespace = potionRL.getNamespace();

            if (BeautifulPotions.POTION_NAMESPACES.contains(potionNamespace))
                cir.setReturnValue(0xFFFFFF);
        }
    }
}
