/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.misc;

import buildcraft.api.core.BuildCraftAPI;
import buildcraft.api.mj.MjAPI;
import buildcraft.core.BCCoreConfig;
import buildcraft.lib.BCLibConfig;
import buildcraft.lib.compat.CompatManager;
import buildcraft.lib.inventory.TransactorEntityItem;
import buildcraft.lib.inventory.filter.StackFilter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.wrappers.BucketPickupHandlerWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class BlockUtil {

    /** @return A list of itemstacks that are dropped from the block, or null if the block is air */
    @Nullable
    public static NonNullList<ItemStack> getItemStackFromBlock(ServerLevel world, BlockPos pos, GameProfile owner) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (world.isEmptyBlock(pos)) {
            return null;
        }

        // Use the (old) method as not all mods have converted to the new one
        // (and the old method calls the new one internally)
//        List<ItemStack> drops = block.getDrops(world, pos, state, 0);
        List<ItemStack> drops = block.getDrops(state, world, pos, world.getBlockEntity(pos));
        Player fakePlayer = BuildCraftAPI.fakePlayerProvider.getFakePlayer(world, owner, pos);
//        float dropChance = ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 1.0F, false, fakePlayer);
        float dropChance = 1;

        NonNullList<ItemStack> returnList = NonNullList.create();
        for (ItemStack s : drops) {
            if (world.random.nextFloat() <= dropChance) {
                returnList.add(s);
            }
        }

        return returnList;
    }

    public static boolean breakBlock(ServerLevel world, BlockPos pos, BlockPos ownerPos, GameProfile owner) {
        return breakBlock(world, pos, BCLibConfig.itemLifespan * 20, ownerPos, owner);
    }

    public static boolean breakBlock(ServerLevel world, BlockPos pos, int forcedLifespan, BlockPos ownerPos,
                                     GameProfile owner) {
        NonNullList<ItemStack> items = NonNullList.create();

        if (breakBlock(world, pos, items, ownerPos, owner)) {
            for (ItemStack item : items) {
                dropItem(world, pos, forcedLifespan, item);
            }
            return true;
        }
        return false;
    }

    public static boolean harvestBlock(ServerLevel world, BlockPos pos, @Nonnull ItemStack tool, GameProfile owner) {
        FakePlayer fakePlayer = getFakePlayerWithTool(world, tool, owner);
        BreakEvent breakEvent = new BreakEvent(world, pos, world.getBlockState(pos), fakePlayer);
        MinecraftForge.EVENT_BUS.post(breakEvent);

        if (breakEvent.isCanceled()) {
            return false;
        }

        BlockState state = world.getBlockState(pos);

        if (!state.getBlock().canHarvestBlock(state, world, pos, fakePlayer)) {
            return false;
        }

        // set blockState
//        state.getBlock().onBlockHarvested(world, pos, state, fakePlayer);
        state.getBlock().onDestroyedByPlayer(state, world, pos, fakePlayer, true, world.getFluidState(pos));
        // drop
//        state.getBlock().harvestBlock(world, fakePlayer, pos, state, world.getBlockEntity(pos), tool);
        state.getBlock().playerDestroy(world, fakePlayer, pos, state, world.getBlockEntity(pos), tool);
        // Don't drop items as we do that ourselves
        world.destroyBlock(pos, /* dropBlock = */ false);

        return true;
    }

    public static boolean destroyBlock(ServerLevel world, BlockPos pos, @Nonnull ItemStack tool, GameProfile owner) {
        FakePlayer fakePlayer = getFakePlayerWithTool(world, tool, owner);
        BreakEvent breakEvent = new BreakEvent(world, pos, world.getBlockState(pos), fakePlayer);
        MinecraftForge.EVENT_BUS.post(breakEvent);

        if (breakEvent.isCanceled()) {
            return false;
        }
        world.destroyBlock(pos, true);
        return true;
    }

    public static FakePlayer getFakePlayerWithTool(ServerLevel world, @Nonnull ItemStack tool, GameProfile owner) {
        FakePlayer player = BuildCraftAPI.fakePlayerProvider.getFakePlayer(world, owner);
        int i = 0;

        while (player.getItemInHand(InteractionHand.MAIN_HAND) != tool && i < 9) {
            if (i > 0) {
                player.getInventory().setItem(i - 1, StackUtil.EMPTY);
            }

            player.getInventory().setItem(i, tool);
            i++;
        }

        return player;
    }

    public static boolean breakBlock(ServerLevel world, BlockPos pos, NonNullList<ItemStack> drops, BlockPos ownerPos,
                                     GameProfile owner) {
        FakePlayer fakePlayer = BuildCraftAPI.fakePlayerProvider.getFakePlayer(world, owner, ownerPos);
        BreakEvent breakEvent = new BreakEvent(world, pos, world.getBlockState(pos), fakePlayer);
        MinecraftForge.EVENT_BUS.post(breakEvent);

        if (breakEvent.isCanceled()) {
            return false;
        }

        if (!world.isEmptyBlock(pos) && !world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            drops.addAll(getItemStackFromBlock(world, pos, owner));
        }
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

        return true;
    }

    public static void dropItem(ServerLevel world, BlockPos pos, int forcedLifespan, ItemStack stack) {
        float var = 0.7F;
        double dx = world.random.nextFloat() * var + (1.0F - var) * 0.5D;
        double dy = world.random.nextFloat() * var + (1.0F - var) * 0.5D;
        double dz = world.random.nextFloat() * var + (1.0F - var) * 0.5D;
        ItemEntity entityitem = new ItemEntity(world, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, stack);

        entityitem.lifespan = forcedLifespan;
        entityitem.setDefaultPickUpDelay();

        world.addFreshEntity(entityitem);
    }

    public static Optional<List<ItemStack>> breakBlockAndGetDrops(ServerLevel world, BlockPos pos,
                                                                  @Nonnull ItemStack tool, GameProfile owner) {
        return breakBlockAndGetDrops(world, pos, tool, owner, false);
    }

    /** @param grabAll If true then this will pickup every item in range of the position, false to only get the items
     *            that the dropped while breaking the block. */
    public static Optional<List<ItemStack>> breakBlockAndGetDrops(ServerLevel world, BlockPos pos,
                                                                  @Nonnull ItemStack tool, GameProfile owner, boolean grabAll) {
        AABB aabb = new AABB(pos).inflate(1);
        Set<Entity> entities;
        if (grabAll) {
            entities = Collections.emptySet();
        } else {
            entities = Sets.newIdentityHashSet();
            entities.addAll(world.getEntitiesOfClass(ItemEntity.class, aabb));
        }
        if (!harvestBlock(world, pos, tool, owner)) {
            if (!destroyBlock(world, pos, tool, owner)) {
                return Optional.empty();
            }
        }
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemEntity entity : world.getEntitiesOfClass(ItemEntity.class, aabb)) {
            if (entities.contains(entity)) {
                continue;
            }
            TransactorEntityItem transactor = new TransactorEntityItem(entity);
            ItemStack stack;
            while (!(stack = transactor.extract(StackFilter.ALL, 0, Integer.MAX_VALUE, false)).isEmpty()) {
                stacks.add(stack);
            }
        }
        return Optional.of(stacks);
    }

    public static boolean canChangeBlock(Level world, BlockPos pos, GameProfile owner) {
        return canChangeBlock(world.getBlockState(pos), world, pos, owner);
    }

    public static boolean canChangeBlock(BlockState state, Level world, BlockPos pos, GameProfile owner) {
        if (state == null) return true;

        Block block = state.getBlock();
        if (world.isEmptyBlock(pos)) {
            return true;
        }

        if (isUnbreakableBlock(world, pos, state, owner)) {
            return false;
        }

        // Calen: still/flow -> 1.12.2 same fluid different block / 1.18.2 different fluid same block
//        if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
        if (block == Blocks.LAVA) {
            return false;
        }
//        else if (block instanceof IFluidBlock && ((IFluidBlock) block).getFluid() != null)
        else if (block instanceof IFluidBlock fluidBlock && fluidBlock.getFluid() != null) {
//            Fluid f = ((IFluidBlock) block).getFluid();
            Fluid f = fluidBlock.getFluid();
//            if (f.getDensity(world, pos) >= 3000)
            if (f.getAttributes().getDensity(world, pos) >= 3000) {
                return false;
            }
        }

        return true;
    }

    public static float getBlockHardnessMining(Level world, BlockPos pos, BlockState state, GameProfile owner) {
        if (world instanceof ServerLevel) {
            Player fakePlayer = BuildCraftAPI.fakePlayerProvider.getFakePlayer((ServerLevel) world, owner);
//            float relativeHardness = state.getPlayerRelativeBlockHardness(fakePlayer, world, pos);
//            float relativeHardness = state.getDestroySpeed(world, pos);
            boolean canDestroy = state.canEntityDestroy(world, pos, fakePlayer);
//            if (relativeHardness <= 0.0F)
//            if (relativeHardness < 0.0F)
            if (!canDestroy) {
                // Forge's getPlayerRelativeBlockHardness hook returns 0.0F if the hardness is < 0.0F.
                return -1.0F;
            }
        }
        return state.getDestroySpeed(world, pos);
    }

    public static boolean isUnbreakableBlock(Level world, BlockPos pos, BlockState state, GameProfile owner) {
        return getBlockHardnessMining(world, pos, state, owner) < 0;
    }

    public static boolean isUnbreakableBlock(Level world, BlockPos pos, GameProfile owner) {
        return isUnbreakableBlock(world, pos, world.getBlockState(pos), owner);
    }

    /** Returns true if a block cannot be harvested without a tool. */
    public static boolean isToughBlock(Level world, BlockPos pos) {
//        return !world.getBlockState(pos).getMaterial().isToolNotRequired();
        return world.getBlockState(pos).requiresCorrectToolForDrops();
    }

    public static boolean isFullFluidBlock(Level world, BlockPos pos) {
        return isFullFluidBlock(world.getBlockState(pos), world, pos);
    }

    public static boolean isFullFluidBlock(BlockState state, Level world, BlockPos pos) {
        Block block = state.getBlock();
//        if (block instanceof IFluidBlock)
        if (block instanceof IFluidBlock fluidBlock) {
//            FluidStack fluid = ((IFluidBlock) block).drain(world, pos, IFluidHandler.FluidAction.SIMULATE);
            FluidStack fluid = fluidBlock.drain(world, pos, IFluidHandler.FluidAction.SIMULATE);
//            return fluid == null || fluid.getAmount() > 0;
            return fluid.isEmpty() || fluid.getAmount() > 0;
        } else if (block instanceof LiquidBlock) {
            int level = state.getValue(LiquidBlock.LEVEL);
            return level == 0;
        }
        return false;
    }

    public static Fluid getFluid(Level world, BlockPos pos) {
//        BlockState blockState = world.getBlockState(pos);
//        Block block = blockState.getBlock();
//        return getFluid(block);
        // drainBlock(world, pos, IFluidHandler.FluidAction.SIMULATE)只能尝试取容器里的流体不能取方块流体?
//        FluidStack fluid = drainBlock(world, pos, IFluidHandler.FluidAction.SIMULATE);
//        // Calen added: && !fluid.isEmpty() 空流体会返回一个EmptyFluid对象
//        return fluid != null && !fluid.isEmpty() ? fluid.getFluid() : null;
        // Calen: this may be better
//        Fluid ret = world.getFluidState(pos).getType();
//        return (ret == null || ret instanceof EmptyFluid) ? null : ret;
//        FluidStack fluid = drainBlock(world, pos, false);
//        return fluid != null ? fluid.getFluid() : null;
        FluidStack ret = drainBlock(world, pos, IFluidHandler.FluidAction.SIMULATE);
        return (ret == null || ret.isEmpty()) ? null : ret.getFluid();
    }

    public static Fluid getFluidWithFlowing(Level world, BlockPos pos) {
//        BlockState blockState = world.getBlockState(pos);
//        Block block = blockState.getBlock();
////        if (block == Blocks.FLOWING_WATER)
//        if (block == Blocks.WATER)
//        {
//            return Fluids.WATER;
//        }
////        if (block == Blocks.FLOWING_LAVA)
//        if (block == Blocks.LAVA)
//        {
//            return Fluids.LAVA;
//        }
//        return getFluid(block);
        // Calen: this may be better
        Fluid ret = world.getFluidState(pos).getType();
        return (ret == null || ret instanceof EmptyFluid) ? null : ret;
    }

    public static Fluid getFluid(Block block) {
        if (block instanceof IFluidBlock fluidBlock) {
            return fluidBlock.getFluid();
        }
//        return FluidRegistry.lookupFluidForBlock(block);
        return null;
    }

    public static Fluid getFluidWithoutFlowing(BlockState state) {
        Block block = state.getBlock();
//        if (block instanceof BlockFluidClassic)
//        {
//            if (((BlockFluidClassic) block).isSourceBlock(new SingleBlockAccess(state), SingleBlockAccess.POS))
//            {
//                return getFluid(block);
//            }
//        }
        if (block instanceof LiquidBlock) {
//            if (state.getFluidState().getAmount() != 0)
//            {
//                return null;
//            }
////            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
//            if (block == Blocks.WATER)
//            {
//                return Fluids.WATER;
//            }
////            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
//            if (block == Blocks.LAVA)
//            {
//                return Fluids.LAVA;
//            }
////            return FluidRegistry.lookupFluidForBlock(block);
//            return state.getFluidState().getType();
            FluidState fluidState = state.getFluidState();
            Fluid fluid = fluidState.getType();
            if (fluid != null && !(fluid instanceof EmptyFluid) && fluid.isSource(fluidState)) {
                return state.getFluidState().getType();
            }
        }
        return null;
    }

    public static Fluid getFluidWithFlowing(Block block) {
        Fluid fluid = null;
//        if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
        if (block == Blocks.LAVA) {
            fluid = Fluids.LAVA;
        }
//        else if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
        else if (block == Blocks.WATER) {
            fluid = Fluids.WATER;
        } else if (block instanceof IFluidBlock) {
            fluid = ((IFluidBlock) block).getFluid();
        }
        return fluid;
    }

    public static FluidStack drainBlock(Level world, BlockPos pos, IFluidHandler.FluidAction doDrain) {
        // Calen: never pick up flowing fake fluid
        BlockState state = world.getBlockState(pos);
        if (!state.getFluidState().getType().isSource(state.getFluidState())) {
            return StackUtil.EMPTY_FLUID;
        }
        // 1.18.2 getFluidHandler只可挖有capability的BE的流体 tryPickUpFluid可以挖流体方块和BE中流体
        IFluidHandler handler;
        Block block = state.getBlock();
        // 写法参考 FluidUtil#public static FluidActionResult tryPickUpFluid(@Nonnull ItemStack emptyContainer, @Nullable Player playerIn, Level level, BlockPos pos, Direction side)
        if (block instanceof IFluidBlock fluidBlock) {
            handler = new FluidBlockWrapper(fluidBlock, world, pos);
        } else if (block instanceof BucketPickup bucketPickup) {
            handler = new BucketPickupHandlerWrapper(bucketPickup, world, pos);
        } else {
            // Calen: 1.18.2 this can only get FluidHandler of TileEntity
            handler = FluidUtil.getFluidHandler(world, pos, null).orElse(null);
        }
        if (handler != null) {
            return handler.drain(FluidAttributes.BUCKET_VOLUME, doDrain);
        } else {
            return StackUtil.EMPTY_FLUID;
        }
    }

    /** Create an explosion which only affects a single block. */
    public static void explodeBlock(Level world, BlockPos pos) {
//        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
//        {
//            return;
//        }
        if (world.isClientSide) {
            return;
        }

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

//        Explosion explosion = new Explosion(world, null, x, y, z, 3f, false, false);
        Explosion explosion = new Explosion(world, null, x, y, z, 3f, false, Explosion.BlockInteraction.NONE);
        explosion.getToBlow().add(pos);
        explosion.finalizeExplosion(true); // 不破坏方块

        for (Player player : world.players()) {
            if (!(player instanceof ServerPlayer)) {
                continue;
            }

            if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 4096) {
                ((ServerPlayer) player).connection
                        .send(new ClientboundExplodePacket(x, y, z, 3f, explosion.getToBlow(), null));
            }
        }
    }

    public static long computeBlockBreakPower(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        float hardness = state.getDestroySpeed(world, pos);
        return (long) Math.floor(16 * MjAPI.MJ * ((hardness + 1) * 2) * BCCoreConfig.miningMultiplier);
    }

    /** The following functions let you avoid unnecessary chunk loads, which is nice. */
    public static BlockEntity getTileEntity(Level world, BlockPos pos) {
        return getTileEntity(world, pos, false);
    }

    public static BlockEntity getTileEntity(Level world, BlockPos pos, boolean force) {
        return CompatManager.getTile(world, pos, force);
    }

    public static BlockState getBlockState(Level world, BlockPos pos) {
        return getBlockState(world, pos, false);
    }

    public static BlockState getBlockState(Level world, BlockPos pos, boolean force) {
        return CompatManager.getState(world, pos, force);
    }

    public static boolean useItemOnBlock(Level world, Player player, ItemStack stack, BlockPos pos, Direction direction) {
//        boolean done = stack.getItem().onItemUseFirst(player, world, pos, direction, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND) == InteractionResult.SUCCESS;
        UseOnContext ctx = new UseOnContext(
                world,
                player,
                InteractionHand.MAIN_HAND,
                stack,
                new BlockHitResult(
                        new Vec3(0.5F, 0.5F, 0.5F),
                        direction, pos, false
                )
        );
        boolean done = stack.getItem().onItemUseFirst(
                stack,
                ctx
        ) == InteractionResult.SUCCESS;

        if (!done) {
            done = stack.getItem().useOn(ctx) == InteractionResult.SUCCESS;
        }
        return done;
    }

    public static void onComparatorUpdate(Level world, BlockPos pos, Block block) {
//        world.updateComparatorOutputLevel(pos, block);
        world.updateNeighbourForOutputSignal(pos, block);
    }

    // Calen: ChestBlock#CHEST_COMBINER
    // TODO Calen maybe not right
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<ChestBlockEntity>> CHEST_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<ChestBlockEntity>>() {
        public Optional<ChestBlockEntity> acceptDouble(ChestBlockEntity p_51591_, ChestBlockEntity p_51592_) {
            return Optional.of(p_51592_);
        }

        public Optional<ChestBlockEntity> acceptSingle(ChestBlockEntity p_51589_) {
            return Optional.empty();
        }

        public Optional<ChestBlockEntity> acceptNone() {
            return Optional.empty();
        }
    };

    // public static TileEntityChest getOtherDoubleChest(TileEntity inv)
    public static ChestBlockEntity getOtherDoubleChest(BlockEntity inv) {
        if (inv instanceof ChestBlockEntity) {
//            ChestBlockEntity chest = (ChestBlockEntity) inv;
//
//            ChestBlockEntity adjacent = null;
//
//            chest.checkForAdjacentChests();
//
//            if (chest.adjacentChestXNeg != null)
//            {
//                adjacent = chest.adjacentChestXNeg;
//            }
//
//            if (chest.adjacentChestXPos != null)
//            {
//                adjacent = chest.adjacentChestXPos;
//            }
//
//            if (chest.adjacentChestZNeg != null)
//            {
//                adjacent = chest.adjacentChestZNeg;
//            }
//
//            if (chest.adjacentChestZPos != null)
//            {
//                adjacent = chest.adjacentChestZPos;
//            }
//
//            return adjacent;

            return ((ChestBlock) Blocks.CHEST).combine(inv.getBlockState(), inv.getLevel(), inv.getBlockPos(), true).apply(CHEST_COMBINER).orElse(null);
        }
        return null;
    }

    public static <T extends Comparable<T>> BlockState copyProperty(Property<T> property, BlockState dst,
                                                                    BlockState src) {
        return dst.getProperties().contains(property) ? dst.setValue(property, src.getValue(property)) : dst;
    }

    public static <T extends Comparable<T>> int compareProperty(Property<T> property, BlockState a, BlockState b) {
        return a.getValue(property).compareTo(b.getValue(property));
    }

    public static <T extends Comparable<T>> String getPropertyStringValue(BlockState blockState,
                                                                          Property<T> property) {
        return property.getName(blockState.getValue(property));
    }

    public static Map<String, String> getPropertiesStringMap(BlockState blockState,
                                                             Collection<Property<?>> properties) {
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<>();
        for (Property<?> property : properties) {
            mapBuilder.put(property.getName(), getPropertyStringValue(blockState, property));
        }
        return mapBuilder.build();
    }

    public static Map<String, String> getPropertiesStringMap(BlockState blockState) {
        return getPropertiesStringMap(blockState, blockState.getProperties());
    }

    public static Comparator<BlockState> blockStateComparator() {
        return (blockStateA, blockStateB) ->
        {
            Block blockA = blockStateA.getBlock();
            Block blockB = blockStateB.getBlock();
            if (blockA != blockB) {
                return blockA.getRegistryName().toString().compareTo(blockB.getRegistryName().toString());
            }
            for (Property<?> property : Sets.intersection(new HashSet<>(blockStateA.getProperties()),
                    new HashSet<>(blockStateB.getProperties()))) {
                int compareResult = BlockUtil.compareProperty(property, blockStateA, blockStateB);
                if (compareResult != 0) {
                    return compareResult;
                }
            }
            return 0;
        };
    }

    public static boolean blockStatesWithoutBlockEqual(BlockState a, BlockState b,
                                                       Collection<Property<?>> ignoredProperties) {
        return Sets.intersection(new HashSet<>(a.getProperties()), new HashSet<>(b.getProperties())).stream()
                .filter(property -> !ignoredProperties.contains(property))
                .allMatch(property -> Objects.equals(a.getValue(property), b.getValue(property)));
    }

    public static boolean blockStatesWithoutBlockEqual(BlockState a, BlockState b) {
        return Sets.intersection(new HashSet<>(a.getProperties()), new HashSet<>(b.getProperties())).stream()
                .allMatch(property -> Objects.equals(a.getValue(property), b.getValue(property)));
    }

    public static boolean blockStatesEqual(BlockState a, BlockState b, Collection<Property<?>> ignoredProperties) {
        return a.getBlock() == b.getBlock()
                && Sets.intersection(new HashSet<>(a.getProperties()), new HashSet<>(b.getProperties())).stream()
                .filter(property -> !ignoredProperties.contains(property))
                .allMatch(property -> Objects.equals(a.getValue(property), b.getValue(property)));
    }

    public static boolean blockStatesEqual(BlockState a, BlockState b) {
        return a.getBlock() == b.getBlock()
                && Sets.intersection(new HashSet<>(a.getProperties()), new HashSet<>(b.getProperties())).stream()
                .allMatch(property -> Objects.equals(a.getValue(property), b.getValue(property)));
    }

    public static Comparator<BlockPos> uniqueBlockPosComparator(Comparator<BlockPos> parent) {
        return (a, b) ->
        {
            int parentValue = parent.compare(a, b);
            if (parentValue != 0) {
                return parentValue;
            } else if (a.getX() != b.getX()) {
                return Integer.compare(a.getX(), b.getX());
            } else if (a.getY() != b.getY()) {
                return Integer.compare(a.getY(), b.getY());
            } else if (a.getZ() != b.getZ()) {
                return Integer.compare(a.getZ(), b.getZ());
            } else {
                return 0;
            }
        };
    }

    // Calen
    public static Block getBlockFromName(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
    }

    public static Block getBlockFromName(ResourceLocation name) {
        return ForgeRegistries.BLOCKS.getValue(name);
    }
}
