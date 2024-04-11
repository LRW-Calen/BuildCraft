package buildcraft.api.filler;

import buildcraft.api.statements.IStatement;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IFillerRegistry {
    void addPattern(IFillerPattern pattern);

    /** @return An {@link IFillerPattern} from its {@link IStatement#getUniqueTag()} */
    @Nullable
    IFillerPattern getPattern(String name);

    Collection<IFillerPattern> getPatterns();

    IFilledTemplate createFilledTemplate(BlockPos pos, BlockPos size);
}
