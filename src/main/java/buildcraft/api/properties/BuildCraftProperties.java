package buildcraft.api.properties;

import buildcraft.api.enums.*;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public final class BuildCraftProperties
{
    public static final Property<Direction> BLOCK_FACING = EnumProperty.create("facing", Direction.class, new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
    public static final Property<Direction> BLOCK_FACING_6 = EnumProperty.create("facing", Direction.class);

    public static final Property<DyeColor> BLOCK_COLOR = EnumProperty.create("color", DyeColor.class);
//    public static final EnumProperty<EnumSpring> SPRING_TYPE = EnumProperty.create("type", EnumSpring.class);
    public static final Property<EnumEngineType> ENGINE_TYPE = EnumProperty.create("type", EnumEngineType.class);
    public static final Property<EnumLaserTableType> LASER_TABLE_TYPE = EnumProperty.create("type", EnumLaserTableType.class);
    public static final Property<EnumMachineState> MACHINE_STATE = EnumProperty.create("state", EnumMachineState.class);
    public static final Property<EnumPowerStage> ENERGY_STAGE = EnumProperty.create("stage", EnumPowerStage.class);
    public static final Property<EnumOptionalSnapshotType> SNAPSHOT_TYPE = EnumProperty.create("snapshot_type", EnumOptionalSnapshotType.class);
    public static final Property<EnumDecoratedBlock> DECORATED_BLOCK = EnumProperty.create("decoration_type", EnumDecoratedBlock.class);

    public static final Property<Integer> GENERIC_PIPE_DATA = IntegerProperty.create("pipe_data", 0, 15);
    public static final Property<Integer> LED_POWER = IntegerProperty.create("led_power", 0, 3);
//    // Calen: [/ 8] for pump oil [* 7 / 8]
//    public static final Property<Integer> SPRING_TOTAL_SOURCE = IntegerProperty.create("spring_total_source", 0, Integer.MAX_VALUE / 8);

    public static final Property<Boolean> JOINED_BELOW = BooleanProperty.create("joined_below"); // 这里的注册名就是model文件里的条件
    public static final Property<Boolean> MOVING = BooleanProperty.create("moving");
    public static final Property<Boolean> LED_DONE = BooleanProperty.create("led_done");
    public static final Property<Boolean> ACTIVE = BooleanProperty.create("active");
    public static final Property<Boolean> VALID = BooleanProperty.create("valid");

    public static final Property<Boolean> CONNECTED_UP = BooleanProperty.create("connected_up");
    public static final Property<Boolean> CONNECTED_DOWN = BooleanProperty.create("connected_down");
    public static final Property<Boolean> CONNECTED_EAST = BooleanProperty.create("connected_east");
    public static final Property<Boolean> CONNECTED_WEST = BooleanProperty.create("connected_west");
    public static final Property<Boolean> CONNECTED_NORTH = BooleanProperty.create("connected_north");
    public static final Property<Boolean> CONNECTED_SOUTH = BooleanProperty.create("connected_south");

    public static final Map<Direction, Property<Boolean>> CONNECTED_MAP;

    // Block state setting flags -these are used by Level.markAndNotifyBlock and Level.setBlockState. These flags can be
    // added together to pass the additions
    public static final int UPDATE_NONE = 0;
    /**
     * This updates the neighbouring blocks that the new core is set. It also updates the comparator output of this
     * core.
     */
    public static final int UPDATE_NEIGHBOURS = 1;
    /**
     * This will mark the core for an update next tick, as well as send an update to the client (if this is a server
     * world).
     */
    public static final int MARK_BLOCK_FOR_UPDATE = 2;
    /**
     * This will mark the core for an update, even if this is a client world. It is useless to use this if
     * world.isRemote returns false.
     */
    public static final int UPDATE_EVEN_CLIENT = 4 + MARK_BLOCK_FOR_UPDATE; // 6

    // Pre-added flags- pass these as-is to the Level.markAndNotifyBlock and Level.setBlockState methods.
    /**
     * This will do what both {@link #UPDATE_NEIGHBOURS} and {@link #MARK_BLOCK_FOR_UPDATE} do.
     */
    public static final int MARK_THIS_AND_NEIGHBOURS = UPDATE_NEIGHBOURS + MARK_BLOCK_FOR_UPDATE;
    /**
     * This will update everything about this core.
     */
    public static final int UPDATE_ALL = UPDATE_NEIGHBOURS + MARK_BLOCK_FOR_UPDATE + UPDATE_EVEN_CLIENT;

    static
    {
        Map<Direction, Property<Boolean>> map = Maps.newEnumMap(Direction.class);
        map.put(Direction.DOWN, CONNECTED_DOWN);
        map.put(Direction.UP, CONNECTED_UP);
        map.put(Direction.EAST, CONNECTED_EAST);
        map.put(Direction.WEST, CONNECTED_WEST);
        map.put(Direction.NORTH, CONNECTED_NORTH);
        map.put(Direction.SOUTH, CONNECTED_SOUTH);
        CONNECTED_MAP = Maps.immutableEnumMap(map);
    }

//    // 初始化一下 否则spring block拿到的BuildCraftProperties.SPRING_TYPE是null?
//    public static void init()
//    {
//        new BuildCraftProperties();
//    }

    /**
     * Deactivate constructor
     */
    private BuildCraftProperties()
    {
    }
}
