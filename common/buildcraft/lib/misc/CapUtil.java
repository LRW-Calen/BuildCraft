package buildcraft.lib.misc;

import buildcraft.api.core.IFluidHandlerAdv;
import buildcraft.api.inventory.IItemTransactor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class CapUtil {
    // @CapabilityInject(IItemTransactor.class)
    private static Capability<IItemTransactor> capTransactor = CapabilityManager.get(new CapabilityToken<>() {
    });
    @Nonnull
    public static final Capability<IItemHandler> CAP_ITEMS = getCapNonNull(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, IItemHandler.class);

    @Nonnull
    public static final Capability<IFluidHandler> CAP_FLUIDS = getCapNonNull(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, IFluidHandler.class);
    @Nonnull
    public static final Capability<IItemTransactor> CAP_ITEM_TRANSACTOR = getCapNonNull(capTransactor, IItemTransactor.class);

    // Calen: called in BCLib
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent evt) {
//        evt.register(IFluidHandler.class);
        evt.register(IFluidHandlerAdv.class);
//        evt.register(ICraftingMachine.class);
//        evt.register(GenericInternalInventory.class);
        evt.register(IItemTransactor.class);
    }

//    private static <T> void registerAbstractCapability(Class<T> clazz) {
//        // By default storing and creating are illegal operations, as we don't necessarily have good default impl's
//        IStorage<T> ourStorage = new IStorage<T>() {
//            @Override
//            public Tag writeNBT(Capability<T> capability, T instance, Direction side) {
//                throw new IllegalStateException("You must provide your own implementations of " + clazz);
//            }
//
//            @Override
//            public void readNBT(Capability<T> capability, T instance, Direction side, Tag nbt) {
//                throw new IllegalStateException("You must provide your own implementations of " + clazz);
//            }
//        };
//        Callable<T> factory = () -> {
//            throw new IllegalStateException("You must provide your own instances of " + clazz);
//        };
//        CapabilityManager.INSTANCE.register(clazz, ourStorage, factory);
//    }

    @Nonnull
    private static <T> Capability<T> getCapNonNull(Capability<T> cap, Class<T> clazz) {
        if (cap == null) {
            throw new NullPointerException("The capability " + clazz + " was null!");
        }
        return cap;
    }
}
