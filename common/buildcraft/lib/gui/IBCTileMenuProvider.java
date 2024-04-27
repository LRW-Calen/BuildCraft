package buildcraft.lib.gui;

import buildcraft.lib.net.MessageUpdateTile;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;

public interface IBCTileMenuProvider extends MenuProvider {
    public abstract MessageUpdateTile onServerPlayerOpenNoSend(Player player);
}
