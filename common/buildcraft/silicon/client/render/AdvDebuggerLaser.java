/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.silicon.client.render;

import buildcraft.api.properties.BuildCraftProperties;
import buildcraft.lib.client.render.DetachedRenderer;
import buildcraft.lib.debug.DebugRenderHelper;
import buildcraft.lib.misc.VolumeUtil;
import buildcraft.silicon.BCSiliconBlocks;
import buildcraft.silicon.tile.TileLaser;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvDebuggerLaser implements DetachedRenderer.IDetachedRenderer {
    private static final int COLOUR_VISIBLE = 0xFF_99_FF_99;
    private static final int COLOUR_NOT_VISIBLE = 0xFF_11_11_99;

    private final BlockPos pos;
    private final Direction face;

    public AdvDebuggerLaser(TileLaser tile) {
        pos = tile.getBlockPos();
        BlockState state = tile.getLevel().getBlockState(pos);
        face = state.getBlock() == BCSiliconBlocks.laser.get()
                ? state.getValue(BuildCraftProperties.BLOCK_FACING_6)
                : null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(PlayerEntity player, float partialTicks, MatrixStack poseStack) {
        if (pos == null || face == null) {
            return;
        }
//        BufferBuilder bb = Tessellator.getInstance().getBuffer();
//        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        IVertexBuilder bb = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(Atlases.translucentCullBlockSheet());
        VolumeUtil.iterateCone(player.level, pos, face, 6, true, (world, start, p, visible) ->
        {
            int colour = visible ? COLOUR_VISIBLE : COLOUR_NOT_VISIBLE;
//            DebugRenderHelper.renderSmallCuboid(poseStack, bb, p, colour);
            DebugRenderHelper.renderSmallCuboid(poseStack, bb, p, colour);
        });
//        Tessellator.getInstance().draw();
    }
}
