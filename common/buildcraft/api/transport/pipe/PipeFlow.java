package buildcraft.api.transport.pipe;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.transport.pipe.IPipeHolder.IWriter;
import buildcraft.api.transport.pipe.IPipeHolder.PipeMessageReceiver;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkDirection;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class PipeFlow implements ICapabilityProvider
{
    /**
     * The ID for completely refreshing the state of this flow.
     */
    public static final int NET_ID_FULL_STATE = 0;
    /**
     * The ID for updating what has changed since the last NET_ID_FULL_STATE or NET_ID_UPDATE has been sent.
     */
    // Wait, what? How is that a good idea or even sensible to make updates work this way?
    public static final int NET_ID_UPDATE = 1;

    public final IPipe pipe;

    public PipeFlow(IPipe pipe)
    {
        this.pipe = pipe;
    }

    public PipeFlow(IPipe pipe, CompoundTag nbt)
    {
        this.pipe = pipe;
    }

    public CompoundTag writeToNbt()
    {
        return new CompoundTag();
    }

    /**
     * Writes a payload with the specified id. Standard ID's are NET_ID_FULL_STATE and NET_ID_UPDATE.
     */
    public void writePayload(int id, FriendlyByteBuf buffer, Dist side)
    {
    }

    /**
     * Reads a payload with the specified id. Standard ID's are NET_ID_FULL_STATE and NET_ID_UPDATE.
     */
    public void readPayload(int id, FriendlyByteBuf buffer, NetworkDirection side) throws IOException
    {
    }

    public void sendPayload(int id)
    {
        final Dist side = pipe.getHolder().getPipeWorld().isClientSide ? Dist.CLIENT : Dist.DEDICATED_SERVER;
        sendCustomPayload(id, (buf) -> writePayload(id, buf, side));
    }

    public final void sendCustomPayload(int id, IWriter writer)
    {
        pipe.getHolder().sendMessage(PipeMessageReceiver.FLOW, buffer ->
        {
            buffer.writeBoolean(true);
            buffer.writeShort(id);
            writer.write(buffer);
        });
    }

    public abstract boolean canConnect(Direction face, PipeFlow other);

    public abstract boolean canConnect(Direction face, BlockEntity oTile);

    /**
     * Used to force a connection to a given tile, even if the {@link PipeBehaviour} wouldn't normally connect to
     * it.
     */
    public boolean shouldForceConnection(Direction face, BlockEntity oTile)
    {
        return false;
    }

    public void onTick()
    {
    }

    public void addDrops(NonNullList<ItemStack> toDrop, int fortune)
    {
    }

    public boolean onFlowActivate(Player player, HitResult trace, float hitX, float hitY, float hitZ,
                                  EnumPipePart part)
    {
        return false;
    }

//    @Override
//    public final boolean hasCapability(@Nonnull Capability<?> capability, Direction facing)
//    {
////        return getCapability(capability, facing) != null;
//        return getCapability(capability, facing).isPresent();
//    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing)
    {
        return LazyOptional.empty();
    }
}
