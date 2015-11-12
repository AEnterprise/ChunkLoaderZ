package info.aenterprise.chunkloaderz.core;

import net.minecraft.block.Block;

/**
 * Created by AEnterprise
 */
public class CommonProxy {

	public void registerInventoryBlockModel(Block block, String blockname) {
		registerInventoryBlockModel(block, 0, blockname);
	}

	public void registerInventoryBlockModel(Block block, int meta, String blockname) {

	}
}
