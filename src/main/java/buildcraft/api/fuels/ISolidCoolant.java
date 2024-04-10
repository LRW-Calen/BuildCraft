/**
 * Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p>
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution.
 */
package buildcraft.api.fuels;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ISolidCoolant extends ICoolant
{
    FluidStack getFluidFromSolidCoolant(ItemStack stack);

    // Calen
    float getMultiplierForSerialize();

    ItemStack getSolid();
}
