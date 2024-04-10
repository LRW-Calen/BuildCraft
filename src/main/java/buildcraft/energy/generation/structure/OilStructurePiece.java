package buildcraft.energy.generation.structure;

import buildcraft.api.core.BCLog;
import buildcraft.energy.tile.TileSpringOil;
import buildcraft.core.BCCoreBlocks;
import buildcraft.energy.BCEnergyFluids;
import buildcraft.lib.BCLib;
import buildcraft.lib.misc.BlockUtil;
import buildcraft.lib.misc.VecUtil;
import buildcraft.lib.misc.data.Box;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;

import java.util.Random;
import java.util.function.Predicate;

public abstract class OilStructurePiece
{
    public final Box box;
    public final ReplaceType replaceType;

    public OilStructurePiece(Box containingBox, ReplaceType replaceType)
    {
        this.box = containingBox;
        this.replaceType = replaceType;
    }

    public final void generate(LevelAccessor world, Box within)
    {
        // 1.12.2 每个区块生成时用相同的rand生成一次 通过within限制只实际修改当前区块
        // 1.18.2 用NBT保存 分多次生成 通过BB限定可修改范围 每次生成区域内的部分 好像不用考虑被削的问题
        Box intersect = box.getIntersect(within);
        if (intersect != null)
        {
            generateWithin(world, intersect);
        }
    }

    /**
     * Generates this structure in the world, but only between the given coordinates.
     */
    protected abstract void generateWithin(LevelAccessor world, Box intersect);

    /**
     * @return The number of oil blocks that this structure will set. Note that this is called *after*
     * {@link #generateWithin(LevelAccessor, Box)}, by the Spring type, so this can store the number set.
     */
    public abstract int countOilBlocks();

    public void setOilIfCanReplace(LevelAccessor world, BlockPos pos)
    {
        if (canReplaceForOil(world, pos))
        {
            setOil(world, pos);
        }
    }

    public boolean canReplaceForOil(LevelAccessor world, BlockPos pos)
    {
        return replaceType.canReplace(world, pos);
    }

    // Calen: localize the oil instead of call the same methods everytime
    private static final BlockState OIL_BLOCK_STATE = BCEnergyFluids.crudeOil[0].get().getSource().defaultFluidState().createLegacyBlock();
    private static final Fluid OIL_FLUID = BCEnergyFluids.crudeOil[0].get().getSource().defaultFluidState().getType();

    public static void setOil(LevelAccessor world, BlockPos pos)
    {
        world.setBlock(pos, OIL_BLOCK_STATE, Block.UPDATE_CLIENTS);
        // Calen: SpringFeature:71 scheduleTick后才可以流动
        world.scheduleTick(pos, OIL_FLUID, 0);

    }

    public enum ReplaceType
    {
        ALWAYS
                {
                    @Override
                    public boolean canReplace(LevelAccessor world, BlockPos pos)
                    {
                        return true;
                    }
                },
        IS_FOR_LAKE
                {
                    @Override
                    public boolean canReplace(LevelAccessor world, BlockPos pos)
                    {
                        return ALWAYS.canReplace(world, pos);
                    }
                };

        public abstract boolean canReplace(LevelAccessor world, BlockPos pos);
    }

    public static class GenByPredicate extends OilStructurePiece
    {
        public final Predicate<BlockPos> predicate;
        // Calen: Only for NBT Serialize
        public final Object[] predicateArgs;

        // public GenByPredicate(Box containingBox, ReplaceType replaceType, Predicate<BlockPos> predicate)
        public GenByPredicate(Box containingBox, ReplaceType replaceType, Object... predicateArgs)
        {
            super(containingBox, replaceType);
            this.predicateArgs = predicateArgs;
            Predicate<BlockPos> tester;
            if (predicateArgs.length == 2)
            {
                tester = p -> p.distSqr((BlockPos) predicateArgs[0]) <= (double) predicateArgs[1];
            }
            else if (predicateArgs.length == 4)
            {
                tester = p -> VecUtil.replaceValue(p, (Axis) predicateArgs[0], (int) predicateArgs[1]).distSqr((BlockPos) predicateArgs[2]) <= (double) predicateArgs[3];
            }
            else
            {
                throw new RuntimeException("Unexpected Predict Type! Predict Types are Limited for NBT Serialize");
            }
            this.predicate = tester;
        }

