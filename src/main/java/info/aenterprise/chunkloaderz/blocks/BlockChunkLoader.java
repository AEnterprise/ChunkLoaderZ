package info.aenterprise.chunkloaderz.blocks;

import java.util.Locale;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;

/**
 * Created by AEnterprise
 */
public class BlockChunkLoader extends Block {
	private static final PropertyEnum STATUS = PropertyEnum.create("status", EnumStatus.class);
	private final BiMap<Integer, IBlockState> stateMap = HashBiMap.create();

	public BlockChunkLoader() {
		super(Material.iron);
		setUnlocalizedName("blockChunkLoader");
		setLightLevel(15);
		setLightOpacity(0);
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STATUS, EnumStatus.ALONE));
		stateMap.put(0, getDefaultState());
        stateMap.put(1, getDefaultState().withProperty(STATUS, EnumStatus.HIDDEN));
        stateMap.put(2, getDefaultState().withProperty(STATUS, EnumStatus.MASTER));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, STATUS);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    if (stateMap.inverse().containsKey(state)) return stateMap.inverse().get(state);
	    return 0;
	}

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

//	@Override
//	public boolean isFullCube() {
//		return false;
//	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess access, BlockPos pos) {
	    IBlockState state = access.getBlockState(pos);
	    // Just in case the state doesn't have the property. We don't want to cause a crash.
	    if (state.getBlock() != this) return state.getBlock().getMixedBrightnessForBlock(access, pos);
	    // If its not the master, then the default works fine
	    if (state.getValue(STATUS) != EnumStatus.MASTER) return super.getMixedBrightnessForBlock(access, pos);
	    // Return the maximum light value to stop any blocks above lighting incorectly
	    return 0xFFFFFF;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
	    return EnumWorldBlockLayer.CUTOUT;
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
