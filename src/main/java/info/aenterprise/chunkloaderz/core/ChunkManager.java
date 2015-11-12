package info.aenterprise.chunkloaderz.core;

import info.aenterprise.chunkloaderz.tileEntity.TileEntityChunkLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.List;

/**
 * Created by AEnterprise
 */
public class ChunkManager implements ForgeChunkManager.LoadingCallback {
	public static final ChunkManager INSTANCE = new ChunkManager();

	private ChunkManager(){}


	@Override
	public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
		for (ForgeChunkManager.Ticket ticket: tickets) {
			int x = ticket.getModData().getInteger("x");
			int y = ticket.getModData().getInteger("y");
			int z = ticket.getModData().getInteger("z");
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			if (tile instanceof TileEntityChunkLoader) {
				TileEntityChunkLoader chunkLoader = (TileEntityChunkLoader) tile;
				chunkLoader.setTicket(ticket);
			}
		}
	}
}
