package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AEnterprise
 */
public class BlockAnhoredPearl extends Block implements ITileEntityProvider {
	private static final PropertyEnum LOCATION = PropertyEnum.create("location", EnumLocation.class);
	private static final PropertyInteger SCALE = PropertyInteger.create("scale", 0, 10);

	public BlockAnhoredPearl() {
		super(Material.iron);
		setUnlocalizedName("anchoredPearl");
		setRegistryName("anchoredPearl");
		setLightLevel(15);
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(SCALE, 1).withProperty(LOCATION, EnumLocation.HERE));
		setHardness(2.5f);
		setResistance(2.5f);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl) {
			TileEntityAnchoredPearl pearl = (TileEntityAnchoredPearl) entity;
			if (!pearl.isStillHere() && !worldIn.isRemote) {
				BlockPos whereItWent = pearl.getWhereItWent();
				playerIn.addChatComponentMessage(new TextComponentString(String.format("This pearl teleported to %d, %d, %d", whereItWent.getX(), whereItWent.getY(), whereItWent.getZ())));
			}
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAnchoredPearl();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (world.isRemote) return;
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl) {
			TileEntityAnchoredPearl pearl = (TileEntityAnchoredPearl) entity;
			if (pearl.isStillHere()) {
				ItemStack stack = new ItemStack(BlockLoader.anchoredPearl);
				NBTTagCompound tag = new NBTTagCompound();
				pearl.writeToTeleportNBT(tag);
				stack.setTagCompound(tag);
				EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
				world.spawnEntityInWorld(entityItem);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl) {
			if (stack.hasTagCompound())
				((TileEntityAnchoredPearl) entity).readFromTeleportNBT(stack.getTagCompound());
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl)
			return state.withProperty(SCALE, ((TileEntityAnchoredPearl) entity).getScale()).withProperty(LOCATION, ((TileEntityAnchoredPearl) entity).isStillHere() ? EnumLocation.HERE : EnumLocation.THERE);
		return state.withProperty(SCALE, 1).withProperty(LOCATION, EnumLocation.HERE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SCALE, LOCATION);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	public enum EnumLocation implements IStringSerializable {
		HERE,
		THERE;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}
