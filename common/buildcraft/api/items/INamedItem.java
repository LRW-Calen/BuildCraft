package buildcraft.api.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface INamedItem
{
//    Component getName(@Nonnull ItemStack stack);
    String getName_INamedItem(@Nonnull ItemStack stack);

    boolean setName(@Nonnull ItemStack stack, String name);
}
