package buildcraft.compat.module.crafttweaker;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.AssemblyRecipeBasic;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

//@ZenClass("mods.buildcraft.AssemblyTable")
//@ModOnly("buildcraftsilicon")
@ZenRegister
@ZenCodeType.Name("mods.buildcraft.AssemblyTable")
@IRecipeHandler.For(AssemblyRecipe.class)
//public class AssemblyTable
public enum AssemblyTable implements IRecipeManager<AssemblyRecipe>, IRecipeHandler<AssemblyRecipe>
{
    @ZenCodeGlobals.Global("assemblyTable")
    INSTANCE;

    private static int ids;

    //    @ZenMethod
    @ZenCodeType.Method
//    public static void addRecipe(IItemStack output, int power, IIngredient[] ingredients)
    public void addRecipe(IItemStack output, int power, IIngredient[] ingredients)
    {
        addRecipe0("auto_" + ids++, output, power, ingredients);
    }

    //    @ZenMethod
    @ZenCodeType.Method
//    public static void addRecipe(String name, IItemStack output, int power, IIngredient[] ingredients)
    public void addRecipe(String name, IItemStack output, int power, IIngredient[] ingredients)
    {
        addRecipe0("custom/" + name, output, power, ingredients);
    }

    //    private static void addRecipe0(String name, IItemStack output, int power, IIngredient[] ingredients)
    private void addRecipe0(String name, IItemStack output, int power, IIngredient[] ingredients)
    {
//        CraftTweakerAPI.apply(new AddRecipeAction(name, output, power, ingredients));
        CraftTweakerAPI.apply(AddRecipeAction.create(this, name, output, power, ingredients));
    }

    //    @ZenMethod
    @ZenCodeType.Method
//    public static void removeByName(String name)
    public void removeByName(String name)
    {
//        CraftTweakerAPI.apply(new RemoveRecipeByNameAction(new ResourceLocation(name)));
        CraftTweakerAPI.apply(new RemoveRecipeByNameAction(this, new ResourceLocation(name)));
    }

    @Override
    public RecipeType<AssemblyRecipe> getRecipeType()
    {
        return AssemblyRecipe.TYPE;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, AssemblyRecipe recipe)
    {
        return String.format(
                "assemblyTable.addRecipe(%s, %s, %s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                ItemStackUtil.getCommandString(recipe.getOutputPreviews().stream().toList().get(0)),
                new IIngredientList(recipe.getRequiredIngredientStacksForSerialize().stream().map(i -> IIngredient.fromIngredient(i.ingredient)).toArray(IIngredient[]::new)).getCommandString()
        );
    }

    //    private static class RemoveRecipeByNameAction implements IAction
    private static class RemoveRecipeByNameAction extends ActionRemoveRecipeByName<AssemblyRecipe>
    {
        private final ResourceLocation name;

        //        RemoveRecipeByNameAction(ResourceLocation name)
        RemoveRecipeByNameAction(IRecipeManager<AssemblyRecipe> manager, ResourceLocation name)
        {
            super(manager);
            this.name = name;
        }

        public void apply()
        {
//            AssemblyRecipeRegistry.REGISTRY.remove(this.name);
            getManager().removeByName(this.name.toString());
        }

        public String describe()
        {
            return "Removing assembly table recipe " + this.name;
        }
    }

    //    private static class AddRecipeAction implements IAction
    private static class AddRecipeAction extends ActionAddRecipe<AssemblyRecipe>
    {
//        private final ItemStack output;
//        private final ResourceLocation name;
//        private final long requiredMj;
//        private final ImmutableSet<IngredientStack> requiredStacks;

        //        public AddRecipeAction(IRecipeManager<AssemblyRecipe> manager, String name, IItemStack output, int power, IIngredient[] ingredients)
        private AddRecipeAction(IRecipeManager<AssemblyRecipe> manager, AssemblyRecipe recipe)
        {
            super(manager, recipe);
//            this.output = CraftTweakerMC.getItemStack(output);
//            ImmutableSet.Builder<IngredientStack> stacks = ImmutableSet.builder();
//
//            for (int i = 0; i < ingredients.length; ++i)
//            {
//                IIngredient ctIng = ingredients[i];
//                Ingredient ingredient = CraftTweakerMC.getIngredient(ctIng);
//                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.getAmount())));
//            }
//
//            this.requiredStacks = stacks.build();
//            this.requiredMj = (long) power * MjAPI.MJ;
//            this.name = new ResourceLocation("crafttweaker", name);
        }

        public static AddRecipeAction create(IRecipeManager<AssemblyRecipe> manager, String name, IItemStack output, int power, IIngredient[] ingredients)
        {
//            ItemStack output = CraftTweakerMC.getItemStack(output);
            ItemStack _output = output.getImmutableInternal();
            ImmutableSet.Builder<IngredientStack> stacks = ImmutableSet.builder();

            for (int i = 0; i < ingredients.length; ++i)
            {
                IIngredient ctIng = ingredients[i];
//                Ingredient ingredient = CraftTweakerMC.getIngredient(ctIng);
                Ingredient ingredient = ctIng.asVanillaIngredient();
//                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.getAmount())));
                stacks.add(new IngredientStack(ingredient, Math.max(1, ctIng.asIIngredientWithAmount().getAmount())));
            }

            ImmutableSet<IngredientStack> requiredStacks = stacks.build();
            long requiredMj = (long) power * MjAPI.MJ;
            ResourceLocation _name = new ResourceLocation("crafttweaker", name);
            AssemblyRecipeBasic recipe = new AssemblyRecipeBasic(_name, requiredMj, requiredStacks, _output);
            return new AddRecipeAction(manager, recipe);
        }

//        public void apply()
//        {
//            AssemblyRecipeRegistry.REGISTRY.put(this.name, new AssemblyRecipeBasic(this.name, this.requiredMj, this.requiredStacks, this.output));
//        }

        public String describe()
        {
//            return "Adding assembly table recipe for " + this.output;
            return "Adding assembly table recipe for " + this.recipe.getOutputPreviews().stream().toList().get(0);
        }
    }
}
