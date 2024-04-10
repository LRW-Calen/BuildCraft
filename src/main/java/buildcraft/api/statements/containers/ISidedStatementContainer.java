package buildcraft.api.statements.containers;

import buildcraft.api.statements.IStatementContainer;
import net.minecraft.core.Direction;

/** Created by asie on 3/14/15. */
public interface ISidedStatementContainer extends IStatementContainer {
    Direction getSide();
}
