package com.cerbon.beautiful_potions.forge.platform;

import com.cerbon.beautiful_potions.forge.BeautifulPotionsForge;
import com.cerbon.beautiful_potions.platform.services.IPlatformHelper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public BakedModel getItemModel(ResourceLocation potionId, ModelManager modelManager) {
        return modelManager.getModel(BeautifulPotionsForge.REGISTERED_MODELS.get(potionId));
    }
}
