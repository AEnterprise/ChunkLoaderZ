package info.aenterprise.chunkloaderz.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by AEnterprise
 */
public class AnhoredPearlRenderer extends TileEntitySpecialRenderer {
	private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

	@Override
	public void renderTileEntityAt(TileEntity entity, double posX, double posY, double posZ, float p_180535_8_, int p_180535_9_) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		this.bindTexture(TextureMap.locationBlocksTexture);
		RenderHelper.disableStandardItemLighting();
		worldrenderer.startDrawingQuads();
		worldrenderer.setVertexFormat(DefaultVertexFormats.BLOCK);
		worldrenderer.setTranslation(posX, posY, posZ);
		worldrenderer.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		World world = this.getWorld();
		IBlockState state = world.getBlockState(entity.getPos());

		this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(state, world, entity.getPos()), state, entity.getPos(), worldrenderer, true);

		worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
	}
}
