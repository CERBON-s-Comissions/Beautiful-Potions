package com.cerbon.beautiful_potions.fabric;

import com.cerbon.beautiful_potions.BeautifulPotions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.joml.Matrix4f;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BeautifulPotionsFabric implements ClientModInitializer, PreparableModelLoadingPlugin<Set<Identifier>>, PreparableModelLoadingPlugin.DataLoader<Set<Identifier>> {
    public static final Map<Identifier, ExtraModelKey<ItemModel>> REGISTERED_MODELS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        PreparableModelLoadingPlugin.register(this, this);
    }

    @Override
    public void initialize(Set<Identifier> potionIds, ModelLoadingPlugin.@NonNull Context pluginContext) {
        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (Identifier id : potionIds) {
            Identifier model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");

            ExtraModelKey<ItemModel> key = ExtraModelKey.create(model::toString);
            REGISTERED_MODELS.putIfAbsent(id, key);

            pluginContext.addModel(key, new SimpleUnbakedExtraModel<>(model, (resolvedModel, modelBaker) -> {
                TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
                QuadCollection list = resolvedModel.bakeTopGeometry(textureSlots, modelBaker, BlockModelRotation.IDENTITY);
                ModelRenderProperties modelRenderProperties = ModelRenderProperties.fromResolvedModel(modelBaker, resolvedModel, textureSlots);
                return new CuboidItemModelWrapper(List.of(), list, modelRenderProperties, new Matrix4f());
            }));
        }
    }

    @Override
    public @NonNull CompletableFuture<Set<Identifier>> load(PreparableReloadListener.@NonNull SharedState sharedState, @NonNull Executor executor) {
        return CompletableFuture.supplyAsync(()-> BeautifulPotions.findCITs(sharedState.resourceManager()), executor);
    }
}