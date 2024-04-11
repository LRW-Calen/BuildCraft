/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class BlockPlacedDownEvent extends Event
{
    public final Player player;
    public final BlockState state;
    public final BlockPos pos;

    public BlockPlacedDownEvent(Player player, BlockPos pos, BlockState state) {
        this.player = player;
        this.state = state;
        this.pos = pos;
    }
}
