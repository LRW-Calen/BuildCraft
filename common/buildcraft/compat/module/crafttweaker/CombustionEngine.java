package buildcraft.compat.module.crafttweaker;

import buildcraft.api.fuels.IFluidCoolant;
import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.lib.fluid.FuelRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

//@ZenClass("mods.buildcraft.CombustionEngine")
//@ModOnly("buildcraftenergy")
@ZenRegister
@ZenCodeType.Name("mods.buildcraft.CombustionEngine")
@IRecipeHandler.For(IFuel.class)
//public class CombustionEngine
public enum CombustionEngine implements IRecipeManager<IFuel>, IRecipeHandler<IFuel>
{
    @ZenCodeGlobals.Global("combustionEngine")
    INSTANCE;

    private static final double MAX_POWER = 100000.0;

    //    @ZenMethod
    @ZenCodeType.Method
    public void addCleanFuel(String name, IFluidStack liquid, double powerPerTick, int timePerBucket)
    {
        addCleanFuel0("custom/" + name, liquid, powerPerTick, timePerBucket);
    }

    //    @ZenMethod
//    @ZenCodeType.Method
//    public static void addCleanFuel(ILiquidStack liquid, double powerPerTick, int timePerBucket)
    private void addCleanFuel0(String name, IFluidStack liquid, double powerPerTick, int timePerBucket)
    {
//        FluidStack fluid = CraftTweakerMC.getLiquidStack(liquid);
        FluidStack fluid = liquid.getImmutableInternal();
//        if (fluid == null)
        if (fluid.getFluid() == null || fluid.getFluid() instanceof EmptyFluid)
        {
            throw new IllegalArgumentException("Fluid was null or empty!");
        }
//        else if (BuildcraftFuelRegistry.fuel.getFuel(fluid) != null)
        else if (getAllRecipes().stream().anyMatch(r -> r.getFluid().equals(fluid)))
        {
            throw new IllegalArgumentException("The fluid " + fluid + " is already registered as a fuel!");
        }
//        else if (BuildcraftFuelRegistry.coolant.getCoolant(fluid) != null)
        else if (Coolant.INSTANCE.getAllRecipes().stream().anyMatch(r -> r instanceof IFluidCoolant fluidCoolant && fluidCoolant.getFluid().equals(fluid)))
        {
            throw new IllegalArgumentException("The fluid " + fluid + " is already registered as a coolant - so it won't work very well if you add it as a fuel too!");
        }
        else if (powerPerTick <= 0.0)
        {
            throw new IllegalArgumentException("Power was less than or equal to 0!");
        }
        else if (powerPerTick > 100000.0)
        {
            throw new IllegalArgumentException("Maximum power is 100000.0, as any values above this would instantly bring the engine to overheat.");
        }
        else
        {
            long mj = (long) ((double) MjAPI.MJ * powerPerTick);
//            CraftTweakerAPI.apply(new AddCleanFuel(fluid, mj, timePerBucket));
            CraftTweakerAPI.apply(AddCleanFuel.create(this, name, fluid, mj, timePerBucket));
        }
    }

    //    @ZenMethod
    @ZenCodeType.Method
    public void addDirtyFuel(String name, IFluidStack lFuel, double powerPerTick, int timePerBucket, IFluidStack lResidue)
    {
        addDirtyFuel0("custom/" + name, lFuel, powerPerTick, timePerBucket, lResidue);
    }

    //    @ZenMethod
//    @ZenCodeType.Method
//    public static void addDirtyFuel(ILiquidStack lFuel, double powerPerTick, int timePerBucket, ILiquidStack lResidue)
    private void addDirtyFuel0(String name, IFluidStack lFuel, double powerPerTick, int timePerBucket, IFluidStack lResidue)
    {
//        FluidStack fuel = CraftTweakerMC.getLiquidStack(lFuel);
        FluidStack fuel = lFuel.getImmutableInternal();
//        FluidStack residue = CraftTweakerMC.getLiquidStack(lResidue);
        FluidStack residue = lResidue.getImmutableInternal();
//        if (fuel.getFluid() == null)
        if (fuel.getFluid() == null || fuel.getFluid() instanceof EmptyFluid)
        {
//            throw new IllegalArgumentException("Fuel fluid was null!");
            throw new IllegalArgumentException("Fuel fluid was null or empty!");
        }
//        else if (residue.getFluid() == null)
        else if (residue.getFluid() == null || residue.getFluid() instanceof EmptyFluid)
        {
//            throw new IllegalArgumentException("Residue fluid was null!");
            throw new IllegalArgumentException("Residue fluid was null or empty!");
        }
//        else if (BuildcraftFuelRegistry.fuel.getFuel(fuel) != null)
        else if (getAllRecipes().stream().anyMatch(r -> r.getFluid().equals(fuel)))
        {
            throw new IllegalArgumentException("The fluid " + fuel + " is already registered as a fuel!");
        }
//        else if (BuildcraftFuelRegistry.coolant.getCoolant(fuel) != null)
        else if (Coolant.INSTANCE.getAllRecipes().stream().anyMatch(r -> r instanceof IFluidCoolant fluidCoolant && fluidCoolant.getFluid().equals(fuel)))
        {
            throw new IllegalArgumentException("The fluid " + fuel + " is already registered as a coolant - so it won't work very well if you add it as a fuel too!");
        }
        else if (powerPerTick <= 0.0)
        {
            throw new IllegalArgumentException("Power was less than or equal to 0!");
        }
        else if (powerPerTick > 100000.0)
        {
            throw new IllegalArgumentException("Maximum power is 100000.0, as any values above this would instantly bring the engine to overheat.");
        }
        else
        {
            long mj = (long) ((double) MjAPI.MJ * powerPerTick);
//            CraftTweakerAPI.apply(new AddDirtyFuel(fuel, mj, timePerBucket, residue));
            CraftTweakerAPI.apply(AddDirtyFuel.create(this, name, fuel, mj, timePerBucket, residue));
        }
    }

