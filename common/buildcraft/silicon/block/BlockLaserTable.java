/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon.block;

import buildcraft.api.enums.EnumLaserTableType;
import buildcraft.api.mj.ILaserTargetBlock;
import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.block.IBlockWithTickableTE;
import buildcraft.lib.misc.MessageUtil;
import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.silicon.tile.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//@ILaserTargetAnnotation // Calen
public class BlockLaserTable extends BlockBCTile_Neptune<TileLaserTableBase> implements ILaserTargetBlock, IBlockWithTickableTE<TileLaserTableBase> {
    private final EnumLaserTableType type;

    public BlockLaserTable(String id, BlockBehaviour.Properties props, EnumLaserTableType type) {
        super(id, props);
        this.type = type;
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

//    @Override
//    public BlockRenderLayer getBlockLayer()
//    {
//        return BlockRenderLayer.CUTOUT;
//    }

    @Override
//    public TileBC_Neptune createTileEntity(Level world, BlockState state)
    public TileBC_Neptune newBlockEntity(BlockPos pos, BlockState state) {
        switch (type) {
            case ASSEMBLY_TABLE:
                return new TileAssemblyTable(pos, state);
            case ADVANCED_CRAFTING_TABLE:
                return new TileAdvancedCraftingTable(pos, state);
            case INTEGRATION_TABLE:
                return new TileIntegrationTable(pos, state);
            case CHARGING_TABLE:
                return new TileChargingTable(pos, state);
            case PROGRAMMING_TABLE:
                return new TileProgrammingTable_Neptune(pos, state);
        }
        return null;
    }

    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);

    @Override
//    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos)
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
//    public boolean onBlockActivated(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, Direction side, float hitX, float hitY, float hitZ)
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        if (world.getBlockEntity(pos) instanceof TileLaserTableBase laserTable)
//        {
//            switch (type)
//            {
//                case ASSEMBLY_TABLE:
//                    if (!world.isClientSide)
//                    {
////                    BCSiliconGuis.ASSEMBLY_TABLE.openGUI(player, pos, state);
//                        BCSiliconGuis.ASSEMBLY_TABLE.openGUI(player, laserTable, pos);=
//                    }
//                    return InteractionResult.SUCCESS;
//                case ADVANCED_CRAFTING_TABLE:
//                    if (!world.isClientSide)
//                    {
////                    BCSiliconGuis.ADVANCED_CRAFTING_TABLE.openGUI(player, pos, state);
//                        BCSiliconGuis.ADVANCED_CRAFTING_TABLE.openGUI(player, laserTable, pos);
//                    }
//                    return InteractionResult.SUCCESS;
//                case INTEGRATION_TABLE:
//                    if (!world.isClientSide)
//                    {
////                    BCSiliconGuis.INTEGRATION_TABLE.openGUI(player, pos, state);
//                        BCSiliconGuis.INTEGRATION_TABLE.openGUI(player, laserTable, pos);
//                    }
//                    return InteractionResult.SUCCESS;
//                case CHARGING_TABLE:
//                case PROGRAMMING_TABLE:
//            }
//        }
        if (!world.isClientSide) {
            if (world.getBlockEntity(pos) instanceof TileBC_Neptune tile) {
//                    BCSiliconGuis.ADVANCED_CRAFTING_TABLE.openGUI(player, pos, state);
                MessageUtil.serverOpenTileGUI(player, tile);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
