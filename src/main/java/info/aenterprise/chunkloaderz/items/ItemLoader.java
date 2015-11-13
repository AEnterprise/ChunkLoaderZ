package info.aenterprise.chunkloaderz.items;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by AEnterprise
 */
public class ItemLoader {
	public static Item guardianPearl;
	public static Item guardianPearlShard;
	public static Item enderPearlShard;
	public static Item brokenAnchoredPearl;

	public static void init() {
		guardianPearl = registerItem("guardianPearl").setMaxStackSize(16);
		guardianPearlShard = registerItem("guardianPearlShard");
		enderPearlShard = registerItem("enderPearlShard");
		brokenAnchoredPearl = registerItem("brokenAnchoredPearl");
	}

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(brokenAnchoredPearl), "geg", "epe", "geg", 'g', guardianPearlShard, 'e', enderPearlShard, 'p', Items.ender_pearl);
	}


	private static Item registerItem(String name) {
		Item item = new Item().setUnlocalizedName(name).setCreativeTab(ChunkLoaderZ.creativeTab);
		GameRegistry.registerItem(item, name);
		ChunkLoaderZ.proxy.registerInventoryModel(item, name);
		return item;
	}

}
