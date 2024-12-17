package buildcraft.builders;

import buildcraft.api.schematics.ISchematicBlock;
import buildcraft.api.schematics.SchematicBlockContext;
import buildcraft.api.schematics.SchematicBlockFactoryRegistry;
import buildcraft.api.schematics.SchematicEntityFactoryRegistry;
import buildcraft.builders.snapshot.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class BCBuildersSchematics {
    public static void preInit() {
        registerSchematicFactory("air", 0, SchematicBlockAir::predicate, SchematicBlockAir::new);
        // Calen debug
        if (!SchematicBlockFactoryRegistry.getFactoriesSynchronized().stream().anyMatch(f -> f.name.getPath().equals("air"))) {
            throw new RuntimeException("[builders.schematics.debug] Added Schematic Factory [air], but not found. Something wrong...");
        }
        registerSchematicFactory("default", 100, SchematicBlockDefault::predicate, SchematicBlockDefault::new);
        registerSchematicFactory("fluid", 200, SchematicBlockFluid::predicate, SchematicBlockFluid::new);

        registerSchematicFactory("banner", 300, c -> c.block instanceof BannerBlock, BCBuildersSchematics::getBanner);
        registerSchematicFactory("vine", 300, c -> c.block instanceof VineBlock, BCBuildersSchematics::getVine);
        registerSchematicFactory("bed", 300, c -> c.block instanceof BedBlock, SchematicBlockBed::new);

        SchematicEntityFactoryRegistry.registerFactory("default", 100, SchematicEntityDefault::predicate,
                SchematicEntityDefault::new);
    }

    private static synchronized <S extends ISchematicBlock> void registerSchematicFactory(String name, int priority, Predicate<SchematicBlockContext> predicate, Supplier<S> supplier) {
        SchematicBlockFactoryRegistry.registerFactory(name, priority, predicate, supplier);
    }

    private static SchematicBlockDefault getBanner() {
        // Calen: moved to SchematicBlockBanner#computeRequiredItems
//        return new SchematicBlockDefault() {
//            @Nonnull
//            @Override
//            public List<ItemStack> computeRequiredItems() {
//                return Collections.singletonList(ItemBanner.makeBanner(
//                        EnumDyeColor.byDyeDamage(tileNbt.getInteger("Base")), tileNbt.getTagList("Patterns", 10)));
//            }
//        };
        return new SchematicBlockBanner();
    }

    private static SchematicBlockDefault getVine() {
        return new SchematicBlockDefault() {
            @Override
            public boolean isReadyToBuild(Level world, BlockPos blockPos) {
                return super.isReadyToBuild(world, blockPos)
                        && (world.getBlockState(blockPos.above()).getBlock() instanceof VineBlock
                        || StreamSupport.stream(Direction.Plane.HORIZONTAL.spliterator(), false).map(blockPos::relative)
                        .map(world::getBlockState)
//                        .anyMatch(state -> state.isFullCube() && state.getMaterial().blocksMovement()));
                        .anyMatch(state -> state.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO) == Shapes.block() && state.blocksMotion()));
            }
        };
    }
}
