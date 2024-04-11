package buildcraft.api.recipes;

import buildcraft.api.BCModules;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IRefineryRecipeManager
{
    //    IHeatableRecipe createHeatingRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo);
    IHeatableRecipe createHeatingRecipe(ResourceLocation id, FluidStack in, FluidStack out, int heatFrom, int heatTo);

    //    default IHeatableRecipe addHeatableRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo)
    default IHeatableRecipe addUnregisteredHeatableRecipe(ResourceLocation id, FluidStack in, FluidStack out, int heatFrom, int heatTo)
    {
//        return getHeatableRegistry().addRecipe(createHeatingRecipe(in, out, heatFrom, heatTo));
        return getHeatableRegistry().addUnregisteredRecipe(createHeatingRecipe(id, in, out, heatFrom, heatTo));
    }

    //    ICoolableRecipe createCoolableRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo);
    ICoolableRecipe createCoolableRecipe(ResourceLocation id, FluidStack in, FluidStack out, int heatFrom, int heatTo);

    //    default ICoolableRecipe addCoolableRecipe(FluidStack in, FluidStack out, int heatFrom, int heatTo)
    default ICoolableRecipe addUnregisteredCoolableRecipe(ResourceLocation id, FluidStack in, FluidStack out, int heatFrom, int heatTo)
    {
//        return getCoolableRegistry().addRecipe(createCoolableRecipe(in, out, heatFrom, heatTo));
        return getCoolableRegistry().addUnregisteredRecipe(createCoolableRecipe(id, in, out, heatFrom, heatTo));
    }

    //    IDistillationRecipe createDistillationRecipe(FluidStack in, FluidStack outGas, FluidStack outLiquid, long powerRequired);
    IDistillationRecipe createDistillationRecipe(ResourceLocation id, FluidStack in, FluidStack outGas, FluidStack outLiquid, long powerRequired);

    //    default IDistillationRecipe addDistillationRecipe(FluidStack in, FluidStack outGas, FluidStack outLiquid, long powerRequired)
    default IDistillationRecipe addUnregisteredDistillationRecipe(ResourceLocation id, FluidStack in, FluidStack outGas, FluidStack outLiquid, long powerRequired)
    {
//        return getDistillationRegistry().addRecipe(createDistillationRecipe(in, outGas, outLiquid, powerRequired));
        return getDistillationRegistry().addUnregisteredRecipe(createDistillationRecipe(id, in, outGas, outLiquid, powerRequired));
    }

    IRefineryRegistry<IHeatableRecipe> getHeatableRegistry();

    IRefineryRegistry<ICoolableRecipe> getCoolableRegistry();

    IRefineryRegistry<IDistillationRecipe> getDistillationRegistry();

    interface IRefineryRegistry<R extends IRefineryRecipe>
    {
        /**
         * @return an unmodifiable collection containing all of the distillation recipes that satisfy the given
         * predicate. All of the recipe objects are guaranteed to never be null.
         */
//        Stream<R> getRecipes(Predicate<R> toReturn);
        Stream<R> getRecipes(Level world, Predicate<R> toReturn);

        /**
         * @return an unmodifiable set containing all of the distillation recipes.
         */
//        Collection<R> getAllRecipes();
        Collection<R> getAllRecipes(Level world);

        @Nullable
//        R getRecipeForInput(@Nullable FluidStack fluid);
        R getRecipeForInput(Level world, @Nullable FluidStack fluid);

        //        Collection<R> removeRecipes(Predicate<R> toRemove);
        Collection<R> removeUnregisteredRecipes(Predicate<R> toRemove);

        /**
         * Adds the given recipe to the registry. Note that this will remove any existing recipes for the passed
         * recipe's {@link IRefineryRecipe#in()}
         *
         * @param recipe The recipe to add.
         * @return The input recipe.
         */
//        R addRecipe(R recipe);
        R addUnregisteredRecipe(R recipe);

//        // Calen
//        RecipeType<R> getRecipeType;
    }

    //    interface IRefineryRecipe
    interface IRefineryRecipe extends Recipe<Container>
    {
        FluidStack in();
    }

    interface IHeatExchangerRecipe extends IRefineryRecipe
    {
        @Nullable
        FluidStack out();

        int heatFrom();

        int heatTo();
    }

    interface IHeatableRecipe extends IHeatExchangerRecipe
    {
        public static final ResourceLocation TYPE_ID = new ResourceLocation(BCModules.FACTORY.getModId(), "heat_exchange/heatable");

        public static final RecipeType<IHeatableRecipe> TYPE = RecipeType.register(TYPE_ID.toString());


        @Override
        default RecipeType<IHeatableRecipe> getType()
        {
            return TYPE;
        }
    }

    interface ICoolableRecipe extends IHeatExchangerRecipe
    {
        public static final ResourceLocation TYPE_ID = new ResourceLocation(BCModules.FACTORY.getModId(), "heat_exchange/coolable");

        public static final RecipeType<ICoolableRecipe> TYPE = RecipeType.register(TYPE_ID.toString());

        @Override
        default RecipeType<ICoolableRecipe> getType()
        {
            return TYPE;
        }
    }

    interface IDistillationRecipe extends IRefineryRecipe
    {
        public static final ResourceLocation TYPE_ID = new ResourceLocation(BCModules.FACTORY.getModId(), "distillation");

        public static final RecipeType<IDistillationRecipe> TYPE = RecipeType.register(TYPE_ID.toString());

        long powerRequired();

        FluidStack outGas();

        FluidStack outLiquid();

        @Override
        default RecipeType<IDistillationRecipe> getType()
        {
            return TYPE;
        }
    }
}
