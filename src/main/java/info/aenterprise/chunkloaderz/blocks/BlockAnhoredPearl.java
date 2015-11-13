package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by AEnterprise
 */
public class BlockAnhoredPearl extends Block implements ITileEntityProvider {

	public BlockAnhoredPearl() {
		super(Material.iron);
		setUnlocalizedName("anchoredPearl");
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAnchoredPearl();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		//empty list for now, shards based on remaining size later
		return new ArrayList<ItemStack>();
	}

	@Override
	public int getRenderType() {
		return -1;
	}
}
