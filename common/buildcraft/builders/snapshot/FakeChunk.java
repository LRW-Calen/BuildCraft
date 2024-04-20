package buildcraft.builders.snapshot;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

// Calen
public class FakeChunk extends LevelChunk {
    final Level level;

    public FakeChunk(Level world, ChunkPos chunkPos) {
        super(world, chunkPos);
        this.level = world;
    }

//    @Nullable
//    public BlockState setBlockState(BlockPos p_62865_, BlockState p_62866_, boolean p_62867_)
//    {
//        int i = p_62865_.getY();
//        LevelChunkSection levelchunksection = this.getSection(this.getSectionIndex(i));
//        boolean flag = levelchunksection.hasOnlyAir();
//        if (flag && p_62866_.isAir())
//        {
//            return null;
//        }
//        else
//        {
//            int j = p_62865_.getX() & 15;
//            int k = i & 15;
//            int l = p_62865_.getZ() & 15;
//            BlockState blockstate = levelchunksection.setBlockState(j, k, l, p_62866_);
//            if (blockstate == p_62866_)
//            {
//                return null;
//            }
//            else
//            {
//                Block block = p_62866_.getBlock();
//                this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING).update(j, i, l, p_62866_);
//                this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).update(j, i, l, p_62866_);
//                this.heightmaps.get(Heightmap.Types.OCEAN_FLOOR).update(j, i, l, p_62866_);
//                this.heightmaps.get(Heightmap.Types.WORLD_SURFACE).update(j, i, l, p_62866_);
//                boolean flag1 = levelchunksection.hasOnlyAir();
//                if (flag != flag1)
//                {
//                    this.level.getChunkSource().getLightEngine().updateSectionStatus(p_62865_, flag1);
//                }
//
////                boolean flag2 = blockstate.hasBlockEntity();
//                if (!this.level.isClientSide)
//                {
//                    blockstate.onRemove(this.level, p_62865_, p_62866_, p_62867_);
//                }
////                else if ((!blockstate.is(block) || !p_62866_.hasBlockEntity()) && flag2)
////                {
////                    this.removeBlockEntity(p_62865_);
////                }
//
//                if (!levelchunksection.getBlockState(j, k, l).is(block))
//                {
//                    return null;
//                }
//                else
//                {
//                    if (!this.level.isClientSide && !this.level.captureBlockSnapshots)
//                    {
//                        p_62866_.onPlace(this.level, p_62865_, blockstate, p_62867_);
//                    }
//
//                    // Calen: don't create TE, or -> IllegalArgumentException: Cannot use model data for a level other than the current client level
//                    if (p_62866_.hasBlockEntity())
//                    {
//                        BlockEntity blockentity = this.getBlockEntity(p_62865_, LevelChunk.EntityCreationType.CHECK);
//                        if (blockentity == null)
//                        {
//                            blockentity = ((EntityBlock) block).newBlockEntity(p_62865_, p_62866_);
//                            if (blockentity != null)
//                            {
//                                this.addAndRegisterBlockEntity(blockentity);
//                            }
//                        }
//                        else
//                        {
//                            blockentity.setBlockState(p_62866_);
//                            this.updateBlockEntityTicker(blockentity);
//                        }
//                    }
//
//                    this.unsaved = true;
//                    return blockstate;
//                }
//            }
//        }
//    }

    @Override
    public void addAndRegisterBlockEntity(BlockEntity p_156391_) {
        this.setBlockEntity(p_156391_);
        // Calen: don't call TE#onLoad, or -> IllegalArgumentException: Cannot use model data for a level other than the current client level
    }

    @Override
    public void setBlockEntity(BlockEntity p_156374_) {
        BlockPos blockpos = p_156374_.getBlockPos();
        if (this.getBlockState(blockpos).hasBlockEntity()) {
            p_156374_.setLevel(this.level);
//            p_156374_.clearRemoved();
            BlockEntity blockentity = this.blockEntities.put(blockpos.immutable(), p_156374_);
            // Calen: setRemoved -> IllegalArgumentException: Cannot use model data for a level other than the current client level
//            if (blockentity != null && blockentity != p_156374_)
//            {
//                blockentity.setRemoved();
//            }

        }
    }
}
