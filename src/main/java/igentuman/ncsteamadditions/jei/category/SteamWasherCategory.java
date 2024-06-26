package igentuman.ncsteamadditions.jei.category;

import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.machine.gui.GuiSteamWasher;
import igentuman.ncsteamadditions.processors.*;
import mezz.jei.api.IGuiHelper;
import nc.recipe.*;
import nclegacy.jei.*;

public class SteamWasherCategory extends ParentProcessorCategory
{
	private SteamWasher processor;

	protected int getCellSpan()
	{
		return GuiSteamWasher.cellSpan;
	}

	protected int getItemsLeft()
	{
		return GuiSteamWasher.inputItemsLeft;
	}

	protected int getFluidsLeft()
	{
		return GuiSteamWasher.inputFluidsLeft;
	}

	protected int getItemsTop()
	{
		return GuiSteamWasher.inputItemsTop;
	}

	protected int getFluidsTop()
	{
		return GuiSteamWasher.inputFluidsTop;
	}

	@Override
	public SteamWasher getProcessor()
	{
		return processor;
	}

	public SteamWasherCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler, SteamWasher proc)
	{
		super(guiHelper, handler, proc.code, 24, 7, 148, 56, proc);
		processor = proc;
	}

	public static class SteamWasherWrapper extends JEIMachineRecipeWrapperLegacy
	{

		public SteamWasherWrapper(IGuiHelper guiHelper, IJEIHandlerLegacy jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 24, 7, 0, 0, 0, 0, 0, 0, 94, 30, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_WASHER.GUID];
			return recipe.getBaseProcessTime(NCSteamAdditionsConfig.processor_time[ProcessorsRegistry.get().STEAM_WASHER.GUID]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}
	}
}
