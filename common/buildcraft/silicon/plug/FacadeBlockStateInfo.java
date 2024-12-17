package buildcraft.silicon.plug;

import buildcraft.api.facades.IFacadeState;
import buildcraft.lib.misc.BlockUtil;
import buildcraft.lib.world.SingleBlockAccess;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Objects;

public class FacadeBlockStateInfo implements IFacadeState {
    public final BlockState state;
    public final ItemStack requiredStack;
    public final ImmutableSet<Property<?>> varyingProperties;
    public final boolean isTransparent;
    public final boolean isVisible;
    public final boolean[] isSideSolid = new boolean[6];
//    public final BlockFaceShape[] blockFaceShape = new BlockFaceShape[6];

    public FacadeBlockStateInfo(BlockState state, ItemStack requiredStack, ImmutableSet<Property<?>> varyingProperties) {
        this.state = Objects.requireNonNull(state, "state must not be null!");
        Objects.requireNonNull(state.getBlock(), "state.getBlock must not be null!");
        Objects.requireNonNull(BlockUtil.getRegistryName(state.getBlock()),
                "state.getBlock.getRegistryName() must not be null!");
        this.requiredStack = requiredStack;
        this.varyingProperties = varyingProperties;
//        this.isTransparent = !state.isOpaqueCube();
        // TODO Calen state.canOcclude() or !state.canOcclude() ?
        this.isTransparent = state.canOcclude();
        this.isVisible = !requiredStack.isEmpty();
        BlockGetter access = new SingleBlockAccess(state);
        for (Direction side : Direction.VALUES) {
//            isSideSolid[side.ordinal()] = state.isSideSolid(access, BlockPos.ZERO, side);
            isSideSolid[side.ordinal()] = state.isFaceSturdy(access, BlockPos.ZERO, side);
            // Calen: use VoxelShape, no blockFaceShape
//            blockFaceShape[side.ordinal()] = state.getBlockFaceShape(access, BlockPos.ZERO, side);
        }
    }

    // Helper methods

    public FacadePhasedState createPhased(DyeColor activeColour) {
        return new FacadePhasedState(this, activeColour);
    }

    @Override
    public String toString() {
        return "StateInfo [id=" + System.identityHashCode(this) + ", block = " + state.getBlock() + ", state =  "
                + state.toString() + "]";
    }

    // IFacadeState

    @Override
    public BlockState getBlockState() {
        return state;
    }

    @Override
    public boolean isTransparent() {
        return isTransparent;
    }

    @Override
    public ItemStack getRequiredStack() {
        return requiredStack;
    }
}
