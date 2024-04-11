package buildcraft.api.transport.pluggable;

import buildcraft.api.transport.pipe.IPipeHolder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public abstract class PipePluggable
{
    public final PluggableDefinition definition;
    public final IPipeHolder holder;
    public final Direction side;

    public PipePluggable(PluggableDefinition definition, IPipeHolder holder, Direction side)
    {
        this.definition = definition;
        this.holder = holder;
        this.side = side;
    }

    public CompoundTag writeToNbt()
    {
        CompoundTag nbt = new CompoundTag();
        return nbt;
    }

    /**
     * Writes the payload that will be passed into
     * {@link PluggableDefinition#loadFromBuffer(IPipeHolder, Direction, FriendlyByteBuf)} on the client. (This is called
     * on the server and sent to the client). Note that this will be called *instead* of write and read payload.
     */
    public void writeCreationPayload(FriendlyByteBuf buffer)
    {

    }

    public void writePayload(FriendlyByteBuf buffer, Dist side)
    {

    }

    public void readPayload(FriendlyByteBuf buffer, NetworkDirection side, NetworkEvent.Context ctx) throws IOException
    {

    }

    public final void scheduleNetworkUpdate()
    {
        holder.scheduleNetworkUpdate(IPipeHolder.PipeMessageReceiver.PLUGGABLES[side.ordinal()]);
    }

    public void onTick()
    {
    }

    /**
     * @return A bounding box that will be used for collisions and raytracing.
     */
    public abstract VoxelShape getBoundingBox();

    /**
     * @return True if the pipe cannot connect outwards (it is blocked), or False if this does not core the pipe.
     */
    public boolean isBlocking()
    {
        return false;
    }

    /**
     * Gets the value of a specified capability key, or null if the given capability is not supported at the call time.
     * This is effectively {@link ICapabilityProvider}, but where
     * {@link ICapabilityProvider#hasCapability(Capability, Direction)} will return true when this returns a non-null
     * value.
     */
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        return LazyOptional.empty();
    }

    /**
     * Gets the {@link Capability} that is accessible from the pipe that this is attached to.
     *
     * @param cap
     * @return
     */
    public <T> T getInternalCapability(@Nonnull Capability<T> cap)
    {
        return null;
    }

    /**
     * Called whenever this pluggable is removed from the pipe.
     */
    public void onRemove()
    {
    }

    /**
     * @param toDrop A list containing all the items to drop (so you should add your items to this list)
     */
    public void addDrops(NonNullList<ItemStack> toDrop, int fortune)
    {
        ItemStack stack = getPickStack();
        if (!stack.isEmpty())
        {
            toDrop.add(stack);
        }
    }

    /**
     * Called whenever this pluggable is picked by the player (similar to Block.getPickBlock)
     *
     * @return The stack that should be picked, or ItemStack.EMPTY if no stack can be picked from this pluggable.
     */
    public ItemStack getPickStack()
    {
        return ItemStack.EMPTY;
    }

    public boolean onPluggableActivate(Player player, HitResult trace, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public PluggableModelKey getModelRenderKey(RenderType layer)
    {
        return null;
    }

    /**
     * Called if the {@link IPluggableStaticBaker} returns quads with tint indexes set to
     * <code>data * 6 + key.side.ordinal()</code>. <code>"data"</code> is passed in here as <code>"tintIndex"</code>.
     *
     * @return The tint index to render the quad with, or -1 for default.
     */
    @OnlyIn(Dist.CLIENT)
    public int getBlockColor(int tintIndex)
    {
        return -1;
    }

    /**
     * PipePluggable version of
     * {@link Block#canBeConnectedTo(net.minecraft.world.level.LevelAccessor, BlockPos, Direction)}.
     */
    public boolean canBeConnected()
    {
        return false;
    }

    /**
     * PipePluggable version of
     * {@link BlockState#isSideSolid(IBlockAccess, BlockPos, Direction)}
     */
    public boolean isSideSolid()
    {
        return false;
    }

    /**
     * PipePluggable version of {@link Block#getExplosionResistance(Level, BlockPos, Entity, Explosion)}
     */
//    public float getExplosionResistance(@Nullable Entity exploder, Explosion explosion)
    public float getExplosionResistance(@Nonnull Entity exploder, Explosion explosion)
    {
        return 0;
    }

    public boolean canConnectToRedstone(@Nullable Direction to)
    {
        return false;
    }

    // Calen: seems use block Shape in 1.18.2
//    /**
//     * PipePluggable version of
//     * {@link net.minecraft.core.state.BlockState#getBlockFaceShape(LevelAccessor, BlockPos, Direction)}
//     */
//    public BlockFaceShape getBlockFaceShape()
//    public SupportType getBlockFaceShape()
//    {
////        return BlockFaceShape.UNDEFINED;
//        return SupportType.FULL;
//    }

    public void onPlacedBy(Player player)
    {

    }
}
