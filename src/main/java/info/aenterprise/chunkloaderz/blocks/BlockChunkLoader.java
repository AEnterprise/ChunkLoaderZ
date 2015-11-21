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

	public boolean isAlone(World world, BlockPos pos) {
		return getActualState(getDefaultState(), world, pos).getProperties().get(STATUS).equals(EnumStatus.ALONE);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess access, BlockPos pos) {
	    // Return the maximum light value to stop any blocks above lighting incorectly
	    return 0xF << 0x14 | 0xF << 0x4;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
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
