package buildcraft.silicon;

import buildcraft.silicon.item.ItemGateCopier;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BCSilicon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCSiliconModBusEventDist {
    public static final ResourceLocation PREDICATE_HAS_DATA = new ResourceLocation("buildcraft", "has_data");

    @SubscribeEvent
    public static void itemPropReg(FMLClientSetupEvent event) {
        event.enqueueWork(
                () ->
                {
                    ItemProperties.register(
                            BCSiliconItems.gateCopier.get(),
                            PREDICATE_HAS_DATA,
                            (stack, world, entity, pSeed) -> ItemGateCopier.getMetadata(stack)
                    );
                }
        );
    }
}
