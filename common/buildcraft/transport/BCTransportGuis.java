/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.transport;

import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.lib.net.MessageUpdateTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

public enum BCTransportGuis {
    FILTERED_BUFFER,
    PIPE_DIAMOND,
    PIPE_DIAMOND_WOOD,
    PIPE_EMZULI;

    public static final BCTransportGuis[] VALUES = values();

    public static BCTransportGuis get(int id) {
        if (id < 0 || id >= VALUES.length) return null;
        return VALUES[id];
    }

//    public void openGui(Player player)
//    {
//        openGui(player, 0, -1, 0);
//    }

    //    public void openGui(Player player, BlockPos pos)
//    {
////        openGui(player, pos.getX(), pos.getY(), pos.getZ());
//        if (player instanceof ServerPlayer serverPlayer)
//        {
////        player.openGui(BCTransport.INSTANCE, ordinal(), player.getEntityWorld(), x, y, z);
//            if (serverPlayer.level.getBlockEntity(pos) instanceof MenuProvider menuProvider)
//            {
//                NetworkHooks.openGui(serverPlayer, menuProvider, pos);
//            }
//            else
//            {
//                player.sendMessage(new TranslatableComponent("buildcraft.error.open_null_menu"), Util.NIL_UUID);
//            }
//        }
//    }
    public void openPipeGui(Player player, BlockPos pos, IPipeHolder holder) {
//        openGui(player, pos.getX(), pos.getY(), pos.getZ());
        if (player instanceof ServerPlayer serverPlayer) {
//        player.openGui(BCTransport.INSTANCE, ordinal(), player.getEntityWorld(), x, y, z);
            // Calen 1.18.2: moved from ContainerGate#<init>
            // recreate plug object before gui packed received
            // Client call in BCSiliconMenuTypes#GATE
//            PacketBufferBC buffer = new PacketBufferBC(Unpooled.buffer());

            MessageUpdateTile msg = holder.onServerPlayerOpenNoSend(player);
            NetworkHooks.openGui(
                    serverPlayer, holder, buf ->
                    {
                        buf.writeBlockPos(pos);

                        msg.toBytes(buf);
                    }
            );
        }
    }

//    public void openGui(Player player, int x, int y, int z)
//    {
////        player.openGui(BCTransport.INSTANCE, ordinal(), player.getEntityWorld(), x, y, z);
//    }
}
