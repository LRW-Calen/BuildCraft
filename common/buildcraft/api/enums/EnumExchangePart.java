package buildcraft.api.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum EnumExchangePart implements StringRepresentable
{
    START,
    MIDDLE,
    END;

    private final String lowerCaseName = name().toLowerCase(Locale.ROOT);

    @Override
    public String getSerializedName()
    {
        return lowerCaseName;
    }
}
