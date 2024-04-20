/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.builders;

import buildcraft.api.enums.EnumSnapshotType;
import buildcraft.builders.item.ItemFillerPlanner;
import buildcraft.builders.item.ItemSchematicSingle;
import buildcraft.builders.item.ItemSnapshot;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class BCBuildersItems {
    private static final RegistrationHelper HELPER = new RegistrationHelper(BCBuilders.MODID);

    public static RegistryObject<ItemSnapshot> snapshotBLUEPRINT;
    //    public static RegistryObject<ItemSnapshot> snapshotBLUEPRINT_CLEAN;
//    public static RegistryObject<ItemSnapshot> snapshotBLUEPRINT_USED;
    public static RegistryObject<ItemSnapshot> snapshotTEMPLATE;
    //    public static RegistryObject<ItemSnapshot> snapshotTEMPLATE_CLEAN;
//    public static RegistryObject<ItemSnapshot> snapshotTEMPLATE_USED;
    public static RegistryObject<ItemSchematicSingle> schematicSingle;
    public static RegistryObject<ItemFillerPlanner> addonFillerPlanner;

    public static void fmlPreInit() {
        snapshotBLUEPRINT = HELPER.addItem("item.snapshot.blueprint", PROP_DEFAULT, (idBC, prop) -> new ItemSnapshot(idBC, prop, EnumSnapshotType.BLUEPRINT));
//        snapshotBLUEPRINT_CLEAN = HELPER.addItem("item.snapshot.blueprint.clean", PROP_DEFAULT, (idBC, prop) -> new ItemSnapshot(idBC, prop, ItemSnapshot.EnumItemSnapshotType.BLUEPRINT_CLEAN));
//        snapshotBLUEPRINT_USED = HELPER.addItem("item.snapshot.blueprint.used", PROP_STACK1, (idBC, prop) -> new ItemSnapshot(idBC, prop, ItemSnapshot.EnumItemSnapshotType.BLUEPRINT_USED));
        snapshotTEMPLATE = HELPER.addItem("item.snapshot.template", PROP_DEFAULT, (idBC, prop) -> new ItemSnapshot(idBC, prop, EnumSnapshotType.TEMPLATE));
//        snapshotTEMPLATE_CLEAN = HELPER.addItem("item.snapshot.template.clean", PROP_DEFAULT, (idBC, prop) -> new ItemSnapshot(idBC, prop, ItemSnapshot.EnumItemSnapshotType.TEMPLATE_CLEAN));
//        snapshotTEMPLATE_USED = HELPER.addItem("item.snapshot.template.used", PROP_STACK1, (idBC, prop) -> new ItemSnapshot(idBC, prop, ItemSnapshot.EnumItemSnapshotType.TEMPLATE_USED));
        schematicSingle = HELPER.addItem(
                "item.schematic.single",
                PROP_STACK1,
                ItemSchematicSingle::new
        );
        addonFillerPlanner = HELPER.addItem(
                "item.filler_planner",
                PROP_DEFAULT,
                ItemFillerPlanner::new
        );
    }


    public static Item.Properties PROP_DEFAULT =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .stacksTo(64)
                    .rarity(Rarity.COMMON);
    public static Item.Properties PROP_STACK1 =
            new Item.Properties()
//                    .tab(BCCreativeTab.BC_MAIN_TAB)
                    .stacksTo(1)
                    .rarity(Rarity.COMMON);
}
