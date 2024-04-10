/**
 * Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p>
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution.
 */
package buildcraft.api.fuels;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

public interface IFuelManager
{
    //    <F extends IFuel> F addFuel(F fuel);
    <F extends IFuel> F addUnregisteredFuel(F fuel);

    //    IFuel addFuel(FluidStack fluid, long powerPerCycle, int totalBurningTime);
    IFuel addUnregisteredFuel(ResourceLocation id, FluidStack fluid, long powerPerCycle, int totalBurningTime);

    //    default IFuel addFuel(Fluid fluid, long powerPerCycle, int totalBurningTime)
    default IFuel addUnregisteredFuel(ResourceLocation id, Fluid fluid, long powerPerCycle, int totalBurningTime)
    {
//        return addFuel(new FluidStack(fluid, 1), powerPerCycle, totalBurningTime);
        return addUnregisteredFuel(id, new FluidStack(fluid, 1), powerPerCycle, totalBurningTime);
    }

    /**
     * @param residue The residue fluidstack, per bucket of the original fuel.
     */
//    IDirtyFuel addDirtyFuel(FluidStack fuel, long powerPerCycle, int totalBurningTime, FluidStack residue);
//    IDirtyFuel addDirtyFuel(ResourceLocation id, FluidStack fuel, long powerPerCycle, int totalBurningTime, FluidStack residue);
    IDirtyFuel addUnregisteredDirtyFuel(ResourceLocation id, FluidStack fuel, long powerPerCycle, int totalBurningTime, FluidStack residue);

    /**
     * @param residue The residue fluidstack, per bucket of the original fuel.
     */
//    default IDirtyFuel addDirtyFuel(Fluid fuel, long powerPerCycle, int totalBurningTime, FluidStack residue)
    default IDirtyFuel addUnregisteredDirtyFuel(ResourceLocation id, Fluid fuel, long powerPerCycle, int totalBurningTime, FluidStack residue)
    {
//        return addDirtyFuel(new FluidStack(fuel, 1), powerPerCycle, totalBurningTime, residue);
        return addUnregisteredDirtyFuel(id, new FluidStack(fuel, 1), powerPerCycle, totalBurningTime, residue);
    }

    //    Collection<IFuel> getFuels();
    Collection<IFuel> getFuels(Level world);

    //    IFuel getFuel(FluidStack fluid);
    IFuel getFuel(Level world, FluidStack fluid);

    interface IDirtyFuel extends IFuel
    {
        /**
         * @return The residue fluidstack, per bucket of original fuel.
         */
        FluidStack getResidue();
    }
}
