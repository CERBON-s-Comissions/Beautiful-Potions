package com.cerbon.beautiful_potions.mixin;

import com.cerbon.beautiful_potions.platform.Services;
import com.cerbon.beautiful_potions.potion.PotionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public class ItemRendererMixin {

    @Inject(method = "appendItemLayers", at = @At(value = "INVOKE", target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
    private void appendItemLayers(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        if (!(stack.getItem() instanceof PotionItem)) return;

        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        if (potionContents.potion().isPresent()) {
            Potion potion = potionContents.potion().get().value();
            ResourceLocation potionRL = BuiltInRegistries.POTION.getKey(potion);
            if (potionRL == null) return;

            PotionType potionType = PotionType.get(stack);
            String potionNamespace = potionRL.getNamespace();
            String potionId = potionRL.getPath();

            String basePotionId = getBasePotionId(potionId);
            String variant = getPotionVariant(potionId, potionType);

            String modelPath = potionNamespace + ":" + basePotionId + "/" + variant;
            ResourceLocation modelLocation = ResourceLocation.tryParse(modelPath);

            if (modelLocation != null) {
                ItemModel model = Services.PLATFORM.getItemModel(modelLocation, Minecraft.getInstance().getModelManager());

                if (model != null && !(model instanceof MissingItemModel)) {
                    model.update(renderState, stack, (ItemModelResolver) (Object) this, displayContext, level instanceof ClientLevel clientLevel ? clientLevel : null, entity, seed);
                    ci.cancel();
                }
            }
        }
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