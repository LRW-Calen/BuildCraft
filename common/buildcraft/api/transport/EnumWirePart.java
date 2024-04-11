package buildcraft.api.transport;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum EnumWirePart
{
    EAST_UP_SOUTH(true, true, true),
    EAST_UP_NORTH(true, true, false),
    EAST_DOWN_SOUTH(true, false, true),
    EAST_DOWN_NORTH(true, false, false),
    WEST_UP_SOUTH(false, true, true),
    WEST_UP_NORTH(false, true, false),
    WEST_DOWN_SOUTH(false, false, true),
    WEST_DOWN_NORTH(false, false, false);

    public static final EnumWirePart[] VALUES = values();

    public final AxisDirection x, y, z;

    /**
     * The bounding box for rendering a wire or selecting an already-placed wire.
     */
    public final VoxelShape boundingBox;

    /**
     * The bounding box that is used when adding pipe wire to a pipe
     */
    public final VoxelShape boundingBoxPossible;

    EnumWirePart(boolean x, boolean y, boolean z)
    {
        this.x = x ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
        this.y = y ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
        this.z = z ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE;
        double x1 = this.x.getStep() * (5 / 16.0) + 0.5;
        double y1 = this.y.getStep() * (5 / 16.0) + 0.5;
        double z1 = this.z.getStep() * (5 / 16.0) + 0.5;
        double x2 = this.x.getStep() * (4 / 16.0) + 0.5;
        double y2 = this.y.getStep() * (4 / 16.0) + 0.5;
        double z2 = this.z.getStep() * (4 / 16.0) + 0.5;
        this.boundingBox = Shapes.box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

        Vec3 center = new Vec3(0.5, 0.5, 0.5);
        Vec3 edge = new Vec3(x ? 0.75 : 0.25, y ? 0.75 : 0.25, z ? 0.75 : 0.25);
        this.boundingBoxPossible = Shapes.box(
                Math.min(center.x, edge.x),
                Math.min(center.y, edge.y),
                Math.min(center.z, edge.z),
                Math.max(center.x, edge.x),
                Math.max(center.y, edge.y),
                Math.max(center.z, edge.z)
        );
    }

    public AxisDirection getDirection(Direction.Axis axis)
    {
        switch (axis)
        {
            case X:
                return x;
            case Y:
                return y;
            case Z:
                return z;
            default:
                return null;
        }
    }

    public static EnumWirePart get(int x, int y, int z)
    {
        boolean bx = (x % 2 + 2) % 2 == 1;
        boolean by = (y % 2 + 2) % 2 == 1;
        boolean bz = (z % 2 + 2) % 2 == 1;
        return get(bx, by, bz);
    }

    public static EnumWirePart get(boolean x, boolean y, boolean z)
    {
        if (x)
        {
            if (y)
            {
                return z ? EAST_UP_SOUTH : EAST_UP_NORTH;
            }
            else
            {
                return z ? EAST_DOWN_SOUTH : EAST_DOWN_NORTH;
            }
        }
        else
        {
            if (y)
            {
                return z ? WEST_UP_SOUTH : WEST_UP_NORTH;
            }
            else
            {
                return z ? WEST_DOWN_SOUTH : WEST_DOWN_NORTH;
            }
        }
    }
}
