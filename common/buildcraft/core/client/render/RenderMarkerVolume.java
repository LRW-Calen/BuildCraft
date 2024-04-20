/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package buildcraft.core.client.render;

import buildcraft.core.BCCoreConfig;
import buildcraft.core.client.BuildCraftLaserManager;
import buildcraft.core.marker.VolumeConnection;
import buildcraft.core.tile.TileMarkerVolume;
import buildcraft.lib.client.render.laser.LaserData_BC8;
import buildcraft.lib.client.render.laser.LaserData_BC8.LaserType;
import buildcraft.lib.client.render.laser.LaserRenderer_BC8;
import buildcraft.lib.misc.VecUtil;
import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class RenderMarkerVolume implements BlockEntityRenderer<TileMarkerVolume> {
    private static final double SCALE = 1 / 16.2; // smaller than normal lasers

//    public static final RenderMarkerVolume<TileMarkerVolume> INSTANCE = new RenderMarkerVolume(null);

    private static final LaserType LASER_TYPE = BuildCraftLaserManager.MARKER_VOLUME_SIGNAL;
    private static final Vec3 VEC_HALF = new Vec3(0.5, 0.5, 0.5);

    public RenderMarkerVolume(BlockEntityRendererProvider.Context context) {
    }

    @Override
//    public boolean isGlobalRenderer(TileMarkerVolume te)
    public boolean shouldRenderOffScreen(TileMarkerVolume te) {
        return true;
    }

    // Calen: default 64
    @Override
    public int getViewDistance() {
        // Calen: as beacon and endGateway
        return BCCoreConfig.markerMaxDistance * 2;
    }

    @Override
//    public void render(TileMarkerVolume marker, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage, float alpha)
    public void render(TileMarkerVolume marker, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        // Calen: in 1.12.2 this never called
        // rendered at VolumeConnection#renderInWorld
        if (marker == null || !marker.isShowingSignals()) {
            return;
        }

        Minecraft.getInstance().getProfiler().push("bc");
        Minecraft.getInstance().getProfiler().push("marker");
        Minecraft.getInstance().getProfiler().push("volume");

//        DetachedRenderer.fromWorldOriginPre(Minecraft.getInstance().player, partialTicks);// Calen: not need
//        RenderHelper.disableStandardItemLighting();
//        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
//        RenderSystem.disableBlend(); // Calen test

        // solidBlockSheet or translucent seems not different here?
        VertexConsumer buffer = bufferSource.getBuffer(Sheets.solidBlockSheet()); // Calen
//        VertexConsumer buffer = bufferSource.getBuffer(RenderType.translucent()); // Calen

        VolumeConnection volume = marker.getCurrentConnection();
        Set<Axis> taken = volume == null ? ImmutableSet.of() : volume.getConnectedAxis();

        BlockPos from = marker.getBlockPos(); // Calen
//        Vec3 start = VecUtil.add(VEC_HALF, marker.getBlockPos());
        Vec3 start = VecUtil.add(VEC_HALF, from);
        poseStack.pushPose();
        poseStack.translate(-from.getX(), -from.getY(), -from.getZ());
        for (Direction face : Direction.values()) {
            if (taken.contains(face.getAxis())) {
                continue;
            }
            Vec3 end = VecUtil.offset(start, face, BCCoreConfig.markerMaxDistance);
            renderLaser(start, end, face.getAxis(), poseStack, buffer);
        }
        poseStack.popPose();

//        RenderHelper.enableStandardItemLighting();
//        DetachedRenderer.fromWorldOriginPost();

        Minecraft.getInstance().getProfiler().pop();
        Minecraft.getInstance().getProfiler().pop();
        Minecraft.getInstance().getProfiler().pop();
    }

    private static void renderLaser(Vec3 min, Vec3 max, Axis axis, PoseStack poseStack, VertexConsumer buffer) {
        Direction faceForMin = VecUtil.getFacing(axis, true);
        Direction faceForMax = VecUtil.getFacing(axis, false);
        Vec3 one = offset(min, faceForMin);
        Vec3 two = offset(max, faceForMax);
        LaserData_BC8 data = new LaserData_BC8(LASER_TYPE, one, two, SCALE);
//        LaserRenderer_BC8.renderLaserStatic(data);
        LaserRenderer_BC8.renderLaserDynamic(data, poseStack.last(), buffer);
    }

    private static Vec3 offset(Vec3 vec, Direction face) {
        double by = 1 / 16.0;
        if (face == Direction.DOWN) {
            return vec.add(0, -by, 0);
        } else if (face == Direction.UP) {
            return vec.add(0, by, 0);
        } else if (face == Direction.EAST) {
            return vec.add(by, 0, 0);
        } else if (face == Direction.WEST) {
            return vec.add(-by, 0, 0);
        } else if (face == Direction.SOUTH) {
            return vec.add(0, 0, by);
        } else {// North
            return vec.add(0, 0, -by);
        }
    }
}
