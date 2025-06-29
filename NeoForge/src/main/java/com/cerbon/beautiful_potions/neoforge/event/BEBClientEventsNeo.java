package com.cerbon.beautiful_potions.neoforge.event;

import com.cerbon.beautiful_potions.BeautifulPotions;
import com.cerbon.beautiful_potions.neoforge.BeautifulPotionsNeo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = BeautifulPotions.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BEBClientEventsNeo {

    @SubscribeEvent
    public static void onRegisterModel(ModelEvent.RegisterStandalone event) {
        Set<ResourceLocation> potionIds = BeautifulPotions.findCITs(Minecraft.getInstance().getResourceManager());

        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (ResourceLocation id : potionIds) {
            ResourceLocation model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");

            StandaloneModelKey<ItemModel> key = new StandaloneModelKey<>(model);
            BeautifulPotionsNeo.REGISTERED_MODELS.putIfAbsent(id, key);

            event.register(key, (resolvedModel, modelBaker) -> {
                TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
                List<BakedQuad> list = resolvedModel.bakeTopGeometry(textureSlots, modelBaker, BlockModelRotation.X0_Y0).getAll();
                ModelRenderProperties modelRenderProperties = ModelRenderProperties.fromResolvedModel(modelBaker, resolvedModel, textureSlots);
                return new BlockModelWrapper(List.of(), list, modelRenderProperties);
            });
        }
    }
}
