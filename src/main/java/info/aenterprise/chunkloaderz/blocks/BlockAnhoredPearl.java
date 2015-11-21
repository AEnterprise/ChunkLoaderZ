package info.aenterprise.chunkloaderz.blocks;

import info.aenterprise.chunkloaderz.ChunkLoaderZ;
import info.aenterprise.chunkloaderz.blocks.properties.PropertyRange;
import info.aenterprise.chunkloaderz.tileEntity.TileEntityAnchoredPearl;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AEnterprise
 */
public class BlockAnhoredPearl extends Block implements ITileEntityProvider {
	private static final PropertyRange SCALE = new PropertyRange("scale", 0, 10, 1);
	private static final PropertyEnum LOCATION = PropertyEnum.create("location", EnumLocation.class);

	public BlockAnhoredPearl() {
		super(Material.iron);
		setUnlocalizedName("anchoredPearl");
		setLightLevel(15);
		setCreativeTab(ChunkLoaderZ.creativeTab);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(SCALE, 1).withProperty(LOCATION, EnumLocation.HERE));
		setHardness(2.5f);
		setResistance(2.5f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileEntityAnchoredPearl) {
			TileEntityAnchoredPearl pearl = (TileEntityAnchoredPearl) entity;
			if (!pearl.isStillHere() && !world.isRemote) {
				BlockPos whereItWent = pearl.getWhereItWent();
				player.addChatComponentMessage(new ChatComponentText(String.format("This pearl teleported to %d, %d, %d", whereItWent.getX(), whereItWent.getY(), whereItWent.getZ())));
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
	public int getRenderType() {
		return 3;
	}


	@Override
	public boolean isFullCube() {
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
			return state.withProperty(SCALE, ((TileEntityAnchoredPearl) entity).getScale()).withProperty(LOCATION, ((TileEntityAnchoredPearl) entity).isStillHere() ? EnumLocation.HERE : EnumLocation.THERE);
		return state.withProperty(SCALE, 1).withProperty(LOCATION, EnumLocation.HERE);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, SCALE, LOCATION);
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

	public enum EnumLocation implements IStringSerializable	{
		HERE,
		THERE;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
