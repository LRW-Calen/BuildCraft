package buildcraft.api.transport.pipe;

import net.minecraft.world.level.block.entity.BlockEntity;

/** Fired when the state of a pipe's tile entity changes. Listen for subclasses, not this one! */
public abstract class PipeEventTileState extends PipeEvent {
    PipeEventTileState(IPipeHolder holder) {
        super(holder);
    }

    /** Fired in {@link BlockEntity#invalidate()} */
    public static class Invalidate extends PipeEventTileState {
        public Invalidate(IPipeHolder holder) {
            super(holder);
        }
    }

    /** Fired in {@link BlockEntity#validate()} */
    public static class Validate extends PipeEventTileState {
        public Validate(IPipeHolder holder) {
            super(holder);
        }
    }

    /** Fired in {@link BlockEntity#onChunkUnloaded()} */
    public static class ChunkUnload extends PipeEventTileState {
        public ChunkUnload(IPipeHolder holder) {
            super(holder);
        }
    }
}
