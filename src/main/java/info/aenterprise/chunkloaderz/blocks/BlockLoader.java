package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		GameRegistry.registerBlock(chunkLoader, "blockChunkLoader");
		ChunkLoaderZ.proxy.registerInventoryModel(chunkLoader, "blockChunkLoader");


		anchoredPearl = new BlockAnhoredPearl();
		GameRegistry.registerBlock(anchoredPearl, "anchoredPearl");
		ChunkLoaderZ.proxy.registerInventoryModel(anchoredPearl, "anchoredPearl");

		GameRegistry.registerTileEntity(TileEntityAnchoredPearl.class, "anchoredpearl");
		GameRegistry.registerTileEntity(TileEntityChunkLoader.class, "chunkloader");
	}

	public static void addRecipes() {
		GameRegistry.addSmelting(ItemLoader.brokenAnchoredPearl, new ItemStack(anchoredPearl), 0.5f);
		GameRegistry.addRecipe(new ItemStack(chunkLoader, 26), "DED", "ESE", "DED", 'D', Blocks.diamond_block, 'E', Blocks.end_stone, 'S', Items.nether_star);
	}

	private static Block registerBlock(String name) {
		Block block = new Block(Material.iron).setUnlocalizedName(name).setCreativeTab(ChunkLoaderZ.creativeTab);
		GameRegistry.registerBlock(block, name);
		ChunkLoaderZ.proxy.registerInventoryModel(block, name);
		return block;
	}
}
