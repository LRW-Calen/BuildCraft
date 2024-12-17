/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.factory.client.render;

import buildcraft.factory.BCFactoryBlocks;
import buildcraft.factory.BCFactoryModels;
import buildcraft.factory.tile.TileDistiller_BC8;
import buildcraft.lib.block.BlockBCBase_Neptune;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.client.render.fluid.FluidRenderer;
import buildcraft.lib.client.render.fluid.FluidRenderer.TankSize;
import buildcraft.lib.client.render.fluid.FluidSpriteType;
import buildcraft.lib.fluid.FluidSmoother;
import buildcraft.lib.fluid.FluidSmoother.FluidStackInterp;
import buildcraft.lib.misc.VecUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderDistiller implements BlockEntityRenderer<TileDistiller_BC8> {
    private static final Map<Direction, TankRenderSizes> TANK_SIZES = new EnumMap<>(Direction.class);

    static {
        Direction face = Direction.WEST;
        TankSize tankIn = new TankSize(0, 0, 4, 8, 16, 12).shrink(1 / 64.0);
        TankSize tankGasOut = new TankSize(8, 8, 0, 16, 16, 16).shrink(1 / 64.0);
        TankSize tankLiquidOut = new TankSize(8, 0, 0, 16, 8, 16).shrink(1 / 64.0);
        TankRenderSizes sizes = new TankRenderSizes(tankIn, tankGasOut, tankLiquidOut);
        for (int i = 0; i < 4; i++) {
            TANK_SIZES.put(face, sizes);
            face = face.getClockWise();
            sizes = sizes.rotateY();
        }
    }

    public RenderDistiller(BlockEntityRendererProvider.Context context) {
    }

    @Override
//    public void render(TileDistiller_BC8 tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    public void render(TileDistiller_BC8 tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

        BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
        if (state.getBlock() != BCFactoryBlocks.distiller.get()) {
            return;
        }

        ProfilerFiller profiler = Minecraft.getInstance().getProfiler();
        profiler.push("bc");
        profiler.push("distiller");

        // 1.18.2: provided
//        int combinedLight = tile.getWorld().getCombinedLight(tile.getBlockPos(), 0);
        Direction face = state.getValue(BlockBCBase_Neptune.PROP_FACING);
        TankRenderSizes sizes = TANK_SIZES.get(face);

//        // gl state setup
//        RenderHelper.disableStandardItemLighting();
//        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

        // buffer setup
//        try (AutoTessellator tess = RenderUtil.getThreadLocalUnusedTessellator()) {
//            BufferBuilder bb = tess.tessellator.getBuffer();
//            bb.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
//            bb.setTranslation(x, y, z);

        profiler.push("model");
        profiler.push("compute");
        if (tile.clientModelData.hasNoNodes()) {
            tile.clientModelData.setNodes(BCFactoryModels.DISTILLER.createTickableNodes());
        }
        tile.setClientModelVariables(partialTicks);
        tile.clientModelData.refresh();
        MutableQuad[] quads = BCFactoryModels.DISTILLER.getCutoutQuads();
        profiler.popPush("render");

        MutableQuad copy = new MutableQuad(0, null);
        int lightc = combinedLight;
//        int light_block = (lightc >> 4) & 15;
//        int light_sky = (lightc >> 20) & 15;
        byte light_block = (byte) ((lightc >> 4) & 15);
        byte light_sky = (byte) ((lightc >> 20) & 15);
        VertexConsumer bb = bufferSource.getBuffer(RenderType.solid());
        for (MutableQuad q : quads) {
            copy.copyFrom(q);
            copy.maxLighti(light_block, light_sky);
            copy.overlay(combinedOverlay);
            copy.multShade();
            copy.render(poseStack.last(), bb);
        }

        profiler.pop();
        profiler.popPush("fluid");

        renderTank(poseStack.last(), sizes.tankIn, tile.smoothedTankIn, combinedLight, combinedOverlay, partialTicks, bb);
        renderTank(poseStack.last(), sizes.tankOutGas, tile.smoothedTankGasOut, combinedLight, combinedOverlay, partialTicks, bb);
        renderTank(poseStack.last(), sizes.tankOutLiquid, tile.smoothedTankLiquidOut, combinedLight, combinedOverlay, partialTicks, bb);

        // buffer finish
//        bb.setTranslation(0, 0, 0);
        profiler.popPush("draw");
//        tess.tessellator.draw();

//        // gl state finish
//        RenderHelper.enableStandardItemLighting();

        profiler.pop();
        profiler.pop();
        profiler.pop();
    }

    public static void renderTank(
            PoseStack.Pose pose,
            TankSize size,
            FluidSmoother tank,
            int combinedLight,
            int combinedOverlay,
            float partialTicks,
            VertexConsumer bb
    ) {
        FluidStackInterp fluid = tank.getFluidForRender(partialTicks);
        if (fluid == null || fluid.amount <= 0) {
            return;
        }
        int blockLight = fluid.fluid.getRawFluid().getFluidType().getLightLevel(fluid.fluid) & 0xF;
        combinedLight |= blockLight << 4;
        FluidRenderer.vertex.lighti(combinedLight);
        FluidRenderer.vertex.overlay(combinedOverlay);
        FluidRenderer.renderFluid(
                FluidSpriteType.STILL,
                fluid.fluid,
                fluid.amount,
                tank.getCapacity(),
                size.min,
                size.max,
                pose,
                bb,
                null
        );
    }

    static class TankRenderSizes {
        final TankSize tankIn, tankOutGas, tankOutLiquid;

        public TankRenderSizes(TankSize tankIn, TankSize tankOutGas, TankSize tankOutLiquid) {
            this.tankIn = tankIn;
            this.tankOutGas = tankOutGas;
            this.tankOutLiquid = tankOutLiquid;
        }

        public TankRenderSizes rotateY() {
            return new TankRenderSizes(tankIn.rotateY(), tankOutGas.rotateY(), tankOutLiquid.rotateY());
        }
    }

    static class Size {
        final Vec3 min, max;

        public Size(int sx, int sy, int sz, int ex, int ey, int ez) {
            this(new Vec3(sx, sy, sz).scale(1 / 16.0), new Vec3(ex, ey, ez).scale(1 / 16.0));
        }

        public Size(Vec3 min, Vec3 max) {
            this.min = min;
            this.max = max;
        }

        public Size shrink(double by) {
            return new Size(min.add(by, by, by), max.subtract(by, by, by));
        }

        public Size rotateY() {
            Vec3 _min = rotateY(min);
            Vec3 _max = rotateY(max);
            return new Size(VecUtil.min(_min, _max), VecUtil.max(_min, _max));
        }

        private static Vec3 rotateY(Vec3 vec) {
            return new Vec3(//
                    1 - vec.z, //
                    vec.y, //
                    vec.x//
            );
        }
    }
}
