package buildcraft.datagen.transport;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.datagen.silicon.AssemblyRecipeBuilder;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.transport.BCTransport;
import buildcraft.transport.BCTransportItems;
import com.google.common.collect.ImmutableSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class TransportAssemblyRecipeGenerator extends RecipeProvider
{
    private final ExistingFileHelper existingFileHelper;

    private Consumer<FinishedRecipe> consumer;

    public TransportAssemblyRecipeGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        if (BCTransportItems.wire != null)
        {
            for (DyeColor color : ColourUtil.COLOURS)
            {
//                String name = String.format("wire-%s", color.getUnlocalizedName());
                String name = String.format("wire_%s", color.getSerializedName());
                ImmutableSet<IngredientStack> input = ImmutableSet.of(IngredientStack.of(Tags.Items.DUSTS_REDSTONE),
//                        IngredientStack.of(ColourUtil.getDyeName(color)));
                        IngredientStack.of(color.getTag()));
//                AssemblyRecipeRegistry.register(new AssemblyRecipeBasic(name, 10_000 * MjAPI.MJ, input,
//                        new ItemStack(BCTransportItems.wire, 8, color.getMetadata())));
                ItemStack wireStack = new ItemStack(BCTransportItems.wire.get(), 8);
                ColourUtil.addColorTagToStack(wireStack, color);
//                AssemblyRecipeRegistry.register(new AssemblyRecipeBasic(name, 10_000 * MjAPI.MJ, input, wireStack));
                AssemblyRecipeBuilder.basic(10_000 * MjAPI.MJ, input, wireStack).save(consumer, BCTransport.MOD_ID, name);
            }
        }
    }
}
