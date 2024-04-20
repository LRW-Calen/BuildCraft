/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.engine;


import buildcraft.api.blocks.ICustomRotationHandler;
import buildcraft.api.core.IEngineType;
import buildcraft.api.enums.EnumEngineType;
import buildcraft.core.BCCoreBlocks;
import buildcraft.lib.block.BlockBCTile_Neptune;
import buildcraft.lib.block.IBlockWithTickableTE;
import buildcraft.lib.tile.TileBC_Neptune;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public abstract class BlockEngineBase_BC8<E extends Enum<E> & IEngineType> extends BlockBCTile_Neptune<TileEngineBase_BC8> implements ICustomRotationHandler, IBlockWithTickableTE<TileEngineBase_BC8> {
    public final E engineType;

    //    private final Map<E, Supplier<? extends TileEngineBase_BC8>> engineTileConstructors = new EnumMap<>(E);
//    public final Map<E, Block> engineBlockMap = new HashMap<>();

    // Calen
    public BlockEngineBase_BC8(
            String id,
            BlockBehaviour.Properties props,
            E type
//            BiFunction<BlockPos, BlockState, ? extends TileEngineBase_BC8> constructor

    ) {
        super(id, props);
        this.engineType = type;
//        registerEngine(type, constructor);
//        registerEngine();
    }

    // Engine directly related methods

    //    public void registerEngine(E type, BiFunction<BlockPos, BlockState, ? extends TileEngineBase_BC8> constructor)
//    public void registerEngine()
//    {
////        if (RegistryConfig.isEnabled("engines", getRegistryName() + "/" + type.name().toLowerCase(Locale.ROOT),
////                getUnlocalizedName(type)))
////        {
////            engineTileConstructors.put(type, constructor);
////        }
//        engineBlockMap.put(this.engineType, this);
//    }

    // Calen: never used
//    public boolean isRegistered(EnumEngineType type)
//    {
////        return engineTileConstructors.containsKey(type);
//        return engineBlockMap.containsKey(type);
//    }

    @Nonnull
    public ItemStack getStack(EnumEngineType type) {
//        return new ItemStack(this, 1, type.ordinal());
        return new ItemStack(this, 1);
    }

//    public abstract Property<E> getEngineProperty();

//    public abstract EnumEngineType getEngineType(int meta);

//    public abstract String getUnlocalizedName();

    // BlockState

    @Override
//    protected BlockStateContainer createBlockState()
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        // Calen: 不super! engine没有facing属性
//        super.createBlockStateDefinition(builder);
//        builder.add(getEngineProperty());
    }

//    @Override
//    public int getMetaFromState(BlockState state)
//    {
//        E type = state.getValue(getEngineProperty());
//        return type.ordinal();
//    }

//    @Override
//    public BlockState getStateFromMeta(int meta)
//    {
//        E engineType = getEngineType(meta);
//        return this.defaultBlockState().setValue(getEngineProperty(), engineType);
//    }

    // Misc Block Overrides

//    @Override
//    public boolean isOpaqueCube(BlockState state)
//    {
//        return false;
//    }

//    @Override
//    public boolean isFullBlock(BlockState state)
//    {
//        return false;
//    }

//    @Override
//    public boolean isFullCube(BlockState state)
//    {
//        return false;
//    }

