/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.client.render;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.transport.pipe.IPipeFlowRenderer;
import buildcraft.lib.client.render.fluid.FluidRenderer;
import buildcraft.lib.client.render.fluid.FluidSpriteType;
import buildcraft.lib.misc.RenderUtil;
import buildcraft.lib.misc.VecUtil;
import buildcraft.transport.pipe.Pipe;
import buildcraft.transport.pipe.flow.PipeFlowFluids;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public enum PipeFlowRendererFluids implements IPipeFlowRenderer<PipeFlowFluids> {
    INSTANCE;

    @Override
//    public void render(PipeFlowFluids flow, double x, double y, double z, float partialTicks, BufferBuilder vb)
    public void render(PipeFlowFluids flow, float partialTicks, PoseStack poseStack, VertexConsumer fluidBuffer, int combinedLight, int combinedOverlay) {
        FluidStack forRender = flow.getFluidStackForRender();
        if (forRender == null) {
            return;
        }

        ProfilerFiller prof = Minecraft.getInstance().getProfiler();
        prof.push("calc");

        boolean[] sides = new boolean[6];
        Arrays.fill(sides, true);

        double[] amounts = flow.getAmountsForRender(partialTicks);
        Vec3[] offsets = flow.getOffsetsForRender(partialTicks);

        int blocklight = forRender.getRawFluid().getAttributes().getLuminosity(forRender);
//        IPipeHolder holder = flow.pipe.getHolder();
//        combinedLight = holder.getPipeWorld().getCombinedLight(holder.getPipePos(), blocklight);
        combinedLight = RenderUtil.combineWithFluidLight(combinedLight, (byte) blocklight);

        FluidRenderer.vertex.lighti(combinedLight);
        FluidRenderer.vertex.overlay(combinedOverlay);

//        try (AutoTessellator tess = RenderUtil.getThreadLocalUnusedTessellator()) {
//            BufferBuilder fluidBuffer = tess.tessellator.getBuffer();
//            fluidBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
//            fluidBuffer.setTranslation(x, y, z);

        boolean gas = forRender.getRawFluid().getAttributes().isGaseous(forRender);
        boolean horizontal = false;
        boolean vertical = flow.pipe.isConnected(gas ? Direction.DOWN : Direction.UP);

        prof.popPush("build");
        for (Direction face : Direction.values()) {
            double size = ((Pipe) flow.pipe).getConnectedDist(face);
//            double amount = amounts[face.getIndex()];
            double amount = amounts[face.get3DDataValue()];
            if (face.getAxis() != Axis.Y) {
                horizontal |= flow.pipe.isConnected(face) && amount > 0;
            }

            Vec3 center = VecUtil.offset(new Vec3(0.5, 0.5, 0.5), face, 0.245 + size / 2);
            Vec3 radius = new Vec3(0.24, 0.24, 0.24);
            radius = VecUtil.replaceValue(radius, face.getAxis(), 0.005 + size / 2);

            if (face.getAxis() == Axis.Y) {
                double perc = amount / flow.capacity;
                perc = Math.sqrt(perc);
                radius = new Vec3(perc * 0.24, radius.y, perc * 0.24);
            }

//            Vec3 offset = offsets[face.getIndex()];
            Vec3 offset = offsets[face.get3DDataValue()];
            // TODO Calen: when complete SpriteFluidFrozen.class, release the comments ##0-6/7
            // TODO Calen: if force use FROZEN, the texture will flash
            if (offset == null) offset = Vec3.ZERO;
//            center = center.add(offset); // TODO Calen ##0/7
//            fluidBuffer.setTranslation(x - offset.x, y - offset.y, z - offset.z);
            poseStack.pushPose();

//            poseStack.translate(-offset.x, -offset.y, -offset.z); // TODO Calen ##1/7

            Vec3 min = center.subtract(radius);
            Vec3 max = center.add(radius);

            if (face.getAxis() == Axis.Y) {
                FluidRenderer.renderFluid(FluidSpriteType.FROZEN, forRender, 1, 1, min, max, poseStack.last(), fluidBuffer, sides);
            } else {
                FluidRenderer.renderFluid(FluidSpriteType.FROZEN, forRender, amount, flow.capacity, min, max, poseStack.last(), fluidBuffer, sides);
            }
            poseStack.popPose();
        }

        double amount = amounts[EnumPipePart.CENTER.getIndex()];

        double horizPos = 0.26;

        Vec3 offset = offsets[EnumPipePart.CENTER.getIndex()];
        if (offset == null) offset = Vec3.ZERO;
//            fluidBuffer.setTranslation(x - offset.x, y - offset.y, z - offset.z);
        poseStack.pushPose();
//        poseStack.translate(-offset.x, -offset.y, -offset.z); // TODO Calen ##2/7
        PoseStack.Pose pose = poseStack.last();

        if (horizontal | !vertical) {
            Vec3 min = new Vec3(0.26, 0.26, 0.26);
            Vec3 max = new Vec3(0.74, 0.74, 0.74);

//            min = min.add(offset); // TODO Calen ##3/7
//            max = max.add(offset); // TODO Calen ##4/7

            FluidRenderer.renderFluid(FluidSpriteType.FROZEN, forRender, amount, flow.capacity, min, max, pose, fluidBuffer, sides);
            horizPos += (max.y - min.y) * amount / flow.capacity;
        }
        poseStack.popPose();

        if (vertical && horizPos < 0.74) {
            double perc = amount / flow.capacity;
            perc = Math.sqrt(perc);
            double minXZ = 0.5 - 0.24 * perc;
            double maxXZ = 0.5 + 0.24 * perc;

            double yMin = gas ? 0.26 : horizPos;
            double yMax = gas ? 1 - horizPos : 0.74;

            Vec3 min = new Vec3(minXZ, yMin, minXZ);
            Vec3 max = new Vec3(maxXZ, yMax, maxXZ);
//            min = min.add(offset); // TODO Calen ##5/7
//            max = max.add(offset); // TODO Calen ##6/7

            FluidRenderer.renderFluid(FluidSpriteType.FROZEN, forRender, 1, 1, min, max, pose, fluidBuffer, sides);
        }

//            // gl state setup
//            RenderHelper.disableStandardItemLighting();
//            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//            GlStateManager.enableBlend();
//            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
//            GlStateManager.enableCull();

        prof.popPush("draw");
//            fluidBuffer.setTranslation(0, 0, 0);
//            tess.tessellator.draw();
//        }

//        RenderHelper.enableStandardItemLighting();

        FluidRenderer.vertex.lighti((byte) 0xF, (byte) 0xF);
        prof.pop();
    }
}
