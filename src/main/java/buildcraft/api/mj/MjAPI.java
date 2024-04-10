package buildcraft.api.mj;

import buildcraft.api.BCModules;
import buildcraft.core.BCCore;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BCCore.MOD_ID)
public class MjAPI
{

    // ################################
    //
    // Useful constants (Public API)
    //
    // ################################

    /**
     * A single minecraft joule, in micro joules (the power system base unit)
     */
    public static final long ONE_MINECRAFT_JOULE = getMjValue();
    /**
     * The same as {@link #ONE_MINECRAFT_JOULE}, but a shorter field name
     */
    public static final long MJ = ONE_MINECRAFT_JOULE;

    /**
     * The decimal format used to display values of MJ to the player. Note that this
     */
    public static final DecimalFormat MJ_DISPLAY_FORMAT = new DecimalFormat("#,##0.##");

    public static IMjEffectManager EFFECT_MANAGER = NullaryEffectManager.INSTANCE;

    // ###############
    //
    // Helpful methods
    //
    // ###############

    /**
     * Formats a given MJ value to a player-oriented string. Note that this does not append "MJ" to the value.
     */
    public static String formatMj(long microMj)
    {
        return formatMjInternal(microMj / (double) MJ);
    }

    private static String formatMjInternal(double val)
    {
        return MJ_DISPLAY_FORMAT.format(val);
    }

    // ########################################
    //
    // Null based classes
    //
    // ########################################

    public enum NullaryEffectManager implements IMjEffectManager
    {
        INSTANCE;

        @Override
        public void createPowerLossEffect(Level world, Vec3 center, long microJoulesLost)
        {
        }

        @Override
        public void createPowerLossEffect(Level world, Vec3 center, Direction direction, long microJoulesLost)
        {
        }

        @Override
        public void createPowerLossEffect(Level world, Vec3 center, Vec3 direction, long microJoulesLost)
        {
        }
    }
    // @formatter:on

    // ###############
    //
    // Capabilities
    //
    // ###############

    @Nonnull
    public static final Capability<IMjConnector> CAP_CONNECTOR = CapabilityManager.get(new CapabilityToken<>()
    {
    });
    public static final ResourceLocation CAP_CONNECTOR_ID = new ResourceLocation(BCModules.CORE.getModId(), "mj_connector");

    @Nonnull
    public static final Capability<IMjReceiver> CAP_RECEIVER = CapabilityManager.get(new CapabilityToken<>()
    {
    });
    public static final ResourceLocation CAP_RECEIVER_ID = new ResourceLocation(BCModules.CORE.getModId(), "mj_receiver");

    @Nonnull
    public static final Capability<IMjRedstoneReceiver> CAP_REDSTONE_RECEIVER = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @Nonnull
    public static final Capability<IMjReadable> CAP_READABLE = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @Nonnull
    public static final Capability<IMjPassiveProvider> CAP_PASSIVE_PROVIDER = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event)
    {
        event.register(IMjConnector.class);
        event.register(IMjReceiver.class);
        event.register(IMjRedstoneReceiver.class);
        event.register(IMjReadable.class);
        event.register(IMjPassiveProvider.class);
    }

    // Calen 上面直接注册
//    static
//    {
//        CAP_CONNECTOR = CapabilitiesHelper.registerCapability(IMjConnector.class);
//        CAP_RECEIVER = CapabilitiesHelper.registerCapability(IMjReceiver.class);
//        CAP_REDSTONE_RECEIVER = CapabilitiesHelper.registerCapability(IMjRedstoneReceiver.class);
//        CAP_READABLE = CapabilitiesHelper.registerCapability(IMjReadable.class);
//        CAP_PASSIVE_PROVIDER = CapabilitiesHelper.registerCapability(IMjPassiveProvider.class);
//    }
//
//    public static void attachAbility(AttachCapabilitiesEvent<BlockEntity> event)
//    {
//        final BlockEntity be = event.getObject();
////        if (be instanceof TileDistiller_BC8||be instanceof TilePump)
////        {
////            if (!be.getCapability(MjAPI.CAP_RECEIVER).isPresent())
////            {
////                event.addCapability(MjAPI.CAP_RECEIVER_ID, new ICapabilityProvider()
////                {
////                    @NotNull
////                    @Override
////                    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
////                    {
////                        if (cap == MjAPI.CAP_RECEIVER)
////                        {
////                            return (LazyOptional<T>)be.getCapability(MjAPI.CAP_RECEIVER);
//////                            return LazyOptional.of(() -> ((IFluidHandlerAdv)((TileBC_Neptune) be).getCapability())).cast();
////                        }
////                        return LazyOptional.empty();
////                    }
////                });
////            }
////        }
//    }


    private static long getMjValue()
    {
        return 1_000_000L;
    }
}
