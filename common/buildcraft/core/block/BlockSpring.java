package buildcraft.core.block;

import buildcraft.api.enums.EnumSpring;
import buildcraft.lib.block.BlockBCTile_Neptune;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;

public class BlockSpring<SpringBlockEntity> extends BlockBCTile_Neptune {
    //    public static final Property<EnumSpring> SPRING_TYPE = BuildCraftProperties.SPRING_TYPE;
    public final EnumSpring springType;

    public BlockSpring(String idBC, BlockBehaviour.Properties properties, EnumSpring springType) {
        super(idBC, properties);
//        disableStats();
//        registerDefaultState(
//                defaultBlockState()
//        );
        this.springType = springType;
    }


    @Override
    // protected BlockStateContainer createBlockState()
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
//        builder.add(BuildCraftProperties.SPRING_TYPE); // Calen: not use meta, type not need to reg to blockstate
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
//        Level world = context.getLevel();
//        BlockPos pos = context.getClickedPos();
//        BlockState state = world.getBlockState(pos);
//        EnumSpring springType = state.getValue(BuildCraftProperties.SPRING_TYPE);
//        world.scheduleTick(pos, state.getBlock(), state.getBlock() springType.tickRate); // 预订下次tick


//        BlockState ;
//        Block block, @Nullable BlockState state, @Nonnull LevelAccessor world, @Nonnull BlockPos pos, @Nullable Player player, @Nonnull Direction face
//        (this, , context.getLevel(), context.getClickedPos(), context.getPlayer(), context.getClickedFace())
//        if (super.getStateForPlacement(context) == null) {
//            return null;
//        }
////        for (Attribute attr : Attribute.getAll(block)) {
////            if (attr instanceof AttributeState atr) {
////                state = atr.getStateForPlacement(block, state, world, pos, player, face);
////            }
////        }
//        if (this instanceof IStateFluidLoggable fluidLoggable) {
//            FluidState fluidState = world.getFluidState(pos);
//            state = fluidLoggable.setState(state, fluidState.getType());
//        }
        return super.getStateForPlacement(context);
    }

    // BlockState

//    @Override
//    protected BlockStateContainer createBlockState()
//    {
//        return new BlockStateContainer(this, SPRING_TYPE);
//    }

    //    @Override
//    public int getMetaFromState(BlockState state)
//    {
//        return state.getValue(BuildCraftProperties.SPRING_TYPE).ordinal();
//    }

    //    @Override
//    public BlockState getStateFromMeta(int meta)
//    {
//        if (meta == EnumSpring.OIL.ordinal())
//        {
//            return defaultBlockState().setValue(BuildCraftProperties.SPRING_TYPE, EnumSpring.OIL);
//        }
//        else
//        {
//            return defaultBlockState().setValue(BuildCraftProperties.SPRING_TYPE, EnumSpring.WATER);
//        }
//    }

    // Other

//    //    @Override
//    public void getSubBlocks(CreativeModeTab tab, NonNullList<ItemStack> list)
//    {
//        list.add(new ItemStack(BCCoreBlocks.SPRING_OIL.get(), 1));
//        list.add(new ItemStack(BCCoreBlocks.SPRING_WATER.get(), 1));
////        for (EnumSpring type : EnumSpring.VALUES)
////        {
////            list.add(new ItemStack(this, 1, type.ordinal()));
////        }
//    }

//    @Override
//    public int damageDropped(BlockState state)
//    {
//        return state.getValue(SPRING_TYPE).ordinal();
//    }

    // Calen 不存在公用meta的情况 所以不用Override?
//    @Override
//    public List<ItemStack> getDrops(BlockState state, LootContext.Builder p_60538_)
//    {
//        return switch (state.getValue(SPRING_TYPE))
//        {
//            case OIL -> new ArrayList<ItemStack>().add(new ItemStack(BCCoreBlocks.SPRING_OIL, 1));
//            case WATER -> new ArrayList<ItemStack>().add(new ItemStack(BCCoreBlocks.SPRING_WATER, 1));
//            default -> new ArrayList<ItemStack>().add(ItemStack.EMPTY);
//        };
//    }


//    @Override
//    public boolean hasTileEntity(BlockState state)
//    {
//        return state.getValue(SPRING_TYPE).tileConstructor != null;
//    }

    @Override
//    public BlockEntity getBlockEntity(BlockGetter world, BlockPos pos)
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        BlockState state = world.getBlockState(pos);
//        Supplier<BlockEntity> constructor = state.getValue(BuildCraftProperties.SPRING_TYPE).tileConstructor;
        BiFunction<BlockPos, BlockState, BlockEntity> constructor = this.springType.tileConstructor;
        if (constructor != null) {
            return constructor.apply(pos, state);
        }
        return null;
    }

    // @Override
    // public void onNeighborBlockChange(Level world, int x, int y, int z, int blockid) {
    // assertSpring(world, x, y, z);
    // }

    @Override
//    public void onBlockAdded(Level world, BlockPos pos, BlockState state)
    // 参考红石中继器 DiodeBlock.java
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity p_52509_, ItemStack p_52510_) {
        super.setPlacedBy(world, pos, state, p_52509_, p_52510_);
//        EnumSpring springType = state.getValue(BuildCraftProperties.SPRING_TYPE);
        EnumSpring springType = this.springType;
        world.scheduleTick(pos, this, springType.tickRate);
    }

    // 随机tick生成水/油 BE是用来和泵交互的
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random p_60554_) {
        generateSpringBlock(world, pos, state, p_60554_);
    }

    private void generateSpringBlock(Level world, BlockPos pos, BlockState state, Random p_60554_) {
//        EnumSpring springType = state.getValue(BuildCraftProperties.SPRING_TYPE);
        EnumSpring springType = this.springType;
        world.scheduleTick(pos, this, springType.tickRate); // 预订下次tick
//        if (!springType.canGen || springType.liquidBlock == null)
//        {
//            return;
//        }
        if (!springType.canGen || springType.liquidBlock == null) {
            return;
        }
        if (!world.isEmptyBlock(pos.above())) {
            return;
        }
        if (springType.chance != -1 && p_60554_.nextInt(springType.chance) != 0) {
            return;
        }
        world.setBlock(pos.above(), springType.liquidBlock, 3);
//        switch (springType)
//        {
//            case OIL:
//                world.setBlock(pos.above(), BCEnergyFluids.OIL.getBlock().defaultBlockState(), 3);
//                return;
//            case WATER:
//                world.setBlock(pos.above(), Blocks.WATER.defaultBlockState(), 3);
//                return;
//        }

    }

    // 原版BC油泉不update
//    @Override
//    @Nullable
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
//    {
////        return pBlockEntityType==BCFactoryBlockEntities.PUMP.get()?(BlockEntityTicker<T>)TilePump::tick:null;
//        return BCCoreBlockEntities.createTickerHelper(pBlockEntityType, BCEnergyBlockEntities.SPRING.get(), TileSpringOil::tick);
//    }

//    // Calen add
//    @Override
//    public String getDescriptionId()
//    {
//        return "tile.spring." + springType.name().toLowerCase() + ".name";
//    }
}
