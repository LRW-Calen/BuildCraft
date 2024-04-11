package buildcraft.api.schematics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class SchematicEntityContext
{
    @Nonnull
    public final Level world;
    @Nonnull
    public final BlockPos basePos;
    @Nonnull
    public final Entity entity;

    public SchematicEntityContext(@Nonnull Level world,
                                  @Nonnull BlockPos basePos,
                                  @Nonnull Entity entity) {
        this.world = world;
        this.basePos = basePos;
        this.entity = entity;
    }
}
