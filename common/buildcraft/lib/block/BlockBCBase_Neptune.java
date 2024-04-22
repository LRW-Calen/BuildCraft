/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.lib.block;

import buildcraft.api.properties.BuildCraftProperties;
import buildcraft.lib.registry.TagManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;

public class BlockBCBase_Neptune extends Block {
    public static final Property<Direction> PROP_FACING = BuildCraftProperties.BLOCK_FACING;
    public static final Property<Direction> BLOCK_FACING_6 = BuildCraftProperties.BLOCK_FACING_6;

    /**
     * The tag used to identify this in the {@link TagManager}. Note that this may be empty if this core doesn't use
     * the tag system.
     */
    public final String id;
//    public final String namespace;

    /**
     * @param id The ID that will be looked up in the {@link TagManager} when registering blocks. Pass null or the
     *           empty string to bypass the {@link TagManager} entirely.
     */
    public BlockBCBase_Neptune(String id, Properties props) {
        super(props);
//        if (id == null)
//        {
//            id.toString().equals("");
//        }
        this.id = id;
//        this.namespace = namespace;

//        // Sensible default core properties
//        setHardness(5.0F);
//        setResistance(10.0F);
//        setSoundType(SoundType.METAL);

        if (!id.isEmpty()) {
            // Init names from the tag manager
//            setUnlocalizedName("tile." + TagManager.getTag(id, TagManager.EnumTagType.UNLOCALIZED_NAME) + ".name");
            // Calen: for BCEnergy chocolate engine
            String unlocalizedName = TagManager.getTag(id, TagManager.EnumTagType.UNLOCALIZED_NAME);
            if (unlocalizedName.startsWith("buildcraft.christmas.")) {
                unlocalizedName = unlocalizedName.replace("buildcraft.christmas.", "buildcraft.christmas.tile.") + ".name";
            } else {
                unlocalizedName = "tile." + unlocalizedName + ".name";
            }
            setUnlocalizedName(unlocalizedName);
//            setRegistryName(TagManager.getTag(id, TagManager.EnumTagType.REGISTRY_NAME));
//            setCreativeTab(CreativeTabManager.getTab(TagManager.getTag(id, TagManager.EnumTagType.CREATIVE_TAB)));
        }

        if (this instanceof IBlockWithFacing) {
            Property<Direction> facingProp = ((IBlockWithFacing) this).getFacingProperty();
//            setDefaultState(defaultBlockState().setValue(facingProp, Direction.NORTH));
            registerDefaultState(
                    defaultBlockState()
                            .setValue(facingProp, Direction.NORTH)
            );
        }
    }

    // IBlockState
    // @Override
    @Override
//    protected BlockStateContainer createBlockState()
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        if (this instanceof IBlockWithFacing) {
            builder.add(((IBlockWithFacing) this).getFacingProperty());
        }
    }

    //    @Override
//    public int getMetaFromState(BlockState state)
//    {
//        int meta = 0;
//        if (this instanceof IBlockWithFacing)
//        {
//            if (((IBlockWithFacing) this).canFaceVertically())
//            {
//                meta |= state.getValue(((IBlockWithFacing) this).getFacingProperty()).get3DDataValue();
//            }
//            else
//            {
//                meta |= state.getValue(((IBlockWithFacing) this).getFacingProperty()).get2DDataValue();
//            }
//        }
//        return meta;
//    }

