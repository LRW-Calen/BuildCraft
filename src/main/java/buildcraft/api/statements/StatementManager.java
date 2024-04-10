/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.statements;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class StatementManager
{
    // Calen: Thread Safety -> InvalidInputDataException: Unknown paramater type buildcraft:fillerParameterYDir
    public static Map<String, IStatement> statements = new HashMap<>();
//    public static Map<Component, IStatement> statements = new ConcurrentHashMap<>();
    public static Map<String, IParameterReader> parameters = new HashMap<>();
//    public static Map<Component, IParameterReader> parameters = new ConcurrentHashMap<>();
    public static Map<String, IParamReaderBuf> paramsBuf = new HashMap<>();
//    public static Map<Component, IParamReaderBuf> paramsBuf = new ConcurrentHashMap<>();
    private static List<ITriggerProvider> triggerProviders = new LinkedList<>();
    private static List<IActionProvider> actionProviders = new LinkedList<>();

    static {
        registerParameter(StatementParameterItemStack::new);
    }

    @FunctionalInterface
    public interface IParameterReader {
        IStatementParameter readFromNbt(CompoundTag nbt);
    }

    @FunctionalInterface
    public interface IParamReaderBuf {
        IStatementParameter readFromBuf(FriendlyByteBuf buffer) throws IOException;
    }

    /** Deactivate constructor */
    private StatementManager() {}

    public static void registerTriggerProvider(ITriggerProvider provider) {
        if (provider != null && !triggerProviders.contains(provider)) {
            triggerProviders.add(provider);
        }
    }

    public static void registerActionProvider(IActionProvider provider) {
        if (provider != null && !actionProviders.contains(provider)) {
            actionProviders.add(provider);
        }
    }

    public static void registerStatement(IStatement statement) {
        statements.put(statement.getUniqueTag().getString(), statement);
    }

    public static void registerParameter(IParameterReader reader) {
        registerParameter(reader, buf -> reader.readFromNbt(buf.readNbt()));
    }

    public static void registerParameter(IParameterReader reader, IParamReaderBuf bufReader) {
        Component name = reader.readFromNbt(new CompoundTag()).getUniqueTag();
        registerParameter(name, reader);
        registerParameter(name, bufReader);
    }

    public static void registerParameter(Component name, IParameterReader reader) {
        parameters.put(name.getString(), reader);
    }

    public static void registerParameter(Component name, IParamReaderBuf reader) {
        paramsBuf.put(name.getString(), reader);
    }

    public static List<ITriggerExternal> getExternalTriggers(Direction side, BlockEntity entity) {
        if (entity instanceof IOverrideDefaultStatements) {
            List<ITriggerExternal> result = ((IOverrideDefaultStatements) entity).overrideTriggers();
            if (result != null) {
                return result;
            }
        }

        LinkedHashSet<ITriggerExternal> triggers = new LinkedHashSet<>();

        for (ITriggerProvider provider : triggerProviders) {
            provider.addExternalTriggers(triggers, side, entity);
        }

        return new ArrayList<>(triggers);
    }

    public static List<IActionExternal> getExternalActions(Direction side, BlockEntity entity) {
        if (entity instanceof IOverrideDefaultStatements) {
            List<IActionExternal> result = ((IOverrideDefaultStatements) entity).overrideActions();
            if (result != null) {
                return result;
            }
        }

        LinkedHashSet<IActionExternal> actions = new LinkedHashSet<>();

        for (IActionProvider provider : actionProviders) {
            provider.addExternalActions(actions, side, entity);
        }

        return new ArrayList<>(actions);
    }

    public static List<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
        LinkedHashSet<ITriggerInternal> triggers = new LinkedHashSet<>();

        for (ITriggerProvider provider : triggerProviders) {
            provider.addInternalTriggers(triggers, container);
        }

        return new ArrayList<>(triggers);
    }

    public static List<IActionInternal> getInternalActions(IStatementContainer container) {
        LinkedHashSet<IActionInternal> actions = new LinkedHashSet<>();

        for (IActionProvider provider : actionProviders) {
            provider.addInternalActions(actions, container);
        }

        return new ArrayList<>(actions);
    }

    public static List<ITriggerInternalSided> getInternalSidedTriggers(IStatementContainer container, Direction side) {
        LinkedHashSet<ITriggerInternalSided> triggers = new LinkedHashSet<>();

        for (ITriggerProvider provider : triggerProviders) {
            provider.addInternalSidedTriggers(triggers, container, side);
        }

        return new ArrayList<>(triggers);
    }

    public static List<IActionInternalSided> getInternalSidedActions(IStatementContainer container, Direction side) {
        LinkedHashSet<IActionInternalSided> actions = new LinkedHashSet<>();

        for (IActionProvider provider : actionProviders) {
            provider.addInternalSidedActions(actions, container, side);
        }

        return new ArrayList<>(actions);
    }

    public static IParameterReader getParameterReader(String kind) {
        return parameters.get(kind);
    }
}