//    @Override
//    public BlockFaceShape getBlockFaceShape(BlockAccess world, IBlockState state, BlockPos pos, Direction side)
////    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos p_54204_, CollisionContext p_54205_)
//    {
//        BlockEntity tile = world.getTileEntity(pos);
//        if (tile instanceof TileEngineBase_BC8)
//        {
//            TileEngineBase_BC8 engine = (TileEngineBase_BC8) tile;
//            if (side == engine.currentDirection.getOpposite())
//            {
//                return BlockFaceShape.SOLID;
//            }
//            else
//            {
//                return BlockFaceShape.UNDEFINED;
//            }
//        }
//        return BlockFaceShape.UNDEFINED;
//    }

    // Calen
    private static final VoxelShape BASE_U = Block.box(0, 0, 0, 16, 4, 16);
    private static final VoxelShape TRUNK_U = Block.box(4, 4, 4, 12, 16, 12);
    private static final VoxelShape UP = Shapes.or(BASE_U, TRUNK_U);
    private static final VoxelShape BASE_D = Block.box(0, 12, 0, 16, 16, 16);
    private static final VoxelShape TRUNK_D = Block.box(4, 0, 4, 12, 12, 12);
    private static final VoxelShape DOWN = Shapes.or(BASE_D, TRUNK_D);
    private static final VoxelShape BASE_E = Block.box(0, 0, 0, 4, 16, 16);
    private static final VoxelShape TRUNK_E = Block.box(4, 4, 4, 16, 12, 12);
    private static final VoxelShape EAST = Shapes.or(BASE_E, TRUNK_E);
    private static final VoxelShape BASE_W = Block.box(12, 0, 0, 16, 16, 16);
    private static final VoxelShape TRUNK_W = Block.box(0, 4, 4, 12, 12, 12);
    private static final VoxelShape WEST = Shapes.or(BASE_W, TRUNK_W);
    private static final VoxelShape BASE_N = Block.box(0, 0, 12, 16, 16, 16);
    private static final VoxelShape TRUNK_N = Block.box(4, 4, 0, 12, 12, 12);
    private static final VoxelShape NORTH = Shapes.or(BASE_N, TRUNK_N);
    private static final VoxelShape BASE_S = Block.box(0, 0, 0, 16, 16, 4);
    private static final VoxelShape TRUNK_S = Block.box(4, 4, 4, 12, 12, 16);
    private static final VoxelShape SOUTH = Shapes.or(BASE_S, TRUNK_S);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof TileEngineBase_BC8 engine) {
            return switch (engine.currentDirection) {
                case DOWN -> DOWN;
                case UP -> UP;
                case WEST -> WEST;
                case EAST -> EAST;
                case SOUTH -> SOUTH;
                case NORTH -> NORTH;
            };
        }
        return Shapes.empty();
    }

    @Override
//    public TileBC_Neptune createTileEntity(Level world, BlockState state)
    public TileBC_Neptune newBlockEntity(BlockPos pos, BlockState state) {
//        E engineType = state.getValue(getEngineProperty());
        E engineType = this.engineType;
//        BiFunction<BlockPos, BlockState, ? extends TileEngineBase_BC8> constructor = engineTileConstructors.get(engineType);
        BiFunction<BlockPos, BlockState, ? extends TileEngineBase_BC8> constructor = BCCoreBlocks.engineTileConstructors.get(engineType);
        if (constructor == null) {
            return null;
        }
        TileEngineBase_BC8 tile = constructor.apply(pos, state);
//        tile.setWorld(world);
        return tile;
    }

//    @Override
//    public boolean isSideSolid(BlockState base_state, LevelAccessor world, BlockPos pos, Direction side)
//    {
//        BlockEntity tile = world.getBlockEntity(pos);
//        if (tile instanceof TileEngineBase_BC8)
//        {
//            TileEngineBase_BC8 engine = (TileEngineBase_BC8) tile;
//            return side == engine.currentDirection.getOpposite();
//        }
//        return false;
//    }

    @Override
//    public EnumBlockRenderType getRenderType(BlockState state)
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

//    @Override
//    public void getSubBlocks(CreativeModeTab tab, NonNullList<ItemStack> list)
//    {
//        for (E engine : getEngineProperty().getAllowedValues())
//        {
//            if (engineTileConstructors.containsKey(engine))
//            {
//                list.add(new ItemStack(this, 1, engine.ordinal()));
//            }
//        }
//    }

    // Calen: moved to datagen LootTable
//    @Override
//    public int damageDropped(BlockState state)
//    {
//        return state.getValue(getEngineProperty()).ordinal();
//    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean p_60514_) {
        super.neighborChanged(state, world, pos, block, fromPos, p_60514_);
        if (world.isClientSide) return;
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEngineBase_BC8) {
            TileEngineBase_BC8 engine = (TileEngineBase_BC8) tile;
            engine.rotateIfInvalid();
        }
    }

    // ICustomRotationHandler

    @Override
    public InteractionResult attemptRotation(Level world, BlockPos pos, BlockState state, Direction sideWrenched) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileEngineBase_BC8) {
            TileEngineBase_BC8 engine = (TileEngineBase_BC8) tile;
            return engine.attemptRotation();
        }
        return InteractionResult.FAIL;
    }
}
