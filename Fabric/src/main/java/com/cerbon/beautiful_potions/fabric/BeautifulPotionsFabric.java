package com.cerbon.beautiful_potions.fabric;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BeautifulPotionsFabric implements ClientModInitializer, PreparableModelLoadingPlugin<Set<ResourceLocation>>, PreparableModelLoadingPlugin.DataLoader<Set<ResourceLocation>> {
    public static final Map<ResourceLocation, ExtraModelKey<ItemModel>> REGISTERED_MODELS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        PreparableModelLoadingPlugin.register(this, this);
    }

    @Override
    public void initialize(Set<ResourceLocation> potionIds, ModelLoadingPlugin.Context pluginContext) {
        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (ResourceLocation id : potionIds) {
            ResourceLocation model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");

            ExtraModelKey<ItemModel> key = ExtraModelKey.create(model::toString);
            REGISTERED_MODELS.putIfAbsent(id, key);

            pluginContext.addModel(key, new SimpleUnbakedExtraModel<>(model, (resolvedModel, modelBaker) -> {
                TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
                List<BakedQuad> list = resolvedModel.bakeTopGeometry(textureSlots, modelBaker, BlockModelRotation.X0_Y0).getAll();
                ModelRenderProperties modelRenderProperties = ModelRenderProperties.fromResolvedModel(modelBaker, resolvedModel, textureSlots);
                return new BlockModelWrapper(List.of(), list, modelRenderProperties);
            }));
        }
    }

    @Override
    public CompletableFuture<Set<ResourceLocation>> load(ResourceManager manager, Executor executor) {
        return CompletableFuture.supplyAsync(()-> BeautifulPotions.findCITs(manager), executor);
    }
}