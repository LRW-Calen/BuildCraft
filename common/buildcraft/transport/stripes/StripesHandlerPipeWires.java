/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.stripes;

import buildcraft.api.transport.IStripesActivator;
import buildcraft.api.transport.IStripesHandlerItem;
import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.lib.misc.ColourUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StripesHandlerPipeWires implements IStripesHandlerItem {

    private static final int PIPES_TO_TRY = 8;

    @Override
    public boolean handle(World world, BlockPos pos, Direction direction, ItemStack stack, PlayerEntity player, IStripesActivator activator) {
//        EnumDyeColor pipeWireColor = EnumDyeColor.byMetadata(stack.getMetadata());
        DyeColor pipeWireColor = ColourUtil.getStackColourFromTag(stack);

        for (int i = PIPES_TO_TRY; i > 0; i--) {
            pos = pos.relative(direction.getOpposite());

            TileEntity tile = world.getBlockEntity(pos);
//            if (tile != null && tile.hasCapability(PipeApi.CAP_PIPE_HOLDER, null))
            if (tile != null && tile.getCapability(PipeApi.CAP_PIPE_HOLDER, null).isPresent()) {
                IPipeHolder pipeHolder = tile.getCapability(PipeApi.CAP_PIPE_HOLDER, null).orElse(null);

                /*
                if (!pipeHolder.pipe.wireSet[pipeWireColor]) {
                    pipeHolder.pipe.wireSet[pipeWireColor] = true;
                    pipeHolder.pipe.signalStrength[pipeWireColor] = 0;

                    pipeHolder.pipe.updateSignalState();
                    pipeHolder.scheduleRenderUpdate();
                    world.notifyNeighborsOfStateChange(pipeHolder.getPipePos(), tile.getBlockType(), false);
                    */
                //stack.shrink(1);
                    /*
                    return true;

            }
            */

            } else {
                // Not a pipe, don't follow chain
                return false;
            }
        }

        return false;
    }
}
