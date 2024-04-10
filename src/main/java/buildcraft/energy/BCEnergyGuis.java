/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.energy;

import buildcraft.lib.net.MessageUpdateTile;
import buildcraft.lib.tile.TileBC_Neptune;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public enum BCEnergyGuis
{
    ENGINE_STONE,
    ENGINE_IRON;

    public static final BCEnergyGuis[] VALUES = values();

    public static BCEnergyGuis get(int id)
    {
        if (id < 0 || id >= VALUES.length) return null;
        return VALUES[id];
    }

    // Calen: no usage
//    public void openGUI(Player player, BlockState blockState)
//    {
////        player.openGui(BCEnergy.INSTANCE, ordinal(), player.getEntityWorld(), 0, 0, 0);
//        player.openMenu(blockState.getMenuProvider(player.level, new BlockPos(0, 0, 0)));
//    }

    //    public void openGUI(Player player, BlockPos pos, BlockState blockState)
    public void openGUI(Player player, TileBC_Neptune tile)
    {
//        player.openGui(BCEnergy.INSTANCE, ordinal(), player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
//        player.openMenu(blockState.getMenuProvider(player.level, pos));
        if (player instanceof ServerPlayer serverPlayer)
        {
//            player.openMenu(state.getMenuProvider(player.level, pos));
            if (tile instanceof MenuProvider menuProvider)
            {
//                NetworkHooks.openGui(serverPlayer, menuProvider, pos);

                MessageUpdateTile msg = tile.onServerPlayerOpenNoSend(player);
                NetworkHooks.openGui(
                        serverPlayer, menuProvider, buf ->
                        {
                            buf.writeBlockPos(tile.getBlockPos());

                            msg.toBytes(buf);
                        }
                );
            }
            else
            {
                player.sendMessage(new TranslatableComponent("buildcraft.error.open_null_menu"), Util.NIL_UUID);
            }
        }
    }

}
