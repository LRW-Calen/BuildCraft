package buildcraft.api.transport.pipe;

import buildcraft.api.statements.containers.IRedstoneStatementContainer;
import buildcraft.api.transport.IWireManager;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.lib.net.MessageUpdateTile;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Designates a tile that can contain a pipe, up to 6 sided pluggables.
 */
public interface IPipeHolder extends IRedstoneStatementContainer, MenuProvider
{
    Level getPipeWorld();

    BlockPos getPipePos();

    BlockEntity getPipeTile();

    IPipe getPipe();

    /**
     * @return true if the player should be able to interact with the pipe holder in GUI form. Implementors should
     * generally check to ensure they are still present in-world.
     */
    boolean canPlayerInteract(Player player);

    @Nullable
    PipePluggable getPluggable(Direction side);

    @Nullable
    BlockEntity getNeighbourTile(Direction side);

    @Nullable
    IPipe getNeighbourPipe(Direction side);

    /**
     * Gets the given capability going outwards from the pipe. This will test the
     * {@link PipePluggable#getInternalCapability(Capability)} first, and the look at the neighbouring tile.
     */
    @Nullable
    <T> T getCapabilityFromPipe(Direction side, @Nonnull Capability<T> capability);

//    // Calen test for power flow circle
//    @Nullable
//    default <T> T getCapabilityFromPipe(Direction side, @Nonnull Capability<T> capability, LinkedList<IPipeHolder> trackList)
//    {
//        if (trackList.contains(this))
//        {
//            return null;
//        }
//        else
//        {
//            trackList.push(this);
//            T cap = getCapabilityFromPipe(side, capability);
//            trackList.pop();
//            return cap;
//        }
//    }

    IWireManager getWireManager();

    GameProfile getOwner();

    /**
     * @return True if at least 1 handler received this event, false if not.
     */
    boolean fireEvent(PipeEvent event);

    void scheduleRenderUpdate();

    /**
     * @param parts The parts that want to send a network update.
     */
    void scheduleNetworkUpdate(PipeMessageReceiver... parts);

    /**
     * Schedules a GUI network update, that is only the players who currently have a pipe element open in a GUI will be
     * updated.
     *
     * @param parts The parts that want to send a network update.
     */
    void scheduleNetworkGuiUpdate(PipeMessageReceiver... parts);

    /**
     * Sends a custom message from a pluggable or pipe centre to the server/client (depending on which side this is
     * currently on).
     */
    void sendMessage(PipeMessageReceiver to, IWriter writer);

    void sendGuiMessage(PipeMessageReceiver to, IWriter writer);

    /**
     * Called on the server whenever a gui container object is opened.
     */
    void onPlayerOpen(Player player);

    // Calen: only for Server preparing for opening Gate GUI
    MessageUpdateTile onServerPlayerOpenNoSend(Player player);

    /**
     * Called on the server whenever a gui container object is closed.
     */
    void onPlayerClose(Player player);

    enum PipeMessageReceiver
    {
        BEHAVIOUR(null),
        FLOW(null),
        PLUGGABLE_DOWN(Direction.DOWN),
        PLUGGABLE_UP(Direction.UP),
        PLUGGABLE_NORTH(Direction.NORTH),
        PLUGGABLE_SOUTH(Direction.SOUTH),
        PLUGGABLE_WEST(Direction.WEST),
        PLUGGABLE_EAST(Direction.EAST),
        WIRES(null);
        // Wires are updated differently (they never use this API)

        public static final PipeMessageReceiver[] VALUES = values();
        public static final PipeMessageReceiver[] PLUGGABLES = new PipeMessageReceiver[6];

        static
        {
            for (PipeMessageReceiver type : VALUES)
            {
                if (type.face != null)
                {
                    PLUGGABLES[type.face.ordinal()] = type;
                }
            }
        }

        public final Direction face;

        PipeMessageReceiver(Direction face)
        {
            this.face = face;
        }
    }

    interface IWriter
    {
        void write(FriendlyByteBuf buffer);
    }
}
