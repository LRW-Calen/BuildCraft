package buildcraft.compat.module.jei.factory;

import buildcraft.api.BCModules;

import java.awt.*;
import java.util.List;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IRefineryRecipeManager;
import buildcraft.factory.BCFactoryBlocks;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//public class CategoryDistiller extends BlankRecipeCategory<WrapperDistiller>
public class CategoryDistiller implements IRecipeCategory<IRefineryRecipeManager.IDistillationRecipe>
{
    // Calen
    public static final RecipeType<IRefineryRecipeManager.IDistillationRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.FACTORY.getModId(), "category_distiller", IRefineryRecipeManager.IDistillationRecipe.class);
    public static final ResourceLocation UID = new ResourceLocation(BCModules.FACTORY.getModId(), "category_distiller");
    public static final ResourceLocation distillerBackground = new ResourceLocation("buildcraftfactory:textures/gui/distiller.png");
    private final IDrawable background;
    private final IDrawable slot;
    private final IDrawable fakeBackground;

    private final IDrawable icon;

    private final IDrawableAnimated animated;

    @OnlyIn(Dist.CLIENT)
    private Font font = Minecraft.getInstance().font;

    public CategoryDistiller(IGuiHelper guiHelper)
    {
//        this.fakeBackground = guiHelper.createBlankDrawable(76, 65);
        this.fakeBackground = guiHelper.createBlankDrawable(90, 65);
        this.background = guiHelper.createDrawable(distillerBackground, 61, 12, 36, 57);
        this.slot = guiHelper.createDrawable(distillerBackground, 7, 34, 18, 18);

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCFactoryBlocks.distiller.get()));

        IDrawableStatic overComplete = guiHelper.createDrawable(CategoryDistiller.distillerBackground, 212, 0, 36, 57);
        this.animated = guiHelper.createAnimatedDrawable(overComplete, 40, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends IRefineryRecipeManager.IDistillationRecipe> getRecipeClass()
    {
        return IRefineryRecipeManager.IDistillationRecipe.class;
    }

    @Override
    public RecipeType<IRefineryRecipeManager.IDistillationRecipe> getRecipeType()
    {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
//        return new TextComponent("Distillable Fluids");
        return new TranslatableComponent("buildcraft.jei.title.distillable_fluids");
    }

    public String getModName()
    {
        return BCModules.FACTORY.name();
    }

    @Override
    public IDrawable getBackground()
    {
        return this.fakeBackground;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
//    public void drawExtras(Minecraft minecraft)
    public void draw(IRefineryRecipeManager.IDistillationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        this.background.draw(stack, 20, 4);
        this.slot.draw(stack, 0, 25);
        this.slot.draw(stack, 56, 0);
        this.slot.draw(stack, 56, 45);

        this.animated.draw(stack, 20, 4);
        this.font.draw(stack, MjAPI.formatMj(recipe.powerRequired()) + " MJ", 58, 28, Color.CYAN.getRGB());
    }

    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WrapperDistiller recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IRefineryRecipeManager.IDistillationRecipe recipe, IFocusGroup focuses)
    {
//        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 26, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(0, (List) ingredients.getInputs(FluidStack.class).get(0));
        builder
                .addSlot(RecipeIngredientRole.INPUT, 1, 26)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.in());
//        guiFluidStacks.init(1, false, 57, 1, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 57, 1)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.outGas());
//        guiFluidStacks.init(2, false, 57, 46, 16, 16, 10, false, (IDrawable) null);
//        guiFluidStacks.set(2, (List) ingredients.getOutputs(FluidStack.class).get(1));
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 57, 46)
                .setFluidRenderer(10, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.outLiquid());
    }

    @Override
    public List<Component> getTooltipStrings(IRefineryRecipeManager.IDistillationRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY)
    {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleInput(IRefineryRecipeManager.IDistillationRecipe recipe, double mouseX, double mouseY, InputConstants.Key input)
    {
        return false;
    }
}
