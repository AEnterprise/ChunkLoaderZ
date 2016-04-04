package info.aenterprise.chunkloaderz.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

/**
 * Created by AEnterprise
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerInventoryModel(Block block, int meta, String blockname) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation("chunkloaderz:" + blockname, "inventory"));
	}

	@Override
	public void registerInventoryModel(Item item, int meta, String itemname) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("chunkloaderz:" + itemname, "inventory"));
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void preInit() {
		super.preInit();
		OBJLoader.INSTANCE.addDomain("chunkloaderz");
	}
}
