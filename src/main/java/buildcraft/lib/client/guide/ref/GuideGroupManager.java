package buildcraft.lib.client.guide.ref;

import buildcraft.api.statements.IStatement;
import buildcraft.builders.BCBuildersBlocks;
import buildcraft.core.BCCoreBlocks;
import buildcraft.core.BCCoreItems;
import buildcraft.energy.BCEnergyBlocks;
import buildcraft.factory.BCFactoryBlocks;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.lib.client.guide.entry.*;
import buildcraft.silicon.BCSiliconItems;
import buildcraft.transport.BCTransportItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class GuideGroupManager
{
    public static final List<PageValueType<?>> knownTypes = new ArrayList<>();
    public static final Map<ResourceLocation, GuideGroupSet> sets = new HashMap<>();

    private static final Map<Class<?>, PageValueType<?>> knownClasses = new WeakHashMap<>();
    private static final Map<Class<?>, Function<Object, PageValue<?>>> transformers = new WeakHashMap<>();

    static
    {
        addValidClass(ItemStackValueFilter.class, PageEntryItemStack.INSTANCE);
        addValidClass(IStatement.class, PageEntryStatement.INSTANCE);
        addTransformer(ItemStack.class, ItemStackValueFilter.class, ItemStackValueFilter::new);
        addTransformer(Item.class, ItemStack.class, ItemStack::new);
        addTransformer(Block.class, ItemStack.class, ItemStack::new);

        temp();
    }

    private static void temp()
    {
//        addEntries("buildcraftcore", "pipe_power_providers",
//                BCCoreItems.Silicon.PLUG_PULSAR,
//                BCCoreItems.Transport.PLUG_POWER_ADAPTOR,
//                new ItemStack(BCCoreBlocks.Core.ENGINE),
//                new ItemStack(BCCoreBlocks.Core.ENGINE, 1, 1),
//                new ItemStack(BCCoreBlocks.Core.ENGINE, 1, 2))//
//                .addKeyArray(
//                        BCCoreItems.Transport.PIPE_WOOD_ITEM,
//                        BCCoreItems.Transport.PIPE_DIAMOND_WOOD_ITEM,
//                        BCCoreItems.Transport.PIPE_EMZULI_ITEM, BCCoreItems.Transport.PIPE_WOOD_FLUID,
//                        BCCoreItems.Transport.PIPE_DIAMOND_WOOD_FLUID
//                );
        addEntries("buildcraft", "pipe_power_providers",
                BCSiliconItems.plugPulsar.get(),
                BCTransportItems.plugPowerAdaptor.get(),
                new ItemStack(BCCoreBlocks.engineWood.get()),
                new ItemStack(BCEnergyBlocks.engineStone.get(), 1),
                new ItemStack(BCEnergyBlocks.engineIron.get(), 1))//
                .addKeyArray(
                        (Item) BCTransportItems.pipeItemWood.get(null).get(),
                        (Item) BCTransportItems.pipeItemDiaWood.get(null).get(),
                        (Item) BCTransportItems.pipeItemEmzuli.get(null).get(),
                        (Item) BCTransportItems.pipeFluidWood.get(null).get(),
                        (Item) BCTransportItems.pipeFluidDiaWood.get(null).get()
                );
//        addEntries("buildcraftcore", "full_power_providers",
//                new ItemStack(BCCoreBlocks.Core.ENGINE, 1, 1),
//                new ItemStack(BCCoreBlocks.Core.ENGINE, 1, 2))
//                .addKeyArray(
//                        BCCoreBlocks.Builders.BUILDER, BCCoreBlocks.Builders.FILLER,
//                        BCCoreBlocks.Builders.QUARRY,
//                        BCCoreBlocks.Factory.DISTILLER,
//                        BCCoreBlocks.Factory.MINING_WELL,
//                        BCCoreBlocks.Factory.PUMP,
//                        BCCoreBlocks.Silicon.LASER
//                );
        addEntries("buildcraft", "full_power_providers",
                new ItemStack(BCEnergyBlocks.engineStone.get(), 1),
                new ItemStack(BCEnergyBlocks.engineIron.get(), 1))
                .addKeyArray(
                        BCBuildersBlocks.builder.get(),
                        BCBuildersBlocks.filler.get(),
                        BCBuildersBlocks.quarry.get(),
                        BCFactoryBlocks.distiller.get(),
                        BCFactoryBlocks.miningWell.get(),
                        BCFactoryBlocks.pump.get(),
                        BCSiliconBlocks.laser.get()
                );
//        addEntries("buildcraft", "laser_power_providers",
//                BCSiliconBlocks.laser)
//                .addKeyArray(
//                        BCCoreBlocks.Silicon.ADVANCED_CRAFTING_TABLE,
//                        BCCoreBlocks.Silicon.ASSEMBLY_TABLE,
//                        BCCoreBlocks.Silicon.INTEGRATION_TABLE
//                );
        addEntries("buildcraft", "laser_power_providers",
                BCSiliconBlocks.laser.get())
                .addKeyArray(
                        BCSiliconBlocks.advancedCraftingTable.get(),
                        BCSiliconBlocks.assemblyTable.get(),
                        BCSiliconBlocks.integrationTable.get()
                );
//        addEntries("buildcraftcore", "area_markers",
//                BCCoreBlocks.Core.MARKER_VOLUME,
//                BCCoreItems.Core.VOLUME_BOX)
//                .addKeyArray(BCCoreBlocks.Builders.QUARRY,
//                        BCCoreBlocks.Builders.ARCHITECT,
//                        BCCoreBlocks.Builders.FILLER
//                );
        addEntries("buildcraft", "area_markers",
                BCCoreBlocks.markerVolume.get(),
                BCCoreItems.volumeBox.get())
                .addKeyArray(BCBuildersBlocks.quarry.get(),
                        BCBuildersBlocks.architect.get(),
                        BCBuildersBlocks.filler.get()
                );
    }

    // Known types

    public static <F, T> void addTransformer(Class<F> fromClass, Class<T> toClass, Function<F, T> transform)
    {
        if (isValidClass(fromClass))
        {
            throw new IllegalArgumentException("You cannot register a transformer from an already-registered class!");
        }
        PageValueType<?> destType = getEntryType(toClass);
        if (destType == null)
        {
            // Function<T, PageValue<Dest>> where Dest is presumed to be a valid type
            Function<Object, PageValue<?>> destTransform = getTransform(toClass);
            if (destTransform != null)
            {
                Function<Object, PageValue<?>> realTransform = o ->
                {
                    F from = fromClass.cast(o);
                    T to = transform.apply(from);
                    return destTransform.apply(to);
                };
                transformers.put(fromClass, realTransform);

                return;
            }
            throw new IllegalArgumentException("You cannot register a transformer to an unregistered class!");
        }
        Function<Object, PageValue<?>> realTransform = o ->
        {
            F from = fromClass.cast(o);
            T to = transform.apply(from);
            return destType.wrap(to);
        };
        transformers.put(fromClass, realTransform);
    }

    public static <T> void addValidClass(Class<T> clazz, PageValueType<T> type)
    {
        if (clazz.isArray())
        {
            throw new IllegalArgumentException("Arrays are never valid!");
        }
        knownClasses.put(clazz, type);
        knownTypes.add(type);
    }

    /**
     * This checks to see if then given object is valid. There are two types of validity:
     * <ul>
     * <li>If the object is of one of the registered classes in {@link GuideGroupManager#knownClasses}</li>
     * <li>If the object is not null or is an invalid value in some other way.</li>
     * </ul>
     * This will throw an exception if the value is not of a registered class, and return false if it is an invalid
     * value in some other way (for example if it is null).
     */
    static boolean isValidObject(Object value)
    {
        if (value == null)
        {
            return false;
        }

        return isValidClass(value.getClass());
    }

    public static PageValue<?> toPageValue(Object value)
    {
        if (value == null)
        {
            return null;
        }
        if (value instanceof PageValue)
        {
            return (PageValue<?>) value;
        }
        PageValueType<?> entryType = getEntryType(value.getClass());
        if (entryType != null)
        {
            return entryType.wrap(value);
        }
        Function<Object, PageValue<?>> transform = getTransform(value.getClass());
        if (transform != null)
        {
            return transform.apply(value);
        }
        throw new IllegalArgumentException("Unknown " + value.getClass()
                + " - is this a programming mistake, or have you forgotton to register the class as valid?");
    }

    private static boolean isValidClass(Class<?> clazz)
    {
        return getEntryType(clazz) != null;
    }

    @Nullable
    private static PageValueType<?> getEntryType(Class<?> clazz)
    {
        if (knownClasses.containsKey(clazz))
        {
            return knownClasses.get(clazz);
        }
        PageValueType<?> type = null;
        if (!clazz.isArray())
        {
            search:
            {
                Class<?> superClazz = clazz.getSuperclass();
                if (superClazz != null)
                {
                    type = getEntryType(superClazz);
                    if (type != null)
                    {
                        break search;
                    }
                }
                for (Class<?> cls : clazz.getInterfaces())
                {
                    type = getEntryType(cls);
                    if (type != null)
                    {
                        break search;
                    }
                }
            }
            knownClasses.put(clazz, type);
        }
        return type;
    }

    private static Function<Object, PageValue<?>> getTransform(Class<? extends Object> clazz)
    {
        Function<Object, PageValue<?>> func = transformers.get(clazz);
        if (func != null)
        {
            return func;
        }
        if (!clazz.isArray())
        {
            search:
            {
                Class<?> superClazz = clazz.getSuperclass();
                if (superClazz != null)
                {
                    func = getTransform(superClazz);
                    if (func != null)
                    {
                        break search;
                    }
                }
                for (Class<?> cls : clazz.getInterfaces())
                {
                    func = getTransform(cls);
                    if (func != null)
                    {
                        break search;
                    }
                }
            }
            transformers.put(clazz, func);
        }
        return func;
    }

    // Internals

    @Nullable
    public static GuideGroupSet get(ResourceLocation group)
    {
        return sets.get(group);
    }

    @Nullable
    public static GuideGroupSet get(String domain, String group)
    {
        return get(new ResourceLocation(domain, group));
    }

    public static GuideGroupSet getOrCreate(String domain, String group)
    {
        return sets.computeIfAbsent(new ResourceLocation(domain, group), GuideGroupSet::new);
    }

    // Basic adders

    public static GuideGroupSet addEntry(String domain, String group, Object value)
    {
        return getOrCreate(domain, group).addSingle(value);
    }

    public static GuideGroupSet addEntries(String domain, String group, Object... values)
    {
        return getOrCreate(domain, group).addArray(values);
    }

    public static GuideGroupSet addEntries(String domain, String group, Collection<Object> values)
    {
        return getOrCreate(domain, group).addCollection(values);
    }

    public static GuideGroupSet addKey(String domain, String group, Object value)
    {
        return getOrCreate(domain, group).addKey(value);
    }

    public static GuideGroupSet addKeys(String domain, String group, Object... values)
    {
        return getOrCreate(domain, group).addKeyArray(values);
    }

    public static GuideGroupSet addKeys(String domain, String group, Collection<Object> values)
    {
        return getOrCreate(domain, group).addKeyCollection(values);
    }
}
