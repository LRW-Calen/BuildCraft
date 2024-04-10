package buildcraft.api.transport.pipe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IPipeBehaviourRenderer<B extends PipeBehaviour> {
//    void render(B behaviour, double x, double y, double z, float partialTicks, BufferBuilder bb);
//    public void render(B gate, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay);
    public void render(B gate, float partialTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay);
}