    @Override
    public RecipeType<IFuel> getRecipeType()
    {
        return IFuel.TYPE;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, IFuel recipe)
    {
        if (recipe instanceof IFuelManager.IDirtyFuel dirtyFuel)
        {
            return String.format(
                    "combustionEngine.addDirtyFuel(%s, %s, %s, %s, %s);",
                    StringUtil.quoteAndEscape(recipe.getId()),
                    StringUtil.quoteAndEscape(recipe.getFluid().getFluid().getRegistryName()),
                    recipe.getPowerPerCycle(),
                    recipe.getTotalBurningTime(),
                    StringUtil.quoteAndEscape(dirtyFuel.getResidue().getFluid().getRegistryName())
            );
        }
        else
        {
            return String.format(
                    "combustionEngine.addCleanFuel(%s, %s, %s, %s);",
                    StringUtil.quoteAndEscape(recipe.getId()),
                    StringUtil.quoteAndEscape(recipe.getFluid().getFluid().getRegistryName()),
                    recipe.getPowerPerCycle(),
                    recipe.getTotalBurningTime()
            );
        }
    }

    //    static final class AddDirtyFuel implements IAction
    static final class AddDirtyFuel extends ActionAddRecipe<IFuel>
    {
//        private final FluidStack fuel;
//        private final FluidStack residue;
//        private final long powerPerTick;
//        private final int totalBurningTime;

        //        public AddDirtyFuel(FluidStack fuel, long powerPerCycle, int totalBurningTime, FluidStack residue)
        private AddDirtyFuel(IRecipeManager<IFuel> manager, IFuel recipe)
        {
            super(manager, recipe);
//            this.fuel = fuel;
//            this.powerPerTick = powerPerCycle;
//            this.totalBurningTime = totalBurningTime;
//            this.residue = residue;
        }

        // Calen
        public static AddDirtyFuel create(IRecipeManager<IFuel> manager, String name, FluidStack fuel, long powerPerCycle, int totalBurningTime, FluidStack residue)
        {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddDirtyFuel(manager, new FuelRegistry.DirtyFuel(_name, fuel, powerPerCycle, totalBurningTime, residue));
        }

//        public void apply()
//        {
//            BuildcraftFuelRegistry.fuel.addDirtyFuel(this.fuel, this.powerPerTick, this.totalBurningTime, this.residue);
//        }

        public String describe()
        {
//            return "Adding combustion engine fuel " + this.fuel;
            return "Adding combustion engine fuel " + this.recipe.getFluid();
        }
    }

    //    static final class AddCleanFuel implements IAction
    static final class AddCleanFuel extends ActionAddRecipe<IFuel>
    {
//        private final FluidStack fluid;
//        private final long powerPerTick;
//        private final int totalBurningTime;

        //        public AddCleanFuel(FluidStack fluid, long powerPerCycle, int totalBurningTime)
        public AddCleanFuel(IRecipeManager<IFuel> manager, IFuel recipe)
        {
            super(manager, recipe);
//            this.fluid = fluid;
//            this.powerPerTick = powerPerCycle;
//            this.totalBurningTime = totalBurningTime;
        }

        // Calen
        public static AddCleanFuel create(IRecipeManager<IFuel> manager, String name, FluidStack fluid, long powerPerCycle, int totalBurningTime)
        {
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            return new AddCleanFuel(manager, new FuelRegistry.Fuel(_name, fluid, powerPerCycle, totalBurningTime));
        }

//        public void apply()
//        {
//            BuildcraftFuelRegistry.fuel.addFuel(this.fluid, this.powerPerTick, this.totalBurningTime);
//        }

        public String describe()
        {
//            return "Adding combustion engine fuel " + this.fluid;
            return "Adding combustion engine fuel " + this.recipe.getFluid();
        }
    }
}
