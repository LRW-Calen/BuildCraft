/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon;

import buildcraft.api.BCModules;
import buildcraft.core.BCCoreConfig;
import buildcraft.lib.config.EnumRestartRequirement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge_1_12_2.common.config.Configuration;
import net.minecraftforge_1_12_2.common.config.Property;

public class BCSiliconConfig
{

    public static boolean renderLaserBeams = true;
    // Calen
    public static boolean differStatesOfNoteBlockForFacade = false;

    private static Property propRenderLaserBeams;
    // Calen
    private static Property propDifferStatesOfNoteBlockForFacade;

    public static void preInit()
    {

//        Configuration config = BCCoreConfig.config;
        Configuration config = BCCoreConfig.getConfig(true);
        propRenderLaserBeams = config.get("display", "renderLaserBeams", true,
                "When false laser beams will not be visible while transmitting power without wearing Goggles");
        // Calen
        propDifferStatesOfNoteBlockForFacade = config.get("display", "differStatesOfNoteBlockForFacade", false,
                "If different textures in resource packs are used for different instruments and notes, or whether powered, please set this [true]");

        reloadConfig(EnumRestartRequirement.NONE);
        MinecraftForge.EVENT_BUS.register(BCSiliconConfig.class);
    }

    public static void reloadConfig(EnumRestartRequirement restarted)
    {
        renderLaserBeams = propRenderLaserBeams.getBoolean();
        differStatesOfNoteBlockForFacade = propDifferStatesOfNoteBlockForFacade.getBoolean();
    }

    @SubscribeEvent
//    public static void onConfigChange(OnConfigChangedEvent cce)
    public static void onConfigChange(ModConfigEvent.Reloading cce)
    {
        if (BCModules.isBcMod(cce.getConfig().getModId()))
        {
            reloadConfig(EnumRestartRequirement.NONE);
        }
    }
}
