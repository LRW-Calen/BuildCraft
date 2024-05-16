/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport.client.render;

import buildcraft.api.mj.MjAPI;
import buildcraft.api.transport.pipe.IPipeFlowRenderer;
import buildcraft.lib.client.model.ModelUtil;
import buildcraft.lib.client.model.ModelUtil.UvFaceData;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.misc.MathUtil;
import buildcraft.lib.misc.VecUtil;
import buildcraft.transport.BCTransportSprites;
import buildcraft.transport.pipe.flow.PipeFlowPower;
import buildcraft.transport.pipe.flow.PipeFlowPower.Section;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Point3f;

@OnlyIn(Dist.CLIENT)
public enum PipeFlowRendererPower implements IPipeFlowRenderer<PipeFlowPower> {
    INSTANCE;

    @Override
//    public void render(PipeFlowPower flow, double x, double y, double z, float partialTicks, BufferBuilder bb)
    public void render(PipeFlowPower flow, float partialTicks, MatrixStack poseStack, IVertexBuilder bb, int combinedLight, int combinedOverlay) {
        double centrePower = 0;
        double[] power = new double[6];
        for (Direction side : Direction.values()) {
            Section s = flow.getSection(side);
            int i = side.ordinal();
            power[i] = s.displayPower / (double) MjAPI.MJ;
            centrePower = Math.max(centrePower, power[i]);
        }

//        bb.setTranslation(x, y, z);

        if (centrePower > 0) {
            for (Direction side : Direction.values()) {
                if (!flow.pipe.isConnected(side)) {
                    continue;
                }
                int i = side.ordinal();
                Section s = flow.getSection(side);
                double offset = MathUtil.interp(partialTicks, s.clientDisplayFlowLast, s.clientDisplayFlow);
                renderSidePower(side, power[i], centrePower, offset, poseStack.last(), bb);
            }

            renderCentrePower(centrePower, flow.clientDisplayFlowCentre, poseStack.last(), bb);
        }

//        bb.setTranslation(0, 0, 0);
    }

    private static void renderSidePower(Direction side, double power, double centrePower, double offset, Entry pose, IVertexBuilder bb) {
        if (power < 0) {
            return;
        }
        boolean overload = false;
        double radius = 0.248 * power;
        if (radius >= 0.248) {
            // overload = true;
            radius = 0.248;
        }

        TextureAtlasSprite sprite = (overload ? BCTransportSprites.POWER_FLOW_OVERLOAD : BCTransportSprites.POWER_FLOW)
                .getSprite();

        double centreRadius = 0.252 - (0.248 * centrePower);

        Vector3d centre = VecUtil.offset(VecUtil.VEC_HALF, side, 0.25 + 0.125 - centreRadius / 2);
        Vector3d radiusV = new Vector3d(radius, radius, radius);
        radiusV = VecUtil.replaceValue(radiusV, side.getAxis(), 0.125 + centreRadius / 2);

        Point3f centreF = new Point3f((float) centre.x, (float) centre.y, (float) centre.z);
        Point3f radiusF = new Point3f((float) radiusV.x, (float) radiusV.y, (float) radiusV.z);

        UvFaceData uvs = new UvFaceData();
        for (Direction face : Direction.values()) {
            if (face == side.getOpposite()) {
                continue;
            }

            AxisAlignedBB box = new AxisAlignedBB(centre.subtract(radiusV).scale(0.5), centre.add(radiusV).scale(0.5));
//            box = box.move(VecUtil.offset(Vector3d.ZERO, side, offset * side.getAxisDirection().getOffset() / 32));
            box = box.move(VecUtil.offset(Vector3d.ZERO, side, offset * side.getAxisDirection().getStep() / 32));
            ModelUtil.mapBoxToUvs(box, face, uvs);

            MutableQuad quad = ModelUtil.createFace(face, centreF, radiusF, uvs);
            quad.texFromSprite(sprite);
            quad.lighti((byte) 15, (byte) 15);
            quad.normalf(1, 1, 1);
            quad.render(pose, bb);
        }
    }

    private static void renderCentrePower(double power, Vector3d offset, Entry pose, IVertexBuilder bb) {
        boolean overload = false;
        float radius = 0.248f * (float) power;
        if (radius > 0.248f) {
            // overload = true;
            radius = 0.248f;
        }
        TextureAtlasSprite sprite = (overload ? BCTransportSprites.POWER_FLOW_OVERLOAD : BCTransportSprites.POWER_FLOW)
                .getSprite();

        Point3f centre = new Point3f(0.5f, 0.5f, 0.5f);
        Point3f radiusP = new Point3f(radius, radius, radius);

        UvFaceData uvs = new UvFaceData();

        for (Direction face : Direction.values()) {

            AxisAlignedBB box = new AxisAlignedBB(
                    new Vector3d(0.5 - radius, 0.5 - radius, 0.5 - radius).scale(0.5), //
                    new Vector3d(0.5 + radius, 0.5 + radius, 0.5 + radius).scale(0.5)//
            );
            box = box.move(offset.scale(1 / 32.0));
            ModelUtil.mapBoxToUvs(box, face, uvs);

            MutableQuad quad = ModelUtil.createFace(face, centre, radiusP, uvs);
            quad.texFromSprite(sprite);
            quad.lighti((byte) 15, (byte) 15);
            quad.normalf(1, 1, 1);
            quad.render(pose, bb);
        }
    }
}
