package com.cerbon.beautiful_potions.fabric;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class BeautifulPotionsFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        BeautifulPotions.init();
    }

    @Override
    public void onInitializeClient() {}
}