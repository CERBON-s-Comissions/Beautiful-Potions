package com.cerbon.beautiful_potions.forge.event;

import com.cerbon.beautiful_potions.BeautifulPotions;
import com.cerbon.beautiful_potions.forge.BeautifulPotionsForge;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BeautifulPotions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BEBClientEventsForge {

    @SubscribeEvent
    public static void onRegisterModel(ModelEvent.RegisterAdditional event) {
        Set<ResourceLocation> potionIds = BeautifulPotions.findCITs(Minecraft.getInstance().getResourceManager());

        BeautifulPotions.LOGGER.info("Found {} enchanted-book CITs", potionIds.size());

        for (ResourceLocation id : potionIds) {
            ResourceLocation model = id.withPrefix(BeautifulPotions.MODEL_PREFIX + "/");
            BeautifulPotionsForge.REGISTERED_MODELS.putIfAbsent(id, model);
            event.register(model);
        }
    }
}
