package buildcraft.api.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum EnumPowerStage implements StringRepresentable
{
    BLUE,
    GREEN,
    YELLOW,
    RED,
    OVERHEAT,
    BLACK;

    public static final EnumPowerStage[] VALUES = values();

    private final String modelName = name().toLowerCase(Locale.ROOT);

    public String getModelName() {
        return modelName;
    }

    @Override
    public String getSerializedName() {
        return getModelName();
    }
}
