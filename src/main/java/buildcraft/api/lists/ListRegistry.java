package buildcraft.api.lists;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListRegistry {
    public static final List<Class<? extends Item>> itemClassAsType = new ArrayList<>();
    private static final List<ListMatchHandler> handlers = new ArrayList<>();

    private ListRegistry() {

    }

    public static void registerHandler(ListMatchHandler h) {
        if (h != null) {
            handlers.add(h);
        }
    }

    public static List<ListMatchHandler> getHandlers() {
        return Collections.unmodifiableList(handlers);
    }
}
