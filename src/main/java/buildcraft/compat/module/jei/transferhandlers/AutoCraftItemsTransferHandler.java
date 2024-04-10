package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.factory.container.ContainerAutoCraftItems;
import buildcraft.factory.tile.TileAutoWorkbenchItems;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class AutoCraftItemsTransferHandler implements IRecipeTransferHandler<ContainerAutoCraftItems, CraftingRecipe>
{
    public AutoCraftItemsTransferHandler()
    {
    }

    @Override
    public Class<ContainerAutoCraftItems> getContainerClass()
    {
        return ContainerAutoCraftItems.class;
    }

    @Override
    public Class getRecipeClass()
    {
        return CraftingRecipe.class;
    }

    @Override
    @Nullable
//    public IRecipeTransferError transferRecipe(ContainerAutoCraftItems container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    public IRecipeTransferError transferRecipe(ContainerAutoCraftItems container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer)
    {
        if (doTransfer)
        {
            transferRecipe((itemStacks) ->
                    {
                        container.sendSetPhantomSlots((container.tile).invBlueprint, itemStacks);
                    },
//                    recipeLayout
                    recipe,
                    recipeSlots
            );
        }

        return null;
    }

    //    static void transferRecipe(Consumer<List<ItemStack>> callback, IRecipeLayout recipeLayout)
    static void transferRecipe(Consumer<List<ItemStack>> callback, CraftingRecipe recipe, IRecipeSlotsView recipeLayout)
//    public IRecipeTransferError transferRecipe(ContainerAutoCraftItems container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer)
    {
//        Map<Integer, ? extends IGuiIngredient<ItemStack>> inputs = recipeLayout.getItemStacks().getGuiIngredients();

        List<IRecipeSlotView> inputs = recipeLayout.getSlotViews();
        List<ItemStack> stacks = Lists.newArrayList();

        for (int slot = 0; slot < 9; ++slot)
        {
//            IGuiIngredient<ItemStack> ingredient = (IGuiIngredient) inputs.getOrDefault(slot + 1, (Object) null);
            IRecipeSlotView ingredient = inputs.get(slot + 1);
//            ItemStack stack = ingredient == null ? ItemStack.EMPTY : (ItemStack) ingredient.getDisplayedIngredient();
            ItemStack stack = ingredient == null ? ItemStack.EMPTY : (ItemStack) ingredient.getDisplayedIngredient().get().getIngredient();
            stacks.add(stack == null ? ItemStack.EMPTY : stack);
        }

        callback.accept(stacks);
    }
}
