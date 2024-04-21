package buildcraft.energy;

import buildcraft.energy.item.ItemOilPlacer;
import buildcraft.lib.item.ItemBC_Neptune;
import buildcraft.lib.item.ItemPropertiesCreator;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class BCEnergyItems {
    private static final RegistrationHelper HELPER = new RegistrationHelper(BCEnergy.MODID);

    public static final RegistryObject<Item> oilPlacer;
    public static final RegistryObject<Item> globOil;

    static {
        globOil = HELPER.addItem("item.glob_oil", ItemPropertiesCreator.epic64(), ItemBC_Neptune::new);
        oilPlacer = HELPER.addItem("item.oil_placer", ItemPropertiesCreator.rare1(), ItemOilPlacer::new);
    }

    public static void preInit() {

    }
}
