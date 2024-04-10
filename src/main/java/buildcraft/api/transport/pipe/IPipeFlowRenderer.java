package buildcraft.api.transport.pipe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public interface IPipeFlowRenderer<F extends PipeFlow> {
    /** @param flow The flow to render
     * @param x
     * @param y
     * @param z
     * @param bufferBuilder The (optional) vertex buffer that you can render into. Note that you can still do GL stuff. */
//    void render(F flow, double x, double y, double z, float partialTicks, BufferBuilder bufferBuilder);
    void render(F flow, float partialTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int combinedLight, int combinedOverlay);
}
