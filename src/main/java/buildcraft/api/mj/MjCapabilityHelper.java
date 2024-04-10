package buildcraft.api.mj;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides a quick way to return all types of a single {@link IMjConnector} for all the different capabilities.
 */
public class MjCapabilityHelper implements ICapabilityProvider
{

    @Nonnull
    private final IMjConnector connector;

    @Nullable
    private final IMjReceiver receiver;

    @Nullable
    private final IMjRedstoneReceiver rsReceiver;

    @Nullable
    private final IMjReadable readable;

    @Nullable
    private final IMjPassiveProvider provider;

    public MjCapabilityHelper(@Nonnull IMjConnector mj)
    {
        this.connector = mj;
        this.receiver = mj instanceof IMjReceiver ? (IMjReceiver) mj : null;
        this.rsReceiver = mj instanceof IMjRedstoneReceiver ? (IMjRedstoneReceiver) mj : null;
        this.readable = mj instanceof IMjReadable ? (IMjReadable) mj : null;
        this.provider = mj instanceof IMjPassiveProvider ? (IMjPassiveProvider) mj : null;
    }

    // Calen: getCapability(...).isPresent()
    //    @Override
//    public boolean hasCapability(@Nonnull Capability<?> capability, Direction facing)
//    {
////        return getCapability(capability, facing) != null;
//        return getCapability(capability, facing).isPresent();
//    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing)
    {
        if (capability == MjAPI.CAP_CONNECTOR)
        {
//            return MjAPI.CAP_CONNECTOR.cast(connector);
            return connector == null ? LazyOptional.empty() : LazyOptional.of(() -> connector).cast();
        }
        if (capability == MjAPI.CAP_RECEIVER)
        {
//            return MjAPI.CAP_RECEIVER.cast(receiver);
            return receiver == null ? LazyOptional.empty() : LazyOptional.of(() -> receiver).cast();
        }
        if (capability == MjAPI.CAP_REDSTONE_RECEIVER)
        {
//            return MjAPI.CAP_REDSTONE_RECEIVER.cast(rsReceiver);
            return rsReceiver == null ? LazyOptional.empty() : LazyOptional.of(() -> rsReceiver).cast();
        }
        if (capability == MjAPI.CAP_READABLE)
        {
//            return MjAPI.CAP_READABLE.cast(readable);
            return readable == null ? LazyOptional.empty() : LazyOptional.of(() -> readable).cast();
        }
        if (capability == MjAPI.CAP_PASSIVE_PROVIDER)
        {
//            return MjAPI.CAP_PASSIVE_PROVIDER.cast(provider);
            return provider == null ? LazyOptional.empty() :LazyOptional.of(() -> provider).cast();
        }
        return LazyOptional.empty();
    }
}
