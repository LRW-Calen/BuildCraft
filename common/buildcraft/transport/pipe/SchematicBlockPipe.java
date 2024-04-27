/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.pipe;

import buildcraft.api.core.InvalidInputDataException;
import buildcraft.api.schematics.ISchematicBlock;
import buildcraft.api.schematics.SchematicBlockContext;
import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import buildcraft.builders.snapshot.FakeWorld;
import buildcraft.lib.misc.NBTUtilBC;
import buildcraft.transport.BCTransportBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

public class SchematicBlockPipe implements ISchematicBlock {
    private CompoundTag tileNbt;
    // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
//    private Rotation tileRotation = Rotation.NONE;

    public static boolean predicate(SchematicBlockContext context) {
        return context.world.getBlockState(context.pos).getBlock() == BCTransportBlocks.pipeHolder.get();
    }

    @Override
    public void init(SchematicBlockContext context) {
        BlockEntity tileEntity = context.world.getBlockEntity(context.pos);
        if (tileEntity == null) {
            throw new IllegalStateException();
        }
        tileNbt = tileEntity.serializeNBT();
    }

    @Nonnull
    @Override
    public List<ItemStack> computeRequiredItems() {
        try {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            PipeDefinition definition = PipeRegistry.INSTANCE.loadDefinition(
                    tileNbt.getCompound("pipe").getString("def")
            );
            DyeColor color = NBTUtilBC.readEnum(
                    tileNbt.getCompound("pipe").get("col"),
                    DyeColor.class
            );
            // Calen: reg different item objects for different colours
//            Item item = (Item) PipeApi.pipeRegistry.getItemForPipe(definition);
            Item item = (Item) PipeApi.pipeRegistry.getItemForPipe(definition, color);
            if (item != null) {
//                builder.add(
//                        new ItemStack(
//                                item,
//                                1,
//                                color == null ? 0 : color.getMetadata() + 1
//                        )
//                );
                builder.add(new ItemStack(item, 1));
            }
            return builder.build();
        } catch (InvalidInputDataException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SchematicBlockPipe getRotated(Rotation rotation) {
        SchematicBlockPipe schematicBlock = new SchematicBlockPipe();
        schematicBlock.tileNbt = tileNbt;
        // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
//        schematicBlock.tileRotation = tileRotation.add(rotation);
        return schematicBlock;
    }

    @Override
    public boolean canBuild(Level world, BlockPos blockPos) {
//        return world.isAirBlock(blockPos);
        return world.isEmptyBlock(blockPos);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean build(Level world, BlockPos blockPos) {
        BlockState state = BCTransportBlocks.pipeHolder.get().defaultBlockState();
        boolean setBlockResult = world.setBlock(blockPos, state, Block.UPDATE_ALL_IMMEDIATE);
        if (setBlockResult) {
//            TileEntity tileEntity = TileEntity.create(world, tileNbt);
            BlockEntity tileEntity = BlockEntity.loadStatic(blockPos, state, tileNbt);
            if (tileEntity != null) {
                // Calen: tileEntity#setLevel and tileEntity#clearRemoved will be called in world#setBlockEntity
//                tileEntity.setWorld(world);
//                world.setTileEntity(blockPos, tileEntity);
                world.setBlockEntity(tileEntity);
//                if (tileRotation != Rotation.NONE) {
//                    tileEntity.rotate(tileRotation);
//                }
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("Duplicates")
    @Override
//    public boolean buildWithoutChecks(World world, BlockPos blockPos)
    public boolean buildWithoutChecks(FakeWorld world, BlockPos blockPos) {
        BlockState state = BCTransportBlocks.pipeHolder.get().defaultBlockState();
        if (world.setBlock(blockPos, state, 0)) {
//            TileEntity tileEntity = TileEntity.create(world, tileNbt);
            BlockEntity tileEntity = BlockEntity.loadStatic(blockPos, state, tileNbt);
            if (tileEntity != null) {
                // Calen: tileEntity#setLevel and tileEntity#clearRemoved will be called in world.setBlockEntity
//                tileEntity.setWorld(world);
//                world.setTileEntity(blockPos, tileEntity);
                world.setBlockEntity(tileEntity);
                // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
//                if (tileRotation != Rotation.NONE) {
//                    tileEntity.rotate(tileRotation);
//                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBuilt(Level world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock() == BCTransportBlocks.pipeHolder.get();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("tileNbt", tileNbt);
        // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
//        nbt.put("tileRotation", NBTUtilBC.writeEnum(tileRotation));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) throws InvalidInputDataException {
        tileNbt = nbt.getCompound("tileNbt");
        // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
//        tileRotation = NBTUtilBC.readEnum(nbt.get("tileRotation"), Rotation.class);
    }
}
