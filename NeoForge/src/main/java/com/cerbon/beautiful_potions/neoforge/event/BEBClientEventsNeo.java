package com.cerbon.beautiful_potions.neoforge.event;

import com.cerbon.beautiful_potions.BeautifulPotions;
import com.cerbon.beautiful_potions.neoforge.BeautifulPotionsNeo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.Set;

@EventBusSubscriber(modid = BeautifulPotions.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BEBClientEventsNeo {

    @SubscribeEvent
    public static void onRegisterModel(ModelEvent.RegisterAdditional event) {
        Set<ResourceLocation> potionIds = BeautifulPotions.findCITs(Minecraft.getInstance().getResourceManager());

        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (ResourceLocation id : potionIds) {
            ResourceLocation model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");
            BeautifulPotionsNeo.REGISTERED_MODELS.putIfAbsent(id, ModelResourceLocation.standalone(model));
            event.register(ModelResourceLocation.standalone(model));
        }
    }
}
