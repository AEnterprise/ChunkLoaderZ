package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by AEnterprise
 */
public class BlockLoader {
	public static Block chunkLoader;
	public static Block anchoredPearl;

	public static void init() {
		chunkLoader = registerBlock("blockChunkLoader");

		anchoredPearl = new BlockAnhoredPearl();
		GameRegistry.registerBlock(anchoredPearl, "anchoredPearl");
		ChunkLoaderZ.proxy.registerInventoryModel(anchoredPearl, "anchoredPearl");

		GameRegistry.registerTileEntity(TileEntityAnchoredPearl.class, "anchoredpearl");
	}

	public static void addRecipes() {
		GameRegistry.addSmelting(ItemLoader.brokenAnchoredPearl, new ItemStack(anchoredPearl), 0.5f);
	}

	private static Block registerBlock(String name) {
		Block block = new Block(Material.iron).setUnlocalizedName(name).setCreativeTab(ChunkLoaderZ.creativeTab);
		GameRegistry.registerBlock(block, name);
		ChunkLoaderZ.proxy.registerInventoryModel(block, name);
		return block;
	}
}
