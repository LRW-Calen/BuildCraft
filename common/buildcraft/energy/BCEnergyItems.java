package buildcraft.energy;

import buildcraft.energy.item.ItemOilPlacer;
import buildcraft.lib.item.ItemBC_Neptune;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class BCEnergyItems {
    private static final RegistrationHelper HELPER = new RegistrationHelper(BCEnergy.MODID);
//    public static Item.Properties BC_ENERGY_BLOCKITEM_DEFAULT_PROP =
//            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB);
//    public static Item.Properties BC_ENERGY_BLOCKITEM_PROP_NO_TAB =
//            new Item.Properties();
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NameSpaces.BUILDCRAFT_ENERGY);

    // 油井放置器
    public static final RegistryObject<Item> oilPlacer;
    // 油井放置器
    public static final RegistryObject<Item> globOil;

    static {
        globOil = HELPER.addItem(
                "item.glob_oil",
                new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .stacksTo(64),
                ItemBC_Neptune::new
        );
        oilPlacer = HELPER.addItem(
                "item.oil_placer",
                new Item.Properties()
//                        .tab(BCCreativeTab.BC_MAIN_TAB)
                        .rarity(Rarity.RARE)
                        .stacksTo(1),
                ItemOilPlacer::new
        );
    }

    public static void preInit() {

    }
}
