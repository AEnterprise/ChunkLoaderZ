package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by AEnterprise
 */
public class BlockLoader {
	public static BlockChunkLoader chunkLoader;
	public static Block anchoredPearl;

	public static void init() {
		chunkLoader = new BlockChunkLoader();
		GameRegistry.register(chunkLoader);
		GameRegistry.register(new ItemBlock(chunkLoader).setRegistryName(chunkLoader.getRegistryName()));
		ChunkLoaderZ.proxy.registerInventoryModel(chunkLoader, "blockChunkLoader");


		anchoredPearl = new BlockAnhoredPearl();
		GameRegistry.register(anchoredPearl);
		GameRegistry.register(new ItemBlock(anchoredPearl).setRegistryName(anchoredPearl.getRegistryName()));
		ChunkLoaderZ.proxy.registerInventoryModel(anchoredPearl, "anchoredPearl");

		GameRegistry.registerTileEntity(TileEntityAnchoredPearl.class, "anchoredpearl");
		GameRegistry.registerTileEntity(TileEntityChunkLoader.class, "chunkloader");
	}

	public static void addRecipes() {
		GameRegistry.addSmelting(ItemLoader.brokenAnchoredPearl, new ItemStack(anchoredPearl), 0.5f);
		GameRegistry.addRecipe(new ItemStack(chunkLoader, 4), "SES", "E E", "SES", 'E', Blocks.end_stone, 'S', ItemLoader.enderPearlShard);
	}
}
