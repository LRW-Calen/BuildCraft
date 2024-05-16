/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 * <p/>
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.transport.stripes;

import buildcraft.api.transport.IStripesActivator;
import buildcraft.api.transport.IStripesHandlerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public enum StripesHandlerPlaceBlock implements IStripesHandlerItem {
    INSTANCE;

    @Override
    public boolean handle(World world,
                          BlockPos pos,
                          Direction direction,
                          ItemStack stack,
                          PlayerEntity player,
                          IStripesActivator activator) {
        if (!(stack.getItem() instanceof BlockItem)) {
            return false;
        }
        if (!world.isEmptyBlock(pos.relative(direction))) {
            return false;
        }
//        stack.getItem().onItemUse(
        stack.onItemUseFirst(
//                player,
//                world,
//                pos.offset(direction),
//                EnumHand.MAIN_HAND,
//                direction,
//                0.5f,
//                0.5f,
//                0.5f
                new ItemUseContext(
                        world,
                        player,
                        Hand.MAIN_HAND,
                        stack,
                        new BlockRayTraceResult(
                                new Vector3d(0, 0, 0),
                                direction,
                                pos.relative(direction),
                                false
                        )
                )
        );
        return true;
    }
}
