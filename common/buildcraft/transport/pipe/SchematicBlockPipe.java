/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.pipe;

import buildcraft.api.core.BCLog;
import buildcraft.api.core.IFakeWorld;
import buildcraft.api.core.InvalidInputDataException;
import buildcraft.api.schematics.ISchematicBlock;
import buildcraft.api.schematics.SchematicBlockContext;
import buildcraft.api.transport.EnumWirePart;
import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import buildcraft.api.transport.pluggable.PluggableDefinition;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.lib.misc.NBTUtilBC;
import buildcraft.lib.misc.RotationUtil;
import buildcraft.lib.registry.TagManager;
import buildcraft.lib.registry.TagManager.EnumTagType;
import buildcraft.transport.BCTransportBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class SchematicBlockPipe implements ISchematicBlock {
    private CompoundTag tileNbt;
    // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
    // private Rotation tileRotation = Rotation.NONE;

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
            // plug
            CompoundTag plugs = tileNbt.getCompound("plugs");
            for (Direction face : Direction.values()) {
                CompoundTag nbt = plugs.getCompound(face.getName());
                if (nbt.isEmpty()) {
                    continue;
                }
                CompoundTag data = nbt.getCompound("data");
                String id = nbt.getString("id");
                ResourceLocation identifier = new ResourceLocation(id);
                PluggableDefinition def = PipeApi.pluggableRegistry.getDefinition(identifier);
                ItemStack plugItemStack = def.readFromNbt(null, Direction.NORTH, data).getPickStack();
                builder.add(plugItemStack);
            }
            // wire
            ResourceLocation wireId = new ResourceLocation(TagManager.getTag("item.wire", EnumTagType.REGISTRY_NAME));
            Item wireItem = ForgeRegistries.ITEMS.getValue(wireId);
            CompoundTag wireManagerNbt = tileNbt.getCompound("wireManager");
            int[] wiresArray = wireManagerNbt.getIntArray("parts");
            for (int i = 0; i < wiresArray.length; i += 2) {
                ItemStack wireStack = new ItemStack(wireItem);
                builder.add(ColourUtil.addColourTagToStack(wireStack, DyeColor.byId(wiresArray[i + 1])));
            }

            return builder.build();
        } catch (InvalidInputDataException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SchematicBlockPipe getRotated(Rotation rotation) {
        SchematicBlockPipe schematicBlock = new SchematicBlockPipe();
//        schematicBlock.tileNbt = tileNbt;
        schematicBlock.tileNbt = tileNbt.copy();
        rotatePlugs(schematicBlock.tileNbt, rotation);
        // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
        // schematicBlock.tileRotation = tileRotation.add(rotation);
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
    public boolean buildWithoutChecks(IFakeWorld world, BlockPos blockPos) {
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
        // nbt.put("tileRotation", NBTUtilBC.writeEnum(tileRotation));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) throws InvalidInputDataException {
        tileNbt = nbt.getCompound("tileNbt");
        // Calen 1.12.2 tileRotation -> 1.18.2 SkullBlock BlockState ROTATION_16
        // tileRotation = NBTUtilBC.readEnum(nbt.get("tileRotation"), Rotation.class);
    }

    // Calen FIX: builder rotates plugs on pipes
    private void rotatePlugs(CompoundTag nbt, Rotation rotation) {
        try {
            CompoundTag nbtPlugs = nbt.getCompound("plugs");
            // get plug tag of each facing
            CompoundTag wPlug = nbtPlugs.getCompound(Direction.WEST.getName());
            CompoundTag ePlug = nbtPlugs.getCompound(Direction.EAST.getName());
            CompoundTag nPlug = nbtPlugs.getCompound(Direction.NORTH.getName());
            CompoundTag sPlug = nbtPlugs.getCompound(Direction.SOUTH.getName());
            // rotate triggers
            rotatePlugData(wPlug, rotation);
            rotatePlugData(ePlug, rotation);
            rotatePlugData(nPlug, rotation);
            rotatePlugData(sPlug, rotation);
            // rotate facings
            Direction wPlugNewFacing = RotationUtil.rotateFacing(Direction.WEST, rotation);
            Direction ePlugNewFacing = RotationUtil.rotateFacing(Direction.EAST, rotation);
            Direction nPlugNewFacing = RotationUtil.rotateFacing(Direction.NORTH, rotation);
            Direction sPlugNewFacing = RotationUtil.rotateFacing(Direction.SOUTH, rotation);
            // move plugs
            CompoundTag nbtPlugsNew = new CompoundTag();
            nbt.put("plugs", nbtPlugsNew);
            Optional.of(wPlug).filter(tag -> !tag.isEmpty()).ifPresent(tag -> nbtPlugsNew.put(wPlugNewFacing.getName(), tag));
            Optional.of(ePlug).filter(tag -> !tag.isEmpty()).ifPresent(tag -> nbtPlugsNew.put(ePlugNewFacing.getName(), tag));
            Optional.of(nPlug).filter(tag -> !tag.isEmpty()).ifPresent(tag -> nbtPlugsNew.put(nPlugNewFacing.getName(), tag));
            Optional.of(sPlug).filter(tag -> !tag.isEmpty()).ifPresent(tag -> nbtPlugsNew.put(sPlugNewFacing.getName(), tag));

            // rotate wires
            CompoundTag wireManagerNbt = nbt.getCompound("wireManager");
            int[] wiresArray = wireManagerNbt.getIntArray("parts");
            int[] wiresArrayNew = wiresArray.clone();
            for (int i = 0; i < wiresArray.length; i += 2) {
                wiresArrayNew[i] = RotationUtil.rotateEnumWirePart(EnumWirePart.VALUES[wiresArray[i]], rotation).ordinal();
            }
            wireManagerNbt.putIntArray("parts", wiresArrayNew);
        } catch (Exception e) {
            BCLog.logger.error("[transport.pipe.schematic] Failed to rotate PipeHolder[" + this.tileNbt.toString() + "], rotation = [" + rotation + "]");
            BCLog.logger.error("[transport.pipe.schematic] ", e);
        }
    }

    private void rotatePlugData(CompoundTag plugNbt, Rotation rotation) {
        CompoundTag dataNbt = plugNbt.getCompound("data").getCompound("data");
        for (String subTagName : dataNbt.getAllKeys()) {
            CompoundTag subTag = dataNbt.getCompound(subTagName);
            if (subTag.contains("s")) {
                CompoundTag sNbt = subTag.getCompound("s");
                if (sNbt.contains("side")) {
                    byte oldFacing = sNbt.getByte("side");
                    // 6 -> no direction
                    if (oldFacing >= 0 && oldFacing < 6) {
                        byte newFacing = RotationUtil.rotateFacing(sNbt.getByte("side"), rotation);
                        sNbt.putByte("side", newFacing);
                    }
                }
            }
        }
    }
}
