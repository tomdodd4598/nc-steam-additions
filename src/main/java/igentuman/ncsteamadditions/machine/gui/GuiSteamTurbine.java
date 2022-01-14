package igentuman.ncsteamadditions.machine.gui;

import igentuman.ncsteamadditions.machine.container.ContainerSteamTurbine;
import igentuman.ncsteamadditions.processors.SteamTurbine;
import nc.container.ContainerTile;
import nc.tile.processor.TileItemFluidProcessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSteamTurbine extends GuiItemFluidMachine
{
	public SteamTurbine processor;

	@Override
	public SteamTurbine getProcessor()
	{
		return processor;
	}

	public GuiSteamTurbine(EntityPlayer player, TileItemFluidProcessor tile, SteamTurbine processor)
	{
		this(player, tile, new ContainerSteamTurbine(player, tile), processor);
	}

	private GuiSteamTurbine(EntityPlayer player, TileItemFluidProcessor tile, ContainerTile container, SteamTurbine processor)
	{
		super(processor.getCode(), player, tile, container);
		xSize = 176;
		ySize = 166;
		this.processor = processor;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(135), 12);
		drawBackgroundExtras();
	}

	public void initButtons()
	{
		super.initButtons();
	}


}