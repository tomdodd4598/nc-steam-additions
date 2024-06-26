package igentuman.ncsteamadditions.machine.gui;

import com.google.common.collect.Lists;
import igentuman.ncsteamadditions.machine.container.ContainerDigitalTransformer;
import igentuman.ncsteamadditions.processors.DigitalTransformer;
import igentuman.ncsteamadditions.tile.*;
import nc.gui.element.GuiItemRenderer;
import nc.tile.energy.ITileEnergy;
import nc.util.*;
import nclegacy.container.ContainerTileLegacy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class GuiDigitalTransformer extends GuiItemFluidMachine
{
	public DigitalTransformer processor;
	protected GuiItemRenderer energyUpgradeRender = null;
	@Override
	public DigitalTransformer getProcessor()
	{
		return processor;
	}

	@Override
	public List<String> energyInfo(ITileEnergy tile)
	{
		String energy = UnitHelper.prefix(tile.getEnergyStorage().getEnergyStored(),
				tile.getEnergyStorage().getMaxEnergyStored(), 5, "RF");
		String power = UnitHelper.prefix(this.tile.getProcessPower(), 5, "RF/t");

		String powerMultiplier = "x" + NCMath.decimalPlaces(this.tile.getPowerMultiplier(), 2);

		return Lists.newArrayList(
				TextFormatting.LIGHT_PURPLE + Lang.localize("gui.nc.container.energy_stored") + TextFormatting.WHITE
						+ " " + energy,
				TextFormatting.LIGHT_PURPLE + Lang.localize("gui.nc.container.process_power") + TextFormatting.WHITE
						+ " " + power,
				TextFormatting.AQUA + Lang.localize("gui.nc.container.power_multiplier") + TextFormatting.WHITE + " "
						+ powerMultiplier);
	}

	@Override
	public void drawEnergyTooltip(ITileEnergy tile, int mouseX, int mouseY, int x, int y, int width, int height)
	{
		this.drawTooltip(this.energyInfo(tile), mouseX, mouseY, x, y, width, height);
	}

	public GuiDigitalTransformer(EntityPlayer player, TileNCSProcessor tile, DigitalTransformer processor)
	{
		this(player, tile, new ContainerDigitalTransformer(player, tile), processor);
	}

	private GuiDigitalTransformer(EntityPlayer player, TileNCSProcessor tile, ContainerTileLegacy container, DigitalTransformer processor)
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
		if (tile.defaultProcessPower != 0)
		{
			int e = (int) Math.round(74D * tile.getEnergyStorage().getEnergyStored() / tile.getEnergyStorage().getMaxEnergyStored());
			drawTexturedModalRect(guiLeft + 8, guiTop + 6 + 74 - e, 176, 90 + 74 - e, 15, e);
		}
		else
			drawGradientRect(guiLeft + 8, guiTop + 6, guiLeft + 8 + 16, guiTop + 6 + 74, 0xFFC6C6C6, 0xFF8B8B8B);

			drawTexturedModalRect(guiLeft + inputFluidsLeft+4, guiTop + 33, 0, 168, getCookProgressScaled(135), 8);
		drawTexturedModalRect(guiLeft +101, guiTop + 63, 0, 176, (int) Math.round(tile.getRecipeEfficiency() * 66 / ((TileDigitalTransformer)tile).getEfficiencyCap()), 3);
		drawTexturedModalRect(guiLeft +101, guiTop + 69, 0, 179,  (int) Math.round(tile.getCurrentReactivity() * 66 / 20), 3);
		drawTexturedModalRect(guiLeft +101, guiTop + 75, 0, 182, (int) Math.round(tile.getTargetReactivity() * 66 / 20), 3);

		drawBackgroundExtras();
		TileDigitalTransformer t = (TileDigitalTransformer) tile;
	}

	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		drawEnergyTooltip(tile, mouseX, mouseY, 8, 6, 16, 74);
		renderButtonTooltips(mouseX, mouseY);
		this.drawTooltip(Lang.localize("gui.nc.dt.efficiency") +" "+String.format("%.2f", tile.getRecipeEfficiency()), mouseX, mouseY, 101, 63, 66, 3);
		this.drawTooltip(Lang.localize("gui.nc.dt.current_reactivity") +" "+String.format("%.2f", tile.getCurrentReactivity()), mouseX, mouseY, 101, 69, 66, 3);
		this.drawTooltip(Lang.localize("gui.nc.dt.target_reactivity") +" "+String.format("%.2f", tile.getTargetReactivity()), mouseX, mouseY, 101, 75, 66, 3);

	}
}
