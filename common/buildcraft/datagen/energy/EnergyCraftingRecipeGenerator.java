package buildcraft.datagen.energy;

import buildcraft.energy.BCEnergy;
import buildcraft.energy.BCEnergyBlocks;
import buildcraft.energy.BCEnergyFluids;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.lib.oredicttag.OreDictTags;
import buildcraft.transport.BCTransportItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class EnergyCraftingRecipeGenerator extends RecipeProvider {
    private static final String MOD_ID = BCEnergy.MODID;

    public EnergyCraftingRecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        // engineIron
        ShapedRecipeBuilder.shaped(BCEnergyBlocks.engineIron.get())
                .pattern("www")
                .pattern(" g ")
                .pattern("GpG")
                .define('w', Ingredient.of(Tags.Items.INGOTS_IRON))
                .define('g', Ingredient.of(Tags.Items.GLASS_COLORLESS))
                .define('G', Ingredient.of(OreDictTags.GEAR_IRON))
                .define('p', Ingredient.of(Items.PISTON))
                .unlockedBy("has_item", has(OreDictTags.GEAR_IRON))
                .group(MOD_ID)
                .save(consumer, MOD_ID + ":engine_iron");
        // engineStone
        ShapedRecipeBuilder.shaped(BCEnergyBlocks.engineStone.get())
                .pattern("www")
                .pattern(" g ")
                .pattern("GpG")
                .define('w', Ingredient.of(Tags.Items.COBBLESTONE))
                .define('g', Ingredient.of(Tags.Items.GLASS_COLORLESS))
                .define('G', Ingredient.of(OreDictTags.GEAR_STONE))
                .define('p', Ingredient.of(Items.PISTON))
                .unlockedBy("has_item", has(OreDictTags.GEAR_STONE))
                .group(MOD_ID)
                .save(consumer, MOD_ID + ":engine_stone");
        // waterproof
        ShapelessRecipeBuilder.shapeless(BCTransportItems.waterproof.get(), 8)
                .requires(BCEnergyFluids.oilResidue[0].get().getBucket())
                .unlockedBy("has_item", has(BCFactoryBlocks.distiller.get()))
                .group(MOD_ID)
                .save(consumer, MOD_ID + ":residue_to_pipe_sealant");

    }

    @Override
    public String getName() {
        return "BuildCraft Energy Crafting Recipe Generator";
    }
}
