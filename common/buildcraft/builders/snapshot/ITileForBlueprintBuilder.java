/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.builders.snapshot;

import buildcraft.api.inventory.IItemTransactor;
import buildcraft.lib.fluid.TankManager;

public interface ITileForBlueprintBuilder extends ITileForSnapshotBuilder {
    Blueprint.BuildingInfo getBlueprintBuildingInfo();

    IItemTransactor getInvResources();

    TankManager getTankManager();
}
