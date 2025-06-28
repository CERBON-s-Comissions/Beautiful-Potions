package com.cerbon.beautiful_potions.neoforge;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod(BeautifulPotions.MOD_ID)
public class BeautifulPotionsNeo {
    public static final Map<ResourceLocation, ModelResourceLocation> REGISTERED_MODELS = new HashMap<>();

    public BeautifulPotionsNeo() {}
}
