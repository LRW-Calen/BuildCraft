/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.builders;

import buildcraft.api.BCModules;
import buildcraft.lib.config.BCConfig;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;
import buildcraft.lib.misc.MathUtil;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;

public class BCBuildersConfig {
    private static Configuration config;

    /** Blueprints that save larger than this are stored externally, smaller ones are stored directly in the item. */
    public static int bptStoreExternalThreshold = 20_000;

    /** The minimum height that all quarry frames must be. */
    public static int quarryFrameMinHeight = 4;

    /** If true then the frame will move with the drill in both axis, if false then only 1 axis will follow the
     * drill. */
    public static boolean quarryFrameMoveBoth;

    public static int quarryMaxTasksPerTick = 4;
    public static int quarryTaskPowerDivisor = 2;
    public static double quarryMaxFrameMoveSpeed = 0;
    public static double quarryMaxBlockMineRate = 0;

    /** Client-side config to enable stencils-based drawing for the architect table. */
    public static boolean enableStencil = true;

    private static IntValue propBptStoreExternalThreshold;
    private static IntValue propQuarryFrameMinHeight;
    private static BooleanValue propQuarryFrameMoveBoth;
    private static IntValue propQuarryMaxTasksPerTick;
    private static IntValue propQuarryPowerDivisor;
    private static DoubleValue propQuarryMaxFrameSpeed;
    private static DoubleValue propQuarryMaxBlockMineRate;
    private static BooleanValue propEnableStencil;

    static BooleanValue internalStencilCrashTest;

    public static void preInit() {
//        Configuration config = BCCoreConfig.config;
        BCModules module = BCModules.BUILDERS;
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config = new Configuration(builder, module);
        createProps();
        ForgeConfigSpec spec = config.build();
        ModContainer container = ModList.get().getModContainerById(module.getModId()).get();
        container.addConfig(new ModConfig(ModConfig.Type.COMMON, spec, container, config.getFileName()));

//        BCCoreConfig.config.getCategory("internal").setShowInGui(false);
//        BCCoreConfig.saveConfigs();
//        reloadConfig(EnumRestartRequirement.GAME);
        reloadConfig();
//        BCCoreConfig.addReloadListener(BCBuildersConfig::reloadConfig);
        BCConfig.registerReloadListener(module, BCBuildersConfig::reloadConfig);
    }

    public static void createProps() {
        EnumRestartRequirement none = EnumRestartRequirement.NONE;
//        EnumRestartRequirement game = EnumRestartRequirement.GAME;

        String general = "general";
        String display = "display";
        String internal = "internal";

        propBptStoreExternalThreshold = config
                .defineInRange(general,
                        "",
                        none,
                        "bptStoreExternalThreshold", 20_000);

        propQuarryFrameMinHeight = config
                .defineInRange(general,
                        "The minimum height that all quarry frames must be. A value of 1 will look strange when it drills the uppermost layer.",
                        none,
                        "quarryFrameMinHeight", 4, 1);

        propQuarryMaxTasksPerTick = config
                .defineInRange(general,
                        "The maximum number of tasks that the quarry will do per tick."
                                + "\n(Where a task is either breaking a block, or moving the frame)",
                        none,
                        "quarryMaxTasksPerTick", 4, 1, 20);

        propQuarryPowerDivisor = config
                .defineInRange(general,
                        "1 divided by this value is added to the power cost for each additional task done per tick."
                                + "\nA value of 0 disables this behaviour.",
                        none,
                        "quarryPowerDivisor", 2, 0, 100);

        propQuarryMaxFrameSpeed = config
                .defineInRange(general,
                        "The maximum number of blocks that a quarry is allowed to move, per second."
                                + "\nA value of 0 means no limit.",
                        none,
                        "quarryMaxFrameSpeed", 0.0, 0.0, 5120.0);

        propQuarryMaxBlockMineRate = config
                .defineInRange(general,
                        "The maximum number of blocks that the quarry is allowed to mine each second."
                                + "\nA value of 0 means no limit, and a value of 0.5 will mine up to half a block per second.",
                        none,
                        "quarryMaxFrameSpeed", 0.0, 0.0, 1000.0);

        propQuarryFrameMoveBoth = config
                .define(display,
                        "If true then the quarry frame will move with both of its axis rather than just one.",
                        none,
                        "quarryFrameMoveBoth", false);

        propEnableStencil = config
                .define(display,
                        "If true then the architect table will correctly hide it's translucent parts behind surrounding terrain. (This looks better)",
                        none,
                        "enableStencil", true);

        internalStencilCrashTest = config
                .define(internal,
                        "Use display.enableStencil instead of this!",
                        none,
                        "force_disable_stencil", false);
    }

    // public static void reloadConfig(EnumRestartRequirement restarted)
    public static void reloadConfig() {
        bptStoreExternalThreshold = propBptStoreExternalThreshold.get();
        quarryFrameMinHeight = propQuarryFrameMinHeight.get();
        quarryFrameMoveBoth = propQuarryFrameMoveBoth.get();
        enableStencil = propEnableStencil.get();
        quarryMaxTasksPerTick = MathUtil.clamp(propQuarryMaxTasksPerTick.get(), 0, 20);
        quarryTaskPowerDivisor = MathUtil.clamp(propQuarryPowerDivisor.get(), 0, 100);
        quarryMaxFrameMoveSpeed = MathUtil.clamp(propQuarryMaxFrameSpeed.get(), 0, 5120.0);
        quarryMaxBlockMineRate = MathUtil.clamp(propQuarryMaxBlockMineRate.get(), 0, 1000.0);
    }
}
