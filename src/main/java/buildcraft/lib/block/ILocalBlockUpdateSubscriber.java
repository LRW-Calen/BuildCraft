package buildcraft.lib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ILocalBlockUpdateSubscriber
{

    /**
     * Returns the position of the subscriber. Used with the result of @{getUpdateRange} to determine if a subscriber
     * should be notified about an update. This method should be kept lightweight as it can be called multiple times per
     * tick.
     *
     * @return the @{BlockPos} used to determine if a core update event is in range
     */
    BlockPos getSubscriberPos();

    /**
     * The distance from the @{BlockPos} that subscribers should be notified about updates. This method should be kept
     * lightweight as it can be called multiple times per tick.
     *
     * @return the range from the @{BlockPos} returned by @{getSubscriberPos} where core update events will trigger a
     * notification
     */
    int getUpdateRange();

    /**
     * Called to indicate an update happened within the listener's update range returned by the @{getUpdateRange} call.
     * This method should be kept lightweight as it can be called multiple times per tick.
     *
     * @param world    from the core update event
     * @param eventPos from the core update event
     * @param oldState from the core update event
     * @param newState from the core update event
     * @param flags    from the core update event
     */
//    void setWorldUpdated(Level world, BlockPos eventPos, BlockState oldState, BlockState newState, int flags);
    void setWorldUpdated(Level world, BlockPos eventPos);
}
