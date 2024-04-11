package buildcraft.api.items;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface IList extends INamedItem {
    boolean matches(@Nonnull ItemStack stackList, @Nonnull ItemStack item);
}
