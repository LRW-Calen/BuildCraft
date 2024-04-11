package buildcraft.api.schematics;

import buildcraft.api.core.BuildCraftAPI;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SchematicBlockFactoryRegistry {
//    private static final Set<SchematicBlockFactory<?>> FACTORIES = new TreeSet<>();
    private static final Set<SchematicBlockFactory<?>> FACTORIES = new ConcurrentSkipListSet<>();

    // Calen thread safety: sometimes "air" not registered just after BCBuildersSchematics#preInit:registerSchematicFactory("air", 0, SchematicBlockAir::predicate, SchematicBlockAir::new)
    public synchronized static Set<SchematicBlockFactory<?>> getFactoriesSynchronized()
    {
        return FACTORIES;
    }

    public static <S extends ISchematicBlock> void registerFactory(String name,
                                                                   int priority,
                                                                   Predicate<SchematicBlockContext> predicate,
                                                                   Supplier<S> supplier) {
//        FACTORIES.add(new SchematicBlockFactory<>(
        getFactoriesSynchronized().add(new SchematicBlockFactory<>(
            BuildCraftAPI.nameToResourceLocation(name),
            priority,
            predicate,
            supplier
        ));
    }

    public static <S extends ISchematicBlock> void registerFactory(String name,
                                                                   int priority,
                                                                   List<Block> blocks,
                                                                   Supplier<S> supplier) {
        registerFactory(
            name,
            priority,
            context -> blocks.contains(context.block),
            supplier
        );
    }

    public static List<SchematicBlockFactory<?>> getFactories() {
//        return ImmutableList.copyOf(FACTORIES);
        return ImmutableList.copyOf(getFactoriesSynchronized());
    }

    @Nonnull
    public static <S extends ISchematicBlock> SchematicBlockFactory<S> getFactoryByInstance(S instance) {
        // noinspection unchecked
//        return (SchematicBlockFactory<S>) FACTORIES.stream()
        return (SchematicBlockFactory<S>) getFactoriesSynchronized().stream()
            .filter(schematicBlockFactory -> schematicBlockFactory.clazz == instance.getClass())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Didn't find a factory for " + instance.getClass()));
    }

    @Nullable
    public static SchematicBlockFactory<?> getFactoryByName(ResourceLocation name) {
//        return FACTORIES.stream()
        return getFactoriesSynchronized().stream()
            .filter(schematicBlockFactory -> schematicBlockFactory.name.equals(name))
            .findFirst()
            .orElse(null);
    }
}