        @Override
        protected void generateWithin(LevelAccessor world, Box intersect)
        {
            for (BlockPos pos : BlockPos.betweenClosed(intersect.min(), intersect.max()))
            {
                if (predicate.test(pos))
                {
                    setOilIfCanReplace(world, pos);
                }
            }
        }

        @Override
        public int countOilBlocks()
        {
            int count = 0;
            for (BlockPos pos : BlockPos.betweenClosed(box.min(), box.max()))
            {
                if (predicate.test(pos))
                {
                    count++;
                }
            }
            return count;
        }
    }

    public static class FlatPattern extends OilStructurePiece
    {
        public final boolean[][] pattern;
        public final int depth;

        public FlatPattern(Box containingBox, ReplaceType replaceType, boolean[][] pattern, int depth)
        {
            super(containingBox, replaceType);
            this.pattern = pattern;
            this.depth = depth;
        }

        public static FlatPattern create(BlockPos start, ReplaceType replaceType, boolean[][] pattern, int depth)
        {
            BlockPos min = start.offset(0, 1 - depth, 0);
            BlockPos max = start.offset(pattern.length - 1, 0, pattern.length == 0 ? 0 : pattern[0].length - 1);
            Box box = new Box(min, max);
            return new FlatPattern(box, replaceType, pattern, depth);
        }

        @Override
        protected void generateWithin(LevelAccessor world, Box intersect)
        {
            BlockPos start = box.min();
            for (BlockPos pos : BlockPos.betweenClosed(intersect.min(), intersect.max()))
            {
                int x = pos.getX() - start.getX();
                int z = pos.getZ() - start.getZ();
                if (pattern[x][z])
                {
                    setOilIfCanReplace(world, pos);
                }
            }
        }

        @Override
        public int countOilBlocks()
        {
            int count = 0;
            for (int x = 0; x < pattern.length; x++)
            {
                for (int z = 0; z < pattern[x].length; z++)
                {
                    if (pattern[x][z])
                    {
                        count++;
                    }
                }
            }
            return count * depth;
        }
    }

    public static class PatternTerrainHeight extends OilStructurePiece
    {
        public final boolean[][] pattern;
        public final int depth;

        public PatternTerrainHeight(Box containingBox, ReplaceType replaceType, boolean[][] pattern, int depth)
        {
            super(containingBox, replaceType);
            this.pattern = pattern;
            this.depth = depth;
        }

        public static PatternTerrainHeight create(BlockPos start, ReplaceType replaceType, boolean[][] pattern,
                                                  int depth)
        {
            BlockPos min = VecUtil.replaceValue(start, Axis.Y, 1);
            BlockPos max = min.offset(pattern.length - 1, 255, pattern.length == 0 ? 0 : pattern[0].length - 1);
            Box box = new Box(min, max);
            return new PatternTerrainHeight(box, replaceType, pattern, depth);
        }

        @Override
        protected void generateWithin(LevelAccessor world, Box intersect)
        {
            for (int x = intersect.min().getX(); x <= intersect.max().getX(); x++)
            {
                int px = x - box.min().getX();

                for (int z = intersect.min().getZ(); z <= intersect.max().getZ(); z++)
                {
                    int pz = z - box.min().getZ();

                    if (pattern[px][pz])
                    {
//                        BlockPos upper = world.getHeight(new BlockPos(x, 0, z)).down();
//                        BlockPos upper = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(x, 0, z)).below();
                        BlockPos upper = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, new BlockPos(x, 0, z)).below();
                        if (canReplaceForOil(world, upper))
                        {
                            for (int y = 0; y < 5; y++)
                            {
                                world.setBlock(upper.above(y), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                            }
                            for (int y = 0; y < depth; y++)
                            {
                                setOilIfCanReplace(world, upper.below(y));
                            }
                        }
                    }
                }
            }
        }

