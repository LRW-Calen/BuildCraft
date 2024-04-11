package buildcraft.api.fuels;

import buildcraft.api.BCModules;
import buildcraft.energy.recipe.CoolantRecipeSerializer;
import buildcraft.lib.misc.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public interface ICoolant extends Recipe<Container>
{
    public static final ResourceLocation TYPE_ID = new ResourceLocation(BCModules.ENERGY.getModId(), "coolant");

    public static final RecipeType<ICoolant> TYPE = RecipeType.register(TYPE_ID.toString());

    EnumCoolantType getCoolantType();
    FluidStack getFluid();

    @Override
    default boolean matches(Container inv, Level world)
    {
        return false;
    }

    @Override
    default ItemStack assemble(Container inv)
    {
        return StackUtil.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    default ItemStack getResultItem()
    {
        return StackUtil.EMPTY;
    }

    @Override
    default boolean isSpecial()
    {
        return true;
    }

    @Override
    default RecipeSerializer<?> getSerializer()
    {
        return CoolantRecipeSerializer.INSTANCE;
    }

    @Override
    default RecipeType<?> getType()
    {
        return TYPE;
    }
}
