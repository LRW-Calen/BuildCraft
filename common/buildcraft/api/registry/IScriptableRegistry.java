package buildcraft.api.registry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/** A type of {@link IReloadableRegistry} that can be configured with buildcraft style simple scripts. A full
 * description can be found [...] */
public interface IScriptableRegistry<E> extends IReloadableRegistry<E> {

    /** @return The path that this registry is at. Buildcraft returns entries similar to "buildcraft/recipes/coolant" or
     *         "buildcraft/recipes/fuel" */
    String getEntryType();

    /** @return A map containing GSON-deserisable custom types. These will be deserialised by GSON. The key with an
     *         empty string will be the default type. */
    Map<String, Class<? extends E>> getScriptableTypes();

    /** @return A map containing all custom deserializer types. The key with an empty string will be the default
     *         type. */
    Map<String, IEntryDeserializer<? extends E>> getCustomDeserializers();

    default void addSimpleType(String name, Class<? extends E> type) {
        getScriptableTypes().put(name, type);
    }

    default void addCustomType(String name, IEntryDeserializer<? extends E> deserializer) {
        getCustomDeserializers().put(name, deserializer);
    }

    /** @return A {@link Set} (likely unmodifiable) that contains all of the
     *         {@link ResourceLocation#getNamespace()}'s that had added entries to this registry through
     *         scripts. */
    Set<String> getSourceDomains();

    @FunctionalInterface
    public interface IEntryDeserializer<E> {
        /** @param name The entry name for this entry, that will be used as the key for
         *            {@link IReloadableRegistry#getReloadableEntryMap()}.{@link Map#put(Object, Object) put(name,
         *            instance)}
         * @return The deserialized entry, or an {@link OptionallyDisabled} object with
         *         {@link OptionallyDisabled#isPresent() isPresent()} as false if:
         *         <ol>
         *         <li>The json object is well-formed</li>
         *         <li>Some part of what the json refers to has been disabled externally through configs (for example
         *         blocks, items, pipes etc through buildcraft/objects.cfg or similar).</li>
         *         </ol>
         * @throws JsonSyntaxException if the input {@link JsonObject} was either missing required fields or had the
         *             wrong type or data for those fields. */
        OptionallyDisabled<E> deserialize(ResourceLocation name, JsonObject obj, JsonDeserializationContext ctx)
            throws JsonSyntaxException;
    }

    /** Similar to {@link IEntryDeserializer} except that this guarantees that
     * {@link IEntryDeserializer#deserialize(ResourceLocation, JsonObject, JsonDeserializationContext)} will never be
     * disabled. */
    @FunctionalInterface
    public interface ISimpleEntryDeserializer<E> extends IEntryDeserializer<E> {

        /** Bridge method to {@link #deserializeConst(ResourceLocation, JsonObject, JsonDeserializationContext)}.
         * <p>
         * NOTE: Callers are free to assume that this method is never overridden, and so are free to call
         * {@link #deserializeConst(ResourceLocation, JsonObject, JsonDeserializationContext)} directly. */
        @Override
        default OptionallyDisabled<E> deserialize(ResourceLocation name, JsonObject obj, JsonDeserializationContext ctx)
            throws JsonSyntaxException {
            return new OptionallyDisabled<>(deserializeConst(name, obj, ctx));
        }

        /** @param name The entry name for this entry, that will be used as the key for
         *            {@link IReloadableRegistry#getReloadableEntryMap()}.{@link Map#put(Object, Object) put(name,
         *            instance)}
         * @return The deserialized entry.
         * @throws JsonSyntaxException if the input {@link JsonObject} was either missing required fields or had the
         *             wrong type or data for those fields. */
        E deserializeConst(ResourceLocation name, JsonObject obj, JsonDeserializationContext ctx)
            throws JsonSyntaxException;
    }

    /** A simple wrapper which either contains the object, or a string with a reason why it is allowed to be null. */
    public static final class OptionallyDisabled<E> {

        @Nullable
        private final E object;

        @Nullable
        private final String reason;

        public OptionallyDisabled(E object) {
            this.object = object;
            this.reason = null;
        }

        public OptionallyDisabled(String reason) {
            this.object = null;
            this.reason = reason;
        }

        public boolean isPresent() {
            return object != null;
        }

        @Nonnull
        public E get() {
            final E o = object;
            if (o != null) {
                return o;
            } else {
                throw new IllegalStateException("This object has been disabled! You must call isPresent() first!");
            }
        }

        @Nonnull
        public String getDisabledReason() {
            final String r = reason;
            if (r != null) {
                return r;
            } else {
                throw new IllegalStateException("This object has not been disabled! You must call isPresent() first!");
            }
        }
    }
}
