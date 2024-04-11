/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.factory;

import buildcraft.factory.item.ItemWaterGel;
import buildcraft.lib.item.ItemBC_Neptune;
import buildcraft.lib.BCLib;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class BCFactoryItems
{

    private static final RegistrationHelper HELPER = new RegistrationHelper(BCFactory.MOD_ID);

    public static final RegistryObject<ItemBC_Neptune> plasticSheet;
    public static final RegistryObject<ItemWaterGel> waterGel;
    public static final RegistryObject<ItemBC_Neptune> gelledWater;

    public static final Item.Properties PROP_DEFAULT =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .stacksTo(64)
                    .rarity(Rarity.COMMON);

    static
    {
        if (BCLib.DEV)
        {
            plasticSheet = HELPER.addItem("item.plastic.sheet", PROP_DEFAULT, ItemBC_Neptune::new);
        }
        else
        {
            plasticSheet = null;
        }
        waterGel = HELPER.addItem(
                "item.water_gel_spawn",
                new Item.Properties()
//                        .tab(BCCreativeTab.BC_MAIN_TAB)
                        .stacksTo(16)
                        .rarity(Rarity.COMMON)
                ,
                ItemWaterGel::new
        );
        gelledWater = HELPER.addItem("item.gel", PROP_DEFAULT, ItemBC_Neptune::new);
    }


    public static void init()
    {

    }
}
