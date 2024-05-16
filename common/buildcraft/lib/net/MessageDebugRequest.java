/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.net;

import buildcraft.api.net.IMessage;
import buildcraft.api.net.IMessageHandler;
import buildcraft.api.tiles.IDebuggable;
import buildcraft.lib.item.ItemDebugger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class MessageDebugRequest implements IMessage {
    private BlockPos pos;
    private Direction side;

    @SuppressWarnings("unused")
    public MessageDebugRequest() {
    }

    public MessageDebugRequest(BlockPos pos, Direction side) {
        this.pos = pos;
        this.side = side;
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        PacketBufferBC buf = PacketBufferBC.asPacketBufferBc(buffer);
        buf.writeBlockPos(pos);
        buf.writeEnum(side);
    }

    @Override
    public void fromBytes(PacketBuffer buffer) {
        PacketBufferBC buf = PacketBufferBC.asPacketBufferBc(buffer);
        pos = buf.readBlockPos();
        side = buf.readEnum(Direction.class);
    }

    public static final IMessageHandler<MessageDebugRequest, MessageDebugResponse> HANDLER = (message, ctx) ->
    {
        PlayerEntity player = ctx.getSender();
        if (!ItemDebugger.isShowDebugInfo(player)) {
            return new MessageDebugResponse();
        }
        TileEntity tile = player.level.getBlockEntity(message.pos);
        if (tile instanceof IDebuggable) {
//            List<String> left = new ArrayList<>();
//            List<String> right = new ArrayList<>();
            List<ITextComponent> left = new ArrayList<>();
            List<ITextComponent> right = new ArrayList<>();
            ((IDebuggable) tile).getDebugInfo(left, right, message.side);
            return new MessageDebugResponse(left, right);
        }
        return null;
    };
}
