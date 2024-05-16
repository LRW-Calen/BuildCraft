package buildcraft.energy;

import buildcraft.api.BCModules;
import buildcraft.api.core.BCLog;
import buildcraft.lib.config.BCConfig;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.*;

public class BCEnergyConfig {
    private static Configuration config;

    public static boolean enableOilOceanBiome;
    public static boolean enableOilDesertBiome;

    public static boolean enableOilGeneration;
    public static double oilWellGenerationRate;
    public static boolean enableOilSpouts;
    public static boolean enableOilBurn;
    public static boolean oilIsSticky;

    public static int smallSpoutMinHeight;
    public static int smallSpoutMaxHeight;
    public static int largeSpoutMinHeight;
    public static int largeSpoutMaxHeight;

    public static double smallOilGenProb;
    public static double mediumOilGenProb;
    public static double largeOilGenProb;

    public static final Set<String> excludedDimensions = new HashSet<>();
    /** If false then {@link #excludedDimensions} should be treated as a whitelist rather than a blacklist. */
    public static boolean excludedDimensionsIsBlackList;
    public static final Set<ResourceLocation> excessiveBiomes = new HashSet<>();
    public static final Set<ResourceLocation> surfaceDepositBiomes = new HashSet<>();
    public static final Set<ResourceLocation> excludedBiomes = new HashSet<>();
    /** If false then {@link #excludedBiomes} should be treated as a whitelist rather than a blacklist. */
    public static boolean excludedBiomesIsBlackList;
    public static SpecialEventType christmasEventStatus = SpecialEventType.DAY_ONLY;

    private static BooleanValue propEnableOilOceanBiome;
    private static BooleanValue propEnableOilDesertBiome;

    private static BooleanValue propEnableOilGeneration;
    private static DoubleValue propOilWellGenerationRate;
    private static BooleanValue propEnableOilSpouts;
    private static BooleanValue propEnableOilBurn;
    private static BooleanValue propOilIsSticky;

    private static IntValue propSmallSpoutMinHeight;
    private static IntValue propSmallSpoutMaxHeight;
    private static IntValue propLargeSpoutMinHeight;
    private static IntValue propLargeSpoutMaxHeight;

    private static DoubleValue propSmallOilGenProb;
    private static DoubleValue propMediumOilGenProb;
    private static DoubleValue propLargeOilGenProb;

    private static ConfigValue<List<? extends String>> propExcessiveBiomes;
    private static ConfigValue<List<? extends String>> propSurfaceDepositBiomes;
    private static ConfigValue<List<? extends String>> propExcludedBiomes;
    private static BooleanValue propExcludedBiomesIsBlacklist;
    private static ConfigValue<List<? extends String>> propExcludedDimensions;
    private static BooleanValue propExcludedDimensionsIsBlacklist;
    private static EnumValue<SpecialEventType> propChristmasEventType;

    public static void preInit() {
//        Configuration config = BCCoreConfig.config;
        BCModules module = BCModules.ENERGY;
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config = new Configuration(builder, module);
        createProps();
        ForgeConfigSpec spec = config.build();
        ModContainer container = ModList.get().getModContainerById(module.getModId()).get();
        container.addConfig(new ModConfig(ModConfig.Type.COMMON, spec, container, config.getFileName()));

//        reloadConfig(EnumRestartRequirement.GAME);
        reloadConfig();
//        BCCoreConfig.addReloadListener(BCEnergyConfig::reloadConfig);
        BCConfig.registerReloadListener(module, BCEnergyConfig::reloadConfig);
    }

