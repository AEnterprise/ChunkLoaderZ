package info.aenterprise.chunkloaderz.tileEntity;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AEnterprise
 */
public class TileEntityChunkLoader extends TileEntity implements IUpdatePlayerListBox {
	private Ticket ticket;
	private boolean wantsTicket = true;

	public TileEntityChunkLoader() {
		super();
	}

	@Override
	public void update() {
		if (worldObj.isRemote)
			return;
		if (wantsTicket && ticket == null) {
			setTicket(ForgeChunkManager.requestTicket(ChunkLoaderZ.INSTANCE, worldObj, ForgeChunkManager.Type.NORMAL));
		}
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
		if (ticket != null) {
			ticket.getModData().setInteger("x", pos.getX());
			ticket.getModData().setInteger("y", pos.getY());
			ticket.getModData().setInteger("z", pos.getZ());
			for (ChunkCoordIntPair chunk: getChunksAround(pos.getX() >> 4, pos.getZ() >> 4, 1)) {
				ForgeChunkManager.forceChunk(ticket, chunk);
			}
			System.out.println("chunks loaded");
		}
	}

	public void release() {
		ForgeChunkManager.releaseTicket(ticket);
	}

	private List<ChunkCoordIntPair> getChunksAround(int chunkX, int chunkZ, int radius) {
		List<ChunkCoordIntPair> list = new ArrayList<ChunkCoordIntPair>();
		for (int x = chunkX - radius; x <= chunkX + radius; x++) {
			for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
				list.add(new ChunkCoordIntPair(x, z));
			}
		}
		return list;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		release();
	}
}
