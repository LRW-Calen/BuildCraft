package buildcraft.silicon.item;

import buildcraft.api.enums.EnumRedstoneChipset;
import buildcraft.lib.item.ItemBC_Neptune;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemRedstoneChipset extends ItemBC_Neptune
{
    public final EnumRedstoneChipset type;

    public ItemRedstoneChipset(String idBC, Item.Properties props, EnumRedstoneChipset type)
    {
        super(idBC, props);
        this.type = type;
    }

    // Calen: not still useful in 1.18.2
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void addModelVariants(TIntObjectHashMap<ModelResourceLocation> variants)
//    {
//        for (EnumRedstoneChipset type : EnumRedstoneChipset.values())
//        {
//            addVariant(variants, type.ordinal(), type.getSerializedName());
//        }
//    }

    @Override
    public void addSubItems(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        subItems.add(new ItemStack(this, 1));
    }


    @Override
//    public String getUnlocalizedName(ItemStack stack)
    public String getDescriptionId(ItemStack stack)
    {
//        return "item.redstone_" + EnumRedstoneChipset.values()[stack.getMetadata()].getName() + "_chipset";
        return "item.redstone_" + type.name().toLowerCase() + "_chipset.name";
    }
}
