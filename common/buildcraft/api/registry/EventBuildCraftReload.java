package buildcraft.api.registry;

import com.google.gson.GsonBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Set;

public abstract class EventBuildCraftReload extends Event
{

    /** The manager that is being reloaded. */
    public final IReloadableRegistryManager manager;

    /** A set of all {@link IReloadableRegistry}'s that are being reloaded. */
    public final Set<IReloadableRegistry<?>> reloadingRegistries;

    public EventBuildCraftReload(IReloadableRegistryManager manager, Set<IReloadableRegistry<?>> reloadingRegistries) {
        this.manager = manager;
        this.reloadingRegistries = reloadingRegistries;
    }

    /** Fired before all script based entries are registered, but before the previous recipes have been cleared. I'm not
     * sure how useful this actually is. */
    public static class BeforeClear extends EventBuildCraftReload {
        public BeforeClear(IReloadableRegistryManager manager,
            @Nullable Set<IReloadableRegistry<?>> reloadingRegistries) {
            super(manager, reloadingRegistries);
        }
    }

    /** Fired before all script based buildcraft reloadable entries will be registered. Use this to add your own
     * entries. */
    public static class PreLoad extends EventBuildCraftReload {
        public PreLoad(IReloadableRegistryManager manager, @Nullable Set<IReloadableRegistry<?>> reloadingRegistries) {
            super(manager, reloadingRegistries);
        }
    }

    /** Fired after {@link PreLoad}, but before scripts are loaded in order to register custom type adaptors for various
     * classes. BuildCraft itself adds adaptors for {@link ItemStack}, {@link Ingredient}, and {@link FluidStack} before
     * this event is fired. */
    public static class PopulateGson extends EventBuildCraftReload {

        public final GsonBuilder gsonBuilder;

        public PopulateGson(IReloadableRegistryManager manager,
            @Nullable Set<IReloadableRegistry<?>> reloadingRegistries, GsonBuilder gsonBuilder) {
            super(manager, reloadingRegistries);
            this.gsonBuilder = gsonBuilder;
        }
    }

    /** Fired when all script based buildcraft reloadable entries have been registered. Use this to modify existing
     * entries. */
    public static class PostLoad extends EventBuildCraftReload {
        public PostLoad(IReloadableRegistryManager manager, @Nullable Set<IReloadableRegistry<?>> reloadingRegistries) {
            super(manager, reloadingRegistries);
        }
    }

    /** Fired after {@link PostLoad}, when the {@link IReloadableRegistry#getReloadableEntryMap()} has been frozen until
     * the next reload. */
    public static class FinishLoad extends EventBuildCraftReload {
        public FinishLoad(IReloadableRegistryManager manager,
            @Nullable Set<IReloadableRegistry<?>> reloadingRegistries) {
            super(manager, reloadingRegistries);
        }
    }
}
