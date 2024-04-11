/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.robots;

import net.minecraft.nbt.CompoundTag;

public abstract class ResourceId {

    protected ResourceId() {}

    public void writeToNBT(CompoundTag nbt) {
        nbt.putString("resourceName", RobotManager.getResourceIdName(getClass()));
    }

    protected void readFromNBT(CompoundTag nbt) {}

    public static ResourceId load(CompoundTag nbt) {
        try {
            Class<?> cls;
            if (nbt.contains("class")) {
                // Migration support for 6.4.x
                cls = RobotManager.getResourceIdByLegacyClassName(nbt.getString("class"));
            } else {
                cls = RobotManager.getResourceIdByName(nbt.getString("resourceName"));
            }

            ResourceId id = (ResourceId) cls.newInstance();
            id.readFromNBT(nbt);

            return id;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
