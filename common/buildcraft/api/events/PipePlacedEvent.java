/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class PipePlacedEvent extends Event
{
    public Player player;
    public Item pipeType;
    public BlockPos pos;

    public PipePlacedEvent(Player player, Item pipeType, BlockPos pos) {
        this.player = player;
        this.pipeType = pipeType;
        this.pos = pos;
    }

}
