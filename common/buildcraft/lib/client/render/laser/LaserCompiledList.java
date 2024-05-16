///*
// * Copyright (c) 2017 SpaceToad and the BuildCraft team
// * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
// * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
// */
//
//package buildcraft.lib.client.render.laser;
//
//import buildcraft.lib.misc.RenderUtil;
//import buildcraft.lib.misc.RenderUtil.AutoTessellator;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.*;
//import net.minecraft.util.math.vector.Matrix3f;
//import net.minecraft.util.math.vector.Matrix4f;
//import net.minecraft.client.renderer.FogRenderer;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import org.lwjgl.opengl.GL11;
//
//import javax.vecmath.Vector4f;
//
//// Calen: it seems GLList cannot be used in 1.18.2 world rendering
//@OnlyIn(Dist.CLIENT)
//public abstract class LaserCompiledList
//{
//    public abstract void render();
//
//    public abstract void delete();
//
//    public static class IBuilder implements ILaserRenderer, AutoCloseable
//    {
//        public final AutoTessellator tess;
//        private final boolean useColour;
//
//        public IBuilder(boolean useNormalColour)
//        {
//            this.useColour = useNormalColour;
//            tess = RenderUtil.getThreadLocalUnusedTessellator();
//            BufferBuilder bufferBuilder = tess.tessellator.getBuilder();
//            bufferBuilder.begin(VertexFormat.Mode.QUADS,
//                    useNormalColour ? LaserRenderer_BC8.FORMAT_ALL : LaserRenderer_BC8.FORMAT_LESS);
//        }
//
//        @Override
//        public void vertex(
////                MatrixStack poseStack,
////                IVertexBuilder bufferBuilder,
//                javax.vecmath.Matrix4f matrix,
//                Vector4f normal,
//                double x, double y, double z,
//                double u, double v,
//                int lmap,
//                int overlay,
//                float nx,
//                float ny,
//                float nz,
//                float diffuse
//        )
//        {
//            float[] poseValues = new float[]{
//                    matrix.m00, matrix.m01, matrix.m02, matrix.m03,
//                    matrix.m10, matrix.m11, matrix.m12, matrix.m13,
//                    matrix.m20, matrix.m21, matrix.m22, matrix.m23,
//                    matrix.m30, matrix.m31, matrix.m32, matrix.m33,
//            };
//            Matrix4f mojangPose = new Matrix4f(poseValues);
//            float[] normalValues = new float[]{
//                    normal.x, normal.y, normal.z
//            };
//            Matrix3f mojangNormal = Matrix3f.createScaleMatrix(normal.x, normal.y, normal.z);
//
////            BufferBuilder bufferBuilder = tess.tessellator.getBuffer();
//            BufferBuilder bufferBuilder = tess.tessellator.getBuilder();
////            bufferBuilder.pos(x, y, z);
////            bufferBuilder.vertex(pose.pose(), (float) x, (float) y, (float) z);
//            MatrixStack stack = RenderSystem.getModelViewStack(); // Calen test
////            bufferBuilder.vertex((float) x, (float) y, (float) z);
//            bufferBuilder.vertex(mojangPose, (float) x, (float) y, (float) z);
//            bufferBuilder.uv((float) u, (float) v);
//            bufferBuilder.overlayCoords(overlay);
//            bufferBuilder.uv2((lmap >> 16) & 0xFFFF, lmap & 0xFFFF);
//            if (useColour)
//            {
//                bufferBuilder.color(diffuse, diffuse, diffuse, 1.0f);
//            }
////            bufferBuilder.normal(pose.normal(), nx, ny, nz);
////            bufferBuilder.normal(nx, ny, nz);
//            bufferBuilder.normal(mojangNormal, nx, ny, nz);
//            bufferBuilder.endVertex();
//        }
//
//        public LaserCompiledList build()
//        {
////            if (OpenGlHelper.useVbo())
//            if (false)
//            {
//                BufferBuilder bufferBuilder = tess.tessellator.getBuilder(); // Calen: got the same builder as that in vertex()
////                Tesselator tesselator = Tesselator.getInstance();
////                BufferBuilder bufferBuilder =tesselator.getBuilder();
////                VertexBuffer vertexBuffer = new VertexBuffer(bufferBuilder.getVertexFormat());
//                VertexBuffer vertexBuffer = new VertexBuffer();
////                bufferBuilder.getVertexFormat().setupBufferState();
////                bufferBuilder.finishDrawing();
//                bufferBuilder.end();
////                bufferBuilder.reset();
////                bufferBuilder.clear();
//                vertexBuffer.upload(bufferBuilder);
////                vertexBuffer.draw();
//                return new Vbo(useColour, vertexBuffer, bufferBuilder);
//            }
//            else
//            {
//
//                RenderSystem.disableDepthTest();
//                RenderSystem.disableTexture();
//                RenderSystem.enableBlend();
//                RenderSystem.defaultBlendFunc();
//                RenderSystem.setShader(GameRenderer::getPositionColorShader);
////
//                tess.tessellator.end();
////                BufferUploader.end(tess.tessellator.getBuilder());
//                RenderSystem.disableBlend();
//                RenderSystem.enableTexture();
//                RenderSystem.enableDepthTest();
//
//
//
//
////                RenderSystem.assertOnRenderThread();
////                BufferUploader.reset();
//////                int glList = GLAllocation.generateDisplayLists(1);
//////                GL11.glNewList(glList, GL11.GL_COMPILE);
////                tess.tessellator.end();
//
////                tess.tessellator.getBuilder().end();
//////                GL11.glEndList();
//////                return new GlList(glList);
//                return new GlList(tess.tessellator);
//            }
//        }
//
//        @Override
//        public void close()
//        {
//            tess.close();
//        }
//    }
//
//    private static class GlList extends LaserCompiledList
//    {
//        //        private final int glListId;
//        private int glListId;
//
//        //        private GlList(int glListId)
//
//        private final Tesselator tesselator;
//        private GlList(Tesselator tesselator)
//        {
////            this.glListId = glListId;
////            RenderSystem.glGenVertexArrays((id) -> this.glListId = id);
//            this.tesselator = tesselator;
//        }
//
//        @Override
//        public void render()
//        {
////            GL11.glCallList(glListId);
////            RenderSystem.glBindVertexArray(() -> glListId);
////            RenderSystem.disableDepthTest();
////            RenderSystem.disableTexture();
////            RenderSystem.enableBlend();
////            RenderSystem.defaultBlendFunc();
////            RenderSystem.setShader(GameRenderer::getPositionColorShader);
////
////            tesselator.end();
////            BufferUploader.end(tesselator.getBuilder());
////            RenderSystem.disableBlend();
////            RenderSystem.enableTexture();
////            RenderSystem.enableDepthTest();
//        }
//
//        @Override
//        public void delete()
//        {
////            GL11.glDeleteLists(glListId, 1);
////            RenderSystem.glDeleteVertexArrays(glListId);
//        }
//    }
//
//    private static class Vbo extends LaserCompiledList
//    {
//        //        private int glListId;
//        private final boolean useColour;
//        private final VertexBuffer vertexBuffer;
//        private final BufferBuilder bufferBuilder;
//
//        private Vbo(boolean useColour, VertexBuffer vertexBuffer, BufferBuilder bufferBuilder)
//        {
//            this.useColour = useColour;
//            this.vertexBuffer = vertexBuffer;
//            this.bufferBuilder = bufferBuilder;
////            RenderSystem.glGenVertexArrays((id) -> this.glListId = id);
//        }
//
//        @Override
//        public void render()
//        {
//            vertexBuffer.bind();
//            RenderSystem.disableTexture();
//            FogRenderer.setupNoFog();
////            RenderSystem.glBindVertexArray(() -> glListId);
////            RenderSystem.assertOnRenderThread();
////            RenderSystem.assertOnGameThreadOrInit();
////            final int stride = useColour ? 28 : 24;
//
////            vertexBuffer.bindBuffer();
////            vertexBuffer.bind();
////            GlStateManager.glEnableClientState(GL11.GL_VERTEX_ARRAY);
////            GlStateManager.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);
////
////            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            GlStateManager.glTexCoordPointer(2, GL11.GL_FLOAT, stride, 12);
////
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
////            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            GlStateManager.glTexCoordPointer(2, GL11.GL_SHORT, stride, 20);
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
////
////            if (useColour) {
////                GlStateManager.glEnableClientState(GL11.GL_COLOR_ARRAY);
////                GlStateManager.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, 24);
////            }
//            if (useColour)
//            {
//                RenderSystem.setShaderColor(1, 1, 1, 1);
//            }
//
////            vertexBuffer.drawArrays(GL11.GL_QUADS);
////            vertexBuffer.draw();
////            vertexBuffer.drawWithShader(p_202424_.last().pose(), p_202425_, GameRenderer.getPositionShader());
//            vertexBuffer.drawWithShader(new Matrix4f(), new Matrix4f(), GameRenderer.getPositionShader());
////            BufferUploader.end(bufferBuilder);
////            vertexBuffer.unbindBuffer();
////            vertexBuffer.close();
//
////            GlStateManager.glDisableClientState(GL11.GL_VERTEX_ARRAY);
////            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
////            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
////
////            if (useColour) {
////                GlStateManager.glDisableClientState(GL11.GL_COLOR_ARRAY);
////                GlStateManager.color(1, 1, 1, 1);
////            }
//
//
////            RenderSystem.assertOnRenderThread();
//////            final int stride = useColour ? 28 : 24;
//////
//////            vertexBuffer.bind();
//////            GlStateManager.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//////            GlStateManager.glVertexPointer(3, GL11.GL_FLOAT, stride, 0);
//////
//////            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            GlStateManager.glTexCoordPointer(2, GL11.GL_FLOAT, stride, 12);
//////
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
////            GlStateManager.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            GlStateManager._getTexImage(2, GL11.GL_SHORT, stride, 20);
////            RenderSystem.texParameter(OpenGlHelper.defaultTexUnit);
//////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
////            RenderSystem.activeTexture(OpenGlHelper.defaultTexUnit);
//////
//////            if (useColour)
//////            {
//////                GlStateManager.glEnableClientState(GL11.GL_COLOR_ARRAY);
//////                GlStateManager.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, stride, 24);
//////            }
//////
////////            vertexBuffer.drawArrays(GL11.GL_QUADS);
////////            vertexBuffer.unbindBuffer();
//////            vertexBuffer.drawArrays(GL11.GL_QUADS);
////            vertexBuffer.close();
//////
//////            GlStateManager.glDisableClientState(GL11.GL_VERTEX_ARRAY);
//////            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
//////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
//////            RenderSystem.activeTexture(OpenGlHelper.lightmapTexUnit);
////            GlStateManager.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
////            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
//////            RenderSystem.activeTexture(OpenGlHelper.defaultTexUnit);
//////
////            if (useColour)
////            {
//////                GlStateManager.glDisableClientState(GL11.GL_COLOR_ARRAY);
//////                GlStateManager.color(1, 1, 1, 1);
////                RenderSystem.colorMask(true, true, true, true);
////            }
//        }
//
//        @Override
//        public void delete()
//        {
////            vertexBuffer.deleteGlBuffers();
////            RenderSystem.glDeleteVertexArrays(glListId);
//
//            vertexBuffer.close();
//        }
//    }
//}
