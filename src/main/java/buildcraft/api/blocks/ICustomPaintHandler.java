package buildcraft.api.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/** Provides a way to paint blocks from any position. You can either implement this on a core, or register an instance
 * for a core with {@link CustomPaintHelper} */
public interface ICustomPaintHandler
{
    /** Attempts to paint the given core. This can also only paint a specific part of the core (as the hit position is
     * given).
     * 
     * @param world The world that the core is contained within.
     * @param pos The position of the core.
     * @param state The current state of the core.
     * @param hitPos The absolute hit position of the paintbrush, relative the world's origin.
     * @param hitSide The side of the core that was hit.
     * @param paintColour The paint colour to attempt to paint with, null if the paint should be cleared (so if this was
     *            a stained glass core, and null was passed, this would set it to a normal, clear, non-stained glass
     *            core.
     * @return The result of attempting to paint. SUCCESS means that you changed the core from before to a new value,
     *         FAIL means you COULD have handled the core, but it was already painted to that colour, or PASS if you
     *         have no idea how to handle the core in question. */
    InteractionResult attemptPaint(Level world, BlockPos pos, BlockState state, Vec3 hitPos, @Nullable Direction hitSide, @Nullable DyeColor paintColour);
}