        @Override
        public int countOilBlocks()
        {
            int count = 0;
            for (int x = 0; x < pattern.length; x++)
            {
                for (int z = 0; z < pattern[x].length; z++)
                {
                    if (pattern[x][z])
                    {
                        count++;
                    }
                }
            }
            return count * depth;
        }
    }

    public static class Spout extends OilStructurePiece
    {
        // FIXME (AlexIIL): This won't support cubic chunks - we'll have to do this differently in compat
        // TODO: Use a terrain generator from mc terrain generation to get the height of the world
        // A hook will go in compat for help when using cubic chunks or a different type of terrain generator
        public final BlockPos start;
        public final int radius;
        public final int height;
        private int count = 0;

        public Spout(BlockPos start, ReplaceType replaceType, int radius, int height)
        {
            super(createBox(start), replaceType);
            this.start = start;
            this.radius = radius;
            this.height = height;
        }

        private static Box createBox(BlockPos start)
        {
            // Only a block 1 x 256 x 1 -- that way we area only called once.
            // FIXME: This 256 will need to be rethought for cubic chunk support
            return new Box(start, VecUtil.replaceValue(start, Axis.Y, 256));
        }

        @Override
        protected void generateWithin(LevelAccessor world, Box intersect)
        {
            count = 0;
            int segment = world.getChunk(start).getHighestSectionPosition();
            BlockPos worldTop = new BlockPos(start.getX(), segment + 16, start.getZ());
            for (int y = segment; y >= start.getY(); y--)
            {
                worldTop = worldTop.below();
                BlockState state = world.getBlockState(worldTop);
                if (world.isEmptyBlock(worldTop))
                {
                    continue;
                }
                if (BlockUtil.getFluidWithFlowing(state.getBlock()) != null)
                {
                    break;
                }
                if (state.getMaterial().blocksMotion())
                {
                    break;
                }
            }
            OilStructurePiece tubeY = OilStructurePiece.createTube(start, worldTop.getY() - start.getY(), radius, Axis.Y);
            tubeY.generate(world, tubeY.box);
            count += tubeY.countOilBlocks();
            BlockPos base = worldTop;
            for (int r = radius; r >= 0; r--)
            {
                // BCLog.logger.info(" - " + base + " = " + r);
                OilStructurePiece struct = OilStructurePiece.createTube(base, height, r, Axis.Y);
                struct.generate(world, struct.box);
                base = base.offset(0, height, 0);
                count += struct.countOilBlocks();
            }
        }

        @Override
        public int countOilBlocks()
        {
            if (count == 0)
            {
                throw new IllegalStateException("Called countOilBlocks before calling generateWithin!");
            }
            return count;
        }
    }

    public static class Spring extends OilStructurePiece
    {
        public final BlockPos pos;

        public Spring(BlockPos pos)
        {
            super(new Box(pos, pos), ReplaceType.ALWAYS);
            this.pos = pos;
        }

        @Override
        protected void generateWithin(LevelAccessor world, Box intersect)
        {
            // NO-OP (this one is called separately)
        }

        @Override
        public int countOilBlocks()
        {
            return 0;
        }

        public void generate(LevelAccessor world, int count)
        {
            BlockState state = BCCoreBlocks.springOil.get().defaultBlockState();
//            state = state.setValue(BuildCraftProperties.SPRING_TYPE, EnumSpring.OIL);
            world.setBlock(pos, state, Block.UPDATE_ALL);
            BlockEntity tile = world.getBlockEntity(pos);
            TileSpringOil spring;
            if (tile instanceof TileSpringOil)
            {
                spring = (TileSpringOil) tile;
            }
            else
            {
                BCLog.logger.warn("[energy.gen.oil] Setting the blockstate didn't also set the tile at [" + pos + "], something goes wrong...");
                spring = new TileSpringOil(pos, BCCoreBlocks.springOil.get().defaultBlockState());
//                spring.setLevel(world);
//                world.setBlockEntity(spring);
            }
            spring.totalSources = count;
            if (BCLib.DEV)
            {
                BCLog.logger.info("[energy.gen.oil] Generated TileSpringOil as " + System.identityHashCode(tile));
            }
        }
    }

