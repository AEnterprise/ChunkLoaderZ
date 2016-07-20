package info.aenterprise.chunkloaderz.blocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Created by AEnterprise
 */
public class BlockChunkLoader extends Block {
	private static final PropertyEnum STATUS = PropertyEnum.create("status", EnumStatus.class);
	private final BiMap<Integer, IBlockState> stateMap = HashBiMap.create();

	public BlockChunkLoader() {
		super(Material.IRON);
		setUnlocalizedName("blockChunkLoader");
		setRegistryName("blockChunkLoader");
		setLightLevel(1.0f);
		setLightOpacity(0);
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STATUS, EnumStatus.ALONE));
		stateMap.put(0, getDefaultState());
		stateMap.put(1, getDefaultState().withProperty(STATUS, EnumStatus.HIDDEN));
		stateMap.put(2, getDefaultState().withProperty(STATUS, EnumStatus.MASTER));
		setHardness(2.5f);
		setResistance(2.5f);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STATUS);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (stateMap.inverse().containsKey(state)) return stateMap.inverse().get(state);
		return 0;
	}

	@Nonnull
	@Deprecated
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if (stateMap.containsKey(meta)) return stateMap.get(meta);
		return getDefaultState();
	}

	public void formSlave(World world, BlockPos pos) {
		world.setBlockState(pos, getDefaultState().withProperty(STATUS, EnumStatus.HIDDEN));
	}

	public void deform(World world, BlockPos pos) {
		world.setBlockState(pos, getDefaultState().withProperty(STATUS, EnumStatus.ALONE));
	}

	public void formMaster(World world, BlockPos pos) {
		world.setBlockState(pos, getDefaultState().withProperty(STATUS, EnumStatus.MASTER));
	}

	public boolean isAlone(World world, BlockPos pos) {
		return getDefaultState().getActualState(world, pos).getProperties().get(STATUS).equals(EnumStatus.ALONE);
	}

	@Deprecated
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return state.getValue(STATUS) == EnumStatus.ALONE;
	}

	public enum EnumStatus implements IStringSerializable {
		ALONE,
		HIDDEN,
		MASTER;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
