package buildcraft.compat.module.jei.factory;

import buildcraft.api.BCModules;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.misc.StackUtil;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

//public class CategoryHeatable extends BlankRecipeCategory
public class CategoryHeatable implements IRecipeCategory<IRefineryRecipeManager.IHeatableRecipe>
{
    // Calen
    public static final RecipeType<IRefineryRecipeManager.IHeatableRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.FACTORY.getModId(), "category_heatable", IRefineryRecipeManager.IHeatableRecipe.class);
    public static final ResourceLocation UID = new ResourceLocation(BCModules.FACTORY.getModId(), "category_heatable");
    public static final ResourceLocation energyHeaterBackground = new ResourceLocation("buildcraftfactory:textures/gui/energy_heater.png");
    private final IDrawable background;
    private final IDrawable slotIn;
    private final IDrawable slotOut;

    private final IDrawable icon;

    private final IDrawableAnimated animated;


    public CategoryHeatable(IGuiHelper guiHelper)
    {
//        this.background = helper.createDrawable(energyHeaterBackground, 176, 19, 54, 19, 0, 0, 18, 80);
        this.background = guiHelper.drawableBuilder(energyHeaterBackground, 176, 19, 54, 19).addPadding(0, 0, 18, 80).build();
        // Calen: top+1 to be at the same height as tubes of background
//        this.slotIn = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 0, 0);
//        this.slotIn = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(0, 0, 0, 0).build();
        this.slotIn = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(1, 0, 0, 0).build();
//        this.slotOut = helper.createDrawable(energyHeaterBackground, 7, 22, 18, 18, 0, 0, 72, 0);
//        this.slotOut = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(0, 0, 72, 0).build();
        this.slotOut = guiHelper.drawableBuilder(energyHeaterBackground, 7, 22, 18, 18).addPadding(1, 0, 72, 0).build();

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCFactoryBlocks.heatExchange.get()));

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryHeatable.energyHeaterBackground, 176, 152, 54, 19);
        this.animated = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends IRefineryRecipeManager.IHeatableRecipe> getRecipeClass()
    {
        return IRefineryRecipeManager.IHeatableRecipe.class;
    }

    @Override
    public Component getTitle()
    {
//        return new TextComponent("Heatable Fluids");
        return new TranslatableComponent("buildcraft.jei.title.heatable_fluids");
    }

    public String getModName()
    {
        return BCModules.FACTORY.name();
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
//    public void drawExtras(Minecraft minecraft)
    public void draw(IRefineryRecipeManager.IHeatableRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        this.slotIn.draw(stack);
        this.slotOut.draw(stack);

        this.animated.draw(stack, 18, 0);
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperHeatable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IRefineryRecipeManager.IHeatableRecipe recipe, IFocusGroup focuses)
    {
//        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 1, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(0, (List) ingredients.getInputs(FluidStack.class).get(0));
        builder
//                .addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addSlot(RecipeIngredientRole.INPUT, 1, 2)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.in());
//        guiFluidStacks.init(1, false, 73, 1, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
        builder
//                .addSlot(RecipeIngredientRole.OUTPUT, 73, 1)
                .addSlot(RecipeIngredientRole.OUTPUT, 73, 2)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.out() == null ? StackUtil.EMPTY_FLUID : recipe.out());
    }

    @Override
    public List<Component> getTooltipStrings(IRefineryRecipeManager.IHeatableRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY)
    {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleInput(IRefineryRecipeManager.IHeatableRecipe recipe, double mouseX, double mouseY, InputConstants.Key input)
    {
        return false;
    }
}
