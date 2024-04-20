/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.factory.block;

import buildcraft.lib.block.BlockBCBase_Neptune;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTube extends BlockBCBase_Neptune {
    //    private static final AABB BOUNDING_BOX = new AABB(4 / 16D, 0 / 16D, 4 / 16D, 12 / 16D, 16 / 16D, 12 / 16D);
    private static final VoxelShape BOUNDING_BOX = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BlockTube(String id, Properties props) {
        super(id, props);
    }

    @Override
//    public boolean isOpaqueCube(BlockState state)
    public boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }
//
//    @Override
//    public boolean isFullCube(BlockState state)
//    {
//        return false;
//    }

    // Calen: This will cause fake block without update to client. Moved to BCFactoryForgeBusEventDist.class
//    @Override
////    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
//    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
//    {
//        BlockPos currentPos = pos;
//        // noinspection StatementWithEmptyBody
//        while (world.getBlockState(currentPos = currentPos.above()).getBlock() == this)
//        {
//        }
//        if (!(world.getBlockEntity(currentPos) instanceof TileMiner))
//        {
//            return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
//        }
//        else
//        {
////            world.setBlock(pos, state, world.isClientSide ? 11 : 3); // Calen added 参考原版此方法
//            world.setBlockAndUpdate(pos, state); // Calen added 这样也没用……
//            return false;
//        }
//    }

    @Override
//    public AABB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos)
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return BOUNDING_BOX;
    }
}
