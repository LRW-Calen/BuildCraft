/*
 * Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package buildcraft.core;

import buildcraft.api.BCModules;
import buildcraft.lib.BCLibConfig;
import buildcraft.lib.BCLibConfig.ChunkLoaderLevel;
import buildcraft.lib.BCLibConfig.RenderRotation;
import buildcraft.lib.BCLibConfig.TimeGap;
import buildcraft.lib.config.EnumRestartRequirement;
import buildcraft.lib.config.FileConfigManager;
import buildcraft.lib.misc.ConfigUtil;
import buildcraft.lib.misc.MathUtil;
import buildcraft.lib.registry.RegistryConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge_1_12_2.common.config.Configuration;
import net.minecraftforge_1_12_2.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class BCCoreConfig {
    private static final List<Consumer<EnumRestartRequirement>> reloadListeners = new ArrayList<>();

    public static File configFolder;

    // public static Configuration config;
    private static Configuration config;
    // public static Configuration objConfig;
    private static Configuration objConfig;
    public static FileConfigManager detailedConfigManager;

    public static boolean worldGen;
    public static boolean worldGenWaterSpring;
    public static boolean minePlayerProtected;
    public static boolean hidePower;
    public static boolean hideFluid;
    public static boolean pumpsConsumeWater;
    public static int markerMaxDistance;
    public static int pumpMaxDistance;
    public static int networkUpdateRate = 10;
    public static double miningMultiplier = 1;
    public static int miningMaxDepth;

    private static Property propColourBlindMode;
    private static Property propWorldGen;
    private static Property propWorldGenWaterSpring;
    private static Property propMinePlayerProtected;
    private static Property propUseColouredLabels;
    private static Property propUseHighContrastColouredLabels;
    private static Property propHidePower;
    private static Property propHideFluid;
    private static Property propGuideBookEnableDetail;
    private static Property propGuideItemSearchLimit;
    private static Property propUseBucketsStatic;
    private static Property propUseBucketsFlow;
    private static Property propUseLongLocalizedName;
    private static Property propDisplayTimeGap;
    private static Property propUseSwappableSprites;
    private static Property propEnableAnimatedSprites;
    private static Property propMaxGuideSearchResults;
    private static Property propChunkLoadLevel;
    private static Property propItemRenderRotation;
    private static Property propItemLifespan;
    private static Property propPumpsConsumeWater;
    private static Property propMarkerMaxDistance;
    private static Property propPumpMaxDistance;
    private static Property propNetworkUpdateRate;
    private static Property propMiningMultiplier;
    private static Property propMiningMaxDepth;

    // Calen for thread safety
    public static synchronized Configuration getConfigAndEnsureCreated(boolean notObjConfig) {
        if (BCCoreConfig.config == null) {
            createConfigFile();
        }
        if (notObjConfig) {
            return BCCoreConfig.config;
        } else {
            return BCCoreConfig.objConfig;
        }
    }

    // Calen
    private static void createConfigFile() {
        // Calen changed
        File forgeConfigFolder = FMLPaths.CONFIGDIR.get().toFile();
        File buildCraftConfigFolder = new File(forgeConfigFolder, "buildcraft");

        configFolder = buildCraftConfigFolder;
        config = new Configuration(new File(buildCraftConfigFolder, "main.cfg"));
        objConfig = RegistryConfig.setRegistryConfig(BCCore.MODID, new File(buildCraftConfigFolder, "objects.cfg"));
        // Calen: thread safety
//        BCLibConfig.guiConfigFile = new File(buildCraftConfigFolder, "gui.json");
        BCLibConfig.getGuiConfigFileAndEnsureCreated();

        detailedConfigManager = new FileConfigManager(
                " The buildcraft detailed configuration file. This contains a lot of miscellaneous options that have no "
                        + "affect on gameplay.\n You should refer to the BC source code for a detailed description of what these do. (https://github.com/BuildCraft/BuildCraft)\n"
                        + " This file will be overwritten every time that buildcraf starts, so don't change anything other than the values.");
        detailedConfigManager.setConfigFile(new File(buildCraftConfigFolder, "detailed.properties"));
    }

    public static void preInit() {
//        File forgeConfigFolder = FMLPaths.CONFIGDIR.get().toFile();
//        File buildCraftConfigFolder = new File(forgeConfigFolder, "buildcraft");
//
//        configFolder = buildCraftConfigFolder;
//        config = new Configuration(new File(buildCraftConfigFolder, "main.cfg"));
////        getOrSetConfig(false, new Configuration(new File(buildCraftConfigFolder, "main.cfg")));
////        objConfig = RegistryConfig.setRegistryConfig(NameSpaces.BUILDCRAFT_CORE, new File(buildCraftConfigFolder, "objects.cfg"));
//        getOrSetObjConfig(false, RegistryConfig.setRegistryConfig(NameSpaces.BUILDCRAFT_CORE, new File(buildCraftConfigFolder, "objects.cfg")));
//        BCLibConfig.guiConfigFile = new File(buildCraftConfigFolder, "gui.json");

//        detailedConfigManager = new FileConfigManager(
//                " The buildcraft detailed configuration file. This contains a lot of miscellaneous options that have no "
//                        + "affect on gameplay.\n You should refer to the BC source code for a detailed description of what these do. (https://github.com/BuildCraft/BuildCraft)\n"
//                        + " This file will be overwritten every time that buildcraft starts, so don't change anything other than the values.");
//        detailedConfigManager.setConfigFile(new File(cfgFolder, "detailed.properties"));

        getConfigAndEnsureCreated(true); // ensure object created

        // Variables to make
        String general = Configuration.CATEGORY_GENERAL;
        String display = "display";
        String worldgen = "worldgen";
        String performance = "performance";

        EnumRestartRequirement none = EnumRestartRequirement.NONE;
        EnumRestartRequirement world = EnumRestartRequirement.WORLD;
        EnumRestartRequirement game = EnumRestartRequirement.GAME;

        propColourBlindMode = getConfigAndEnsureCreated(true).get(display, "colorBlindMode", false);
        propColourBlindMode.setComment("Should I enable colorblind mode?");
        none.setTo(propColourBlindMode);

        propWorldGen = getConfigAndEnsureCreated(true).get(worldgen, "enable", true);
        propWorldGen.setComment("Should BuildCraft generate anything in the world?");
        game.setTo(propWorldGen);

        propWorldGenWaterSpring = getConfigAndEnsureCreated(true).get(worldgen, "generateWaterSprings", true);
        propWorldGenWaterSpring.setComment("Should BuildCraft generate water springs?");
        game.setTo(propWorldGenWaterSpring);

        propMinePlayerProtected = getConfigAndEnsureCreated(true).get(general, "miningBreaksPlayerProtectedBlocks", false);
        propMinePlayerProtected
                .setComment("Should BuildCraft miners be allowed to break blocks using player-specific protection?");
        none.setTo(propMinePlayerProtected);

        propUseColouredLabels = getConfigAndEnsureCreated(true).get(display, "useColouredLabels", true);
        propUseColouredLabels.setComment("Should colours be displayed as their own (or a similar) colour in tooltips?");
        none.setTo(propUseColouredLabels);

        propUseHighContrastColouredLabels = getConfigAndEnsureCreated(true).get(display, "useHighContrastColouredLabels", false);
        propUseHighContrastColouredLabels
                .setComment("Should colours displayed in tooltips use higher-contrast colours?");
        none.setTo(propUseHighContrastColouredLabels);

        propHidePower = getConfigAndEnsureCreated(true).get(display, "hidePowerValues", false);
        propHidePower.setComment("Should all power values (MJ, MJ/t) be hidden?");
        none.setTo(propHidePower);

        propHideFluid = getConfigAndEnsureCreated(true).get(display, "hideFluidValues", false);
        propHideFluid.setComment("Should all fluid values (Buckets, mB, mB/t) be hidden?");
        none.setTo(propHideFluid);

        propGuideBookEnableDetail = getConfigAndEnsureCreated(true).get(display, "guideBookEnableDetail", false);
        none.setTo(propGuideBookEnableDetail);

        propGuideItemSearchLimit = getConfigAndEnsureCreated(true).get(performance, "guideItemSearchLimit", 10_000);
        propGuideItemSearchLimit.setComment("The maximum number of items that the guide book will index.");
        propGuideItemSearchLimit.setMinValue(1_500);
        propGuideItemSearchLimit.setMaxValue(5_000_000);
        none.setTo(propGuideItemSearchLimit);

        propUseBucketsStatic = getConfigAndEnsureCreated(true).get(display, "useBucketsStatic", true);
        propUseBucketsStatic.setComment(
                "Should static fluid values be displayed in terms of buckets rather than thousandths of a bucket? (B vs mB)");
        none.setTo(propUseBucketsStatic);

        propUseBucketsFlow = getConfigAndEnsureCreated(true).get(display, "useBucketsFlow", true);
        propUseBucketsFlow.setComment(
                "Should flowing fluid values be displayed in terms of buckets per second rather than thousandths of a bucket per tick? (B/s vs mB/t)");
        none.setTo(propUseBucketsFlow);

        propUseLongLocalizedName = getConfigAndEnsureCreated(true).get(display, "useLongLocalizedName", true);
        propUseLongLocalizedName.setComment(
                "Should localised strings be displayed in long or short form (10 mB / t vs 10 milli buckets per tick");
        none.setTo(propUseLongLocalizedName);

        propDisplayTimeGap = getConfigAndEnsureCreated(true).get(display, "timeGap", TimeGap.SECONDS.name().toLowerCase(Locale.ROOT));
        propDisplayTimeGap
                .setComment("Should localised strings be displayed in terms of seconds (1 MJ/s) or ticks (20 MJ/t)");
        ConfigUtil.setEnumProperty(propDisplayTimeGap, TimeGap.values());
        none.setTo(propDisplayTimeGap);

        propUseSwappableSprites = getConfigAndEnsureCreated(true).get(display, "useSwappableSprites", true);
        propUseSwappableSprites.setComment(
                "Disable this if you get texture errors with optifine. Disables some texture switching functionality "
                        + "when changing config options such as colour blind mode.");
        game.setTo(propUseSwappableSprites);

        propEnableAnimatedSprites = getConfigAndEnsureCreated(true).get(performance, "enableAnimatedSprites", true);
        propEnableAnimatedSprites.setComment(
                "Disable this if you get sub-standard framerates due to buildcraftcore's ~60 sprites animating every frame.");
        none.setTo(propEnableAnimatedSprites);

        propMaxGuideSearchResults = getConfigAndEnsureCreated(true).get(performance, "maxGuideSearchResults", 1200);
        propMaxGuideSearchResults.setComment("The maximum number of search results to display in the guide book.");
        propMaxGuideSearchResults.setMinValue(500).setMaxValue(5000);
        none.setTo(propMaxGuideSearchResults);

        propItemRenderRotation = getConfigAndEnsureCreated(true).get(display, "itemRenderRotation", RenderRotation.ENABLED.name().toLowerCase(Locale.ROOT));
        propItemRenderRotation.setComment(
                "The rotation that items use when travelling through pipes. Set to 'enabled' for full rotation, "
                        + "'disabled' for no rotation, or 'horizontals_only' to only rotate items when going horizontally.");
        ConfigUtil.setEnumProperty(propItemRenderRotation, RenderRotation.values());

        propChunkLoadLevel = getConfigAndEnsureCreated(true).get(general, "chunkLoadLevel", ChunkLoaderLevel.SELF_TILES.name().toLowerCase(Locale.ROOT));
        propChunkLoadLevel.setComment("");
        ConfigUtil.setEnumProperty(propChunkLoadLevel, ChunkLoaderLevel.values());
        world.setTo(propChunkLoadLevel);

        propItemLifespan = getConfigAndEnsureCreated(true).get(general, "itemLifespan", 60);
        propItemLifespan.setMinValue(5).setMaxValue(600);
        propItemLifespan.setComment("How long, in seconds, should items stay on the ground? (Vanilla = 300, default = 60)");
        none.setTo(propItemLifespan);

        propPumpsConsumeWater = getConfigAndEnsureCreated(true).get(general, "pumpsConsumeWater", false);
        propPumpsConsumeWater.setComment("Should pumps consume water? Enabling this will disable"
                + " minor optimisations, but work properly with finite water mods.");
        none.setTo(propPumpsConsumeWater);

        propMarkerMaxDistance = getConfigAndEnsureCreated(true).get(general, "markerMaxDistance", 64);
        propMarkerMaxDistance.setMinValue(16).setMaxValue(256);
        propMarkerMaxDistance.setComment("How far, in minecraft blocks, should markers (volume and path) reach?");
        none.setTo(propMarkerMaxDistance);

        propPumpMaxDistance = getConfigAndEnsureCreated(true).get(general, "pumpMaxDistance", 64);
        propPumpMaxDistance.setMinValue(16).setMaxValue(128);
        propPumpMaxDistance.setComment("How far, in minecraft blocks, should pumps reach in fluids?");
        none.setTo(propPumpMaxDistance);

        propNetworkUpdateRate = getConfigAndEnsureCreated(true).get(general, "updateFactor", networkUpdateRate);
        propNetworkUpdateRate.setMinValue(1).setMaxValue(100);
        propNetworkUpdateRate.setComment(
                "How often, in ticks, should network update packets be sent? Increasing this might help network performance.");
        none.setTo(propNetworkUpdateRate);

        propMiningMultiplier = getConfigAndEnsureCreated(true).get(general, "miningMultiplier", 1.0);
        propMiningMultiplier.setMinValue(1).setMaxValue(200);
        propMiningMultiplier.setComment("How much power should be required for all mining machines?");
        none.setTo(propMiningMultiplier);

        propMiningMaxDepth = getConfigAndEnsureCreated(true).get(general, "miningMaxDepth", 512);
        propMiningMaxDepth.setMinValue(32).setMaxValue(4096);
        propMiningMaxDepth.setComment("How much further down can miners (like the quarry or the mining well) dig?"
                + "\n(Note: values above 256 only have an effect if a mod like cubic chunks is installed).");
        none.setTo(propMiningMaxDepth);

        reloadConfig(game);
        addReloadListener(BCCoreConfig::reloadConfig);

        // 1.18.2: ModConfigEvent is IModBusEvent
//        MinecraftForge.EVENT_BUS.register(BCCoreConfig.class);
        ((FMLModContainer) ModList.get().getModContainerById(BCCore.MODID).get()).getEventBus().register(BCCoreConfig.class);
    }

    public static void addReloadListener(Consumer<EnumRestartRequirement> listener) {
        reloadListeners.add(listener);
    }

    @SubscribeEvent
//    public static void onConfigChange(OnConfigChangedEvent cce)
    public static void onConfigChange(ModConfigEvent cce) {
//        if (BCModules.isBcMod(cce.getModID()))
        if (BCModules.isBcMod(cce.getConfig().getModId())) {
            EnumRestartRequirement req = EnumRestartRequirement.NONE;
//            if (Loader.instance().isInState(LoaderState.AVAILABLE))
            if (ModLoadingContext.get().getActiveContainer().getCurrentState() == ModLoadingStage.DONE) {
                // The loaders state will be LoaderState.SERVER_STARTED when we are in a world
                req = EnumRestartRequirement.WORLD;
            }
            for (Consumer<EnumRestartRequirement> listener : reloadListeners) {
                listener.accept(req);
            }
        }
    }

    public static void postInit() {
//        ConfigUtil.setLang(config);
        ConfigUtil.setLang(getConfigAndEnsureCreated(true));
        saveConfigs();
    }

    public static void saveConfigs() {
        if (getConfigAndEnsureCreated(true).hasChanged()) {
            getConfigAndEnsureCreated(true).save();
        }
        if (getConfigAndEnsureCreated(false).hasChanged()) {
            getConfigAndEnsureCreated(false).save();
        }
    }

    public static void reloadConfig(EnumRestartRequirement restarted) {
        minePlayerProtected = propMinePlayerProtected.getBoolean();
        BCLibConfig.useColouredLabels = propUseColouredLabels.getBoolean();
        BCLibConfig.useHighContrastLabelColours = propUseHighContrastColouredLabels.getBoolean();
        hidePower = propHidePower.getBoolean();
        hideFluid = propHideFluid.getBoolean();
        BCLibConfig.guideShowDetail = propGuideBookEnableDetail.getBoolean();
        BCLibConfig.guideItemSearchLimit = MathUtil.clamp(propGuideItemSearchLimit.getInt(), 1_500, 5_000_000);
        BCLibConfig.useBucketsStatic = propUseBucketsStatic.getBoolean();
        BCLibConfig.useBucketsFlow = propUseBucketsFlow.getBoolean();
        BCLibConfig.useLongLocalizedName = propUseLongLocalizedName.getBoolean();
        BCLibConfig.itemLifespan = propItemLifespan.getInt();
        pumpsConsumeWater = propPumpsConsumeWater.getBoolean();
        markerMaxDistance = propMarkerMaxDistance.getInt();
        pumpMaxDistance = propPumpMaxDistance.getInt();
        BCLibConfig.colourBlindMode = propColourBlindMode.getBoolean();
        BCLibConfig.displayTimeGap = ConfigUtil.parseEnumForConfig(propDisplayTimeGap, TimeGap.TICKS);
        BCLibConfig.rotateTravelingItems =
                ConfigUtil.parseEnumForConfig(propItemRenderRotation, BCLibConfig.RenderRotation.ENABLED);
        BCLibConfig.enableAnimatedSprites = propEnableAnimatedSprites.getBoolean();
        miningMultiplier = MathUtil.clamp(propMiningMultiplier.getDouble(), 1, 200);
        miningMaxDepth = propMiningMaxDepth.getInt();

        if (EnumRestartRequirement.WORLD.hasBeenRestarted(restarted)) {
            BCLibConfig.chunkLoadingLevel =
                    ConfigUtil.parseEnumForConfig(propChunkLoadLevel, BCLibConfig.ChunkLoaderLevel.SELF_TILES);

            if (EnumRestartRequirement.GAME.hasBeenRestarted(restarted)) {
                worldGen = propWorldGen.getBoolean();
                worldGenWaterSpring = propWorldGenWaterSpring.getBoolean();
                BCLibConfig.useSwappableSprites = propUseSwappableSprites.getBoolean();
            }
        }
        BCLibConfig.refreshConfigs();
        saveConfigs();
    }
}
