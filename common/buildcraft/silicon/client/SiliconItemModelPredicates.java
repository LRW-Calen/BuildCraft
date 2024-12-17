package buildcraft.silicon.client;

import buildcraft.silicon.BCSiliconItems;
import buildcraft.silicon.item.ItemGateCopier;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class SiliconItemModelPredicates {
    public static final ResourceLocation PREDICATE_HAS_DATA = new ResourceLocation("buildcraft", "has_data");

    public static void register(FMLClientSetupEvent event) {
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