    public static OilStructurePiece createTube(BlockPos center, int length, int radius, Axis axis)
    {
        int valForAxis = VecUtil.getValue(center, axis);
        BlockPos min = VecUtil.replaceValue(center.offset(-radius, -radius, -radius), axis, valForAxis);
        BlockPos max = VecUtil.replaceValue(center.offset(radius, radius, radius), axis, valForAxis + length);
        double radiusSq = radius * radius;
        int toReplace = valForAxis;
//        Predicate<BlockPos> tester = p -> VecUtil.replaceValue(p, axis, toReplace).distSqr(center) <= radiusSq;
//        return new GenByPredicate(new Box(min, max), ReplaceType.ALWAYS, tester);
        return new GenByPredicate(new Box(min, max), ReplaceType.ALWAYS, new Object[]{axis, toReplace, center, radiusSq});
    }

    public static OilStructurePiece createSpout(BlockPos start, int height, int radius)
    {
        return new OilStructurePiece.Spout(start, OilStructurePiece.ReplaceType.ALWAYS, radius, height);
    }


    public static OilStructurePiece createSpring(BlockPos at)
    {
        return new OilStructurePiece.Spring(at);
    }

    public static OilStructurePiece createSphere(BlockPos center, int radius)
    {
        Box box = new Box(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius));
        double radiusSq = radius * radius + 0.01;
//        Predicate<BlockPos> tester = p -> p.distSqr(center) <= radiusSq;
//        return new OilStructurePiece.GenByPredicate(box, OilStructurePiece.ReplaceType.ALWAYS, tester);
        return new OilStructurePiece.GenByPredicate(box, OilStructurePiece.ReplaceType.ALWAYS, new Object[]{center, radiusSq});
    }

    public static OilStructurePiece createTendril(BlockPos center, int lakeRadius, int radius, Random rand)
    {
        BlockPos start = center.offset(-radius, 0, -radius);
        int diameter = radius * 2 + 1;
        boolean[][] pattern = new boolean[diameter][diameter];

        int x = radius;
        int z = radius;
        for (int dx = -lakeRadius; dx <= lakeRadius; dx++)
        {
            for (int dz = -lakeRadius; dz <= lakeRadius; dz++)
            {
                pattern[x + dx][z + dz] = dx * dx + dz * dz <= lakeRadius * lakeRadius;
            }
        }

        for (int w = 1; w < radius; w++)
        {
            float proba = (float) (radius - w + 4) / (float) (radius + 4);

            fillPatternIfProba(rand, proba, x, z + w, pattern);
            fillPatternIfProba(rand, proba, x, z - w, pattern);
            fillPatternIfProba(rand, proba, x + w, z, pattern);
            fillPatternIfProba(rand, proba, x - w, z, pattern);

            for (int i = 1; i <= w; i++)
            {
                fillPatternIfProba(rand, proba, x + i, z + w, pattern);
                fillPatternIfProba(rand, proba, x + i, z - w, pattern);
                fillPatternIfProba(rand, proba, x + w, z + i, pattern);
                fillPatternIfProba(rand, proba, x - w, z + i, pattern);

                fillPatternIfProba(rand, proba, x - i, z + w, pattern);
                fillPatternIfProba(rand, proba, x - i, z - w, pattern);
                fillPatternIfProba(rand, proba, x + w, z - i, pattern);
                fillPatternIfProba(rand, proba, x - w, z - i, pattern);
            }
        }

        int depth = rand.nextDouble() < 0.5 ? 1 : 2;
        return OilStructurePiece.PatternTerrainHeight.create(start, OilStructurePiece.ReplaceType.IS_FOR_LAKE, pattern, depth);
    }

    private static void fillPatternIfProba(Random rand, float proba, int x, int z, boolean[][] pattern)
    {
        if (rand.nextFloat() <= proba)
        {
            pattern[x][z] = isSet(pattern, x, z - 1) | isSet(pattern, x, z + 1) //
                    | isSet(pattern, x - 1, z) | isSet(pattern, x + 1, z);
        }
    }

    private static boolean isSet(boolean[][] pattern, int x, int z)
    {
        if (x < 0 || x >= pattern.length) return false;
        if (z < 0 || z >= pattern[x].length) return false;
        return pattern[x][z];
    }
}
