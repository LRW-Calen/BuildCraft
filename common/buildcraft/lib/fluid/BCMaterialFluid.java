/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.fluid;


import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

//public class BCMaterialFluid extends MaterialLiquid
public class BCMaterialFluid extends Material {
    //    public BCMaterialFluid(MapColor color, boolean canBurn)
    public BCMaterialFluid(MaterialColor color, boolean canBurn) {
        super(color, /*liquid*/ true, /*solid*/ false, /*blocksMotion*/ false, /*solidBlocking*/ false, /*flammable*/ canBurn, /*replaceable*/ false, /*pushReaction*/ PushReaction.NORMAL);
//        if (canBurn)
//        {
//            setBurning();
//        }
    }

    @Override
//    public boolean blocksMovement()
    public boolean blocksMotion() {
        return true;
    }
}
