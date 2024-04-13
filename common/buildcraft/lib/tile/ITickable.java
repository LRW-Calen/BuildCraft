package buildcraft.lib.tile;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// From 1.12.2 MC
public interface ITickable
{
    /**
     * Like the old updateEntity(), except more generic.
     */
    @MustBeInvokedByOverriders
//    void update();
    default void update()
    {
        LinkedList<Runnable> taskOfCurrentTile = tasks.get(this);
        if (taskOfCurrentTile != null && !taskOfCurrentTile.isEmpty())
        {
            taskOfCurrentTile.forEach(Runnable::run);
            taskOfCurrentTile.clear();
        }
    }

    static final Map<ITickable, LinkedList<Runnable>> tasks = new ConcurrentHashMap<>();

    // Calen

    /**
     * When world loading, BlockEntity#level may be null, or Level#getBlockState may cause dead lock
     *
     * @param task
     * @param forceDelay If true, the task will be delayed to next update() even if the world is not null.
     */
    default void runWhenWorldNotNull(Runnable task, boolean forceDelay)
    {
        if (forceDelay || getLevel() == null)
        {
            List<Runnable> tasksOfCurrentTile = tasks.computeIfAbsent(this, k -> new LinkedList<>());
            tasksOfCurrentTile.add(task);
        }
        else
        {
            task.run();
        }
    }

    Level getLevel();
}
