/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.render.laser;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TIntArrayList;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

@OnlyIn(Dist.CLIENT)
public class LaserCompiledBuffer
{
    private static final int DOUBLE_STRIDE = 5;
    private static final int INT_STRIDE = 2;
    private final int vertices;
    private final double[] da;
    private final int[] ia;

    public LaserCompiledBuffer(int vertices, double[] da, int[] ia)
    {
        this.vertices = vertices;
        this.da = da;
        this.ia = ia;
    }

    /**
     * Assumes the buffer uses {@link DefaultVertexFormat#BLOCK}
     */
    public void render(PoseStack.Pose pose, VertexConsumer buffer)
    {
        for (int i = 0; i < vertices; i++)
        {
            // POSITION_3F
//            buffer.pos(da[DOUBLE_STRIDE * i + 0], da[DOUBLE_STRIDE * i + 1], da[DOUBLE_STRIDE * i + 2]);
            buffer.vertex(pose.pose(), (float) da[DOUBLE_STRIDE * i + 0], (float) da[DOUBLE_STRIDE * i + 1], (float) da[DOUBLE_STRIDE * i + 2]);

            // COLOR_4UB
            int c = ia[INT_STRIDE * i + 0];
            buffer.color(c & 0xFF, (c >> 8) & 0xFF, (c >> 16) & 0xFF, (c >> 24) & 0xFF);

            // TEX_2F
            buffer.uv((float) da[DOUBLE_STRIDE * i + 3], (float) da[DOUBLE_STRIDE * i + 4]);

            // Calen Overlay
            buffer.overlayCoords(OverlayTexture.NO_OVERLAY);

            // TEX_2S
            int lmap = ia[INT_STRIDE * i + 1];
//            buffer.lightmap((lmap >> 16) & 0xFFFF, lmap & 0xFFFF);
//            buffer.uv2(light_1_18_2);
//            buffer.uv2((lmap >> 16) & 0xFFFF, lmap & 0xFFFF); // Calen: this light level too high -> wrong
            buffer.uv2(lmap); // Calen: this right
//            buffer.normal(pose.normal(), normal_x, normal_y, normal_z);
//            buffer.normal(pose.normal(), 0, 0, 0); // Calen test
            buffer.normal(pose.normal(), 1, 1, 1); // Calen test

            buffer.endVertex();
        }
    }

    public static class Builder implements ILaserRenderer
    {
        private final boolean useNormalColour;
        private final TDoubleArrayList doubleData = new TDoubleArrayList();
        private final TIntArrayList intData = new TIntArrayList();
        private int vertices = 0;

        public Builder(boolean useNormalColour)
        {
            this.useNormalColour = useNormalColour;
        }

        @Override
        public void vertex(
                Matrix4f matrix, Vector4f normal,
                double x, double y, double z,
                double u, double v,
                int lmap, int overlay,
                float nx, float ny, float nz,
                float diffuse
        )
        {
            // POSITION_3F
            doubleData.add(x);
            doubleData.add(y);
            doubleData.add(z);

            // COLOR_4UB
            if (useNormalColour)
            {
                int c = (int) (diffuse * 0xFF);
                intData.add(c | c << 8 | c << 16 | 0xFF << 24);
            }
            else
            {
                intData.add(0xFF_FF_FF_FF);
            }

            // TEX_2F
            doubleData.add(u);
            doubleData.add(v);

            // TEX_2S
            intData.add(lmap);

            vertices++;
        }

        public LaserCompiledBuffer build()
        {
            return new LaserCompiledBuffer(vertices, doubleData.toArray(), intData.toArray());
        }
    }
}
