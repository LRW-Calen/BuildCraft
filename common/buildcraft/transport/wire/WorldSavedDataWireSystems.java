/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.wire;

import buildcraft.api.core.BCLog;
import buildcraft.api.transport.EnumWirePart;
import buildcraft.api.transport.IWireEmitter;
import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.lib.net.MessageManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorldSavedDataWireSystems extends SavedData {
    public static final String DATA_NAME = "buildcraft_wire_systems";
    public Level world;
    public final Map<WireSystem, Boolean> wireSystems = new HashMap<>();
    public boolean gatesChanged = true;
    public boolean structureChanged = true;
    public final List<WireSystem> changedSystems = new ArrayList<>();
    public final List<ServerPlayer> changedPlayers = new ArrayList<>();
    public final Map<WireSystem.WireElement, IWireEmitter> emittersCache = new HashMap<>();

    private final Map<WireSystem.WireElement, List<WireSystem>> elementsToWireSystemsIndex = new HashMap<>();

    public String name;

    public WorldSavedDataWireSystems() {
//        super(DATA_NAME);
        this.name = DATA_NAME;
    }

    public WorldSavedDataWireSystems(String name) {
//        super(name);
        this.name = name;
    }

    public void markStructureChanged() {
        structureChanged = true;
        gatesChanged = true;
        emittersCache.clear();
    }

    public List<WireSystem> getWireSystemsWithElement(WireSystem.WireElement element) {
        List<WireSystem> wireSystemsWithElement = this.elementsToWireSystemsIndex.get(element);
        return wireSystemsWithElement != null ? new ArrayList<>(wireSystemsWithElement) : Collections.emptyList();
    }

    public List<WireSystem> getWireSystemsWithElementAsReadOnlyList(WireSystem.WireElement element) {
        return this.elementsToWireSystemsIndex.getOrDefault(element, Collections.emptyList());
    }

    public void removeWireSystem(WireSystem wireSystem) {
        wireSystems.remove(wireSystem);
        wireSystem.elements.forEach(elementIn ->
        {
            elementsToWireSystemsIndex.computeIfPresent(elementIn, (element, wireSystems) ->
            {
                wireSystems.remove(wireSystem);
                return wireSystems.isEmpty() ? null : wireSystems;
            });
        });
        markStructureChanged();
    }

    public void addWireSystem(WireSystem wireSystem, boolean powered) {
        if (this.wireSystems.put(wireSystem, powered) == null) {
            wireSystem.elements.forEach(systemElement ->
            {
                List<WireSystem> wireSystemsWithElement = this.elementsToWireSystemsIndex.computeIfAbsent(systemElement, unused -> new ArrayList<>());
                if (wireSystemsWithElement.contains(wireSystem)) {
                    throw new IllegalStateException();
                }
                wireSystemsWithElement.add(wireSystem);
            });
        }
    }

    public void buildAndAddWireSystem(WireSystem.WireElement element) {
        WireSystem wireSystem = new WireSystem(this, element);
        if (!wireSystem.isEmpty()) {
            this.addWireSystem(wireSystem, false);
            wireSystems.put(wireSystem, wireSystem.update(this));
        }
        markStructureChanged();
    }

    public void rebuildWireSystemsAround(IPipeHolder holder) {
        Arrays.stream(EnumWirePart.values())
                .flatMap(part -> WireSystem.getConnectedElementsOfElement(world, new WireSystem.WireElement(holder.getPipePos(), part)).stream())
                .distinct()
                .forEach(this::buildAndAddWireSystem);
    }

    public IWireEmitter getEmitter(WireSystem.WireElement element) {
        if (element.type == WireSystem.WireElement.Type.EMITTER_SIDE) {
            if (!emittersCache.containsKey(element)) {
                if (!world.isLoaded(element.blockPos)) {
                    BCLog.logger.warn("[transport.wire] Ghost loading " + element.blockPos + " to look for an emitter!");
                }
                BlockEntity tile = world.getBlockEntity(element.blockPos);
                if (tile instanceof IPipeHolder) {
                    IPipeHolder holder = (IPipeHolder) tile;
                    PipePluggable plug = holder.getPluggable(element.emitterSide);
                    if (plug instanceof IWireEmitter) {
                        emittersCache.put(element, (IWireEmitter) plug);
                    }
                }
                if (!emittersCache.containsKey(element)) {
                    throw new IllegalStateException("Tried to get a wire element when none existed! THIS IS A BUG " + element);
                }
            }
            return emittersCache.get(element);
        }
        return null;
    }

    public boolean isEmitterEmitting(WireSystem.WireElement element, DyeColor color) {
        if (!world.isLoaded(element.blockPos)) {
            BCLog.logger.warn("[transport.wire] Ghost loading " + element.blockPos + " to look for an emitter!");
        }
        BlockEntity tile = world.getBlockEntity(element.blockPos);
        if (tile instanceof IPipeHolder) {
            IPipeHolder holder = (IPipeHolder) tile;
            if (holder.getPluggable(element.emitterSide) instanceof IWireEmitter) {
                return getEmitter(element).isEmitting(color);
            }
        }
        return false;
    }

    public void tick() {
        if (gatesChanged) {
            wireSystems.replaceAll((wireSystem, oldPowered) ->
            {
                boolean newPowered = wireSystem.update(this);
                if (oldPowered != newPowered) {
                    changedSystems.add(wireSystem);
                }
                return newPowered;
            });
        }
//        world.getPlayers(ServerPlayer.class, Predicates.alwaysTrue())
        world.getServer().getPlayerList().getPlayers()
                .forEach(player ->
                {
                    Map<Integer, WireSystem> changedWires = this.wireSystems.keySet().stream()
                            .filter(wireSystem -> wireSystem.isPlayerWatching(player) && (structureChanged || changedPlayers.contains(player)))
                            .collect(Collectors.toMap(WireSystem::getWiresHashCode, Function.identity()));
                    if (!changedWires.isEmpty()) {
                        MessageManager.sendTo(new MessageWireSystems(changedWires), player);
                    }
                    Map<Integer, Boolean> hashesPowered = this.wireSystems.entrySet().stream()
                            .filter(systemPower ->
                                    systemPower.getKey().isPlayerWatching(player) &&
                                            (structureChanged || changedSystems.contains(systemPower.getKey()) || changedPlayers.contains(player))
                            )
                            .map(systemPowered -> Pair.of(systemPowered.getKey().getWiresHashCode(), systemPowered.getValue()))
                            .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
                    if (!hashesPowered.isEmpty()) {
                        MessageManager.sendTo(new MessageWireSystemsPowered(hashesPowered), player);
                    }
                });
        if (structureChanged || !changedSystems.isEmpty()) {
            setDirty();
        }
        structureChanged = false;
        changedSystems.clear();
        changedPlayers.clear();
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag entriesList = new ListTag();
        wireSystems.forEach((wireSystem, powered) ->
        {
            CompoundTag entry = new CompoundTag();
            entry.put("wireSystem", wireSystem.writeToNBT());
            entry.putBoolean("powered", powered);
            entriesList.add(entry);
        });
        nbt.put("entries", entriesList);
        return nbt;
    }

    // @Override
    public void readFromNBT(CompoundTag nbt) {
        wireSystems.clear();
        this.elementsToWireSystemsIndex.clear();

        ListTag entriesList = nbt.getList("entries", Tag.TAG_COMPOUND);
        for (int i = 0; i < entriesList.size(); i++) {
            CompoundTag entry = entriesList.getCompound(i);
            this.addWireSystem(new WireSystem(entry.getCompound("wireSystem")), entry.getBoolean("powered"));
        }
    }

    public static WorldSavedDataWireSystems get(Level world) {
        if (world.isClientSide) {
            throw new UnsupportedOperationException("Attempted to get WorldSavedDataWireSystems on the client!");
        }
        ServerLevel serverLevel = (ServerLevel) world;
        DimensionDataStorage storage = serverLevel.getDataStorage();
//        WorldSavedDataWireSystems instance = (WorldSavedDataWireSystems) storage.getOrLoadData(WorldSavedDataWireSystems.class, DATA_NAME);
        WorldSavedDataWireSystems instance = storage.get((nbt) ->
        {
            WorldSavedDataWireSystems ret = new WorldSavedDataWireSystems();
            ret.readFromNBT(nbt);
            return ret;
        }, DATA_NAME);
        if (instance == null) {
            instance = new WorldSavedDataWireSystems();
            storage.set(DATA_NAME, instance);
        }
        instance.world = world;
        return instance;
    }
}
