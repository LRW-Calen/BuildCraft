package buildcraft.silicon.recipe;

import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.AssemblyRecipeType;
import buildcraft.api.recipes.IngredientStack;
import buildcraft.silicon.BCSilicon;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;

public class AssemblyRecipeBuilder {
    public final AssemblyRecipeType type;
    public final long requiredMicroJoules;
    public final Set<IngredientStack> requiredStacks;
    public final ItemStack output;

    private AssemblyRecipeBuilder(long requiredMicroJoules, Set<IngredientStack> requiredStacks, @Nonnull ItemStack output) {
        this.type = AssemblyRecipeType.BASIC;
        this.requiredMicroJoules = requiredMicroJoules;
        this.requiredStacks = requiredStacks;
        this.output = output;
    }

    private AssemblyRecipeBuilder() {
        this.type = AssemblyRecipeType.FACADE;
        requiredMicroJoules = -1;
        requiredStacks = null;
        output = null;
    }

    public static AssemblyRecipeBuilder basic(long requiredMicroJoules, Set<IngredientStack> requiredStacks, @Nonnull ItemStack output) {
        return new AssemblyRecipeBuilder(requiredMicroJoules, requiredStacks, output);
    }


    public static AssemblyRecipeBuilder facade() {
        return new AssemblyRecipeBuilder();
    }

    public void save(Consumer<FinishedRecipe> consumer, String name) {
        consumer.accept(new AssemblyRecipeBuilder.AssemblyRecipeResult(BCSilicon.MODID, name));
    }

    public void save(Consumer<FinishedRecipe> consumer, String namespace, String name) {
        consumer.accept(new AssemblyRecipeBuilder.AssemblyRecipeResult(namespace, name));
    }

    class AssemblyRecipeResult implements FinishedRecipe {
        private final String namespace;
        private final String name;

        public AssemblyRecipeResult(String namespace, String name) {
            this.namespace = namespace;
            this.name = name;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            AssemblyRecipeSerializer.toJson(AssemblyRecipeBuilder.this, json);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(namespace, "assembly/" + name);
        }

        @Override
        public RecipeSerializer<AssemblyRecipe> getType() {
            return AssemblyRecipeSerializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
