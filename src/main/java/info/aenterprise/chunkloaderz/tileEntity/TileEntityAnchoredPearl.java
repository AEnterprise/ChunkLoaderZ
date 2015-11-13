package info.aenterprise.chunkloaderz.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by AEnterprise
 */
public class TileEntityAnchoredPearl extends TileEntity {
	private double size = 1;

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setDouble("size", size);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		size = compound.getDouble("size");
	}

	public double getSize() {
		return size;
	}
}
