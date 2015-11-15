package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.blocks.properties.PropertyRange;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by AEnterprise
 */
public class BlockChunkLoader extends Block implements ITileEntityProvider {
	private static final PropertyRange STATUS = new PropertyRange("STATUS", 0, 26, 1);

	public BlockChunkLoader() {
		super(Material.iron);
		setUnlocalizedName("blockChunkLoader");
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STATUS, 0));


	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, STATUS);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (entity instanceof TileEntityChunkLoader)
			return state.withProperty(STATUS, ((TileEntityChunkLoader) entity).getStatus());
		return getDefaultState();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChunkLoader();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	@Override
	public int getLightValue() {
		return 1;
	}

	@Override
	public int getLightOpacity() {
		return 0;
	}
}
