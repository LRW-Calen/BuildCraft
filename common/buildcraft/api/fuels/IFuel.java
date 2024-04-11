/**
 * Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p>
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution.
 */
package buildcraft.api.fuels;

import buildcraft.api.BCModules;
import buildcraft.lib.misc.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

//public interface IFuel
public interface IFuel extends Recipe<Container>
{
    public static final ResourceLocation TYPE_ID = new ResourceLocation(BCModules.ENERGY.getModId(), "fuel");

    public static final RecipeType<IFuel> TYPE = RecipeType.register(TYPE_ID.toString());

    /** @return The input fluid. The {@link FluidStack#getAmount()} is ignored. */
    FluidStack getFluid();

    /** @return The number of ticks that a single bucket (1000mb) of this fuel will burn for. */
    int getTotalBurningTime();

    /** @return The amount (in micro mj) of power that this fuel will give off in 1 tick. */
    long getPowerPerCycle();

    @Override
    default boolean matches(Container inv, Level world)
    {
        return false;
    }

    @Override
    default ItemStack assemble(Container inv)
    {
        return StackUtil.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    default ItemStack getResultItem()
    {
        return StackUtil.EMPTY;
    }

    @Override
    default boolean isSpecial()
    {
        return true;
    }

    // Calen
    @Override
    default RecipeType<IFuel> getType()
    {
        return TYPE;
    }

//    @Override
//    public NonNullList<Ingredient> getIngredients()
//    {
//        NonNullList<Ingredient> nonnulllist = NonNullList.create();
//        nonnulllist.add(Ingredient.of);
//        nonnulllist.add(this.middleInput);
//        nonnulllist.add(this.bottomOptional);
//        return nonnulllist;
}
