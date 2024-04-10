package buildcraft.api.fuels;

import buildcraft.factory.recipe.EnumHeatExchangeRecipeType;

import java.util.Locale;

// Calen
public enum EnumCoolantType
{
    FLUID,
    SOLID,
    ;

    public String getlowerName()
    {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static EnumCoolantType byName(String name)
    {
        return valueOf(name.toUpperCase(Locale.ROOT));
    }
}
