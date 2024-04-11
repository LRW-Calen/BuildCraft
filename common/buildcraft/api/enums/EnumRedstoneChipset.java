package buildcraft.api.enums;

import buildcraft.silicon.BCSiliconItems;
import buildcraft.silicon.item.ItemRedstoneChipset;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

//public enum EnumRedstoneChipset implements IStringSerializable
public enum EnumRedstoneChipset implements StringRepresentable
{
    RED,
    IRON,
    GOLD,
    QUARTZ,
    DIAMOND;

    private final String name = name().toLowerCase(Locale.ROOT);

    public ItemStack getStack(int stackSize)
    {
//        Item chipset = BCItems.Silicon.REDSTONE_CHIPSET;
        RegistryObject<Item> chipset = BCSiliconItems.chipsetRedstone;
        if (chipset == null)
        {
            return ItemStack.EMPTY;
        }
        // ordinal() -> meta
        // 0 红石
        // 1 铁
        // 2 金
        // 3 石英
        // 4 钻石
//        return new ItemStack(chipset, stackSize, ordinal());
        switch (this)
        {
            case RED:
                return new ItemStack(BCSiliconItems.chipsetRedstone.get(), stackSize);
            case IRON:
                return new ItemStack(BCSiliconItems.chipsetIron.get(), stackSize);
            case GOLD:
                return new ItemStack(BCSiliconItems.chipsetGold.get(), stackSize);
            case QUARTZ:
                return new ItemStack(BCSiliconItems.chipsetQuartz.get(), stackSize);
            case DIAMOND:
                return new ItemStack(BCSiliconItems.chipsetDiamond.get(), stackSize);
            default:
                return ItemStack.EMPTY;
        }
    }

    public ItemStack getStack()
    {
        return getStack(1);
    }

    public static EnumRedstoneChipset fromStack(ItemStack stack)
    {
//        if (stack == null)
        if (stack == null || stack.getItem().getClass() != ItemRedstoneChipset.class)
        {
            return RED;
        }
//        return fromOrdinal(stack.getMetadata());
        return ((ItemRedstoneChipset) stack.getItem()).type;
    }

    public static EnumRedstoneChipset fromOrdinal(int ordinal)
    {
        if (ordinal < 0 || ordinal >= values().length)
        {
            return RED;
        }
        return values()[ordinal];
    }

    @Override
//    public String getName()
    public String getSerializedName()
    {
        return name;
    }
}
