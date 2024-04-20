package buildcraft.energy.generation.structure;

import buildcraft.energy.generation.BCStructures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

// Calen for test
//public record $OilStructurePlacement(int distance, int spread, int count) implements StructurePlacement
public record $OilStructurePlacement(int spacing, int separation, RandomSpreadType spreadType, int salt,
                                     Vec3i locateOffset) implements StructurePlacement {
    public static final Codec<$OilStructurePlacement> CODEC = RecordCodecBuilder.<$OilStructurePlacement>mapCodec((p_204996_) ->
    {
        return p_204996_.group(
                        Codec.intRange(0, 4096)
                                .fieldOf("spacing")
                                .forGetter($OilStructurePlacement::spacing),
                        Codec.intRange(0, 4096)
                                .fieldOf("separation")
                                .forGetter($OilStructurePlacement::separation),
                        RandomSpreadType.CODEC.
                                optionalFieldOf("spread_type", RandomSpreadType.LINEAR)
                                .forGetter($OilStructurePlacement::spreadType),
                        ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt")
                                .forGetter($OilStructurePlacement::salt),
                        Vec3i.offsetCodec(16)
                                .optionalFieldOf("locate_offset", Vec3i.ZERO)
                                .forGetter($OilStructurePlacement::locateOffset)
                )
                .apply(p_204996_, $OilStructurePlacement::new);
    }).flatXmap((p_205002_) ->
    {
        return DataResult.success(p_205002_);
    }, DataResult::success).codec();

    public $OilStructurePlacement(int p_204980_, int p_204981_, RandomSpreadType p_204982_, int p_204983_) {
        this(p_204980_, p_204981_, p_204982_, p_204983_, Vec3i.ZERO);
    }

    public boolean isFeatureChunk(ChunkGenerator p_212310_, long p_212311_, int p_212312_, int p_212313_) {
        return true;
    }

    public StructurePlacementType<?> type() {
        return BCStructures.OIL;
    }
}
