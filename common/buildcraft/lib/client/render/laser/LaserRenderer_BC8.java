/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.client.render.laser;

import buildcraft.lib.client.render.laser.LaserData_BC8.LaserType;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@OnlyIn(Dist.CLIENT)
public class LaserRenderer_BC8 {
    private static final Map<LaserData_BC8.LaserType, CompiledLaserType> COMPILED_LASER_TYPES = new HashMap<>();
    // Calen: it seems GLList cannot be used in 1.18.2 world rendering
//    private static final LoadingCache<LaserData_BC8, LaserCompiledList> COMPILED_STATIC_LASERS;
    private static final LoadingCache<LaserData_BC8, LaserCompiledBuffer> COMPILED_DYNAMIC_LASERS;

    // Calen: not used, because it seems GlList is not available in 1.18.2
//    public static final VertexFormat FORMAT_LESS, FORMAT_ALL;

    static {
        // Calen: it seems GLList cannot be used in 1.18.2 world rendering
//        COMPILED_STATIC_LASERS = CacheBuilder.newBuilder()//
//                .expireAfterWrite(5, TimeUnit.SECONDS)//
//                .removalListener(LaserRenderer_BC8::removeCompiledLaser)//
//                .build(CacheLoader.from(LaserRenderer_BC8::makeStaticLaser));

        COMPILED_DYNAMIC_LASERS = CacheBuilder.newBuilder()//
                .expireAfterWrite(5, TimeUnit.SECONDS)//
                .build(CacheLoader.from(LaserRenderer_BC8::makeDynamicLaser));

        // Calen: not used. Seems GlList is not available in 1.18.2
////        FORMAT_LESS = new VertexFormat();
////        FORMAT_LESS.addElement(DefaultVertexFormats.POSITION_3F);
////        FORMAT_LESS.addElement(DefaultVertexFormats.TEX_2F);
////        FORMAT_LESS.addElement(DefaultVertexFormats.TEX_2S);
//        FORMAT_LESS = new VertexFormat(ImmutableMap.of(
//                "POSITION_3F",
//                DefaultVertexFormat.ELEMENT_POSITION,
//                "TEX_2F",
//                DefaultVertexFormat.ELEMENT_UV0,
//                "TEX_2S",
//                DefaultVertexFormat.ELEMENT_UV1
//        ));

////        FORMAT_ALL = new VertexFormat();
////        FORMAT_ALL.addElement(DefaultVertexFormats.POSITION_3F);
////        FORMAT_ALL.addElement(DefaultVertexFormats.TEX_2F);
////        FORMAT_ALL.addElement(DefaultVertexFormats.TEX_2S);
////        FORMAT_ALL.addElement(DefaultVertexFormats.COLOR_4UB);
//        FORMAT_ALL = new VertexFormat(ImmutableMap.of(
//                "POSITION_3F",
//                DefaultVertexFormat.ELEMENT_POSITION,
//                "TEX_2F",
//                DefaultVertexFormat.ELEMENT_UV0,
//                "TEX_2S",
//                DefaultVertexFormat.ELEMENT_UV1,
//                "COLOR_4UB",
//                DefaultVertexFormat.ELEMENT_COLOR
//        ));
    }

    public static void clearModels() {
        COMPILED_LASER_TYPES.clear();
    }

    private static CompiledLaserType compileType(LaserType laserType) {
        if (!COMPILED_LASER_TYPES.containsKey(laserType)) {
            COMPILED_LASER_TYPES.put(laserType, new CompiledLaserType(laserType));
        }
        return COMPILED_LASER_TYPES.get(laserType);
    }

    // Calen: it seems GLList cannot be used in 1.18.2 world rendering
//    private static LaserCompiledList makeStaticLaser(LaserData_BC8 data) {
//        try (LaserCompiledList.IBuilder renderer = new LaserCompiledList.IBuilder(data.enableDiffuse)) {
//            makeLaser(data, renderer);
//            return renderer.build();
//        }
//    }

    private static LaserCompiledBuffer makeDynamicLaser(LaserData_BC8 data) {
        LaserCompiledBuffer.Builder renderer = new LaserCompiledBuffer.Builder(data.enableDiffuse);
        makeLaser(data, renderer);
        return renderer.build();
    }

    private static void makeLaser(LaserData_BC8 data, ILaserRenderer renderer) {
        LaserContext ctx = new LaserContext(renderer, data, data.enableDiffuse, data.doubleFace);
        CompiledLaserType type = compileType(data.laserType);
        type.bakeFor(ctx);
    }

    // Calen: it seems GLList cannot be used in 1.18.2 world rendering
//    private static void removeCompiledLaser(RemovalNotification<LaserData_BC8, LaserCompiledList> notification) {
//        LaserCompiledList comp = notification.getValue();
//        if (comp != null) {
//            comp.delete();
//        }
//    }

    public static int computeLightmap(double x, double y, double z, int minBlockLight) {
        World world = Minecraft.getInstance().level;
        if (world == null) return 0;
        int blockLight =
                minBlockLight >= 15 ? 15 : Math.max(minBlockLight, getLightFor(world, LightType.BLOCK, x, y, z));
        int skyLight = getLightFor(world, LightType.SKY, x, y, z);
        return skyLight << 20 | blockLight << 4;
    }

    private static int getLightFor(World world, LightType type, double x, double y, double z) {
        int max = 0;
        int count = 0;
        int sum = 0;

        boolean ao = Minecraft.useAmbientOcclusion();

        double xn = (x % 1 + 1) % 1;
        double yn = (y % 1 + 1) % 1;
        double zn = (z % 1 + 1) % 1;

        final double lowerBound = 0.3;
        final double upperBound = 1 - lowerBound;

        int xl = ao ? (xn < lowerBound ? -1 : 0) : -1;
        int yl = ao ? (yn < lowerBound ? -1 : 0) : -1;
        int zl = ao ? (zn < lowerBound ? -1 : 0) : -1;
        int xu = ao ? (xn > upperBound ? 1 : 0) : 1;
        int yu = ao ? (yn > upperBound ? 1 : 0) : 1;
        int zu = ao ? (zn > upperBound ? 1 : 0) : 1;

        for (int xp = xl; xp <= xu; xp++) {
            for (int yp = yl; yp <= yu; yp++) {
                for (int zp = zl; zp <= zu; zp++) {
                    int light = world.getBrightness(type, new BlockPos(x + xp, y + yp, z + zp));
                    if (light > 0) {
                        sum += light;
                        count++;
                    }
                    max = Math.max(max, light);
                }
            }
        }

        if (ao) {
            return count == 0 ? 0 : sum / count;
        } else {
            return max;
        }
    }

    // Calen: it seems GLList cannot be used in 1.18.2 world rendering
//    public static void renderLaserStatic(LaserData_BC8 data) {
//        IProfiler profiler = Minecraft.getInstance().getProfiler();
//        profiler.push("compute");
//        LaserCompiledList compiled = COMPILED_STATIC_LASERS.getUnchecked(data);
//        profiler.popPush("render");
//        SpriteUtil.bindBlockTextureMap();
//        compiled.render();
//        profiler.pop();
//    }

    /** Assumes the buffer uses {@link DefaultVertexFormats#BLOCK} */
    public static void renderLaserDynamic(LaserData_BC8 data, Entry pose, IVertexBuilder buffer) {
        IProfiler profiler = Minecraft.getInstance().getProfiler();
        profiler.push("compute");
        LaserCompiledBuffer compiled = COMPILED_DYNAMIC_LASERS.getUnchecked(data);
        profiler.popPush("render");
        compiled.render(pose, buffer);
        profiler.pop();
    }
}
