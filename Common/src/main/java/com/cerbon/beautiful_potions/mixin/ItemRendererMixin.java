package com.cerbon.beautiful_potions.mixin;

import com.cerbon.beautiful_potions.platform.Services;
import com.cerbon.beautiful_potions.potion.PotionType;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    private @Final ItemModelShaper itemModelShaper;

    @WrapOperation(method="getModel", at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"))
    private BakedModel getModel(ItemModelShaper instance, ItemStack stack, Operation<BakedModel> original) {
        if (!(stack.getItem() instanceof PotionItem)) return original.call(instance, stack);

        Potion potion = PotionUtils.getPotion(stack);
        ResourceLocation potionRL = BuiltInRegistries.POTION.getKey(potion);

        PotionType potionType = PotionType.get(stack);
        String potionNamespace = potionRL.getNamespace();
        String potionId = potionRL.getPath();

        String basePotionId = getBasePotionId(potionId);
        String variant = getPotionVariant(potionId, potionType);

        String modelPath = potionNamespace + ":" + basePotionId + "/" + variant;
        ResourceLocation modelLocation = ResourceLocation.tryParse(modelPath);

        if (modelLocation != null) {
            ModelManager modelManager = itemModelShaper.getModelManager();

            BakedModel model = Services.PLATFORM.getItemModel(modelLocation, modelManager);
            return model != null && model != modelManager.getMissingModel() ? model : original.call(instance, stack);
        }

        return original.call(instance, stack);
    }

    @Unique
    private String getBasePotionId(String potionId) {
        // Remove prefixes like "long_" and "strong_" to get the base potion name
        if (potionId.startsWith("long_"))
            return potionId.substring(5);

        else if (potionId.startsWith("strong_"))
            return potionId.substring(7);

        return potionId;
    }

    @Unique
    private String getPotionVariant(String potionId, PotionType potionType) {
        String typeString = potionType.toString().toLowerCase();

        if (potionId.startsWith("long_"))
            return typeString + "_long";

        else if (potionId.startsWith("strong_"))
            return typeString + "_strong";

        else return typeString.equals("normal") ? "normal" : typeString;
    }
}