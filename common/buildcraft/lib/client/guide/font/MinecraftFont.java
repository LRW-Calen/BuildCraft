/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.guide.font;

import buildcraft.lib.misc.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.util.Arrays;
import java.util.List;

/**
 * Implements a font that delegates to Minecraft's own {@link Font}
 */
public enum MinecraftFont implements IFontRenderer {
    INSTANCE;

    private static Font getFont() {
        return Minecraft.getInstance().font;
    }

    @Override
    public int getStringWidth(String text) {
        return getFont().width(text);
    }

    @Override
    public int getFontHeight(String text) {
        return getMaxFontHeight();
    }

    @Override
    public int getMaxFontHeight() {
        return getFont().lineHeight;
    }

    @Override
    public int drawString(PoseStack poseStack, String text, int x, int y, int colour, boolean shadow, boolean centered, float scale) {
        boolean _scale = scale != 1;
        if (_scale) {
//            GlStateManager.pushMatrix();
            poseStack.pushPose();
//            GL11.glScaled(scale, scale, 1);
            poseStack.scale(scale, scale, 1);
            x = (int) (x / scale);
            y = (int) (y / scale);
        }
        if (centered) {
            x -= getStringWidth(text) / 2;
        }

//        int v = getFont().draw(poseStack,text, x, y, colour, shadow);
//        int v = getFont().drawShadow(poseStack,text, x, y, colour, shadow);
        int v;

//        Font font = getFont();
//        // 和buildcraft.lib.client.render.font.DelegateFontRenderer里面差不多
//        ///////////////////////////////////////////////////////////////////////////////////////
        if (shadow) {
//            int shadowColor = (colour & 16579836) >> 2 | colour & -16777216;
//            int shadow_i = font.drawShadow(poseStack, text, x, y, shadowColor);
//            int foreText_i = font.draw(poseStack, text, x, y, colour);
//            v = Math.max(shadow_i, foreText_i);
            v = getFont().drawShadow(poseStack, text, x, y, colour);
        } else {
//            v = font.draw(poseStack, text, x, y, colour);
            v = getFont().draw(poseStack, text, x, y, colour);
        }
//        // 返回值 1.12.2 FontRenderer:334 文本和shadow取大
//        // i = Math.max(i, this.renderString(text, x, y, color, false));
//        // dropShadow -> 调用2次renderString 分别渲染shadow和上层文本
//        ///////////////////////////////////////////////////////////////////////////////////////
        v -= x;
//        GlStateManager.color(1f, 1f, 1f);
        RenderUtil.color(1f, 1f, 1f);
        if (_scale) {
//            GlStateManager.popMatrix();
            poseStack.popPose();
            v = (int) (v * scale);
        }
        return v;
    }

    @Override
    public List<String> wrapString(String text, int maxWidth, boolean shadow, float scale) {
        return Arrays.stream(getFont().plainSubstrByWidth(text, (int) (maxWidth / scale)).split("\n")).toList();
//        return getFont().listFormattedStringToWidth(text, (int) (maxWidth / scale));
    }
}
