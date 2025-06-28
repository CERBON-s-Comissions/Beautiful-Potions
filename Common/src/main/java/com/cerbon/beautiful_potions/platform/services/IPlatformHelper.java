package com.cerbon.beautiful_potions.platform.services;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;

public interface IPlatformHelper {
    String getPlatformName();

    BakedModel getItemModel(ResourceLocation enchantId, ModelManager modelManager);
}
