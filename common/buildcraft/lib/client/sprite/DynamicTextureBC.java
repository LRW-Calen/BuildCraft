/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.sprite;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// TODO Calen: ZonePlanner TESR Texture
@OnlyIn(Dist.CLIENT)
public class DynamicTextureBC {
    public final int width, height;
    private final int[] colorMap;
    private final int widthPow2, heightPow2;

    private DynamicTexture dynamicTexture;

    // Calen test
//    private final ResourceLocation lightTextureLocation;

    public DynamicTextureBC(int iWidth, int iHeight) {
        width = iWidth;
        height = iHeight;
//        widthPow2 = MathHelper.smallestEncompassingPowerOfTwo(iWidth);
        widthPow2 = Mth.smallestEncompassingPowerOfTwo(iWidth);
//        heightPow2 = MathHelper.smallestEncompassingPowerOfTwo(iHeight);
        heightPow2 = Mth.smallestEncompassingPowerOfTwo(iHeight);
//        dynamicTexture = new DynamicTexture(widthPow2, heightPow2);
        dynamicTexture = new DynamicTexture(widthPow2, heightPow2, false);
//        lightTextureLocation = Minecraft.getInstance().getTextureManager().register("bc_dynamic_" + dynamicTexture.getId(), dynamicTexture);
        colorMap = dynamicTexture.getPixels().makePixelArray();
    }

    public void setColord(int x, int y, double r, double g, double b, double a) {
        int a2 = (int) (a * 255.0F);
        int r2 = (int) (r * 255.0F);
        int g2 = (int) (g * 255.0F);
        int b2 = (int) (b * 255.0F);
        setColor(x, y, a2 << 24 | r2 << 16 | g2 << 8 | b2);
    }

    public void setColori(int x, int y, int r, int g, int b, int a) {
        setColor(x, y, (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255));
    }

    public void setColor(int x, int y, int color, float alpha) {
        int a = (int) (alpha * 255.0F);

        setColor(x, y, a << 24 | (color & 0xFF_FF_FF));
    }

    public void setColor(int x, int y, int color) {
        colorMap[x + y * widthPow2] = color;
    }

    public void updateTexture() {
//        TextureUtil.prepareImage(dynamicTexture.getId(), dynamicTexture.getPixels().getWidth(), dynamicTexture.getPixels().getHeight());
        dynamicTexture.upload();
    }

    public void bindGlTexture() {
//        GlStateManager.bindTexture(dynamicTexture.getId());
        RenderSystem.setShaderTexture(0, dynamicTexture.getId());
//        RenderSystem.setShaderTexture(0, lightTextureLocation);
//        Minecraft.getInstance().getTextureManager().bindForSetup(this.lightTextureLocation);
    }

    public void deleteGlTexture() {
        dynamicTexture.releaseId();
    }

    public void draw(int screenX, int screenY, float zLevel) {
        draw(screenX, screenY, zLevel, 0, 0, width, height);
    }

    public float getMaxU() {
        return width / (float) widthPow2;
    }

    public float getMaxV() {
        return height / (float) heightPow2;
    }

    public void draw(int screenX, int screenY, float zLevel, int clipX, int clipY, int clipWidth, int clipHeight) {
        updateTexture();

        float f = 1F / widthPow2;
        float f1 = 1F / heightPow2;
//        Tessellator tessellator = Tessellator.getInstance();
        Tesselator tessellator = Tesselator.getInstance();
//        BufferBuilder bb = tessellator.getBuffer();
        BufferBuilder bb = tessellator.getBuilder();
        bb.begin(VertexFormat.Mode.QUADS, bb.getVertexFormat());
        vertexUV(bb, screenX + 0, screenY + clipHeight, zLevel, (clipX + 0) * f, (clipY + clipHeight) * f1);
        vertexUV(bb, screenX + clipWidth, screenY + clipHeight, zLevel, (clipX + clipWidth) * f, (clipY + clipHeight) * f1);
        vertexUV(bb, screenX + clipWidth, screenY + 0, zLevel, (clipX + clipWidth) * f, (clipY + 0) * f1);
        vertexUV(bb, screenX + 0, screenY + 0, zLevel, (clipX + 0) * f, (clipY + 0) * f1);
//        tessellator.draw();
        tessellator.end();
    }

    private static void vertexUV(BufferBuilder bb, double x, double y, double z, double u, double v) {
//        bb.pos(x, y, z);
        bb.vertex(x, y, z);
//        bb.tex(u, v);
        bb.uv((float) u, (float) v);
        bb.endVertex();
    }
}
