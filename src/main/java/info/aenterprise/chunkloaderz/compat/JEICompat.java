package info.aenterprise.chunkloaderz.compat;

import info.aenterprise.chunkloaderz.blocks.BlockLoader;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2015, AEnterprise
 * http://www.aenterprise.info/
 */
@JEIPlugin
public class JEICompat implements IModPlugin {
	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {

	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {

	}

	@Override
	public void register(IModRegistry registry) {
		registry.addDescription(new ItemStack(ItemLoader.enderPearlShard), "enderPearlShard.desc");
		registry.addDescription(new ItemStack(BlockLoader.anchoredPearl), "anchoredPearl.desc");
		registry.addDescription(new ItemStack(ItemLoader.guardianPearl), "guardianPearl.desc");
		registry.addDescription(new ItemStack(ItemLoader.guardianPearlShard), "guardianPearlShard.desc");
	}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

	}
}
