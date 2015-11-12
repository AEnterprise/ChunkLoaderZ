package info.aenterprise.chunkloaderz.core;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by AEnterprise
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerInventoryBlockModel(Block block, int meta, String blockname) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation("chunkloaderz:" + blockname, "inventory"));
	}
}
