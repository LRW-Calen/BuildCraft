package buildcraft.compat.module.jei.energy.combustionengine;

import buildcraft.api.BCModules;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buildcraft.api.fuels.IFuel;
import buildcraft.api.fuels.IFuelManager;
import buildcraft.api.mj.MjAPI;
import buildcraft.energy.BCEnergyBlocks;
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
import net.minecraftforge.fluids.FluidStack;

//public class CategoryCombustionEngine extends BlankRecipeCategory<WrapperCombustionEngine>
public class CategoryCombustionEngine implements IRecipeCategory<IFuel>
{
    // Calen
    public static final RecipeType<IFuel> RECIPE_TYPE =
            RecipeType.create(BCModules.ENERGY.getModId(), "engine_combustion", IFuel.class);
    //    public static final ResourceLocation UID = new ResourceLocation("buildcraft-compat:engine.combustion");
    public static final ResourceLocation UID = new ResourceLocation(BCModules.ENERGY.getModId(), "engine_combustion");
    public static final ResourceLocation FURNACE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
    private final IDrawable background;

    private final IDrawable icon;

    //    private final IDrawableAnimated flame;
//    private IDrawableAnimated flame;

    @OnlyIn(Dist.CLIENT)
    private Font font = Minecraft.getInstance().font;
    //    private final IGuiHelper guiHelper;
//    private final IDrawableStatic flameDrawable;
    private final Map<Integer, IDrawableAnimated> burnTimeDrabableMap = new HashMap<>();

    //    public CategoryCombustionEngine(IGuiHelper guiHelper)
//    public CategoryCombustionEngine(IGuiHelper guiHelper,IFuel fuel)
    public CategoryCombustionEngine(IGuiHelper guiHelper, Collection<IFuel> fuels)
    {
        // Calen: 80->100
//        this.background = guiHelper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/furnace.png"), 55, 38, 18, 32, 0, 0, 0, 80);
//        this.background = guiHelper.drawableBuilder(FURNACE, 55, 38, 18, 32).addPadding(0, 0, 0, 80).build();
        this.background = guiHelper.drawableBuilder(FURNACE, 55, 38, 18, 32).addPadding(0, 0, 0, 120).build();
        // Calen: from 1.12.2 what is this doing???
        guiHelper.createDrawable(new ResourceLocation(BCModules.ENERGY.getModId(), ""), 0, 0, 16, 16);

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCEnergyBlocks.engineIron.get()));

//        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(FURNACE, 176, 0, 14, 14);
//        this.flame = guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true);
        for (IFuel fuel : fuels)
        {
            if (!burnTimeDrabableMap.containsKey(fuel.getTotalBurningTime()))
            {
                burnTimeDrabableMap.put(fuel.getTotalBurningTime(), guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true));
            }
        }
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends IFuel> getRecipeClass()
    {
        return IFuel.class;
    }

    @Override
    public Component getTitle()
    {
//        return new TextComponent("Combustion Engine Fuels");
        return new TranslatableComponent("buildcraft.jei.title.combustion_engine_fuels");
    }

    public String getModName()
    {
        return BCModules.ENERGY.name();
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

    // Calen
    private int lastBurnTime = -1;

    @Override
    public void draw(IFuel fuel, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
//        if (fuel.getTotalBurningTime() != lastBurnTime)
//        {
//            this.flame = guiHelper.createAnimatedDrawable(flameDrawable, fuel.getTotalBurningTime() / 10, IDrawableAnimated.StartDirection.TOP, true);
//        }
//        this.flame.draw(stack, 2, 0);
        this.burnTimeDrabableMap.get(fuel.getTotalBurningTime()).draw(stack, 2, 0);
//        GlStateManager.func_179094_E();
        stack.pushPose();
        // Calen: 8->6
//        GlStateManager.func_179109_b(24.0F, 8.0F, 0.0F);
//        stack.translate(24.0F, 8.0F, 0.0F);
        stack.translate(24.0F, 6.0F, 0.0F);
//        this.font.draw(stack, "Burns for " + fuel.getTotalBurningTime() / 20 + "s", 0, 0, Color.darkGray.getRGB());
        this.font.draw(stack, new TranslatableComponent("buildcraft.jei.title.combustion_engine_fuels.burn_time", fuel.getTotalBurningTime() / 20).getString(), 0, 0, Color.darkGray.getRGB());
//        this.font.draw(stack, " at " + MjAPI.formatMj(fuel.getPowerPerCycle()) + " MJ/t", 0, font.lineHeight, Color.darkGray.getRGB());
        this.font.draw(stack, new TranslatableComponent("buildcraft.jei.title.combustion_engine_fuels.burn_speed", MjAPI.formatMj(fuel.getPowerPerCycle())).getString(), 0, font.lineHeight, Color.darkGray.getRGB());
//        GlStateManager.func_179109_b(0.0F, (float)(minecraft.field_71466_p.field_78288_b * 2), 0.0F);
        stack.translate(0.0F, (float) (font.lineHeight * 2), 0.0F);
//        GlStateManager.func_179139_a(0.7, 0.7, 1.0);
        stack.scale(0.7F, 0.7F, 1.0F);
//        this.font.draw(stack, " total " + MjAPI.formatMj(fuel.getPowerPerCycle() * (long) fuel.getTotalBurningTime()) + " MJ", 1, 2, Color.gray.getRGB());
        this.font.draw(stack, new TranslatableComponent("buildcraft.jei.title.combustion_engine_fuels.burn_total", MjAPI.formatMj(fuel.getPowerPerCycle() * (long) fuel.getTotalBurningTime())).getString(), 1, 2, Color.gray.getRGB());
//        GlStateManager.func_179121_F();
        stack.popPose();
    }

    @Override
    //    public void setRecipe(IRecipeLayout recipeLayout, WrapperCombustionEngine recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IFuel fuel, IFocusGroup focuses)
    {
//        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
//        guiFluidStacks.init(0, true, 1, 15, 16, 16, 1000, false, (IDrawable) null);
//        guiFluidStacks.set(0, (List) ingredients.getInputs(FluidStack.class).get(0));
        builder
                .addSlot(RecipeIngredientRole.INPUT, 1, 15)
                .setFluidRenderer(1000, false, 16, 16)
                .addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(fuel.getFluid(), 1000));
        if (fuel instanceof IFuelManager.IDirtyFuel dirtyFuel)
        {
            // Calen: move right 20 to not covered the text
//            guiFluidStacks.init(1, false, 95, 15, 16, 16, 1000, false, (IDrawable) null);
//            guiFluidStacks.set(1, (List) ingredients.getOutputs(FluidStack.class).get(0));
            builder
//                    .addSlot(RecipeIngredientRole.OUTPUT, 95, 15)
                    .addSlot(RecipeIngredientRole.OUTPUT, 115, 15)
                    .setFluidRenderer(1000, false, 16, 16)
                    .addIngredient(ForgeTypes.FLUID_STACK, dirtyFuel.getResidue());
        }
    }

    @Override
    public List<Component> getTooltipStrings(IFuel recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY)
    {
        return Lists.newArrayList();
    }

    @Override
    public boolean handleInput(IFuel recipe, double mouseX, double mouseY, InputConstants.Key input)
    {
        return false;
    }
}
