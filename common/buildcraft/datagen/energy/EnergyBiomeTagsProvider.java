package buildcraft.datagen.energy;

import buildcraft.core.BCCore;
import buildcraft.lib.oredicttag.OreDictTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnergyBiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
    public EnergyBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BCCore.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Add anything but nether, end and void biomes
        tag(OreDictTags.OIL_GEN).addTag(Tags.Biomes.IS_OVERWORLD)
//                .add(Biomes.PLAINS)
//                .add(Biomes.SUNFLOWER_PLAINS)
//                .add(Biomes.SNOWY_PLAINS)
//                .add(Biomes.ICE_SPIKES)
//                .add(Biomes.DESERT)
//                .add(Biomes.SWAMP)
//                .add(Biomes.FOREST)
//                .add(Biomes.FLOWER_FOREST)
//                .add(Biomes.BIRCH_FOREST)
//                .add(Biomes.DARK_FOREST)
//                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
//                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
//                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
//                .add(Biomes.TAIGA)
//                .add(Biomes.SNOWY_TAIGA)
//                .add(Biomes.SAVANNA)
//                .add(Biomes.SAVANNA_PLATEAU)
//                .add(Biomes.WINDSWEPT_HILLS)
//                .add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
//                .add(Biomes.WINDSWEPT_FOREST)
//                .add(Biomes.WINDSWEPT_SAVANNA)
//                .add(Biomes.JUNGLE)
//                .add(Biomes.SPARSE_JUNGLE)
//                .add(Biomes.BAMBOO_JUNGLE)
//                .add(Biomes.BADLANDS)
//                .add(Biomes.ERODED_BADLANDS)
//                .add(Biomes.WOODED_BADLANDS)
//                .add(Biomes.MEADOW)
//                .add(Biomes.GROVE)
//                .add(Biomes.SNOWY_SLOPES)
//                .add(Biomes.FROZEN_PEAKS)
//                .add(Biomes.JAGGED_PEAKS)
//                .add(Biomes.STONY_PEAKS)
//                .add(Biomes.RIVER)
//                .add(Biomes.FROZEN_RIVER)
//                .add(Biomes.BEACH)
//                .add(Biomes.SNOWY_BEACH)
//                .add(Biomes.STONY_SHORE)
//                .add(Biomes.WARM_OCEAN)
//                .add(Biomes.LUKEWARM_OCEAN)
//                .add(Biomes.DEEP_LUKEWARM_OCEAN)
//                .add(Biomes.OCEAN)
//                .add(Biomes.DEEP_OCEAN)
//                .add(Biomes.COLD_OCEAN)
//                .add(Biomes.DEEP_COLD_OCEAN)
//                .add(Biomes.FROZEN_OCEAN)
//                .add(Biomes.DEEP_FROZEN_OCEAN)
//                .add(Biomes.MUSHROOM_FIELDS)
//                .add(Biomes.DRIPSTONE_CAVES)
//                .add(Biomes.LUSH_CAVES)
        ;
    }

    @Override
    public String getName() {
        return "BuildCraft Biome Tags";
    }
}
