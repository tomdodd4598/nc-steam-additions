package igentuman.ncsteamadditions.processors;

import igentuman.ncsteamadditions.block.Blocks;
import igentuman.ncsteamadditions.item.Items;
import igentuman.ncsteamadditions.jei.JEIHandler;
import igentuman.ncsteamadditions.jei.category.SteamBlenderCategory;
import igentuman.ncsteamadditions.machine.container.ContainerSteamBlender;
import igentuman.ncsteamadditions.machine.gui.GuiSteamBlender;
import igentuman.ncsteamadditions.recipes.NCSteamAdditionsRecipes;
import igentuman.ncsteamadditions.tile.TileNCSProcessor;
import mezz.jei.api.IGuiHelper;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.*;
import nclegacy.container.ContainerMachineConfigLegacy;
import nclegacy.jei.JEIBasicCategoryLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.*;

public class SteamBlender extends AbstractProcessor {

    public SteamBlender()
    {
        code = "steam_blender";
        particle1 = "splash";
        particle2 = "endRod";
        GUID = 7;
        SIDEID = 1000 + GUID;
        inputItems = 3;
        inputFluids = 1;
        outputFluids = 1;
        outputItems = 0;
        craftingRecipe = new Object[] {
                "PPP", "CFC", "RHR",
                'P', "chest",
                'F', net.minecraft.init.Blocks.FURNACE,
                'C', Items.items[0],
                'R', net.minecraft.init.Items.ENDER_EYE,
                'H',  RegistryHelper.itemStackFromRegistry("minecraft:cauldron")
        };

    }
    public String getSound()
    {
        return "blender_on";
    }
    public Class getGuiClass()
    {
        return GuiSteamBlender.class;
    }
    public String getBlockType()
    {
        return "nc_processor";
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainer(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBlender(player,  (TileNCSProcessor)tile,this);
    }
    @SideOnly(Side.CLIENT)
    public Object getLocalGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new GuiSteamBlender.SideConfig(player, (TileNCSProcessor) tile, this);
    }

    public Object getGuiContainer(EntityPlayer player, TileEntity tile) {
       return new ContainerSteamBlender(player, (TileNCSProcessor) tile);
    }

    public Object getGuiContainerConfig(EntityPlayer player, TileEntity tile) {
        return new ContainerMachineConfigLegacy(player, (TileNCSProcessor) tile);
    }

    public JEIBasicCategoryLegacy getRecipeCategory(IGuiHelper guiHelper)
    {
        recipeHandler = new JEIHandler(this, NCSteamAdditionsRecipes.processorRecipeHandlers[getGuid()], Blocks.blocks[getGuid()], code, SteamBlenderCategory.SteamBlenderWrapper.class);
        return new SteamBlenderCategory(guiHelper,recipeHandler, this);
    }

    public static class TileSteamBlender extends TileNCSProcessor {
        public TileSteamBlender() {
            super(
                    ProcessorsRegistry.get().STEAM_BLENDER.code,
                    ProcessorsRegistry.get().STEAM_BLENDER.inputItems,
                    ProcessorsRegistry.get().STEAM_BLENDER.inputFluids,
                    ProcessorsRegistry.get().STEAM_BLENDER.outputItems,
                    ProcessorsRegistry.get().STEAM_BLENDER.outputFluids,
                    ProcessorsRegistry.get().STEAM_BLENDER.GUID
            );
        }
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
        return TileSteamBlender.class;
    }

    public TileEntity getTile()
    {
        return new TileSteamBlender();
    }

    public RecipeHandler getRecipes()
    {
        return new RecipeHandler();
    }


    public class RecipeHandler extends AbstractProcessor.RecipeHandler {
        public RecipeHandler()
        {
            super(code, inputItems, inputFluids, outputItems, outputFluids);
        }

        @Override
        public void addRecipes()
        {
            addRecipe("dustUraniumOxide", new EmptyItemIngredient(), new EmptyItemIngredient(),
                    fluidStack("steam", 250),
                    fluidStack("uranium_oxide", FluidStackHelper.BUCKET_VOLUME/2)
            );
            addRecipe("dustIron",oreStack("dustCoal",3),new EmptyItemIngredient(),
                    fluidStack("steam", 250),
                    fluidStack("steel", FluidStackHelper.INGOT_VOLUME)
            );
            addRecipe(oreStack("dustCopper",3),"dustLead",new EmptyItemIngredient(),
                    fluidStack("steam", 250),
                    fluidStack("bronze", FluidStackHelper.INGOT_VOLUME)
            );
            if(FluidRegHelper.fluidExists("ic2distilled_water")) {
                addRecipe("blockSnow", new EmptyItemIngredient(), new EmptyItemIngredient(),
                        fluidStack("low_pressure_steam", 250),
                        fluidStack("ic2distilled_water", FluidStackHelper.BUCKET_VOLUME)
                );
            }
        }
    }
}
