package info.aenterprise.chunkloaderz;

import info.aenterprise.chunkloaderz.blocks.BlockLoader;
import info.aenterprise.chunkloaderz.core.ChunkManager;
import info.aenterprise.chunkloaderz.core.CommonProxy;
import info.aenterprise.chunkloaderz.core.EventListener;
import info.aenterprise.chunkloaderz.items.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChunkLoaderZ.MODID, version = ChunkLoaderZ.VERSION)
public class ChunkLoaderZ {
	public static final String MODID = "chunkloaderz";
	public static final String VERSION = "@MODVERSION@";

	@Instance(ChunkLoaderZ.MODID)
	public static ChunkLoaderZ INSTANCE;

	@SidedProxy(clientSide = "info.aenterprise.chunkloaderz.core.ClientProxy", serverSide = "info.aenterprise.chunkloaderz.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs creativeTab = new CreativeTabs("chunkloaderzTab") {
		@Override
		public Item getTabIconItem() {
			return new ItemStack(BlockLoader.chunkLoader).getItem();
		}
	};


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		OBJLoader.instance.addDomain("chunkloaderz");
		BlockLoader.init();
		ItemLoader.init();
		ItemLoader.addRecipes();
		BlockLoader.addRecipes();
	}


	@EventHandler
	public void init(FMLInitializationEvent event) {
		ForgeChunkManager.setForcedChunkLoadingCallback(INSTANCE, ChunkManager.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new EventListener());
		proxy.init();

	}
}
