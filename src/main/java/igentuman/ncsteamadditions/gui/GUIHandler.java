package igentuman.ncsteamadditions.gui;

import igentuman.ncsteamadditions.processors.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		ID--;
		if (tile != null)
		{
			for(AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
				if(ID == processor.getGuid() && tile.getClass() == processor.getTileClass()) {
					return processor.getGuiContainer(player,tile);
				}
				if(ID == processor.getSideid() && tile.getClass() == processor.getTileClass()) {
					return processor.getGuiContainerConfig(player,tile);
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		ID--;
		if (tile != null)
		{
			for(AbstractProcessor processor: ProcessorsRegistry.get().processors()) {
				if(ID == processor.getGuid() && tile.getClass() == processor.getTileClass()) {
					return processor.getLocalGuiContainer(player,tile);
				}
				if(ID == processor.getSideid() && tile.getClass() == processor.getTileClass()) {
					return processor.getLocalGuiContainerConfig(player,tile);
				}
			}
		}
		return null;
	}
}
