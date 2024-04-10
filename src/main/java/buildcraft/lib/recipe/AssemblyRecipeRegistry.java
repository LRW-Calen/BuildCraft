/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.recipe;

import buildcraft.api.recipes.AssemblyRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.*;

public class AssemblyRecipeRegistry
{
    @Deprecated
    //    public static final Map<ResourceLocation, AssemblyRecipe> REGISTRY = new HashMap<>();
    public static final List<AssemblyRecipe> REGISTRY = new LinkedList<>();

    // Calen
    public static List<AssemblyRecipe> getAll(Level world)
    {
        List<AssemblyRecipe> ret = world.getRecipeManager().getAllRecipesFor(AssemblyRecipe.TYPE);
        ret.addAll(REGISTRY);
        return ret;
    }

    /**
     * Don't call this, and use datagen instead!
     */
    @Deprecated
    public static void register(AssemblyRecipe recipe)
    {
//        REGISTRY.put(recipe.getRegistryName(), recipe);
        REGISTRY.add(recipe);
    }

    // Calen: never used in 1.12.2
    @Nonnull
//    public static List<AssemblyRecipe> getRecipesFor(@Nonnull NonNullList<ItemStack> possibleIn)
    public static List<AssemblyRecipe> getRecipesFor(Level world, @Nonnull NonNullList<ItemStack> possibleIn)
    {
        List<AssemblyRecipe> all = new ArrayList<>();
//        for (AssemblyRecipe ar : REGISTRY.values())
        for (AssemblyRecipe ar : getAll(world))
        {
            if (!ar.getOutputs(possibleIn).isEmpty())
            {
                all.add(ar);
            }
        }
        return all;
    }
}
