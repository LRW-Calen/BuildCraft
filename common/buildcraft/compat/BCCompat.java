package buildcraft.compat;

import buildcraft.api.core.BCLog;
import buildcraft.compat.module.crafttweaker.CompatModuleCraftTweaker;
import buildcraft.compat.module.ic2.CompatModuleIndustrialCraft2;
import buildcraft.compat.module.theoneprobe.CompatModuleTheOneProbe;
import buildcraft.core.BCCoreConfig;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge_1_12_2.common.config.Property;

//@Mod(
//        modid = "buildcraftcompat",
//        name = "BuildCraft Compat",
//        version = "7.99.24.8",
//        updateJSON = "https://mod-buildcraft.com/version/versions-compat.json",
//        acceptedMinecraftVersions = "[1.12.2]",
//        dependencies = "required-after:forge@[14.23.0.2544,);required-after:buildcraftcore@[7.99.24.8,);after:buildcrafttransport;after:buildcraftbuilders;after:buildcraftsilicon;after:theoneprobe;after:forestry;after:crafttweaker;after:ic2"
//)
@Mod(BCCompat.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCCompat
{
    static final String DEPENDENCIES = "required-after:forge@[14.23.0.2544,);required-after:buildcraftcore@[7.99.24.8,);after:buildcrafttransport;after:buildcraftbuilders;after:buildcraftsilicon;after:theoneprobe;after:forestry;after:crafttweaker;after:ic2";
    public static final String MOD_ID = "buildcraftcompat";
    public static final String VERSION = "7.99.24.9";
    public static final String GIT_BRANCH = "8.0.x-1.12.2";
    public static final String GIT_COMMIT_HASH = "16adfdb3d6a3362ba3659be7d5e9b7d12af7eee5";
    public static final String GIT_COMMIT_MSG = "Bump for release.";
    public static final String GIT_COMMIT_AUTHOR = "AlexIIL";
    //    @Instance("buildcraftcompat")
    public static BCCompat instance;
    private static final Map<String, CompatModuleBase> modules = new HashMap();

    public BCCompat()
    {
        instance = this;
    }

    @SubscribeEvent
//    public static void preInit(FMLPreInitializationEvent evt)
    public static void preInit(FMLConstructModEvent evt)
    {
        BCLog.logger.info("");
        BCLog.logger.info("Starting BuildCraftCompat 7.99.24.8");
        BCLog.logger.info("Copyright (c) the BuildCraft team, 2011-2017");
        BCLog.logger.info("https://www.mod-buildcraft.com");
        if (!"16adfdb3d6a3362ba3659be7d5e9b7d12af7eee5".startsWith("${"))
        {
            BCLog.logger.info("Detailed Build Information:");
            BCLog.logger.info("  Branch 8.0.x-1.12.2");
            BCLog.logger.info("  Commit 16adfdb3d6a3362ba3659be7d5e9b7d12af7eee5");
            BCLog.logger.info("    Bump for release.");
            BCLog.logger.info("    committed by AlexIIL");
        }

        BCLog.logger.info("");
        BCLog.logger.info("[compat] Module list:");
        // TODO Calen Forestry
//        offerAndPreInitModule(new CompatModuleForestry());
        offerAndPreInitModule(new CompatModuleTheOneProbe());
        offerAndPreInitModule(new CompatModuleCraftTweaker());
        offerAndPreInitModule(new CompatModuleIndustrialCraft2());
    }

    @SubscribeEvent
//    public static void init(FMLInitializationEvent evt)
    public static void init(FMLCommonSetupEvent evt)
    {
        // TODO Calen compat GUI???
//        NetworkRegistry.INSTANCE.registerGuiHandler(instance, CompatGui.guiHandlerProxy);
        for (CompatModuleBase m : modules.values())
        {
            m.init();
        }
    }

    @SubscribeEvent
//    public static void postInit(FMLPostInitializationEvent evt)
    public static void postInit(FMLLoadCompleteEvent evt)
    {
        for (CompatModuleBase m : modules.values())
        {
            m.postInit();
        }
    }

    private static void offerAndPreInitModule(CompatModuleBase module)
    {
        String cModId = module.compatModId();
        if (module.canLoad())
        {
            Property prop = BCCoreConfig.getConfig(true).get("modules", cModId, true);
            if (prop.getBoolean(true))
            {
                modules.put(cModId, module);
                BCLog.logger.info("[compat]   + " + cModId);
                module.preInit();
            }
            else
            {
                BCLog.logger.info("[compat]   x " + cModId + " (It has been disabled in the config)");
            }
        }
        else
        {
            BCLog.logger.info("[compat]   x " + cModId + " (It cannot load)");
        }

    }
}
