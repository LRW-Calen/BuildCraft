/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.robotics;

import buildcraft.core.BCCore;
import buildcraft.lib.registry.RegistryConfig;
import buildcraft.lib.registry.TagManager;
import buildcraft.lib.registry.TagManager.EnumTagType;
import buildcraft.lib.registry.TagManager.TagEntry;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.function.Consumer;

//@formatter:off
//@Mod(
//    modid = BCRobotics.MODID,
//    name = "BuildCraft Robotics",
//    version = BCLib.VERSION,
//    dependencies = "required-after:buildcraftcore@[" + BCLib.VERSION + "]"
//)
@Mod(BCRobotics.MODID)
@Mod.EventBusSubscriber(modid = BCRobotics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//@formatter:on
public class BCRobotics {
    public static final String MODID = "buildcraftrobotics";

    // @Mod.Instance(MODID)
    public static BCRobotics INSTANCE = null;

    public BCRobotics() {
        INSTANCE = this;
    }

    @SubscribeEvent
//    public static void preInit(FMLPreInitializationEvent evt)
    public static void preInit(FMLConstructModEvent evt) {
        RegistryConfig.useOtherModConfigFor(MODID, BCCore.MODID);

        BCRoboticsBlocks.preInit();
        BCRoboticsItems.preInit();

        BCRoboticsProxy.getProxy().fmlPreInit();

//        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, BCRoboticsProxy.getProxy());
    }

    @SubscribeEvent
//    public static void init(FMLInitializationEvent evt)
    public static void init(FMLCommonSetupEvent evt) {
        BCRoboticsProxy.getProxy().fmlInit();
//        BCRoboticsRecipes.init(); // 1.18.2: datagen
    }

    @SubscribeEvent
//    public static void postInit(FMLPostInitializationEvent evt)
    public static void postInit(FMLLoadCompleteEvent evt) {
        BCRoboticsProxy.getProxy().fmlPostInit();
    }

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public static void onTesrReg(RegisterRenderers event) {
//        ClientRegistry.bindTileEntityRenderer(BCRoboticsBlocks.zonePlannerTile.get(), RenderZonePlanner::new);
//    }

    @SubscribeEvent
    public static void registerGui(RegistryEvent.Register<ContainerType<?>> event) {
        BCRoboticsMenuTypes.registerAll(event);
    }

    // Calen: for thread safety
    private static final TagManager tagManager = new TagManager();


    static {
        startBatch();

        // Items

        // Item Blocks
        registerTag("item.block.zone_planner").reg("zone_planner").locale("zonePlannerBlock");
//                .model("zone_planner");

        // Blocks
//        registerTag("block.zone_planner").reg("zone_planner").oldReg("zonePlannerBlock").locale("zonePlannerBlock").model("zone_planner");
        registerTag("block.zone_planner").reg("zone_planner").locale("zonePlannerBlock");
//                .model("zone_planner");

        // Tiles
        registerTag("tile.zone_planner").reg("zone_planner");

//        endBatch(TagManager.prependTags("buildcraftrobotics:", EnumTagType.REGISTRY_NAME, EnumTagType.MODEL_LOCATION).andThen(TagManager.setTab("buildcraft.main")));
        endBatch(TagManager.prependTags("buildcraftrobotics:", EnumTagType.REGISTRY_NAME).andThen(TagManager.setTab("buildcraft.main")));
    }

    private static TagEntry registerTag(String id) {
//        return TagManager.registerTag(id);
        return tagManager.registerTag(id);
    }

    private static void startBatch() {
//        TagManager.startBatch();
        tagManager.startBatch();
    }

    private static void endBatch(Consumer<TagEntry> consumer) {
//        TagManager.endBatch(consumer);
        tagManager.endBatch(consumer);
    }
}
