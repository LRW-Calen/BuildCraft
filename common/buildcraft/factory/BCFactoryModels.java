/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.factory;

import buildcraft.factory.client.model.ModelHeatExchange;
import buildcraft.factory.client.render.*;
import buildcraft.factory.tile.TileDistiller_BC8;
import buildcraft.lib.client.model.ModelHolderVariable;
import buildcraft.lib.client.model.ModelItemSimple;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.misc.ExpressionCompat;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BCFactoryModels {
    public static final ModelHolderVariable DISTILLER;
    public static final ModelHolderVariable HEAT_EXCHANGE_STATIC;

    static {
        // Calen: ensure ExpressionCompat ENUM_FACING = new NodeType<>("Facing", Direction.UP); runned, or will cause IllegalArgumentException: Unknown NodeType class net.minecraft.core.Direction
        ExpressionCompat.setup();

        DISTILLER = new ModelHolderVariable(
                "buildcraftfactory:models/tiles/distiller.json",
                TileDistiller_BC8.MODEL_FUNC_CTX
        );
        HEAT_EXCHANGE_STATIC = new ModelHolderVariable(
                "buildcraftfactory:models/tiles/heat_exchange_static.json",
                ModelHeatExchange.FUNCTION_CONTEXT
        );
    }

    public static void fmlPreInit() {
        // 1.18.2: following events are IModBusEvent
//        MinecraftForge.EVENT_BUS.register(BCFactoryModels.class);
        IEventBus modEventBus = ((FMLModContainer) ModList.get().getModContainerById(BCFactory.MODID).get()).getEventBus();
        modEventBus.register(BCFactoryModels.class);
    }

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public static void onModelRegistry(ModelRegistryEvent event)
//    {
////        if (BCFactoryBlocks.heatExchange != null)
////        {
////            ResourceLocation heatExchange = BCFactoryBlocks.heatExchange.getId();
//////            ModelLoader.setCustomStateMapper(
//        // Calen: don't call ForgeModelBakery.addSpecialModel, that will cause exception
////            ForgeModelBakery.addSpecialModel(new ResourceLocation(heatExchange.getNamespace(), "block/" + heatExchange.getPath()));
////
////        }
//    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTesrReg(RegisterRenderers event) {
//        ClientRegistry.bindTileEntitySpecialRenderer(TileMiningWell.class, new RenderMiningWell());
        BlockEntityRenderers.register(BCFactoryBlocks.miningWellTile.get(), RenderMiningWell::new);
//        ClientRegistry.bindTileEntitySpecialRenderer(TilePump.class, new RenderPump());
        BlockEntityRenderers.register(BCFactoryBlocks.pumpTile.get(), RenderPump::new);
//        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new RenderTank());
        BlockEntityRenderers.register(BCFactoryBlocks.tankTile.get(), RenderTank::new);
//        ClientRegistry.bindTileEntitySpecialRenderer(TileDistiller_BC8.class, new RenderDistiller());
        BlockEntityRenderers.register(BCFactoryBlocks.distillerTile.get(), RenderDistiller::new);
//        ClientRegistry.bindTileEntitySpecialRenderer(TileHeatExchange.class, new RenderHeatExchange());
        BlockEntityRenderers.register(BCFactoryBlocks.heatExchangeTile.get(), RenderHeatExchange::new);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onModelBake(ModelBakeEvent event) {
        // Calen: to set model for each blockState, the model path contains blockstate props
        ModelHeatExchange modelHeatExchange = new ModelHeatExchange();
        event.getModelRegistry().replaceAll((rl, m) -> (rl.getPath().contains("heat_exchange") && !rl.getPath().contains("inventory")) ? modelHeatExchange : m);
        event.getModelRegistry().replace(
                new ModelResourceLocation(BCFactoryBlocks.heatExchange.getId(), "inventory"),
                new ModelItemSimple(
                        Arrays.stream(BCFactoryModels.HEAT_EXCHANGE_STATIC.getCutoutQuads())
                                .map(MutableQuad::multShade)
                                .map(MutableQuad::toBakedItem)
                                .collect(Collectors.toList()),
                        ModelItemSimple.TRANSFORM_BLOCK,
                        true
                )
        );
    }
}
