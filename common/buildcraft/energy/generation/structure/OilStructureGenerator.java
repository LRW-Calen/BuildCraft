package buildcraft.energy.generation.structure;

import buildcraft.api.core.BCDebugging;
import buildcraft.api.core.BCLog;
import buildcraft.core.BCCoreBlocks;
import buildcraft.energy.BCEnergyConfig;
import buildcraft.lib.misc.data.Box;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class OilStructureGenerator {
    /**
     * The distance that oil generation will be checked to see if their structures overlap with the currently
     * generating chunk. This should be large enough that all oil generation can fit inside this radius. If this number
     * is too big then oil generation will be slightly slower
     */
    public static final int MAX_CHUNK_RADIUS = 5;

    public static final boolean DEBUG_OILGEN_BASIC = BCDebugging.shouldDebugLog("energy.oilgen");
    public static final boolean DEBUG_OILGEN_ALL = BCDebugging.shouldDebugComplex("energy.oilgen");

    public enum GenType {
        LARGE,
        MEDIUM,
        LAKE,
        NONE
    }

    public static void generatePieces(
            StructurePiecesBuilder piecesBuilder,
//            PieceGenerator.Context<NoneFeatureConfiguration> context
            PieceGenerator.Context<OilFeatureConfiguration> context
    ) {
        int minHeight = context.heightAccessor().getMinBuildHeight();
        int maxHeight = context.heightAccessor().getMaxBuildHeight();
        ChunkPos chunkPos = context.chunkPos();
        int chunkX = chunkPos.x;
        int chunkZ = chunkPos.z;

        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        // Calen: moved to OilGenStructureFeature#checkLocation
////        for (int cdx = -MAX_CHUNK_RADIUS; cdx <= MAX_CHUNK_RADIUS; cdx++)
//        int cx = chunkX;
//        int cz = chunkZ;
//
//        // Chunk Rand in 1.18.2
//        WorldgenRandom rand = new WorldgenRandom(new LegacyRandomSource(MAGIC_GEN_NUMBER));
//        rand.setLargeFeatureSeed(context.seed(), cx, cz);
//        // shift to world coordinates
//        int xForGen = cx * 16 + 8 + rand.nextInt(16);
//        int zForGen = cz * 16 + 8 + rand.nextInt(16);
//        Biome biome = context.chunkGenerator().getNoiseBiome(
//                QuartPos.fromBlock(xForGen),
//                QuartPos.fromBlock(63), // Calen: 63?
//                QuartPos.fromBlock(zForGen)
//        ).value();
//        GenType type = getPieceTypeByRand(rand, biome, cx, cz, xForGen, zForGen, true);
        OilFeatureConfiguration.Info info = context.config().get(chunkPos);
        if (info == null) {
            BCLog.logger.error("[energy.oilgen] Tried to gen structure pieces in chunk [" + chunkPos + "], but found none prepared data for this chunk. Something works wrong.");
            return;
        }
        GenType type = info.type;
        WorldgenRandom rand = info.oilRand;
        int xForGen = info.xForGen;
        int zForGen = info.zForGen;
        // StructurePiece 要求的 Box
        BlockPos min = new BlockPos(x - 16 * MAX_CHUNK_RADIUS, minHeight, z - 16 * MAX_CHUNK_RADIUS);
        Box box = new Box(min, min.offset(2 * 16 * MAX_CHUNK_RADIUS, maxHeight - minHeight, 2 * 16 * MAX_CHUNK_RADIUS));

        OilStructure structure = createTotalStructure(type, rand, xForGen, zForGen, minHeight, maxHeight, box);
        // type == NONE -> null
        if (structure != null) {
            piecesBuilder.addPiece(structure);
        }
    }

    /**
     * Random number, used to differentiate generators
     */
    public static final long MAGIC_GEN_NUMBER = 0xD0_46_B4_E4_0C_7D_07_CFL;

    @Nonnull
    public static GenType getPieceTypeByRand(Random rand, Biome biome, int cx, int cz, int x, int z, boolean log) {
        // Do not generate oil in excluded biomes
        boolean isExcludedBiome = BCEnergyConfig.excludedBiomes.contains(biome.getRegistryName());
        if (isExcludedBiome == BCEnergyConfig.excludedBiomesIsBlackList) {
            if (DEBUG_OILGEN_BASIC & log) {
                BCLog.logger.info(
                        "[energy.oilgen] Not generating oil in chunk " + cx + ", " + cz
                                + " because the biome we found (" + biome.getRegistryName() + ") is disabled!"
                );
            }
            return GenType.NONE;
        }

        if (ForgeRegistries.BIOMES.tags().getTag(Tags.Biomes.IS_END).contains(biome) && (Math.abs(x) < 1200 || Math.abs(z) < 1200)) {
            if (DEBUG_OILGEN_BASIC & log) {
                BCLog.logger.info(
                        "[energy.oilgen] Not generating oil in chunk " + cx + ", " + cz
                                + " because it's the end biome and we're within 1200 blocks of the ender dragon fight"
                );
            }
            return GenType.NONE;
        }

        boolean oilBiome = BCEnergyConfig.surfaceDepositBiomes.contains(biome.getRegistryName());

        double bonus = oilBiome ? 3.0 : 1.0;
        bonus *= BCEnergyConfig.oilWellGenerationRate;
        if (BCEnergyConfig.excessiveBiomes.contains(biome.getRegistryName())) {
            bonus *= 30.0;
        }
        final GenType type;

        double nextDouble = rand.nextDouble();

        if (nextDouble <= BCEnergyConfig.largeOilGenProb * bonus) {
            // 0.04%
            type = GenType.LARGE;
        } else if (nextDouble <= BCEnergyConfig.mediumOilGenProb * bonus) {
            // 0.1%
            type = GenType.MEDIUM;
        } else if (oilBiome && nextDouble <= BCEnergyConfig.smallOilGenProb * bonus) {
            // 2%
            type = GenType.LAKE;
        } else {
            if (DEBUG_OILGEN_ALL & log) {
                BCLog.logger.info(
                        "[energy.oilgen] Not generating oil in chunk " + cx + ", " + cz
                                + " because none of the random numbers were above the thresholds for generation"
                );
            }
            type = GenType.NONE;
        }
        if (DEBUG_OILGEN_BASIC & log) {
            BCLog.logger.info(
                    "[energy.oilgen] Generating an oil well (" + type.name().toLowerCase(Locale.ROOT)
                            + ") in chunk " + cx + ", " + cz + " at " + x + ", " + z
            );
        }
        return type;
    }

    // 确定type 创建结构
    public static OilStructure createTotalStructure(final GenType type, Random rand, int x, int z, int worldBottomHeight, int worldTopHeight, Box box) {
        List<OilStructurePiece> structures = new ArrayList<>();
        final int lakeRadius;
        final int tendrilRadius;
        switch (type) {
            case LARGE:
                lakeRadius = 4;
                tendrilRadius = 25 + rand.nextInt(20);
                break;
            case MEDIUM:
                lakeRadius = 2;
                tendrilRadius = 5 + rand.nextInt(10);
                break;
            case LAKE:
                lakeRadius = 6;
                tendrilRadius = 25 + rand.nextInt(20);
                break;
            default:
                return null;
        }
        structures.add(OilStructurePiece.createTendril(new BlockPos(x, 62, z), lakeRadius, tendrilRadius, rand));
//        structures.add(OilStructurePiece.createTendril(new BlockPos(x, 63, z), lakeRadius, tendrilRadius, rand));

        int maxHeight, minHeight;

        if (type != GenType.LAKE) {
            // Generate a spherical cave deposit
            int wellY = worldBottomHeight + 20 + rand.nextInt(10);

            int radius;
            if (type == GenType.LARGE) {
                radius = 8 + rand.nextInt(9);
            } else {
                radius = 4 + rand.nextInt(4);
            }

            structures.add(OilStructurePiece.createSphere(new BlockPos(x, wellY, z), radius));

            // Generate a spout
            if (BCEnergyConfig.enableOilSpouts) {
                if (type == GenType.LARGE) {
                    minHeight = BCEnergyConfig.largeSpoutMinHeight;
                    maxHeight = BCEnergyConfig.largeSpoutMaxHeight;
                    radius = 1;
                } else {
                    minHeight = BCEnergyConfig.smallSpoutMinHeight;
                    maxHeight = BCEnergyConfig.smallSpoutMaxHeight;
                    radius = 0;
                }
                final int height;
                if (maxHeight == minHeight) {
                    height = maxHeight;
                } else {
                    if (maxHeight < minHeight) {
                        int t = maxHeight;
                        maxHeight = minHeight;
                        minHeight = t;
                    }
                    height = minHeight + rand.nextInt(maxHeight - minHeight);
                }
                structures.add(OilStructurePiece.createSpout(new BlockPos(x, wellY, z), height, radius));
            }

            // Generate a spring at the very bottom
            if (type == GenType.LARGE) {
                structures.add(OilStructurePiece.createTube(new BlockPos(x, worldBottomHeight + 2, z), wellY - worldBottomHeight + 1, radius, Direction.Axis.Y));
//                if (BCCoreBlocks.spring != null)
                if (BCCoreBlocks.springOil != null) {
                    structures.add(OilStructurePiece.createSpring(new BlockPos(x, worldBottomHeight + 1, z)));
                }
            }
        }
        return new OilStructure(box, structures);
    }


}
