package buildcraft.api.transport.pipe;

import net.minecraft.client.renderer.block.model.BakedQuad;

import java.util.List;

public interface IPipeBehaviourBaker<B extends PipeBehaviour> {
    List<BakedQuad> bake(B behaviour);
}
