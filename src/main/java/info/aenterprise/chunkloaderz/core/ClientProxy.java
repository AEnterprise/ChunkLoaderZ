package info.aenterprise.chunkloaderz.core;

import info.aenterprise.chunkloaderz.client.AnhoredPearlRenderer;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by AEnterprise
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerInventoryModel(Block block, int meta, String blockname) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation("chunkloaderz:" + blockname, "inventory"));
	}

	@Override
	public void registerInventoryModel(Item item, int meta, String blockname) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("chunkloaderz:" + blockname, "inventory"));
	}

	@Override
	public void init() {
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnchoredPearl.class, new AnhoredPearlRenderer());

	}
}
