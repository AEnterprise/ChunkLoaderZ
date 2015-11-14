package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by AEnterprise
 */
public class BlockChunkLoader extends Block {
	private static final PropertyBool VISIBLE = PropertyBool.create("visible");
	private static final PropertyBool MASTER = PropertyBool.create("master");

	public BlockChunkLoader() {
		super(Material.iron);
		setUnlocalizedName("blockChunkLoader");
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VISIBLE, true).withProperty(MASTER, false));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, VISIBLE, MASTER);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (state.getValue(VISIBLE).equals(false))
			meta++;
		if (state.getValue(MASTER).equals(true))
			meta +=2;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		switch (meta) {
			case 0: return state.withProperty(VISIBLE, true).withProperty(MASTER, false);
			case 1: return state.withProperty(VISIBLE, false).withProperty(MASTER, false);
			case 2: return state.withProperty(VISIBLE, false).withProperty(MASTER, true);
			case 3: return state.withProperty(VISIBLE, true).withProperty(MASTER, true); //shouldn't ever heapen, here for completions sake
		}
		return state;
	}

	public void hide(World world, BlockPos pos) {
		world.setBlockState(pos, blockState.getBaseState().withProperty(VISIBLE, false).withProperty(MASTER, false));
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
