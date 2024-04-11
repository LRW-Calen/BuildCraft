/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.transport;

import buildcraft.api.core.BCLog;
import buildcraft.api.transport.pipe.PipeApiClient;
import buildcraft.api.transport.pluggable.IPluggableStaticBaker;
import buildcraft.lib.client.model.ModelHolderStatic;
import buildcraft.lib.client.model.ModelHolderVariable;
import buildcraft.lib.client.model.ModelPluggableItem;
import buildcraft.lib.client.model.MutableQuad;
import buildcraft.lib.client.model.plug.PlugBakerSimple;
import buildcraft.lib.expression.DefaultContexts;
import buildcraft.lib.expression.FunctionContext;
import buildcraft.lib.expression.node.value.NodeVariableObject;
import buildcraft.lib.misc.ExpressionCompat;
import buildcraft.lib.misc.RenderUtil;
import buildcraft.transport.client.PipeBlockColours;
import buildcraft.transport.client.model.ModelPipe;
import buildcraft.transport.client.model.ModelPipeItem;
import buildcraft.transport.client.model.key.KeyPlugBlocker;
import buildcraft.transport.client.model.key.KeyPlugPowerAdaptor;
import buildcraft.transport.client.render.*;
import buildcraft.transport.pipe.behaviour.PipeBehaviourStripes;
import buildcraft.transport.pipe.flow.PipeFlowFluids;
import buildcraft.transport.pipe.flow.PipeFlowItems;
import buildcraft.transport.pipe.flow.PipeFlowPower;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = NameSpaces.BUILDCRAFT_TRANSPORT, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCTransportModels
{
    public static final ModelHolderStatic BLOCKER;
    public static final ModelHolderStatic POWER_ADAPTER;

    private static final ModelHolderVariable STRIPES;
    private static final NodeVariableObject<Direction> STRIPES_DIRECTION;

    public static final IPluggableStaticBaker<KeyPlugBlocker> BAKER_PLUG_BLOCKER;
    public static final IPluggableStaticBaker<KeyPlugPowerAdaptor> BAKER_PLUG_POWER_ADAPTOR;

    static
    {
        // Calen: ensure ExpressionCompat ENUM_FACING = new NodeType<>("Facing", Direction.UP); runned, or will cause IllegalArgumentException: Unknown NodeType class net.minecraft.core.Direction
        ExpressionCompat.setup();

        BLOCKER = getStaticModel("plugs/blocker");
        POWER_ADAPTER = getStaticModel("plugs/power_adapter");

        BAKER_PLUG_BLOCKER = new PlugBakerSimple<>(BLOCKER::getCutoutQuads);
        BAKER_PLUG_POWER_ADAPTOR = new PlugBakerSimple<>(POWER_ADAPTER::getCutoutQuads);

        {
            FunctionContext fnCtx = DefaultContexts.createWithAll();
            STRIPES_DIRECTION = fnCtx.putVariableObject("side", Direction.class);
            STRIPES = getModel("pipes/stripes", fnCtx);
        }
    }

    private static ModelHolderStatic getStaticModel(String str)
    {
        return new ModelHolderStatic("buildcrafttransport:models/" + str + ".json");
    }

    private static ModelHolderVariable getModel(String str, FunctionContext fnCtx)
    {
        return new ModelHolderVariable("buildcrafttransport:models/" + str + ".json", fnCtx);
    }

    // Calen: moved to @Mod.EventBusSubscriber
//    public static void fmlPreInit()
//    {
//        MinecraftForge.EVENT_BUS.register(BCTransportModels.class);
//    }

    public static void fmlInit()
    {
        // Moved to onRenderRegister
//        ClientRegistry.bindTileEntitySpecialRenderer(TilePipeHolder.class, new RenderPipeHolder());

        PipeApiClient.registry.registerBaker(KeyPlugBlocker.class, BAKER_PLUG_BLOCKER);
        PipeApiClient.registry.registerBaker(KeyPlugPowerAdaptor.class, BAKER_PLUG_POWER_ADAPTOR);

        PipeApiClient.registry.registerRenderer(PipeFlowItems.class, PipeFlowRendererItems.INSTANCE);
        PipeApiClient.registry.registerRenderer(PipeFlowFluids.class, PipeFlowRendererFluids.INSTANCE);
        PipeApiClient.registry.registerRenderer(PipeFlowPower.class, PipeFlowRendererPower.INSTANCE);

        PipeApiClient.registry.registerRenderer(PipeBehaviourStripes.class, PipeBehaviourRendererStripes.INSTANCE);
    }

    public static void fmlPostInit()
    {
        RenderUtil.registerBlockColour(BCTransportBlocks.pipeHolder.get(), PipeBlockColours.INSTANCE);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event)
    {
        // Calen: Useless
//        PipeRegistry.INSTANCE.getAllRegisteredPipes().forEach((def) ->
//                ForgeModelBakery.addSpecialModel(new ModelResourceLocation(def.identifier.getNamespace(), "pipe_" + def.identifier.getPath(), "inventory"))
//        );
//        if (BCFactoryBlocks.heatExchange != null)
//        {
//            ResourceLocation heatExchange = BCFactoryBlocks.heatExchange.getId();
////            ModelLoader.setCustomStateMapper(
//            ForgeModelBakery.addSpecialModel(new ResourceLocation(heatExchange.getNamespace(), "block/" + heatExchange.getPath()));
//
//        }
//        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("buildcrafttransport", "plug_blocker","inventory"));
//        ForgeModelBakery.addSpecialModel(new ModelResourceLocation("buildcrafttransport", "plug_power_adaptor","inventory"));
    }

    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onModelBake(ModelBakeEvent event)
    {
//        putModel(event, "pipe_holder#normal", ModelPipe.INSTANCE);
//        putModel(event, "pipe_item#inventory", ModelPipeItem.INSTANCE);
        event.getModelRegistry().replaceAll(((resourceLocation, bakedModel) ->
        {
            if (resourceLocation instanceof ModelResourceLocation m)
            {
                if (m.getNamespace().equals("buildcrafttransport"))
                {
                    if (m.getVariant().equals("inventory") && m.getPath().startsWith("pipe_"))
                    {
                        return ModelPipeItem.INSTANCE;
                    }
                    else if (m.getPath().equals("pipe_holder"))
                    {
                        return ModelPipe.INSTANCE;
                    }
                    else if (m.getPath().contains("pipe"))
                    {
                        BCLog.logger.warn("Found unexpected pipe at ModelBakeEvent: " + m);
                    }
                }
            }
            return bakedModel;
        }));
        putModel(event, "plug_blocker#inventory", new ModelPluggableItem(BLOCKER.getCutoutQuads()));
        putModel(event, "plug_power_adaptor#inventory", new ModelPluggableItem(POWER_ADAPTER.getCutoutQuads()));
    }

    private static void putModel(ModelBakeEvent event, String str, BakedModel model)
    {
        event.getModelRegistry().replace(new ModelResourceLocation("buildcrafttransport:" + str), model);
    }

    public static MutableQuad[] getStripesDynQuads(Direction side)
    {
        STRIPES_DIRECTION.value = side;
        return STRIPES.getCutoutQuads();
    }
}
