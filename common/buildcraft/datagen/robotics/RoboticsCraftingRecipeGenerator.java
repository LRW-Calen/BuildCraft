package buildcraft.datagen.robotics;

import buildcraft.factory.BCFactory;
import buildcraft.lib.oredictionarytag.OreDictionaryTags;
import buildcraft.robotics.BCRoboticsBlocks;
import buildcraft.transport.BCTransportItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;

import java.util.function.Consumer;

public class RoboticsCraftingRecipeGenerator extends RecipeProvider {
    private static final String MOD_ID = BCFactory.MODID;

    public RoboticsCraftingRecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        // zonePlanner
        ShapedRecipeBuilder.shaped(BCRoboticsBlocks.zonePlanner.get())
                .pattern("wdw")
                .pattern("wcw")
                .pattern("wpw")
                .define('d', (Item) BCTransportItems.pipeItemDiamond.get(null).get())
                .define('w', ItemTags.PLANKS)
                .define('p', Blocks.PISTON)
                .define('c', Blocks.CHEST)
                .unlockedBy("has_item", has(OreDictionaryTags.GEAR_STONE))
                .group(MOD_ID)
                .save(consumer);
    }

    @Override
    public String getName() {
        return "BuildCraft Robotics Crafting IRecipe Generator";
    }
}
