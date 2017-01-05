package info.aenterprise.chunkloaderz.compat;

import info.aenterprise.chunkloaderz.blocks.BlockLoader;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Copyright (c) 2015, AEnterprise
 * http://www.aenterprise.info/
 */
@JEIPlugin
public class JEICompat implements IModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {

	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		registry.addDescription(new ItemStack(ItemLoader.enderPearlShard), "enderPearlShard.desc");
		registry.addDescription(new ItemStack(BlockLoader.anchoredPearl), "anchoredPearl.desc");
		registry.addDescription(new ItemStack(ItemLoader.guardianPearl), "guardianPearl.desc");
		registry.addDescription(new ItemStack(ItemLoader.guardianPearlShard), "guardianPearlShard.desc");
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

	}
}
