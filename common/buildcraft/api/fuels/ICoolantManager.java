/**
 * Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p>
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution.
 */
package buildcraft.api.fuels;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

public interface ICoolantManager
{
//    IFluidCoolant addCoolant(IFluidCoolant coolant);
    IFluidCoolant addUnregisteredFluidCoolant(IFluidCoolant coolant);

    //    IFluidCoolant addCoolant(FluidStack fluid, float degreesCoolingPerMb);
    IFluidCoolant addCoolant(ResourceLocation id, FluidStack fluid, float degreesCoolingPerMb);

    //    default IFluidCoolant addCoolant(Fluid fluid, float degreesCoolingPerMb)
    default IFluidCoolant addCoolant(ResourceLocation id, Fluid fluid, float degreesCoolingPerMb)
    {
//        return addCoolant(new FluidStack(fluid, 1), degreesCoolingPerMb);
        return addCoolant(id, new FluidStack(fluid, 1), degreesCoolingPerMb);
    }

//    ISolidCoolant addSolidCoolant(ISolidCoolant solidCoolant);
    ISolidCoolant addUnregisteredSolidCoolant(ISolidCoolant solidCoolant);

    //    ISolidCoolant addSolidCoolant(ItemStack solid, FluidStack fluid, float multiplier);
    ISolidCoolant addSolidCoolant(ResourceLocation id, ItemStack solid, FluidStack fluid, float multiplier);

//    Collection<IFluidCoolant> getCoolants();
    Collection<IFluidCoolant> getCoolants(Level world);

//    Collection<ISolidCoolant> getSolidCoolants();
    Collection<ISolidCoolant> getSolidCoolants(Level world);

//    IFluidCoolant getCoolant(FluidStack fluid);
    IFluidCoolant getCoolant(Level world, FluidStack fluid);

//    float getDegreesPerMb(FluidStack fluid, float heat);
    float getDegreesPerMb(Level world, FluidStack fluid, float heat);

//    ISolidCoolant getSolidCoolant(ItemStack solid);
    ISolidCoolant getSolidCoolant(Level world, ItemStack solid);
}
