package com.cerbon.beautiful_potions.platform.services;

import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.Identifier;

public interface IPlatformHelper {
    String getPlatformName();

    ItemModel getItemModel(Identifier enchantId, ModelManager modelManager);
}
