package igentuman.ncsteamadditions.item;


import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCopperSheet extends Item {
    public ItemCopperSheet() {
        super();
    }
    public static int regId = 0;
    public CreativeTabs getCreativeTab()
    {
        return NCSteamAdditionsTabs.ITEMS;
    }

}