    public static void createProps() {
        EnumRestartRequirement world = EnumRestartRequirement.WORLD;
//        EnumRestartRequirement game = EnumRestartRequirement.GAME;

        propEnableOilOceanBiome = config
                .define("worldgen.oil",
                        "Should Oil Ocean biomes generate at all?",
                        world,
                        "oil_ocean_biome", true);

        propEnableOilDesertBiome = config
                .define("worldgen.oil",
                        "Should Oil Desert biomes generate at all?",
                        world,
                        "oil_desert_biome", true);

        propEnableOilGeneration = config
                .define("worldgen.oil",
                        "Should any oil spouts or lakes be generated at all?",
                        world,
                        "enable", true);

        propEnableOilBurn = config
                .define("worldgen.oil",
                        "Can oil blocks burn?",
                        world,
                        "can_burn", true);

        propOilWellGenerationRate = config
                .defineInRange("worldgen.oil",
                        "The rate of occurrence of oil wells.",
                        world,
                        "generationRate", 1.0);


        propSmallOilGenProb = config
                .defineInRange("worldgen.oil.spawn_probability",
                        "The percentage probability of a small oil spawn",
                        world,
                        "small", 2.0);

        propMediumOilGenProb = config
                .defineInRange("worldgen.oil.spawn_probability",
                        "The percentage probability of a medium oil spawn",
                        world,
                        "medium", 0.1);

        propLargeOilGenProb = config
                .defineInRange("worldgen.oil.spawn_probability",
                        "The percentage probability of a large oil spawn",
                        world,
                        "large", 0.04);


        propEnableOilSpouts = config
                .define("worldgen.oil.spouts",
                        "Whether oil spouts are generated or not. The oil spring at the bottom of large lakes will still exist.",
                        world,
                        "enable", true);

        propSmallSpoutMinHeight = config
                .defineInRange("worldgen.oil.spouts",
                        "The minimum height for small oil spouts",
                        world,
                        "small_min_height", 6);

        propSmallSpoutMaxHeight = config
                .defineInRange("worldgen.oil.spouts",
                        "The maximum height for small oil spouts",
                        world,
                        "small_max_height", 12);

        propLargeSpoutMinHeight = config
                .defineInRange("worldgen.oil.spouts",
                        "The minimum height for large oil spouts",
                        world,
                        "large_min_height", 10);

        propLargeSpoutMaxHeight = config
                .defineInRange("worldgen.oil.spouts",
                        "The maximum height for large oil spouts",
                        world,
                        "large_max_height", 20);


        String[] _excessive = { //
                BCEnergy.MODID + ":oil_desert", //
                BCEnergy.MODID + ":oil_ocean", //
        };

        propExcessiveBiomes = config
                .defineList("worldgen.oil",
                        "Biome registry names (e.g. 'minecraft:ocean','minecraft:plains')"
                                + " of biomes that should have GREATLY increased oil generation rates.",
                        world,
                        "excessiveBiomes", Lists.newArrayList(_excessive), b -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(b.toString())));

