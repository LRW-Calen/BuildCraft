package buildcraft.api.recipes;

import buildcraft.silicon.BCSilicon;
import buildcraft.silicon.recipe.AssemblyRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * @deprecated TEMPORARY CLASS DO NOT USE!
 */
@Deprecated
//public abstract class AssemblyRecipe implements Comparable<AssemblyRecipe>, IForgeRegistryEntry<AssemblyRecipe>
public abstract class AssemblyRecipe implements Comparable<AssemblyRecipe>, IForgeRegistryEntry<AssemblyRecipe>, Recipe<Container>
{
    public static final ResourceLocation TYPE_ID = new ResourceLocation(BCSilicon.MOD_ID, "assembly");

    public static final RecipeType<AssemblyRecipe> TYPE = RecipeType.register(TYPE_ID.toString());

    private ResourceLocation name;

    /**
     * The outputs this recipe can generate with any of the given inputs
     *
     * @param inputs Current ingredients in the assembly table
     * @return A Set containing all possible outputs given the given inputs or an empty one if nothing can be assembled from the given inputs
     */
    public abstract Set<ItemStack> getOutputs(NonNullList<ItemStack> inputs);

    /**
     * Used to determine all outputs from this recipe for recipe previews (guide book and/or JEI)
     */
    public abstract Set<ItemStack> getOutputPreviews();

    /**
     * Used to determine what items to use up for the given output
     *
     * @param output The output we want to know the inputs for, only ever called using stacks obtained from getOutputs or getOutputPreviews
     */
    public abstract Set<IngredientStack> getInputsFor(@Nonnull ItemStack output);

    /**
     * Used to determine how much MJ is required to asemble the given output item
     *
     * @param output The output we want to know the MJ cost for, only ever called using stacks obtained from getOutputs or getOutputPreviews
     */
    public abstract long getRequiredMicroJoulesFor(@Nonnull ItemStack output);

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AssemblyRecipe that = (AssemblyRecipe) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public int compareTo(AssemblyRecipe o)
    {
        return name.toString().compareTo(o.name.toString());
    }

    @Override
    public AssemblyRecipe setRegistryName(ResourceLocation name)
    {
        this.name = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return name;
    }

    @Override
    public Class<AssemblyRecipe> getRegistryType()
    {
        return AssemblyRecipe.class;
    }
    // Calen: for serialize
    public abstract long getRequiredMicroJoulesForSerialize();
    public abstract Set<IngredientStack> getRequiredIngredientStacksForSerialize();
    public abstract Set<ItemStack> getOutputForSerialize();

    // Calen: forced by Recipe

    @Override
    public ResourceLocation getId()
    {
        return name;
    }

    @Override
    public boolean matches(Container inv, Level world)
    {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public RecipeSerializer<AssemblyRecipe> getSerializer()
    {
        return AssemblyRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<AssemblyRecipe> getType()
    {
        return TYPE;
    }
}
