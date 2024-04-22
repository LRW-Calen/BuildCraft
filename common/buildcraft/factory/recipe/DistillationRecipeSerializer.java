package buildcraft.factory.recipe;

import buildcraft.lib.misc.JsonUtil;
import buildcraft.lib.recipe.RefineryRecipeRegistry;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class DistillationRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RefineryRecipeRegistry.DistillationRecipe> {
    public static final DistillationRecipeSerializer INSTANCE;

    static {
        INSTANCE = new DistillationRecipeSerializer();
        INSTANCE.setRegistryName(RefineryRecipeRegistry.DistillationRecipe.TYPE_ID);
    }

    @Override
    public RefineryRecipeRegistry.DistillationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
//        InscriberProcessType mode = getMode(json);
//
//        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
//
//        // Deserialize the three parts of the input
//        JsonObject ingredients = GsonHelper.getAsJsonObject(json, "ingredients");
//        Ingredient middle = Ingredient.fromJson(ingredients.get("middle"));
//        Ingredient top = Ingredient.EMPTY;
//        if (ingredients.has("top"))
//        {
//            top = Ingredient.fromJson(ingredients.get("top"));
//        }
//        Ingredient bottom = Ingredient.EMPTY;
//        if (ingredients.has("bottom"))
//        {
//            bottom = Ingredient.fromJson(ingredients.get("bottom"));
//        }
//
//        return new InscriberRecipe(recipeId, middle, result, top, bottom, mode);

        String type = GsonHelper.getAsString(json, "type");
//        if (!type.equals(NameSpaces.BUILDCRAFT_FACTORY + "distillation"))
//        {
//            throw new RuntimeException("Invalid HeatExchange Recipe Type!");
//        }
        long powerRequired = json.get("powerRequired").getAsLong();
        FluidStack in = JsonUtil.deSerializeFluidStack(json.getAsJsonObject("in"));
        FluidStack outGas = JsonUtil.deSerializeFluidStack(json.getAsJsonObject("outGas"));
        FluidStack outLiquid = JsonUtil.deSerializeFluidStack(json.getAsJsonObject("outLiquid"));
        return new RefineryRecipeRegistry.DistillationRecipe(recipeId, powerRequired, in, outGas, outLiquid);
    }

    public static void toJson(DistillationRecipeBuilder builder, JsonObject json) {
        json.addProperty("type", RefineryRecipeRegistry.DistillationRecipe.TYPE_ID.toString());
        json.addProperty("powerRequired", builder.powerRequired);
        json.add("in", JsonUtil.serializeFluidStack(builder.in));
        json.add("outGas", JsonUtil.serializeFluidStack(builder.outGas));
        json.add("outLiquid", JsonUtil.serializeFluidStack(builder.outLiquid));
    }

    @Nullable
    @Override
    public RefineryRecipeRegistry.DistillationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
//        Ingredient middle = Ingredient.fromNetwork(buffer);
//        ItemStack result = buffer.readItem();
//        Ingredient top = Ingredient.fromNetwork(buffer);
//        Ingredient bottom = Ingredient.fromNetwork(buffer);
//        InscriberProcessType mode = buffer.readEnum(InscriberProcessType.class);
//
//        return new InscriberRecipe(recipeId, middle, result, top, bottom, mode);


        long powerRequired = buffer.readLong();
        FluidStack in = buffer.readFluidStack();
        FluidStack outGas = buffer.readFluidStack();
        FluidStack outLiquid = buffer.readFluidStack();
        return new RefineryRecipeRegistry.DistillationRecipe(recipeId, powerRequired, in, outGas, outLiquid);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, RefineryRecipeRegistry.DistillationRecipe recipe) {
//        recipe.getMiddleInput().toNetwork(buffer);
//        buffer.writeItem(recipe.getResultItem());
//        recipe.getTopOptional().toNetwork(buffer);
//        recipe.getBottomOptional().toNetwork(buffer);
//        buffer.writeEnum(recipe.getProcessType());

        buffer.writeLong(recipe.powerRequired());
        buffer.writeFluidStack(recipe.in());
        buffer.writeFluidStack(recipe.outGas());
        buffer.writeFluidStack(recipe.outLiquid());
    }
}