        String[] _surface = {};
        propSurfaceDepositBiomes = config
                .defineList("worldgen.oil",
                        "Biome registry names (e.g. 'minecraft:ocean','minecraft:hills') of biomes that should have slightly increased oil generation rates.",
                        world,
                        "surfaceDepositBiomes", Lists.newArrayList(_surface), b -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(b.toString())));

        String[] _excluded = { "minecraft:hell", "minecraft:sky", };
        propExcludedBiomes = config
                .defineList("worldgen.oil",
                        "Biome registry names (e.g. 'minecraft:hell','minecraft:jungle') of biomes that should never generate oil.",
                        world,
                        "excludedBiomes", Lists.newArrayList(_excluded), b -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(b.toString())));

        propExcludedBiomesIsBlacklist = config
                .define("worldgen.oil",
                        "If true then the excluded biomes list will be treated as a blacklist, otherwise it will be treated as a whitelist.",
                        world,
                        "excludedBiomesIsBlacklist", true);

        String[] _dims = { "minecraft:the_nether", "minecraft:the_end", };
        propExcludedDimensions = config
                .defineList("worldgen.oil",
                        "Dimension ID's (e.g. 'minecraft:the_nether' for the nether,'minecraft:the_end' for the end) of dimensions that should never generate oil.",
                        world,
                        "excludedDimensions", Lists.newArrayList(_dims), d -> true);

        propExcludedDimensionsIsBlacklist = config
                .define("worldgen.oil",
                        "If true then the excluded dimenstions list will be treated as a blacklist, otherwise it will be treated as a whitelist.",
                        world,
                        "excludedDimensionsIsBlacklist", true);


        // TODO Calen default false??? but oil is sticky in 1.12.2...
        propOilIsSticky = config
                .define("general",
                        "Should oil be dense and drag entities down?",
                        EnumRestartRequirement.NONE,
                        "oilIsDense", true);

        propChristmasEventType = config
                .defineEnum("events",
                        "",
                        world,
                        "christmas_chocolate", SpecialEventType.DAY_ONLY);
    }

    // public static void reloadConfig(EnumRestartRequirement restarted)
    public static void reloadConfig() {
//        if (EnumRestartRequirement.WORLD.hasBeenRestarted(restarted)) {

        addBiomeNames(propExcludedBiomes, excludedBiomes);
        addBiomeNames(propExcessiveBiomes, excessiveBiomes);
        addBiomeNames(propSurfaceDepositBiomes, surfaceDepositBiomes);
        excludedDimensions.clear();
        excludedDimensions.addAll(propExcludedDimensions.get());
        excludedBiomesIsBlackList = propExcludedBiomesIsBlacklist.get();
        excludedDimensionsIsBlackList = propExcludedDimensionsIsBlacklist.get();

//            if (EnumRestartRequirement.GAME.hasBeenRestarted(restarted)) {
        enableOilOceanBiome = propEnableOilOceanBiome.get();
        enableOilDesertBiome = propEnableOilDesertBiome.get();

        enableOilGeneration = propEnableOilGeneration.get();
        oilWellGenerationRate = propOilWellGenerationRate.get();
        enableOilSpouts = propEnableOilSpouts.get();
        enableOilBurn = propEnableOilBurn.get();
        oilIsSticky = propOilIsSticky.get();

        smallSpoutMinHeight = propSmallSpoutMinHeight.get();
        smallSpoutMaxHeight = propSmallSpoutMaxHeight.get();
        largeSpoutMinHeight = propLargeSpoutMinHeight.get();
        largeSpoutMaxHeight = propLargeSpoutMaxHeight.get();

        smallOilGenProb = propSmallOilGenProb.get() / 100;
        mediumOilGenProb = propMediumOilGenProb.get() / 100;
        largeOilGenProb = propLargeOilGenProb.get() / 100;

        christmasEventStatus = propChristmasEventType.get();
//            } else {
        validateBiomeNames();
//            }
//        }
    }

    private static void addBiomeNames(ConfigValue<List<? extends String>> prop, Set<ResourceLocation> set) {
        set.clear();
        for (String s : prop.get()) {
            set.add(new ResourceLocation(s));
        }
    }

    /** Called in post-init, after all biomes should have been registered. In 1.12 this should be called after the
     * registry event for biomes has been fired. */
    public static void validateBiomeNames() {
        Set<ResourceLocation> invalids = new HashSet<>();
        addInvalidBiomeNames(excessiveBiomes, invalids);
        addInvalidBiomeNames(excludedBiomes, invalids);
        addInvalidBiomeNames(surfaceDepositBiomes, invalids);

        if (invalids.isEmpty()) {
            return;
        }

        List<ResourceLocation> invalidList = new ArrayList<>();
        invalidList.addAll(invalids);
        Collections.sort(invalidList, Comparator.comparing(ResourceLocation::toString));

        List<ResourceLocation> allValid = new ArrayList<>();
        allValid.addAll(ForgeRegistries.BIOMES.getKeys());
        Collections.sort(allValid, Comparator.comparing(ResourceLocation::toString));

        BCLog.logger.warn("****************************************************");
        BCLog.logger.warn("*");
        BCLog.logger.warn("* Unknown biome name detected in buildcraft config!");
        BCLog.logger.warn("* (Config file = " + BCEnergyConfig.config.getFileName() + ")");
        BCLog.logger.warn("*");
        BCLog.logger.warn("* Unknown biomes: ");
        printList(Level.WARN, invalidList);
        BCLog.logger.warn("*");
        BCLog.logger.info("* All possible known names: ");
        printList(Level.INFO, allValid);
        BCLog.logger.info("*");
        BCLog.logger.warn("****************************************************");
    }

    private static void printList(Level level, List<ResourceLocation> list) {
        for (ResourceLocation location : list) {
            BCLog.logger.log(level, "*    - " + location);
        }
    }

    private static void addInvalidBiomeNames(Set<ResourceLocation> toTest, Set<ResourceLocation> invalidDest) {
        for (ResourceLocation test : toTest) {
            if (!ForgeRegistries.BIOMES.containsKey(test)) {
                invalidDest.add(test);
            }
        }
    }

    public enum SpecialEventType {
        DISABLED,
        DAY_ONLY,
        MONTH,
        ENABLED;

        public final String lowerCaseName = name().toLowerCase(Locale.ROOT);

        public boolean isEnabled(MonthDay date) {
            if (this == DISABLED) {
                return false;
            }
            if (this == ENABLED) {
                return true;
            }
            LocalDateTime now = LocalDateTime.now();
            if (now.getMonth() != date.getMonth()) {
                return false;
            }
            if (this == MONTH) {
                return true;
            }
            int thisDay = now.getDayOfMonth();
            int wantedDay = date.getDayOfMonth();
            return thisDay >= wantedDay - 1 && thisDay <= wantedDay + 1;
        }
    }
}
