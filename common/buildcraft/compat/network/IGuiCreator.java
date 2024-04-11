package buildcraft.compat.network;

import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IGuiCreator {
    Enum<?> getGuiType();

    @Nullable
    @OnlyIn(Dist.CLIENT)
//    GuiContainer getClientGuiElement(int var1, EntityPlayer var2);
    AbstractContainerScreen getClientGuiElement(int var1, Player var2);

    @Nullable
//    Container getServerGuiElement(int var1, EntityPlayer var2);
    AbstractContainerMenu getServerGuiElement(int var1, Player var2);
}
