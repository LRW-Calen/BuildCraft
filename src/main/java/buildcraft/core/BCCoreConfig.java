/*
 * Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package buildcraft.core;

import buildcraft.api.BCModules;
import buildcraft.core.config.*;
import buildcraft.lib.BCLibConfig;
import buildcraft.lib.BCLibConfig.*;
import buildcraft.lib.config.EnumRestartRequirement;
import buildcraft.lib.config.FileConfigManager;
import buildcraft.lib.misc.ConfigUtil;
import buildcraft.lib.misc.MathUtil;
import buildcraft.lib.registry.RegistryConfig;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge_1_12_2.common.config.Configuration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge_1_12_2.common.config.Property;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class BCCoreConfig
{
    private static final List<Consumer<EnumRestartRequirement>> reloadListeners = new ArrayList<>();

    public static File configFolder;

    //    public static Configuration config;
    private static Configuration config;
    //    public static Configuration objConfig;
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


    public static synchronized Configuration getConfig(boolean notObjConfig)
    {
        if (BCCoreConfig.config == null)
        {
            createConfigFile();
        }
        if (notObjConfig)
        {
            return BCCoreConfig.config;
        }
        else
        {
            return BCCoreConfig.objConfig;
        }
    }

//    public static synchronized Configuration getOrSetObjConfig(boolean get, Configuration objConfig)
//    {
//    }
    // JADE

    public static final JsonConfig<WailaConfig> CONFIG =
            new JsonConfig<>(BCCore.MOD_ID + "/" + BCCore.MOD_ID, WailaConfig.class).withGson(
                    new GsonBuilder()
                            .setPrettyPrinting()
                            .enableComplexMapKeySerialization()
                            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
                            .create()
            );
    public static int inventoryDetailedShowAmount = 54;
    public static int inventoryNormalShowAmount = 9;
    public static int inventoryShowItemPreLine = 9;
    private static final Set<String> inventoryBlacklist = Sets.newHashSet();
    public static boolean bypassLockedContainer = false;
    private static boolean onlyShowVanilla = false;
    private static final Set<String> modBlacklist = Sets.newHashSet();
    private static ForgeConfigSpec.IntValue inventorySneakShowAmountVal;
    private static ForgeConfigSpec.IntValue inventoryNormalShowAmountVal;
    private static ForgeConfigSpec.IntValue inventoryShowItemPreLineVal;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> inventoryBlacklistVal;
    private static ForgeConfigSpec.BooleanValue bypassLockedContainerVal;
    private static ForgeConfigSpec.BooleanValue onlyShowVanillaVal;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> modBlacklistVal;
    public static final ForgeConfigSpec spec = new ForgeConfigSpec.Builder().configure(BCCoreConfig::new).getRight();

    private BCCoreConfig(ForgeConfigSpec.Builder builder)
    {
        builder.push("inventory");
        inventorySneakShowAmountVal = builder.defineInRange("sneakShowAmount", inventoryDetailedShowAmount, 0, 54);
        inventoryNormalShowAmountVal = builder.defineInRange("normalShowAmount", inventoryNormalShowAmount, 0, 54);
        inventoryShowItemPreLineVal = builder.defineInRange("showItemPreLine", inventoryShowItemPreLine, 1, 18);
        inventoryBlacklistVal = builder.defineList("blacklist", () -> Collections.singletonList("refinedstorage:disk_drive"), Predicates.alwaysTrue());
        bypassLockedContainerVal = builder.define("bypassLockedContainer", bypassLockedContainer);
        builder.pop();
        builder.push("customContainerName");
        onlyShowVanillaVal = builder.define("onlyShowVanilla", onlyShowVanilla);
        modBlacklistVal = builder.defineList("blacklist", () -> Collections.singletonList("thermal"), Predicates.alwaysTrue());


        this.addConfig(CONFIG_REGISTRY_NAME, false);
        this.addConfig(CONFIG_ENTITY_HEALTH, true);
        this.addConfig(CONFIG_ENTITY_ARMOR, true);
        this.addConfig(CONFIG_BLOCK_STATES, false);
        this.addConfig(CONFIG_MOD_NAME, true);
        this.addConfig(CONFIG_ITEM_MOD_NAME, false);

    }

    private static ResourceLocation newConfigResourceLocation(String path)
    {
        return new ResourceLocation(BCCore.MOD_ID, path);
    }

    public static final ResourceLocation CONFIG_REGISTRY_NAME = newConfigResourceLocation("registry_name");
    public static final ResourceLocation CONFIG_ENTITY_HEALTH = newConfigResourceLocation("entity_hp");
    public static final ResourceLocation CONFIG_ENTITY_ARMOR = newConfigResourceLocation("entity_armor");
    public static final ResourceLocation CONFIG_BLOCK_STATES = newConfigResourceLocation("block_states");
    public static final ResourceLocation CONFIG_MOD_NAME = newConfigResourceLocation("mod_name");
    public static final ResourceLocation CONFIG_ITEM_MOD_NAME = newConfigResourceLocation("item_mod_name");


    public void addConfig(ResourceLocation key, boolean defaultValue)
    {
        if (FMLEnvironment.dist.isClient())
        {
            PluginConfig.INSTANCE.addConfig(new ConfigEntry(key, defaultValue, false));
        }
    }

    // Calen
    private static void createConfigFile()
    {
        // Calen changed
        File forgeConfigFolder = FMLPaths.CONFIGDIR.get().toFile();
        File buildCraftConfigFolder = new File(forgeConfigFolder, "buildcraft");

        configFolder = buildCraftConfigFolder;
        config = new Configuration(new File(buildCraftConfigFolder, "main.cfg"));
        objConfig = RegistryConfig.setRegistryConfig(BCCore.MOD_ID, new File(buildCraftConfigFolder, "objects.cfg"));
        // Calen: thread safety
        // access the method to create
//        BCLibConfig.guiConfigFile = new File(buildCraftConfigFolder, "gui.json");
        BCLibConfig.getGuiConfigFile();

        detailedConfigManager = new FileConfigManager(
                " The buildcraft detailed configuration file. This contains a lot of miscellaneous options that have no "
                        + "affect on gameplay.\n You should refer to the BC source code for a detailed description of what these do. (https://github.com/BuildCraft/BuildCraft)\n"
                        + " This file will be overwritten every time that buildcraf starts, so don't change anything other than the values.");
        detailedConfigManager.setConfigFile(new File(buildCraftConfigFolder, "detailed.properties"));
    }

    public static void preInit()
    {
        // Calen
        getConfig(true); // ensure object created
//        // Calen changed
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
//                        + " This file will be overwritten every time that buildcraf starts, so don't change anything other than the values.");
//        detailedConfigManager.setConfigFile(new File(buildCraftConfigFolder, "detailed.properties"));

        // Variables to make
        String general = Configuration.CATEGORY_GENERAL;
        String display = "display";
        String worldgen = "worldgen";
        String performance = "performance";
//
        EnumRestartRequirement none = EnumRestartRequirement.NONE;
        EnumRestartRequirement world = EnumRestartRequirement.WORLD;
        EnumRestartRequirement game = EnumRestartRequirement.GAME;
//
//        // Jade
//
//        ConfigGeneral general = Waila.CONFIG.get().getGeneral();
//        if (showOverlay.isDown()) {
//            DisplayMode mode = general.getDisplayMode();
//            if (mode == WailaConfig.DisplayMode.TOGGLE) {
//                general.setDisplayTooltip(!general.shouldDisplayTooltip());
//                if (!general.shouldDisplayTooltip() && general.hintOverlayToggle) {
//                    SystemToast.add(Minecraft.getInstance().getToasts(), SystemToast.SystemToastIds.TUTORIAL_HINT, new TranslatableComponent("toast.jade.toggle_hint.1"), new TranslatableComponent("toast.jade.toggle_hint.2", showOverlay.getTranslatedKeyMessage()));
//                    general.hintOverlayToggle = false;
//                }
//                Waila.CONFIG.save();
//            }
//        }
//
        propColourBlindMode = getConfig(true).get(display, "colorBlindMode", false);
        propColourBlindMode.setComment("Should I enable colorblind mode?");
        none.setTo(propColourBlindMode);

        propWorldGen = getConfig(true).get(worldgen, "enable", true);
        propWorldGen.setComment("Should BuildCraft generate anything in the world?");
        game.setTo(propWorldGen);

        propWorldGenWaterSpring = getConfig(true).get(worldgen, "generateWaterSprings", true);
        propWorldGenWaterSpring.setComment("Should BuildCraft generate water springs?");
        game.setTo(propWorldGenWaterSpring);
//
        propMinePlayerProtected = getConfig(true).get(general, "miningBreaksPlayerProtectedBlocks", false);
        propMinePlayerProtected
                .setComment("Should BuildCraft miners be allowed to break blocks using player-specific protection?");
        none.setTo(propMinePlayerProtected);

        propUseColouredLabels = getConfig(true).get(display, "useColouredLabels", true);
        propUseColouredLabels.setComment("Should colours be displayed as their own (or a similar) colour in tooltips?");
        none.setTo(propUseColouredLabels);

        propUseHighContrastColouredLabels = getConfig(true).get(display, "useHighContrastColouredLabels", false);
        propUseHighContrastColouredLabels
                .setComment("Should colours displayed in tooltips use higher-contrast colours?");
        none.setTo(propUseHighContrastColouredLabels);

        propHidePower = getConfig(true).get(display, "hidePowerValues", false);
        propHidePower.setComment("Should all power values (MJ, MJ/t) be hidden?");
        none.setTo(propHidePower);

        propHideFluid = getConfig(true).get(display, "hideFluidValues", false);
        propHideFluid.setComment("Should all fluid values (Buckets, mB, mB/t) be hidden?");
        none.setTo(propHideFluid);

        propGuideBookEnableDetail = getConfig(true).get(display, "guideBookEnableDetail", false);
        none.setTo(propGuideBookEnableDetail);

        propGuideItemSearchLimit = getConfig(true).get(performance, "guideItemSearchLimit", 10_000);
        propGuideItemSearchLimit.setComment("The maximum number of items that the guide book will index.");
        propGuideItemSearchLimit.setMinValue(1_500);
        propGuideItemSearchLimit.setMaxValue(5_000_000);
        none.setTo(propGuideItemSearchLimit);

        propUseBucketsStatic = getConfig(true).get(display, "useBucketsStatic", true);
        propUseBucketsStatic.setComment(
                "Should static fluid values be displayed in terms of buckets rather than thousandths of a bucket? (B vs mB)");
        none.setTo(propUseBucketsStatic);

        propUseBucketsFlow = getConfig(true).get(display, "useBucketsFlow", true);
        propUseBucketsFlow.setComment(
                "Should flowing fluid values be displayed in terms of buckets per second rather than thousandths of a bucket per tick? (B/s vs mB/t)");
        none.setTo(propUseBucketsFlow);

        propUseLongLocalizedName = getConfig(true).get(display, "useLongLocalizedName", true);
        propUseLongLocalizedName.setComment(
                "Should localised strings be displayed in long or short form (10 mB / t vs 10 milli buckets per tick");
        none.setTo(propUseLongLocalizedName);

        propDisplayTimeGap = getConfig(true).get(display, "timeGap", TimeGap.SECONDS.name().toLowerCase(Locale.ROOT));
        propDisplayTimeGap
                .setComment("Should localised strings be displayed in terms of seconds (1 MJ/s) or ticks (20 MJ/t)");
        ConfigUtil.setEnumProperty(propDisplayTimeGap, TimeGap.values());
        none.setTo(propDisplayTimeGap);

        propUseSwappableSprites = getConfig(true).get(display, "useSwappableSprites", true);
        propUseSwappableSprites.setComment(
                "Disable this if you get texture errors with optifine. Disables some texture switching functionality "
                        + "when changing config options such as colour blind mode.");
        game.setTo(propUseSwappableSprites);

        propEnableAnimatedSprites = getConfig(true).get(performance, "enableAnimatedSprites", true);
        propEnableAnimatedSprites.setComment(
                "Disable this if you get sub-standard framerates due to buildcraftcore's ~60 sprites animating every frame.");
        none.setTo(propEnableAnimatedSprites);

        propMaxGuideSearchResults = getConfig(true).get(performance, "maxGuideSearchResults", 1200);
        propMaxGuideSearchResults.setComment("The maximum number of search results to display in the guide book.");
        propMaxGuideSearchResults.setMinValue(500).setMaxValue(5000);
        none.setTo(propMaxGuideSearchResults);

        propItemRenderRotation = getConfig(true).get(display, "itemRenderRotation", RenderRotation.ENABLED.name().toLowerCase(Locale.ROOT));
        propItemRenderRotation.setComment(
                "The rotation that items use when travelling through pipes. Set to 'enabled' for full rotation, "
                        + "'disabled' for no rotation, or 'horizontals_only' to only rotate items when going horizontally.");
        ConfigUtil.setEnumProperty(propItemRenderRotation, RenderRotation.values());

        propChunkLoadLevel = getConfig(true).get(general, "chunkLoadLevel", ChunkLoaderLevel.SELF_TILES.name().toLowerCase(Locale.ROOT));
        propChunkLoadLevel.setComment("");
        ConfigUtil.setEnumProperty(propChunkLoadLevel, ChunkLoaderLevel.values());
        world.setTo(propChunkLoadLevel);

        propItemLifespan = getConfig(true).get(general, "itemLifespan", 60);
        propItemLifespan.setMinValue(5).setMaxValue(600);
        propItemLifespan.setComment("How long, in seconds, should items stay on the ground? (Vanilla = 300, default = 60)");
        none.setTo(propItemLifespan);

        propPumpsConsumeWater = getConfig(true).get(general, "pumpsConsumeWater", false);
        propPumpsConsumeWater.setComment("Should pumps consume water? Enabling this will disable"
                + " minor optimisations, but work properly with finite water mods.");
        none.setTo(propPumpsConsumeWater);

        propMarkerMaxDistance = getConfig(true).get(general, "markerMaxDistance", 64);
        propMarkerMaxDistance.setMinValue(16).setMaxValue(256);
        propMarkerMaxDistance.setComment("How far, in minecraft blocks, should markers (volume and path) reach?");
        none.setTo(propMarkerMaxDistance);

        propPumpMaxDistance = getConfig(true).get(general, "pumpMaxDistance", 64);
        propPumpMaxDistance.setMinValue(16).setMaxValue(128);
        propPumpMaxDistance.setComment("How far, in minecraft blocks, should pumps reach in fluids?");
        none.setTo(propPumpMaxDistance);

        propNetworkUpdateRate = getConfig(true).get(general, "updateFactor", networkUpdateRate);
        propNetworkUpdateRate.setMinValue(1).setMaxValue(100);
        propNetworkUpdateRate.setComment(
                "How often, in ticks, should network update packets be sent? Increasing this might help network performance.");
        none.setTo(propNetworkUpdateRate);

        propMiningMultiplier = getConfig(true).get(general, "miningMultiplier", 1.0);
        propMiningMultiplier.setMinValue(1).setMaxValue(200);
        propMiningMultiplier.setComment("How much power should be required for all mining machines?");
        none.setTo(propMiningMultiplier);
//
        propMiningMaxDepth = getConfig(true).get(general, "miningMaxDepth", 512);
        propMiningMaxDepth.setMinValue(32).setMaxValue(4096);
        propMiningMaxDepth.setComment("How much further down can miners (like the quarry or the mining well) dig?"
                + "\n(Note: values above 256 only have an effect if a mod like cubic chunks is installed).");
        none.setTo(propMiningMaxDepth);

        reloadConfig(game);
        addReloadListener(BCCoreConfig::reloadConfig);

        MinecraftForge.EVENT_BUS.register(BCCoreConfig.class);
    }

    public static void addReloadListener(Consumer<EnumRestartRequirement> listener)
    {
        reloadListeners.add(listener);
    }

    @SubscribeEvent
    public static void onConfigChange(ModConfigEvent cce)
    {
//        if (BCModules.isBcMod(cce.getModID()))
        if (BCModules.isBcMod(cce.getConfig().getModId()))
        {
            EnumRestartRequirement req = EnumRestartRequirement.NONE;
//            if (Loader.instance().isInState(LoaderState.AVAILABLE))
            if (ModLoadingContext.get().getActiveContainer().getCurrentState() == ModLoadingStage.COMPLETE)
            {
                // The loaders state will be LoaderState.SERVER_STARTED when we are in a world
                req = EnumRestartRequirement.WORLD;
            }
            for (Consumer<EnumRestartRequirement> listener : reloadListeners)
            {
                listener.accept(req);
            }
        }
    }

    public static void postInit()
    {
//        ConfigUtil.setLang(config);
        ConfigUtil.setLang(getConfig(true));
        saveConfigs();
    }

    public static void saveConfigs()
    {
        if (getConfig(true).hasChanged())
        {
            getConfig(true).save();
        }
        if (getConfig(false).hasChanged())
        {
            getConfig(false).save();
        }
    }

    public static void reloadConfig(EnumRestartRequirement restarted)
    {
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

        if (EnumRestartRequirement.WORLD.hasBeenRestarted(restarted))
        {
            BCLibConfig.chunkLoadingLevel =
                    ConfigUtil.parseEnumForConfig(propChunkLoadLevel, BCLibConfig.ChunkLoaderLevel.SELF_TILES);

            if (EnumRestartRequirement.GAME.hasBeenRestarted(restarted))
            {
                worldGen = propWorldGen.getBoolean();
                worldGenWaterSpring = propWorldGenWaterSpring.getBoolean();
                BCLibConfig.useSwappableSprites = propUseSwappableSprites.getBoolean();
            }
        }
        BCLibConfig.refreshConfigs();
        saveConfigs();
    }
}
