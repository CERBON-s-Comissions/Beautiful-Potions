package com.cerbon.beautiful_potions;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class BeautifulPotions {
	public static final String MOD_ID = "beautiful_potions";
	public static final String MOD_NAME = "BeautifulPotions";

	public static final String MODEL_PREFIX = "item/potion";

	public static final Logger LOGGER = LogUtils.getLogger();

	public static Set<Identifier> findCITs(ResourceManager manager) {
		Set<Identifier> variantIds = new HashSet<>();

		String folder = "models/" + MODEL_PREFIX;

		for (Identifier resourceLocation : manager.listResources(folder, rl -> rl.getPath().endsWith(".json")).keySet()) {
			String path = resourceLocation.getPath();
			path = path.substring(folder.length()+1, path.length()-".json".length());
			variantIds.add(Identifier.tryBuild(resourceLocation.getNamespace(), path));
		}
		return variantIds;
	}
}
