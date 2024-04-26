/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon;

import buildcraft.api.BCModules;
import buildcraft.core.BCCoreConfig;
import buildcraft.lib.config.Configuration;
import buildcraft.lib.config.EnumRestartRequirement;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class BCSiliconConfig {

    public static boolean renderLaserBeams = true;
    public static boolean differStatesOfNoteBlockForFacade = false;

    private static BooleanValue propRenderLaserBeams;
    private static BooleanValue propDifferStatesOfNoteBlockForFacade;

    public static void preInit() {

        Configuration config = BCCoreConfig.config;
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

        config.build();

        reloadConfig(EnumRestartRequirement.NONE);
        MinecraftForge.EVENT_BUS.register(BCSiliconConfig.class);
    }

    public static void reloadConfig(EnumRestartRequirement restarted) {
        renderLaserBeams = propRenderLaserBeams.get();
        differStatesOfNoteBlockForFacade = propDifferStatesOfNoteBlockForFacade.get();
    }

    @SubscribeEvent
//    public static void onConfigChange(OnConfigChangedEvent cce)
    public static void onConfigChange(ModConfigEvent.Reloading cce) {
        if (BCModules.isBcMod(cce.getConfig().getModId())) {
            reloadConfig(EnumRestartRequirement.NONE);
        }
    }
}
