package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.silicon.container.ContainerAdvancedCraftingTable;

import javax.annotation.Nullable;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class AdvancedCraftingItemsTransferHandler implements IRecipeTransferHandler<ContainerAdvancedCraftingTable, CraftingRecipe>
{
    public AdvancedCraftingItemsTransferHandler()
    {
    }

    @Override
    public Class<ContainerAdvancedCraftingTable> getContainerClass()
    {
        return ContainerAdvancedCraftingTable.class;
    }

    @Override
    public Class<CraftingRecipe> getRecipeClass()
    {
        return CraftingRecipe.class;
    }

    @Override
    @Nullable
//    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    public IRecipeTransferError transferRecipe(ContainerAdvancedCraftingTable container, CraftingRecipe recipe, IRecipeSlotsView recipeSlots, Player player, boolean maxTransfer, boolean doTransfer)
    {
        if (doTransfer)
        {
            AutoCraftItemsTransferHandler.transferRecipe((itemStacks) ->
                    {
                        container.sendSetPhantomSlots(container.tile.invBlueprint, itemStacks);
                    },
//                    recipeLayout
                    recipe,
                    recipeSlots
            );
        }

        return null;
    }
}
