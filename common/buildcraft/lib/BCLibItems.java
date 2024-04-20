package buildcraft.lib;

import buildcraft.lib.item.ItemDebugger;
import buildcraft.lib.item.ItemGuide;
import buildcraft.lib.item.ItemGuideNote;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class BCLibItems {
    private static final RegistrationHelper HELPER = new RegistrationHelper(BCLib.MODID);
    public static Item.Properties BC_LIB_ITEM_DEFAULT_PROP =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .stacksTo(64)
                    .rarity(Rarity.COMMON);

    private static boolean enableGuide, enableDebugger;

    public static void enableGuide() {
        enableGuide = true;
    }

    public static void enableDebugger() {
        enableDebugger = true;
    }

    public static boolean isGuideEnabled() {
        return enableGuide;
    }

    public static boolean isDebuggerEnabled() {
        return enableDebugger;
    }

    // 书
    public static RegistryObject<Item> guide;
    public static RegistryObject<Item> guideNote;
    public static RegistryObject<Item> debugger;


    // Calen: 不能statc 因为要在主类static里enable 触发这里的static
    public static void fmlPreInit() {
        if (isGuideEnabled()) {
            guide = HELPER.addForcedItem("item.guide", BC_LIB_ITEM_DEFAULT_PROP, ItemGuide::new);
            guideNote = HELPER.addForcedItem("item.guide.note", BC_LIB_ITEM_DEFAULT_PROP, ItemGuideNote::new);
        }
        if (isDebuggerEnabled()) {
            debugger = HELPER.addForcedItem("item.debugger", BC_LIB_ITEM_DEFAULT_PROP, ItemDebugger::new);
        }
    }

}
