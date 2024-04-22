package buildcraft.datagen.base;

import buildcraft.api.BCModules;
import buildcraft.lib.oredicttag.OreDictTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BCBiomeTagsGenerator extends BiomeTagsProvider {
    public BCBiomeTagsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BCModules.BUILDCRAFT, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Add anything but nether, end and void biomes
        tag(OreDictTags.OIL_GEN)
                .addTag(Tags.Biomes.IS_OVERWORLD)
        ;
    }

    @Override
    public String getName() {
        return "BuildCraft Biome Tags Generator";
    }
}
