package igentuman.ncsteamadditions.block;

import igentuman.ncsteamadditions.block.MetaEnums.*;
import igentuman.ncsteamadditions.tab.NCSteamAdditionsTabs;
import nc.block.IBlockMeta;
import nc.enumm.*;
import nc.tile.ITile;
import nc.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.*;

public abstract class BlockMeta<T extends Enum<T> & IStringSerializable & IBlockMetaEnum> extends Block implements IBlockMeta {
    public final Class<T> enumm;
    public final T[] values;
    public final PropertyEnum<T> type;
    protected boolean canSustainPlant = true;
    protected boolean canCreatureSpawn = true;
    protected static boolean keepInventory;

    public BlockMeta(Class<T> enumm, PropertyEnum<T> property, Material material) {
        super(material);
        this.enumm = enumm;
        this.values = (T[])enumm.getEnumConstants();
        this.type = property;
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.type, this.values[0]));
        this.setMetaHarvestLevels();
        this.setHardness(2.0F);
        this.setResistance(15.0F);
    }
    
    public Class<T> getEnumClass() {
        return enumm;
    }
    
    public T[] getValues() {
        return values;
    }

    public String getMetaName(ItemStack stack) {
        return ((IStringSerializable)this.values[StackHelper.getMetadata(stack)]).getName();
    }

    public void setMetaHarvestLevels() {
        Iterator itr = Arrays.asList(this.values).iterator();

        while(itr.hasNext()) {
            T nextState = (T)itr.next();
            this.setHarvestLevel(((IBlockMetaEnum)nextState).getHarvestTool(), ((IBlockMetaEnum)nextState).getHarvestLevel(), this.getStateFromMeta(((IMetaEnum)nextState).getID()));
        }

    }

    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return ((IBlockMetaEnum)((Enum)state.getValue(this.type))).getLightValue();
    }

    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return ((IBlockMetaEnum)((Enum)state.getValue(this.type))).getHardness();
    }

    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return ((IBlockMetaEnum)((Enum)world.getBlockState(pos).getValue(this.type))).getResistance();
    }

    public int getMetaFromState(IBlockState state) {
        return ((IMetaEnum)((Enum)state.getValue(this.type))).getID();
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(this.type, this.values[meta]);
    }

    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(int i = 0; i < this.values.length; ++i) {
            list.add(new ItemStack(this, 1, i));
        }

    }

    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return this.canSustainPlant && super.canSustainPlant(state, world, pos, direction, plantable);
    }

    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return this.canCreatureSpawn && super.canCreatureSpawn(state, world, pos, type);
    }

    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return false;
    }

    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (this instanceof ITileEntityProvider) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof ITile) {
                ((ITile)tile).onBlockNeighborChanged(state, world, pos, fromPos);
            }
        }

    }

    public void dropItems(World world, BlockPos pos, IInventory inventory) {
        InventoryHelper.dropInventoryItems(world, pos, inventory);
    }

    public void dropItems(World world, BlockPos pos, List<ItemStack> stacks) {
        NCInventoryHelper.dropInventoryItems(world, pos, stacks);
    }


    public static class BlockIngot extends BlockMeta<IngotType> {
        public static final PropertyEnum<IngotType> TYPE = PropertyEnum.create("type", IngotType.class);

        public BlockIngot() {
            super(IngotType.class, TYPE, Material.IRON);
            this.setCreativeTab(NCSteamAdditionsTabs.ITEMS);
        }

        protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, new IProperty[]{TYPE});
        }

        public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
            return ((IngotType)world.getBlockState(pos).getValue(this.type)).getFireSpreadSpeed();
        }

        public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
            return ((IngotType)world.getBlockState(pos).getValue(this.type)).getFlammability();
        }

        public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
            return ((IngotType)world.getBlockState(pos).getValue(this.type)).isFireSource();
        }
    }

    public static class BlockOre extends BlockMeta<OreType> {
        public static final PropertyEnum<OreType> TYPE = PropertyEnum.create("type", OreType.class);

        public BlockOre() {
            super(OreType.class, TYPE, Material.ROCK);
            this.setCreativeTab(NCSteamAdditionsTabs.ITEMS);
        }

        protected BlockStateContainer createBlockState() {
            return new BlockStateContainer(this, new IProperty[]{TYPE});
        }

        @SideOnly(Side.CLIENT)
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.CUTOUT;
        }
    }
}
