package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

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
		return 0;
	}
}
