/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.core.client.render;

import buildcraft.core.client.BuildCraftLaserManager;
import buildcraft.core.marker.volume.Addon;
import buildcraft.core.marker.volume.ClientVolumeBoxes;
import buildcraft.core.marker.volume.IFastAddonRenderer;
import buildcraft.core.marker.volume.Lock;
import buildcraft.lib.client.render.DetachedRenderer;
import buildcraft.lib.client.render.laser.LaserBoxRenderer;
import buildcraft.lib.client.render.laser.LaserData_BC8.LaserType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public enum RenderVolumeBoxes implements DetachedRenderer.IDetachedRenderer
{
    INSTANCE;

    @SuppressWarnings("unchecked")
    @Override
    public void render(Player player, float partialTicks, PoseStack poseStack)
    {
////        GlStateManager.enableBlend();
//        RenderSystem.enableBlend();

////        BufferBuilder bb = Tessellator.getInstance().getBuffer();
//        BufferBuilder bb = Tesselator.getInstance().getBuilder();
////        bb.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        VertexConsumer bb = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(Sheets.solidBlockSheet());

        ClientVolumeBoxes.INSTANCE.volumeBoxes.forEach(volumeBox ->
        {
            LaserType type;
            if (volumeBox.isEditingBy(player))
            {
                type = BuildCraftLaserManager.MARKER_VOLUME_SIGNAL;
            }
            else
            {
                type = volumeBox.getLockTargetsStream()
                        .filter(Lock.Target.TargetUsedByMachine.class::isInstance)
                        .map(Lock.Target.TargetUsedByMachine.class::cast)
                        .map(target -> target.type)
                        .map(Lock.Target.TargetUsedByMachine.EnumType::getLaserType)
                        .findFirst()
                        .orElse(BuildCraftLaserManager.MARKER_VOLUME_CONNECTED);
            }
            LaserBoxRenderer.renderLaserBoxDynamic(volumeBox.box, type, poseStack.last(), bb, false);

            volumeBox.addons.values().forEach(addon ->
                    ((IFastAddonRenderer<Addon>) addon.getRenderer()).renderAddonFast(addon, player, poseStack.last(), partialTicks, bb)
            );
        });

////        Tessellator.getInstance().draw();
//        Tesselator.getInstance().end();

////        GlStateManager.disableBlend();
//        RenderSystem.disableBlend();
    }
}
