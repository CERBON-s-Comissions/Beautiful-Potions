package com.cerbon.beautiful_potions.fabric.platform;

import com.cerbon.beautiful_potions.fabric.BeautifulPotionsFabric;
import com.cerbon.beautiful_potions.platform.services.IPlatformHelper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public ItemModel getItemModel(ResourceLocation enchantId, ModelManager modelManager) {
        return modelManager.getModel(BeautifulPotionsFabric.REGISTERED_MODELS.get(enchantId));
    }
}
