package com.cerbon.beautiful_potions.forge;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod(BeautifulPotions.MOD_ID)
public class BeautifulPotionsForge {
    public static final Map<ResourceLocation, ResourceLocation> REGISTERED_MODELS = new HashMap<>();

    public BeautifulPotionsForge() {}
}