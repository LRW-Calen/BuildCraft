/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.boards;

import buildcraft.api.robots.EntityRobotBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public abstract class RedstoneBoardRobotNBT extends RedstoneBoardNBT<EntityRobotBase> {

    @Override
    public RedstoneBoardRobot create(CompoundTag nbt, EntityRobotBase robot) {
        return create(robot);
    }

    public abstract RedstoneBoardRobot create(EntityRobotBase robot);

    public abstract ResourceLocation getRobotTexture();

}
