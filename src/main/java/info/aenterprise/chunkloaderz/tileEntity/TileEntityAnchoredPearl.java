package info.aenterprise.chunkloaderz.tileEntity;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.blocks.BlockChunkLoader;
import info.aenterprise.chunkloaderz.blocks.BlockLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AEnterprise
 */
public class TileEntityAnchoredPearl extends TileEntity implements IUpdatePlayerListBox {
	private int scale = 1;

	private ForgeChunkManager.Ticket ticket;


	@Override
	public void update() {
		if (worldObj.isRemote)
			return;
		if (canChunkload()) {
			if (ticket == null) {
				setTicket(ForgeChunkManager.requestTicket(ChunkLoaderZ.INSTANCE, worldObj, ForgeChunkManager.Type.NORMAL));
				form();
			}
		} else if (ticket!= null) {
			release();
		}
	}

	public void form() {
		int status = 1;
		for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
			for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
				for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
					if (x == pos.getX() && y == pos.getY() && z == pos.getZ())
						continue;
					BlockPos pos2 = new BlockPos(x, y, z);
					((TileEntityChunkLoader) worldObj.getTileEntity(pos2)).setStatus(status);
					status++;
					worldObj.markBlockForUpdate(pos2);
				}
			}
		}
		worldObj.markBlockRangeForRenderUpdate(new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1), new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
	}

	public boolean canChunkload() {
		return validFrame();
	}

	public boolean validFrame() {
		for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
			for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
				for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
					if (x == pos.getX() && y == pos.getY() && z == pos.getZ())
						continue;
					if (worldObj.getBlockState(new BlockPos(x, y, z)).getBlock() != BlockLoader.chunkLoader || !(worldObj.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityChunkLoader))
						return false;
				}
			}
		}
		return true;
	}

	public void setTicket(ForgeChunkManager.Ticket ticket) {
		this.ticket = ticket;
		if (ticket != null) {
			ticket.getModData().setInteger("x", pos.getX());
			ticket.getModData().setInteger("y", pos.getY());
			ticket.getModData().setInteger("z", pos.getZ());
			for (ChunkCoordIntPair chunk: getChunksAround(pos.getX() >> 4, pos.getZ() >> 4, 1)) {
				ForgeChunkManager.forceChunk(ticket, chunk);
			}
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

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("scale", scale);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		scale = compound.getInteger("scale");
	}

	public int getScale() {
		return scale;
	}
}
