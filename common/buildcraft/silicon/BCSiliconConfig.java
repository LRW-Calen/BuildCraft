/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon;

import buildcraft.api.BCModules;
import buildcraft.lib.config.BCConfig;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;

public class BCSiliconConfig {
    private static Configuration config;

    public static boolean renderLaserBeams = true;
    public static boolean differStatesOfNoteBlockForFacade = false;

    private static BooleanValue propRenderLaserBeams;
    private static BooleanValue propDifferStatesOfNoteBlockForFacade;

    public static void preInit() {
//        Configuration config = BCCoreConfig.config;
        BCModules module = BCModules.SILICON;
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        config = new Configuration(builder, module);
        createProps();
        ForgeConfigSpec spec = config.build();
        ModContainer container = ModList.get().getModContainerById(module.getModId()).get();
        container.addConfig(new ModConfig(ModConfig.Type.COMMON, spec, container, config.getFileName()));

//        reloadConfig(EnumRestartRequirement.NONE);
        reloadConfig();
//        MinecraftForge.EVENT_BUS.register(BCSiliconConfig.class);
        BCConfig.registerReloadListener(module, BCSiliconConfig::reloadConfig);
    }

    public static void createProps() {
        String display = "display";

        propRenderLaserBeams = config
                .define(display,
                        "When false laser beams will not be visible while transmitting power without wearing Goggles",
                        EnumRestartRequirement.NONE,
                        "renderLaserBeams", true);
        propDifferStatesOfNoteBlockForFacade = config
                .define(display,
                        "If different textures in resource packs are used for different instruments and notes, or whether powered, please set this [true]",
                        EnumRestartRequirement.WORLD,
                        "differStatesOfNoteBlockForFacade", false);
    }

    // public static void reloadConfig(EnumRestartRequirement restarted)
    public static void reloadConfig() {
        renderLaserBeams = propRenderLaserBeams.get();
        differStatesOfNoteBlockForFacade = propDifferStatesOfNoteBlockForFacade.get();
    }

//    @SubscribeEvent
//    public static void onConfigChange(OnConfigChangedEvent cce) {
//        if (BCModules.isBcMod(cce.getModID())) {
//            reloadConfig(EnumRestartRequirement.NONE);
//        }
//    }
}
