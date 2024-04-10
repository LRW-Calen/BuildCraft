package buildcraft.compat.module.jei.silicon;

import buildcraft.api.BCModules;

import java.awt.*;
import java.util.List;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.silicon.BCSiliconBlocks;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//public class CategoryIntegrationTable implements IRecipeCategory<WrapperIntegrationTable>
public class CategoryIntegrationTable implements IRecipeCategory<IntegrationRecipe>
{
    // Calen
    public static final RecipeType<IntegrationRecipe> RECIPE_TYPE =
            RecipeType.create(BCModules.SILICON.getModId(), "integration", IntegrationRecipe.class);
    //    public static final ResourceLocation UID = new ResourceLocation("buildcraft-compat:silicon.integration");
    public static final ResourceLocation UID = new ResourceLocation(BCModules.SILICON.getModId(), "integration");
    protected final ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
    private final IDrawable background;

    private final IDrawable icon;

    private final IntegrationRecipe recipe;
    private final IDrawableAnimated progressBar;
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;

    @OnlyIn(Dist.CLIENT)
    private Font font = Minecraft.getInstance().font;

    public CategoryIntegrationTable(IGuiHelper guiHelper, IntegrationRecipe recipe)
    {
//        this.background = guiHelper.createDrawable(this.backgroundLocation, 17, 22, 153, 71, 0, 0, 9, 0);
        this.background = guiHelper.drawableBuilder(this.backgroundLocation, 17, 22, 153, 71).addPadding(0, 0, 9, 0).build();

        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BCSiliconBlocks.integrationTable.get()));

        this.recipe = recipe;
        // Calen: not impl in 1.12.2
        List<ItemStack> inputs = Lists.newArrayList();
        this.inputs = ImmutableList.copyOf(inputs);
        this.outputs = ImmutableList.of(new ItemStack(Blocks.COBBLESTONE));
        ResourceLocation backgroundLocation = new ResourceLocation("buildcraftsilicon", "textures/gui/integration_table.png");
//        IDrawableStatic progressDrawable = guiHelper.createDrawable(backgroundLocation, 176, 17, 4, 69, 0, 0, 0, 0);
        IDrawableStatic progressDrawable = guiHelper.drawableBuilder(this.backgroundLocation, 176, 17, 4, 69).addPadding(0, 0, 0, 0).build();
        this.progressBar = guiHelper.createAnimatedDrawable(progressDrawable, 720, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends IntegrationRecipe> getRecipeClass()
    {
        return IntegrationRecipe.class;
    }

    public Component getTitle()
    {
//        return new TextComponent("Integration Table");
        return new TranslatableComponent("tile.integrationTableBlock.name");
    }

    public String getModName()
    {
        return BCModules.SILICON.name();
    }

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
    public void draw(IntegrationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
    {
        this.progressBar.draw(stack, 156, 1);
        this.font.draw(stack, MjAPI.formatMj(0L) + " MJ", 80, 52, Color.gray.getRGB());

    }

    //    public void setRecipe(IRecipeLayout recipeLayout, WrapperIntegrationTable recipeWrapper, IIngredients ingredients)
    public void setRecipe(IRecipeLayoutBuilder builder, IntegrationRecipe recipe, IFocusGroup focuses)
    {
//        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
//        int inventoryIndex = 0;

        for (int y = 0; y < 3; ++y)
        {
            for (int x = 0; x < 3; ++x)
            {
                int slotIndex = x == 1 && y == 1 ? 0 : x + y * 3 + 1;
                if (inputs.size() > slotIndex)
                {
//                    guiItemStacks.init(inventoryIndex, true, 19 + x * 25, 24 + y * 25);
//                    guiItemStacks.set(inventoryIndex, (List) inputs.get(slotIndex));
                    builder
                            .addSlot(RecipeIngredientRole.INPUT, 19 + x * 25, 24 + y * 25)
                            .addIngredient(VanillaTypes.ITEM_STACK, this.inputs.get(slotIndex));
//                    ++inventoryIndex;
                }
            }
        }

//        guiItemStacks.init(inventoryIndex, false, 129, 26);
//        guiItemStacks.set(inventoryIndex, (List) ingredients.getOutputs(ItemStack.class).get(0));
        builder
                .addSlot(RecipeIngredientRole.INPUT, 129, 26)
                .addIngredients(Ingredient.of(this.outputs.stream()));
//        ++inventoryIndex;
    }
}
