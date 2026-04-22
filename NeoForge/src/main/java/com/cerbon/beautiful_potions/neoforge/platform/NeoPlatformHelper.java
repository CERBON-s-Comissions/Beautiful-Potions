package com.cerbon.beautiful_potions.neoforge.platform;

import com.cerbon.beautiful_potions.neoforge.BeautifulPotionsNeo;
import com.cerbon.beautiful_potions.platform.services.IPlatformHelper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.Identifier;

public class NeoPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public ItemModel getItemModel(Identifier potionId, ModelManager modelManager) {
        return modelManager.getStandaloneModel(BeautifulPotionsNeo.REGISTERED_MODELS.get(potionId));
    }
}
