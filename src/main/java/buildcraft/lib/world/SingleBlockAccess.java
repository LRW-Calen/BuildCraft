/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * An {@link LevelAccessor} for getting the properties of a single {@link BlockState}
 * at the {@link SingleBlockAccess#POS}
 */
//public class SingleBlockAccess implements LevelAccessor
public class SingleBlockAccess implements BlockGetter
{
    public static final BlockPos POS = BlockPos.ZERO;
    public final BlockState state;

    public SingleBlockAccess(BlockState state)
    {
        this.state = state;
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos)
    {
        return null;
    }

//    @Override
//    public int getCombinedLight(BlockPos pos, int lightValue)
//    {
//        return lightValue << 4;
//    }

    @Override
    public BlockState getBlockState(BlockPos pos)
    {
        return POS.equals(pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public FluidState getFluidState(BlockPos p_45569_)
    {
        return null;
    }

//    @Override
//    public boolean isEmptyBlock(BlockPos pos)
//    {
////        return getBlockState(pos).getBlock().isAir(state, this, pos);
//        return getBlockState(pos).isAir();
//    }

//    @Override
////    public Biome getBiome(BlockPos pos) {
//    public Holder<Biome> getBiome(BlockPos pos)
//    {
////        this.getBiomeManager().getBiome(pos);
//        return ForgeRegistries.BIOMES.getHolder(Biomes.PLAINS).get();
//    }

    @Override
    public int getHeight()
    {
        return 0;
    }

//    @Override
//    public int getStrongPower(BlockPos pos, Direction direction)
//    public int getDirectSignal(BlockPos pos, Direction direction)
//    {
//        return 0;
//    }

//    @Override
//    public RealmsServer.WorldType getWorldType() {
//        return RealmsServer.WorldType.DEBUG_ALL_BLOCK_STATES;
//    }

//    @Override
//    public boolean isSideSolid(BlockPos pos, Direction side, boolean _default)
//    public boolean isFaceSturdy(BlockPos pos, Direction side, boolean _default)
//    {
//        if (POS.equals(pos))
//        {
//            return _default;
//        }
//        return state.isFaceSturdy(this, pos, side);
//    }

    @Override
    public int getMinBuildHeight()
    {
        return -64;
    }
}