//    @Override
//    public BlockState getStateFromMeta(int meta) {
//        BlockState state = defaultBlockState();
//        if (this instanceof IBlockWithFacing) {
//            IBlockWithFacing b = (IBlockWithFacing) this;
//            Property<Direction> prop = b.getFacingProperty();
//            if (b.canFaceVertically()) {
//                state = state.setValue(prop, Direction.from3DDataValue(Direction.values()[meta & 7].getOpposite().get3DDataValue()));
//            } else {
//                state = state.withProperty(prop, Direction.getHorizontal(meta & 3));
//            }
//        }
//        return state;
//    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (this instanceof IBlockWithFacing) {
            Property<Direction> prop = ((IBlockWithFacing) this).getFacingProperty();
            Direction facing = state.getValue(prop);
            state = state.setValue(prop, rot.rotate(facing));
        }
        return state;
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (this instanceof IBlockWithFacing) {
            Property<Direction> prop = ((IBlockWithFacing) this).getFacingProperty();
            Direction facing = state.getValue(prop);
            state = state.setValue(prop, mirror.mirror(facing));
        }
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        if (this instanceof IBlockWithFacing) {
            if (!((IBlockWithFacing) this).canBeRotated(world, pos, world.getBlockState(pos))) {
//                return false;
                return state;
            }
        }
        return super.rotate(world.getBlockState(pos), world, pos, Rotation.CLOCKWISE_90);
    }

    // Others

    // Calen: this is called when the block not been placed, to choose a state for place
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context)
//    public BlockState getStateForPlacement(Level world, BlockPos pos, Direction facing, float hitX, float hitY,
//                                           float hitZ, int meta, LivingEntity placer, InteractionHand hand)
    {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        float hitX = context.getClickedPos().getX();
        float hitY = context.getClickedPos().getY();
        float hitZ = context.getClickedPos().getZ();
//        int meta = context.;
        LivingEntity placer = context.getPlayer();
        InteractionHand hand = context.getHand();
//        BlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        BlockState state = super.getStateForPlacement(context);
        if (this instanceof IBlockWithFacing b) {
            Direction orientation = placer.getDirection();
            if (b.canFaceVertically()) {
//                if (MathHelper.abs((float) placer.getX() - pos.getX()) < 2.0F
                if (Mth.abs((float) placer.getX() - pos.getX()) < 2.0F
//                        && MathHelper.abs((float) placer.getZ() - pos.getZ()) < 2.0F)
                        && Mth.abs((float) placer.getZ() - pos.getZ()) < 2.0F)
                {
                    double y = placer.getY() + placer.getEyeHeight();

                    if (y - pos.getY() > 2.0D) {
                        orientation = Direction.DOWN;
                    }

                    if (pos.getY() - y > 0.0D) {
                        orientation = Direction.UP;
                    }
                }
            }
            state = state.setValue(b.getFacingProperty(), orientation.getOpposite());
        }
        return state;
    }

//    public static boolean isExceptBlockForAttachWithPiston(Block attachBlock)
//    {
//        return Block.isExceptBlockForAttachWithPiston(attachBlock);
//    }

    // Calen
//    @Override
//    public MutableComponent getName()
//    {
//        return new TranslatableComponent("tile." + TagManager.getTag("block." + getRegistryName().getPath(), TagManager.EnumTagType.UNLOCALIZED_NAME) + ".name");
//    }
    // Calen:
    // in 1.18.2 setUnlocalizedName setRegistryName are unvailable
    @Override
    public String getDescriptionId() {
        return this.unlocalizedName;
    }

    private String unlocalizedName;

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    // Calen: from mc 1.12.2
    // should be custom called, not by mc
    public BlockState getActualState(BlockState state, LevelAccessor world, BlockPos pos, BlockEntity tile) {
        return state;
    }

//    @Override
//    @OverridingMethodsMustInvokeSuper
//    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos, boolean isMoving)
//    {
//        super.neighborChanged(state, world, pos, neighborBlock, neighbourPos, isMoving);
//        // Calen
//        checkActualStateAndUpdate(state, world, pos);
//    }

    public void checkActualStateAndUpdate(BlockState state, Level world, BlockPos pos, BlockEntity tile) {
        // Calen
        BlockState newState = getActualState(state, world, pos, tile);
        if (!newState.equals(state)) {
            world.setBlockAndUpdate(pos, newState);
        }
    }

//    @OverridingMethodsMustInvokeSuper
//    @Override
//    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
//    {
//        InteractionResult ret = super.use(state, world, pos, player, hand, result);
//        checkActualStateAndUpdate(state, world, pos);
//        return ret;
//    }
//
//    @Override
//    @OverridingMethodsMustInvokeSuper
//    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random)
//    {
//        super.randomTick(state, world, pos, random);
//
//        // Calen
//        checkActualStateAndUpdate(state, world, pos);
//    }
//
//    @Override
//    @OverridingMethodsMustInvokeSuper
//    public void setPlacedBy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack)
//    {
//        super.setPlacedBy(world, pos, state, placer, stack);
//
//        // Calen
//        checkActualStateAndUpdate(state, world, pos);
//    }
}
