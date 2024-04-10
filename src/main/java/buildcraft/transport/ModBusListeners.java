package buildcraft.transport;

import buildcraft.transport.client.render.RenderPipeHolder;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BCTransport.MOD_ID)
public class ModBusListeners
{

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderRegister(EntityRenderersEvent.RegisterRenderers event)
    {
        BlockEntityRenderers.register(BCTransportBlocks.pipeHolderTile.get(), RenderPipeHolder::new);
    }
}
