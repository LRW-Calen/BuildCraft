package buildcraft.compat.module.jei.silicon;

import buildcraft.api.recipes.StackDefinition;
import com.google.common.collect.Lists;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class Utils
{
    public Utils()
    {
    }

    public static List<ItemStack> getItemStacks(StackDefinition definition)
    {
        List<ItemStack> list = Lists.newArrayList();
        if (definition.filter != null)
        {

            for (ItemStack stack : definition.filter.getExamples())
            {
                ItemStack sizedStack = stack.copy();
                sizedStack.setCount(definition.count);
                list.add(sizedStack);
            }
        }

        return list;
    }
}
