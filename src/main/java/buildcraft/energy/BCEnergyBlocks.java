/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.energy;


import buildcraft.api.enums.EnumEngineType;
import buildcraft.api.enums.EnumSpring;
import buildcraft.energy.tile.TileSpringOil;
import buildcraft.energy.tile.TileEngineIron_BC8;
import buildcraft.energy.tile.TileEngineStone_BC8;
import buildcraft.core.block.BlockEngine_BC8;
import buildcraft.core.BCCoreBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class BCEnergyBlocks
{


    // Calen: 1.12.2 use core namespace with meta
    // 斯特林引擎
    public static final RegistryObject<BlockEngine_BC8> engineStone;
    // 燃油引擎
    public static final RegistryObject<BlockEngine_BC8> engineIron;


    // 泉
    public static final RegistryObject<BlockEntityType<TileSpringOil>> springTile;
    // 斯特林引擎
    public static final RegistryObject<BlockEntityType<TileEngineStone_BC8>> engineStoneTile;
    // 燃油引擎
    public static final RegistryObject<BlockEntityType<TileEngineIron_BC8>> engineIronTile;

    static
    {
        if (BCCoreBlocks.engineWood != null)
        {
            engineStone = BCCoreBlocks.registerEngine(EnumEngineType.STONE, TileEngineStone_BC8::new);
            engineIron = BCCoreBlocks.registerEngine(EnumEngineType.IRON, TileEngineIron_BC8::new);
        }
        else
        {
            engineStone = null;
            engineIron = null;
        }

//        EnumSpring.OIL.liquidBlock = BCEnergyFluids.crudeOil[0].getBlock().getDefaultState();
        EnumSpring.OIL.tileConstructor = TileSpringOil::new;

        springTile = BCCoreBlocks.HELPER.registerTile("tile.spring.oil", TileSpringOil::new, BCCoreBlocks.springOil);
        engineStoneTile = BCCoreBlocks.HELPER.registerTile( "tile.engine.stone", TileEngineStone_BC8::new, BCEnergyBlocks.engineStone);
        engineIronTile = BCCoreBlocks.HELPER.registerTile("tile.engine.iron", TileEngineIron_BC8::new, BCEnergyBlocks.engineIron);
    }

    public static void preInit()
    {

    }
}
