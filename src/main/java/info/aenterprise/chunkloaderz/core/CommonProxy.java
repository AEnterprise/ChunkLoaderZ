package info.aenterprise.chunkloaderz.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by AEnterprise
 */
public class CommonProxy {

	public void registerInventoryModel(Block block, String blockname) {
		registerInventoryModel(block, 0, blockname);
	}

	public void registerInventoryModel(Block block, int meta, String blockname) {

	}

	public void registerInventoryModel(Item item, String blockname) {
		registerInventoryModel(item, 0, blockname);
	}

	public void registerInventoryModel(Item item, int meta, String blockname) {

	}

	public void init() {

	}

	public void preInit() {

	}
}
