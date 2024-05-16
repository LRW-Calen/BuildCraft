/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon.tile;

import buildcraft.silicon.BCSiliconBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import javax.annotation.Nullable;

public class TileChargingTable extends TileLaserTableBase {
    public TileChargingTable() {
        super(BCSiliconBlocks.chargingTableTile.get());
    }

    @Override
    public long getTarget() {
        return 0;
    }

    // INamedContainerProvider

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
//        return new ContainerChargingTable(BCSiliconMenuTypes.CHARGING_TABLE, id, player, this);
        return null;
    }
}
