package com.cerbon.beautiful_potions.neoforge.event;

import com.cerbon.beautiful_potions.BeautifulPotions;
import com.cerbon.beautiful_potions.neoforge.BeautifulPotionsNeo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = BeautifulPotions.MOD_ID, value = Dist.CLIENT)
public class BEBClientEventsNeo {

    @SubscribeEvent
    public static void onRegisterModel(ModelEvent.RegisterStandalone event) {
        Set<Identifier> potionIds = BeautifulPotions.findCITs(Minecraft.getInstance().getResourceManager());

        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (Identifier id : potionIds) {
            Identifier model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");

            StandaloneModelKey<ItemModel> key = new StandaloneModelKey<>(model::toString);
            BeautifulPotionsNeo.REGISTERED_MODELS.putIfAbsent(id, key);

            event.register(key, new SimpleUnbakedStandaloneModel<>(model, (resolvedModel, modelBaker, debugName) -> {
                TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
                QuadCollection list = resolvedModel.bakeTopGeometry(textureSlots, modelBaker, BlockModelRotation.IDENTITY);
                ModelRenderProperties modelRenderProperties = ModelRenderProperties.fromResolvedModel(modelBaker, resolvedModel, textureSlots);
                return new CuboidItemModelWrapper(List.of(), list, modelRenderProperties, new Matrix4f());
            }));
        }
    }
}
