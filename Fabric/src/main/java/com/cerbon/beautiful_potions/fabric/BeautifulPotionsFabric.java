package com.cerbon.beautiful_potions.fabric;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BeautifulPotionsFabric implements ClientModInitializer, PreparableModelLoadingPlugin<Set<ResourceLocation>>, PreparableModelLoadingPlugin.DataLoader<Set<ResourceLocation>> {
    public static final Map<ResourceLocation, ResourceLocation> REGISTERED_MODELS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        PreparableModelLoadingPlugin.register(this, this);
    }

    @Override
    public void onInitializeModelLoader(Set<ResourceLocation> potionIds, ModelLoadingPlugin.Context pluginContext) {
        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (ResourceLocation id : potionIds) {
            ResourceLocation model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");
            REGISTERED_MODELS.putIfAbsent(id, model);
            pluginContext.addModels(model);
        }
    }

    @Override
    public CompletableFuture<Set<ResourceLocation>> load(ResourceManager manager, Executor executor) {
        return CompletableFuture.supplyAsync(()-> BeautifulPotions.findCITs(manager), executor);
    }
}