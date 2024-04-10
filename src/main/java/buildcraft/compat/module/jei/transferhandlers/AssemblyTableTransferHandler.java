package buildcraft.compat.module.jei.transferhandlers;

import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.silicon.container.ContainerAssemblyTable;

import javax.annotation.Nullable;

import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;

// Calen: never used in 1.12.2?
public class AssemblyTableTransferHandler implements IRecipeTransferHandler<ContainerAssemblyTable,AssemblyRecipe>
{
    public AssemblyTableTransferHandler()
    {
    }

    @Override
    public Class<ContainerAssemblyTable> getContainerClass()
    {
        return ContainerAssemblyTable.class;
    }

    @Override
    public Class getRecipeClass()
    {
        return AssemblyRecipe.class;
    }

    @Nullable
    public IRecipeTransferError transferRecipe(ContainerAssemblyTable container, IRecipeLayout recipeLayout, Player player, boolean maxTransfer, boolean doTransfer)
    {
        return null;
    }
}
