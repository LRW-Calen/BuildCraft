package buildcraft.energy;

import buildcraft.energy.generation.BCStructures;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = BCEnergy.MOD_ID)
public class BCEnergyWorldGen
{

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration)
    {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(BCEnergy.MOD_ID, name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }

    //    static
    public static void init()
    {
        // 注册油泉结构
        if (BCEnergyConfig.enableOilOceanBiome || BCEnergyConfig.enableOilDesertBiome)
        {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.addGenericListener(StructureFeature.class, BCStructures::register);
        }
//        // 海洋油田
//        if (BCEnergyConfig.enableOilOceanBiome)
//        {
////            BIOMES.register(BCWorldGenNames.BIOME_OIL_OCEAN, () -> new Biome.BiomeBuilder()
////                    .precipitation(Biome.Precipitation.RAIN)
////                    .biomeCategory(Biome.BiomeCategory.OCEAN)
////                    //.depth(0)
////                    .downfall(0.5F)
////                    //.scale(0)
////                    .temperature(0.5F)
////                    .specialEffects(new BiomeSpecialEffects.Builder()
////                            .fogColor(12638463)
////                            .waterColor(4159204)
////                            .waterFogColor(329011)
////                            .skyColor(8103167)
////                            .build())
////                    .generationSettings(new BiomeGenerationSettings.Builder().build())
////                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
////                    .temperatureAdjustment(Biome.TemperatureModifier.NONE)
////                    .build());
//            OIL_OCEAN = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(NameSpaces.BUILDCRAFT_ENERGY, BCWorldGenNames.BIOME_OIL_OCEAN));
////            OIL_OCEAN = makeKey(BCWorldGenNames.BIOME_OIL_OCEAN);
//            // https://blog.csdn.net/Jay_fearless/article/details/120580734
//            // 将所有的生物群系进行register                                          参数：生物群系名称, 生成概率(0~100)
////            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(OIL_OCEAN, 9));
//            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(OIL_OCEAN, 100));
////            BiomeManager.addAdditionalOverworldBiomes(OIL_OCEAN);
//
//        }
//        else
//        {
//            OIL_OCEAN = null;
//        }
//        // 沙漠油田
//        if (BCEnergyConfig.enableOilDesertBiome)
//        {
////            BIOMES.register(BCWorldGenNames.BIOME_OIL_DESERT, () -> new Biome.BiomeBuilder()
////                    .precipitation(Biome.Precipitation.NONE)
////                    .biomeCategory(Biome.BiomeCategory.DESERT)
////                    //.depth(0)
////                    .downfall(0)
////                    //.scale(0)
////                    .temperature(2)
////                    .specialEffects(new BiomeSpecialEffects.Builder()
////                            .fogColor(12638463)
////                            .waterColor(4159204)
////                            .waterFogColor(329011)
////                            .skyColor(7254527)
////                            .build())
////                    .generationSettings(new BiomeGenerationSettings.Builder().build())
////                    .mobSpawnSettings(new MobSpawnSettings.Builder().build())
////                    .temperatureAdjustment(Biome.TemperatureModifier.NONE)
////                    .build());
//            OIL_DESERT = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(NameSpaces.BUILDCRAFT_ENERGY, BCWorldGenNames.BIOME_OIL_DESERT));
////            OIL_DESERT = makeKey(BCWorldGenNames.BIOME_OIL_DESERT);
//            // 将所有的生物群系进行register                                          参数：生物群系名称, 生成概率(0~100)
////            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(OIL_DESERT, 7));
//            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(OIL_DESERT, 100));
////            BiomeManager.addAdditionalOverworldBiomes(OIL_DESERT);
//        }
//        else
//        {
//            OIL_DESERT = null;
//        }
//    }

//    public static void init()
//    {
//        boolean log = OilStructureGenerator.DEBUG_OILGEN_BASIC;
//        if (BCEnergyConfig.enableOilOceanBiome)
//        {
//            BiomeDictionary.addTypes(
//                    OIL_OCEAN,
//                    BiomeDictionary.Type.OCEAN
//            );
//            BCLog.logger.info("[energy.oilgen] Registered the ocean oil biome.");
//        }
//        else
//        {
//            BCLog.logger.info("[energy.oilgen] Not registering the ocean oil biome, as it has been disabled by the config file.");
//        }
//        if (BCEnergyConfig.enableOilDesertBiome)
//        {
//            BiomeDictionary.addTypes(
//                    OIL_DESERT,
//                    BiomeDictionary.Type.HOT,
//                    BiomeDictionary.Type.DRY,
//                    BiomeDictionary.Type.SANDY
//            );
//            BCLog.logger.info("[energy.oilgen] Registered the desert oil biome.");
//        }
//        else
//        {
//            BCLog.logger.info("[energy.oilgen] Not registering the desert oil biome, as it has been disabled by the config file.");
//        }
//        if (BCCoreConfig.worldGen)
//        {
//            if (BCEnergyConfig.enableOilGeneration)
//            {
////                MinecraftForge.EVENT_BUS.register(OilStructureGenerator.class);
//                BCLog.logger.info("[energy.oilgen] Registered the oil spout generator");
//            }
//            else
//            {
//                BCLog.logger.info("[energy.oilgen] Not registering the oil spout generator, as it has been disabled by the config file.");
//            }
//            if (BCEnergyConfig.enableOilOceanBiome || BCEnergyConfig.enableOilDesertBiome)
//            {
////                MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeInitializer());
//                BCLog.logger.info("[energy.oilgen] Registered the oil biome initiializer");
//            }
//            else
//            {
//                BCLog.logger.info("[energy.oilgen] Not registering the oil biome initiializer, as it has been disabled by the config file.");
//            }
//        }
//        else
//        {
//            BCLog.logger.info("[energy.oilgen] Not registering any world-gen, as everything has been disabled by the config file.");
//        }
    }

}
