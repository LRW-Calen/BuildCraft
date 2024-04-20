package buildcraft.energy.generation.structure;

import buildcraft.energy.generation.BCWorldGenNames;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.List;

// Calen: mc 1.18.2 StructureFeature.class
public class EnergyStructureFeatureRegistry {

    //    public static final StructureFeature<NoneFeatureConfiguration> OIL_WELL =
    public static final StructureFeature<OilFeatureConfiguration> OIL_WELL =
            new OilGenStructureFeature(
//                    NoneFeatureConfiguration.CODEC
                    OilFeatureConfiguration.CODEC
            );

    public static final ResourceKey<StructureSet> STRUCTURE_SET_KEY =
            ResourceKey.create(
                    Registry.STRUCTURE_SET_REGISTRY,
                    BCWorldGenNames.STRUCTURE_ID
            );

    public static final ResourceKey<ConfiguredStructureFeature<?, ?>> STRUCTURE_FEATURE_KEY =
            ResourceKey.create(
                    Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
                    BCWorldGenNames.STRUCTURE_ID
            );

    public static final StructurePieceType STRUCTURE_PIECE_TYPE_OIL_STRUCTURE = StructurePieceType.setPieceId(
            OilStructure::deserialize,
            BCWorldGenNames.STRUCTURE_ID.toString()
    );
    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CONFIGURED_FEATURE_HOLDER =
            FeatureUtils.register(
                    BCWorldGenNames.STRUCTURE_ID.toString(),
                    Feature.SEAGRASS,
                    new ProbabilityFeatureConfiguration(0.3F)
            );
    public static final Holder<PlacedFeature> PLACED_OIL_STRUCTURE =
            PlacementUtils.register(
                    BCWorldGenNames.STRUCTURE_ID.toString(),
                    CONFIGURED_FEATURE_HOLDER,
                    modifiers(80)
            );

    public static List<PlacementModifier> modifiers(int p_195234_) {
        return List.of(
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                CountPlacement.of(p_195234_),
                BiomeFilter.biome()
        );
    }
}
