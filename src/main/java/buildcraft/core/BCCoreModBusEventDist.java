package buildcraft.core;

import buildcraft.api.items.IMapLocation;
import buildcraft.builders.BCBuildersItems;
import buildcraft.builders.item.ItemSnapshot;
import buildcraft.core.item.ItemList_BC8;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.lib.misc.StackUtil;
import buildcraft.transport.BCTransportItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCCoreModBusEventDist
{
    public static final ResourceLocation PREDICATE_USED = new ResourceLocation("buildcraft", "used");
    public static final ResourceLocation PREDICATE_MAP_TYPE = new ResourceLocation("buildcraft", "map_type");

    @SubscribeEvent
    public static void itemPropReg(FMLClientSetupEvent event)
    {
        event.enqueueWork(
                () ->
                {
                    ItemProperties.register(
                            BCCoreItems.list.get(),
                            PREDICATE_USED,
//                            (stack, world, entity, pSeed) -> (stack.hasTag() && stack.getTag().contains(ItemSnapshot.TAG_KEY, Tag.TAG_COMPOUND)) ? 1 : 0
                            (stack, world, entity, pSeed) -> ItemList_BC8.isUsed(stack) ? 1 : 0
                    );
                    ItemProperties.register(
                            BCCoreItems.mapLocation.get(),
                            PREDICATE_MAP_TYPE,
//                            (stack, world, entity, pSeed) -> (stack.hasTag() && stack.getTag().contains(ItemSnapshot.TAG_KEY, Tag.TAG_COMPOUND)) ? 1 : 0
                            (stack, world, entity, pSeed) -> IMapLocation.MapLocationType.getFromStack(StackUtil.asNonNull(stack)).meta
                    );
                }
        );
    }
}
