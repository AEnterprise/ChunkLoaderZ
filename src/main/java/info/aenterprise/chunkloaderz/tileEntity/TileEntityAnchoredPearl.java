package info.aenterprise.chunkloaderz.tileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

import net.minecraftforge.common.ForgeChunkManager;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.blocks.BlockLoader;

/**
 * Created by AEnterprise
 */
public class TileEntityAnchoredPearl extends TileEntity implements IUpdatePlayerListBox {
	private int scale = 1;
	private int hours, minutes, seconds, milliseconds, timeUntillTeleport, timer;
	private long lastTime;
	private boolean stillHere, formedFrame, wantsToTeleport;
	private BlockPos whereItWent;

	private static final int RANGE = 32;

	private ForgeChunkManager.Ticket ticket;

	public TileEntityAnchoredPearl() {
		hours = 9;
		lastTime = System.currentTimeMillis();
		stillHere = true;
		Random random = new Random();
		timeUntillTeleport = (int) (12000 + (random.nextDouble() * 60000));
		timer = 20;
	}


	@Override
	public void update() {
		if (worldObj.isRemote) return;

		if (wantsToTeleport) {
			tryTeleporting();
		}

		timer--;
		if (timer <= 0) {
			if (validFrame()) {
				if (!formedFrame && stillHere) form();
			} else {
				if (timeUntillTeleport > 0)
					timeUntillTeleport -= 20;
				if (formedFrame) deform();
			}
			timer = 20;
		}
		if (!stillHere) return;

		if (timeUntillTeleport <= 0) {
			wantsToTeleport = true;
		}
		if (canChunkload()) {
			if (ticket == null) {
				setTicket(ForgeChunkManager.requestTicket(ChunkLoaderZ.INSTANCE, worldObj, ForgeChunkManager.Type.NORMAL));
			}
			tick();
		} else {
			if (ticket != null)
				release();
			if (stillHere)
				worldObj.setBlockToAir(pos);
		}
	}

	@SuppressWarnings("unchecked")
	private void deform() {
		if (!formedFrame)
			return;
		for (BlockPos pos : (Iterable<BlockPos>) BlockPos.getAllInBox(getPos().add(-1, -1, -1), getPos().add(1, 1, 1))) {
			IBlockState state = getWorld().getBlockState(pos);
			if (state.getBlock() == BlockLoader.chunkLoader) {
				BlockLoader.chunkLoader.deform(getWorld(), pos);
			}
		}
		formedFrame = false;
	}

	private void tryTeleporting() {
		int x = ((int) Math.floor(RANGE / 2 * worldObj.rand.nextDouble())) - RANGE + getPos().getX();
		int y = ((int) Math.floor(RANGE / 2 * worldObj.rand.nextDouble())) - RANGE + getPos().getY();
		int z = ((int) Math.floor(RANGE / 2 * worldObj.rand.nextDouble())) - RANGE + getPos().getZ();

		if (y < 0)
			y = 0;
		if (y > 254)
			y = 254;

		while (y < 254) {
			BlockPos target = new BlockPos(x, y, z);
			System.out.println(String.format("Considering target location(%s)", target.toString()));
			if (worldObj.isAirBlock(target)) {
				worldObj.setBlockState(target, BlockLoader.anchoredPearl.getDefaultState());
				NBTTagCompound tag = new NBTTagCompound();
				writeToTeleportNBT(tag);
				TileEntity  entity = worldObj.getTileEntity(target);
				if (entity instanceof TileEntityAnchoredPearl) {
					((TileEntityAnchoredPearl) entity).readFromTeleportNBT(tag);
				}
				stillHere = false;
				worldObj.markBlockForUpdate(pos);
				worldObj.markBlockRangeForRenderUpdate(pos, pos);
				whereItWent = target;
				wantsToTeleport = false;
				System.out.println(String.format("Teleported to target location(%s)", target.toString()));
				release();
				return;
			}
			y++;
		}
	}



	private void tick() {
		long time = System.currentTimeMillis();
		long difference = lastTime - time;
		lastTime = time;
		milliseconds -= Math.abs(difference);
		while (milliseconds <= 0) {
			milliseconds += 1000;
			seconds--;
		}
		if (seconds <= 0) {
			if (minutes == 30 || minutes == 1) {
				scale++;
				worldObj.markBlockForUpdate(pos);
				worldObj.markBlockRangeForRenderUpdate(pos, pos);
			}
			seconds += 60;
			minutes--;
		}
		if (minutes <= 0){
			minutes += 60;
			hours--;
		}

	}

	@SuppressWarnings("unchecked")
	public void form() {
		for (BlockPos pos : (Iterable<BlockPos>) BlockPos.getAllInBox(getPos().add(-1, -1, -1), getPos().add(1, 1, 1))) {
			IBlockState state = getWorld().getBlockState(pos);
			if (state.getBlock() == BlockLoader.chunkLoader) {
				if (pos.equals(getPos().up())) {
					BlockLoader.chunkLoader.formMaster(getWorld(), pos);
				} else {
					BlockLoader.chunkLoader.formSlave(getWorld(), pos);
				}
			}
		}
		formedFrame = true;
	}

	public boolean canChunkload() {
		return stillHere && hours + minutes + seconds + milliseconds != 0;
	}

	@SuppressWarnings("unchecked")
	public boolean validFrame() {
		for (BlockPos pos : (Iterable<BlockPos>) BlockPos.getAllInBox(getPos().add(-1, -1, -1), getPos().add(1, 1, 1))) {
			if (pos.equals(getPos()))
				continue;
			IBlockState state = getWorld().getBlockState(pos);
			if (!(state.getBlock() == BlockLoader.chunkLoader && BlockLoader.chunkLoader.isAlone(worldObj, pos))) {
				return false;
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
		if (ticket == null) return;
		ForgeChunkManager.releaseTicket(ticket);
		ticket = null;
		deform();
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
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
	}

	public void writeToTeleportNBT(NBTTagCompound compound) {
		compound.setInteger("scale", scale);
		compound.setInteger("hours", hours);
		compound.setInteger("minutes", minutes);
		compound.setInteger("seconds", seconds);
		compound.setInteger("milliseconds", milliseconds);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeToTeleportNBT(compound);
		compound.setInteger("timeUntillTeleport", timeUntillTeleport);
		compound.setBoolean("stillHere", stillHere);
		compound.setBoolean("formedFrame", formedFrame);
		if (!stillHere)
			compound.setLong("whereItWent", whereItWent.toLong());
		compound.setBoolean("wantsToTeleport", wantsToTeleport);
	}

	public void readFromTeleportNBT(NBTTagCompound compound) {
		scale = compound.getInteger("scale");
		hours = compound.getInteger("hours");
		minutes = compound.getInteger("minutes");
		seconds = compound.getInteger("seconds");
		milliseconds = compound.getInteger("milliseconds");
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readFromTeleportNBT(compound);
		timeUntillTeleport = compound.getInteger("timeUntillTeleport");
		stillHere = compound.getBoolean("stillHere");
		formedFrame = compound.getBoolean("formedFrame");
		if (!stillHere)
			whereItWent = BlockPos.fromLong(compound.getLong("whereItWent"));
		wantsToTeleport = compound.getBoolean("wantsToTeleport");
	}

	public int getScale() {
		if (scale > 10)
			scale = 10;
		return scale;
	}

	public boolean isStillHere() {
		return stillHere;
	}

	public BlockPos getWhereItWent() {
		return whereItWent;
	}
}
