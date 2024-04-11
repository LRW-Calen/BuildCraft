/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL. Please check the contents
 * of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt */
package buildcraft.api.robots;

import buildcraft.api.core.BCLog;
import buildcraft.api.core.EnumPipePart;
import buildcraft.api.statements.StatementSlot;
import buildcraft.api.transport.IInjectable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Arrays;

public abstract class DockingStation {
    public Direction side;
    public Level world;

    private long robotTakingId = EntityRobotBase.NULL_ROBOT_ID;
    private EntityRobotBase robotTaking;

    private boolean linkIsMain = false;

    private BlockPos pos;

    public DockingStation(BlockPos iIndex, Direction iSide) {
        pos = iIndex;
        side = iSide;
    }

    public DockingStation() {}

    public boolean isMainStation() {
        return linkIsMain;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction side() {
        return side;
    }

    public EntityRobotBase robotTaking() {
        if (robotTakingId == EntityRobotBase.NULL_ROBOT_ID) {
            return null;
        } else if (robotTaking == null) {
            robotTaking = RobotManager.registryProvider.getRegistry(world).getLoadedRobot(robotTakingId);
        }

        return robotTaking;
    }

    public void invalidateRobotTakingEntity() {
        robotTaking = null;
    }

    public long linkedId() {
        return robotTakingId;
    }

    public boolean takeAsMain(EntityRobotBase robot) {
        if (robotTakingId == EntityRobotBase.NULL_ROBOT_ID) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            linkIsMain = true;
            robotTaking = robot;
            robotTakingId = robot.getRobotId();
            registry.registryMarkDirty();
            robot.setMainStation(this);
            registry.take(this, robot.getRobotId());

            return true;
        } else {
            return robotTakingId == robot.getRobotId();
        }
    }

    public boolean take(EntityRobotBase robot) {
        if (robotTaking == null) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            linkIsMain = false;
            robotTaking = robot;
            robotTakingId = robot.getRobotId();
            registry.registryMarkDirty();
            registry.take(this, robot.getRobotId());

            return true;
        } else {
            return robot.getRobotId() == robotTakingId;
        }
    }

    public void release(EntityRobotBase robot) {
        if (robotTaking == robot && !linkIsMain) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            unsafeRelease(robot);
            registry.registryMarkDirty();
            registry.release(this, robot.getRobotId());
        }
    }

    /** Same a release but doesn't clear the registry (presumably called from the registry). */
    public void unsafeRelease(EntityRobotBase robot) {
        if (robotTaking == robot) {
            linkIsMain = false;
            robotTaking = null;
            robotTakingId = EntityRobotBase.NULL_ROBOT_ID;
        }
    }

    public void writeToNBT(CompoundTag nbt) {
        nbt.putIntArray("pos", new int[] { getPos().getX(), getPos().getY(), getPos().getZ() });
        nbt.putByte("side", (byte) side.ordinal());
        nbt.putBoolean("isMain", linkIsMain);
        nbt.putLong("robotId", robotTakingId);
    }

    public void readFromNBT(CompoundTag nbt) {
        if (nbt.contains("index")) {
            // For compatibility with older versions of minecraft and buildcraft
            CompoundTag indexNBT = nbt.getCompound("index");
            int x = indexNBT.getInt("i");
            int y = indexNBT.getInt("j");
            int z = indexNBT.getInt("k");
            pos = new BlockPos(x, y, z);
        } else {
            int[] array = nbt.getIntArray("pos");
            if (array.length == 3) {
                pos = new BlockPos(array[0], array[1], array[2]);
            } else if (array.length != 0) {
                BCLog.logger.warn("Found an integer array that was not the right length! (" + Arrays.toString(array) + ")");
            } else {
                BCLog.logger.warn("Did not find any integer positions! This is a bug!");
            }
        }
        side = Direction.values()[nbt.getByte("side")];
        linkIsMain = nbt.getBoolean("isMain");
        robotTakingId = nbt.getLong("robotId");
    }

    public boolean isTaken() {
        return robotTakingId != EntityRobotBase.NULL_ROBOT_ID;
    }

    public long robotIdTaking() {
        return robotTakingId;
    }

    public BlockPos index() {
        return pos;
    }

    @Override
    public String toString() {
        return "{" + pos + ", " + side + " :" + robotTakingId + "}";
    }

    public boolean linkIsDocked() {
        if (robotTaking() != null) {
            return robotTaking().getDockingStation() == this;
        } else {
            return false;
        }
    }

    public boolean canRelease() {
        return !isMainStation() && !linkIsDocked();
    }

    public boolean isInitialized() {
        return true;
    }

    public abstract Iterable<StatementSlot> getActiveActions();

    public IInjectable getItemOutput() {
        return null;
    }

    public EnumPipePart getItemOutputSide() {
        return EnumPipePart.CENTER;
    }

    public Container getItemInput() {
        return null;
    }

    public EnumPipePart getItemInputSide() {
        return EnumPipePart.CENTER;
    }

    public IFluidHandler getFluidOutput() {
        return null;
    }

    public EnumPipePart getFluidOutputSide() {
        return EnumPipePart.CENTER;
    }

    public IFluidHandler getFluidInput() {
        return null;
    }

    public EnumPipePart getFluidInputSide() {
        return EnumPipePart.CENTER;
    }

    public boolean providesPower() {
        return false;
    }

    public IRequestProvider getRequestProvider() {
        return null;
    }

    public void onChunkUnload() {

    }
}
