package buildcraft.api.transport.pipe;

import net.minecraft.client.renderer.block.model.BakedQuad;

import java.util.List;

public interface IPipeFlowBaker<F extends PipeFlow> {
    List<BakedQuad> bake(F flow);
}
