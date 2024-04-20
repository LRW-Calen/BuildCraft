package buildcraft.factory.recipe;

import buildcraft.datagen.factory.HeatExchangeRecipeBuilder;
import buildcraft.factory.BCFactory;
import buildcraft.lib.misc.JsonUtil;
import buildcraft.lib.recipe.RefineryRecipeRegistry.CoolableRecipe;
import buildcraft.lib.recipe.RefineryRecipeRegistry.HeatExchangeRecipe;
import buildcraft.lib.recipe.RefineryRecipeRegistry.HeatableRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class HeatExchangeRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<HeatExchangeRecipe> {
    public static final HeatExchangeRecipeSerializer HEATABLE;
    public static final HeatExchangeRecipeSerializer COOLABLE;

    static {
        HEATABLE = new HeatExchangeRecipeSerializer(EnumHeatExchangeRecipeType.HEATABLE);
        COOLABLE = new HeatExchangeRecipeSerializer(EnumHeatExchangeRecipeType.COOLABLE);
        HEATABLE.setRegistryName(HeatableRecipe.TYPE_ID);
        COOLABLE.setRegistryName(CoolableRecipe.TYPE_ID);
    }

    private final EnumHeatExchangeRecipeType type;

    private HeatExchangeRecipeSerializer(EnumHeatExchangeRecipeType type) {
        this.type = type;
    }

    @Override
    public HeatExchangeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
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

        String typeStr = GsonHelper.getAsString(json, "type").replace("heat_exchange/", "");
//        EnumHeatExchangeRecipeType type = EnumHeatExchangeRecipeType.byName(new ResourceLocation(typeStr).getPath());
//        if (type != this.type)
//        {
//            throw new RuntimeException("Invalid HeatExchange Recipe Type!");
//        }
        FluidStack in = JsonUtil.deSerializeFluidStack(json.getAsJsonObject("in"));
        FluidStack out = JsonUtil.deSerializeFluidStack(json.getAsJsonObject("out"));
        int heatFrom = json.get("heatFrom").getAsInt();
        int heatTo = json.get("heatTo").getAsInt();
        return switch (this.type) {
            case COOLABLE -> new CoolableRecipe(recipeId, in, out, heatFrom, heatTo);
            case HEATABLE -> new HeatableRecipe(recipeId, in, out, heatFrom, heatTo);
        };
    }

    public static void toJson(HeatExchangeRecipeBuilder builder, JsonObject json) {
        json.addProperty("type", BCFactory.MODID + ":heat_exchange/" + builder.type.getlowerName());
        json.add("in", JsonUtil.serializeFluidStack(builder.in));
        json.add("out", JsonUtil.serializeFluidStack(builder.out));
        json.addProperty("heatFrom", builder.heatFrom);
        json.addProperty("heatTo", builder.heatTo);
    }

    @Nullable
    @Override
    public HeatExchangeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
//        Ingredient middle = Ingredient.fromNetwork(buffer);
//        ItemStack result = buffer.readItem();
//        Ingredient top = Ingredient.fromNetwork(buffer);
//        Ingredient bottom = Ingredient.fromNetwork(buffer);
//        InscriberProcessType mode = buffer.readEnum(InscriberProcessType.class);
//
//        return new InscriberRecipe(recipeId, middle, result, top, bottom, mode);


        FluidStack in = buffer.readFluidStack();
        FluidStack out = buffer.readFluidStack();
        int heatFrom = buffer.readInt();
        int heatTo = buffer.readInt();
        return switch (this.type) {
            case COOLABLE -> new CoolableRecipe(recipeId, in, out, heatFrom, heatTo);
            case HEATABLE -> new HeatableRecipe(recipeId, in, out, heatFrom, heatTo);
        };
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, HeatExchangeRecipe recipe) {
//        recipe.getMiddleInput().toNetwork(buffer);
//        buffer.writeItem(recipe.getResultItem());
//        recipe.getTopOptional().toNetwork(buffer);
//        recipe.getBottomOptional().toNetwork(buffer);
//        buffer.writeEnum(recipe.getProcessType());

        buffer.writeFluidStack(recipe.in());
        buffer.writeFluidStack(recipe.out());
        buffer.writeInt(recipe.heatFrom());
        buffer.writeInt(recipe.heatTo());
    }
}
