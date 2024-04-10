package buildcraft.api.tiles;

import buildcraft.lib.BCLib;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BCLib.MODID) // Calen add for reg caps
public class TilesAPI
{
    @Nonnull
    public static final Capability<IControllable> CAP_CONTROLLABLE = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @Nonnull
    public static final Capability<IHasWork> CAP_HAS_WORK = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @Nonnull
    public static final Capability<IHeatable> CAP_HEATABLE = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @Nonnull
    public static final Capability<ITileAreaProvider> CAP_TILE_AREA_PROVIDER = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    // Calen: 1.18.2直接用CapabilityManager.get获取?
//    static {
//        CAP_CONTROLLABLE = CapabilitiesHelper.registerCapability(IControllable.class);
//        CAP_HAS_WORK = CapabilitiesHelper.registerCapability(IHasWork.class);
//        CAP_HEATABLE = CapabilitiesHelper.registerCapability(IHeatable.class);
//        CAP_TILE_AREA_PROVIDER = CapabilitiesHelper.registerCapability(ITileAreaProvider.class);
//    }

    // Calen
    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event)
    {
        event.register(IControllable.class);
        event.register(IHasWork.class);
        event.register(IHeatable.class);
        event.register(ITileAreaProvider.class);
    }
}
