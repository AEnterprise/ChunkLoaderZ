package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.blocks.properties.PropertyScale;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AEnterprise
 */
public class BlockAnhoredPearl extends Block implements ITileEntityProvider {
	private static final PropertyScale SCALE = new PropertyScale("scale", 0, 10, 1);

	public BlockAnhoredPearl() {
		super(Material.iron);
		setUnlocalizedName("anchoredPearl");
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(SCALE, 1));
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
		return 3;
	}


	@Override
	public boolean isNormalCube() {
		return false;
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl)
			return state.withProperty(SCALE, ((TileEntityAnchoredPearl) entity).getScale());
		return state.withProperty(SCALE, 1);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, SCALE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}


}
