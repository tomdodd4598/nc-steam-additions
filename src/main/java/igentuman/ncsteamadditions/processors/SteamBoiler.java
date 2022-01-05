package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.config.NCSteamAdditionsConfig;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.catergory.SteamBoilerCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamBoiler;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBoiler;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import mezz.jei.api.IGuiHelper;
import nc.container.processor.ContainerMachineConfig;
import nc.init.NCBlocks;
import nc.integration.jei.JEIBasicCategory;
import nc.tile.processor.TileItemFluidProcessor;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SteamBoiler extends AbstractProcessor {

    public static String code = "steam_boiler";

    public static String particle1 = "splash";

    public static String particle2 = "reddust";

    public final static int GUID = 2;

    public final static int SIDEID = 1000+ GUID;

    public static int inputItems = 1;

    public static int inputFluids = 1;

    public static int outputFluids = 1;

    public static int outputItems = 0;

    public static RecipeHandler recipes;

    public Object[] craftingRecipe = new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"};

    public int getInputItems() {
        return inputItems;
    }

    public int getInputFluids() {
        return inputFluids;
    }

    public int getOutputFluids() {
        return outputFluids;
    }

    public int getOutputItems() {
        return outputItems;
    }

    public Object[] getCraftingRecipe()
    {
       return this.craftingRecipe;
    }

    public JEIHandler recipeHandler;

    public int getGuid()
    {
        return GUID;
    }

    public int getSideid()
    {
        return SIDEID;
    }

    public String getCode()
    {
        return code;
    }

    public String getBlockType()
    {
        return "nc_processor";
    }

    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler(player,  (SteamBoiler.TileSteamBoiler)tile,this);
    }

    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBoiler.SideConfig(player,  (SteamBoiler.TileSteamBoiler)tile,code);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
        return new ContainerSteamBoiler(player,  (SteamBoiler.TileSteamBoiler)tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfig(player,  (SteamBoiler.TileSteamBoiler)tile);
    }

    public JEIHandler getRecipeHandler()
    {
        return this.recipeHandler;
    }

    public JEIBasicCategory getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[GUID], Blocks.blocks[SteamBoiler.GUID], SteamBoiler.code, SteamBoilerCategory.SteamBoilerWrapper.class);
        return new SteamBoilerCategory(guiHelper,recipeHandler, this);
    }

    public ProcessorType getType()
    {
        if(type == null) {
            type = new ProcessorType(code,GUID,particle1,particle2);
            type.setProcessor(this);
        }
        return type;
    }

    public Class getTileClass()
    {
        return TileSteamBoiler.class;
    }

    public static class TileSteamBoiler extends TileItemFluidProcessor
    {
        public TileSteamBoiler()
        {
            super(
                    code,
                    inputItems,
                    inputFluids,
                    outputItems,
                    outputFluids,
                    defaultItemSorptions(inputItems, outputItems, true),
                    defaultTankCapacities(20000, inputFluids, outputFluids),
                    defaultTankSorptions(inputFluids, outputFluids),
                    NCSteamAdditionsRecipes.validFluids[GUID],
                    NCSteamAdditionsConfig.processor_time[GUID],
                    0, true,
                    NCSteamAdditionsRecipes.processorRecipeHandlers[GUID],
                    GUID+1, 0
            );
        }
    }

    public SteamBoiler.RecipeHandler getRecipes()
    {
        return new SteamBoiler.RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            addRecipe(
                    "coal",
                    fluidStack("water", FluidStackHelper.BUCKET_VOLUME),
                    fluidStack("steam", Math.round(FluidStackHelper.BUCKET_VOLUME*NCSteamAdditionsConfig.boilerConversion))
            );

            addRecipe(
                    "compressedCoal",
                    fluidStack("water", FluidStackHelper.BUCKET_VOLUME*5),
                    fluidStack("steam", Math.round(FluidStackHelper.BUCKET_VOLUME*NCSteamAdditionsConfig.boilerConversion)*5)
            );

            if(FluidRegHelper.fluidExists("cleanWater")) {
                addRecipe(
                        "coal",
                        fluidStack("cleanWater", FluidStackHelper.BUCKET_VOLUME),
                        fluidStack("steam", Math.round(FluidStackHelper.BUCKET_VOLUME*NCSteamAdditionsConfig.boilerConversion*1.5F))
                );
            }
        }
    }
}
