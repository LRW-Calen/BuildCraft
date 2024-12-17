package buildcraft.lib.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

// Calen 1.20.1
public class BiomeUtil {
    public static Biome getBiomeFromRegistryName(String name) {
        return getBiomeFromRegistryName(new ResourceLocation(name));
    }

    public static Biome getBiomeFromRegistryName(ResourceLocation name) {
        return ForgeRegistries.BIOMES.getValue(name);
    }

    public static ResourceLocation getRegistryName(Biome biome) {
        return ForgeRegistries.BIOMES.getKey(biome);
    }
}
