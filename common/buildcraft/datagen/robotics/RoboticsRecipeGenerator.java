package buildcraft.datagen.robotics;

import buildcraft.factory.BCFactory;
import buildcraft.lib.oredicttag.OreDictTags;
import buildcraft.robotics.BCRoboticsBlocks;
import buildcraft.transport.BCTransportItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class RoboticsRecipeGenerator extends RecipeProvider {
    private static final String MOD_ID = BCFactory.MODID;

    public RoboticsRecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        // zonePlanner
        ShapedRecipeBuilder.shaped(BCRoboticsBlocks.zonePlanner.get())
                .pattern("wdw")
                .pattern("wcw")
                .pattern("wpw")
                .define('d', (Item) BCTransportItems.pipeItemDiamond.get(null).get())
                .define('w', ItemTags.PLANKS)
                .define('p', Blocks.PISTON)
                .define('c', Blocks.CHEST)
                .unlockedBy("has_item", has(OreDictTags.GEAR_STONE))
                .group(MOD_ID)
                .save(consumer);
    }
}
