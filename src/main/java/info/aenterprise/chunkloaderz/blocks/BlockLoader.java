package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by AEnterprise
 */
public class BlockLoader {
	public static Block chunkLoader;

	public static void init() {
		chunkLoader = new BlockChunkLoader();
		GameRegistry.registerBlock(chunkLoader, "blockChunkLoader");
		ChunkLoaderZ.proxy.registerInventoryBlockModel(chunkLoader, "blockChunkLoader");

		GameRegistry.registerTileEntity(TileEntityChunkLoader.class, "chunkloader");
	}
}
