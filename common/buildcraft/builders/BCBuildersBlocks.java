/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.builders;

import buildcraft.builders.block.*;
import buildcraft.builders.tile.*;
import buildcraft.lib.block.BlockPropertiesCreater;
import buildcraft.lib.registry.RegistrationHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class BCBuildersBlocks
{

    private static final RegistrationHelper HELPER = new RegistrationHelper(BCBuilders.MOD_ID);

    public static RegistryObject<BlockFiller> filler;
    public static RegistryObject<BlockBuilder> builder;
    public static RegistryObject<BlockArchitectTable> architect;
    public static RegistryObject<BlockElectronicLibrary> library;
    public static RegistryObject<BlockReplacer> replacer;

    public static RegistryObject<BlockFrame> frame;
    public static RegistryObject<BlockQuarry> quarry;
    public static RegistryObject<BlockEntityType<TileFiller>> fillerTile;
    public static RegistryObject<BlockEntityType<TileBuilder>> builderTile;
    public static RegistryObject<BlockEntityType<TileArchitectTable>> architectTile;
    public static RegistryObject<BlockEntityType<TileElectronicLibrary>> libraryTile;
    public static RegistryObject<BlockEntityType<TileReplacer>> replacerTile;
    public static RegistryObject<BlockEntityType<TileQuarry>> quarryTile;

    public static void fmlPreInit()
    {
        filler = HELPER.addBlockAndItem("block.filler", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockFiller::new);
        builder = HELPER.addBlockAndItem("block.builder", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockBuilder::new);
        architect = HELPER.addBlockAndItem("block.architect", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockArchitectTable::new);
        library = HELPER.addBlockAndItem("block.library", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockElectronicLibrary::new);
        replacer = HELPER.addBlockAndItem("block.replacer", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockReplacer::new);

        frame = HELPER.addBlockAndItem("block.frame", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockFrame::new);
        quarry = HELPER.addBlockAndItem("block.quarry", BlockPropertiesCreater.createDefaultProperties(Material.METAL), BlockQuarry::new);

        fillerTile = HELPER.registerTile("tile.filler", TileFiller::new, filler);
        builderTile = HELPER.registerTile("tile.builder", TileBuilder::new, builder);
        architectTile = HELPER.registerTile("tile.architect", TileArchitectTable::new, architect);
        libraryTile = HELPER.registerTile("tile.library", TileElectronicLibrary::new, library);
        replacerTile = HELPER.registerTile("tile.replacer", TileReplacer::new, replacer);
        quarryTile = HELPER.registerTile("tile.quarry", TileQuarry::new, quarry);
    }
}
