package buildcraft.api.facades;

import net.minecraft.world.item.DyeColor;

import javax.annotation.Nullable;

public interface IFacadePhasedState {
    IFacadeState getState();

    @Nullable
    DyeColor getActiveColor();
}
