package buildcraft.api.transport.pluggable;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public interface IPluggableStaticBaker<K extends PluggableModelKey> {
    List<BakedQuad> bake(K key);
}
