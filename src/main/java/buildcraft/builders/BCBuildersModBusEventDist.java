package buildcraft.builders;

import buildcraft.builders.item.ItemSchematicSingle;
import buildcraft.builders.item.ItemSnapshot;
import buildcraft.core.BCCore;
import buildcraft.lib.misc.ColourUtil;
import buildcraft.transport.BCTransportItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BCBuilders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCBuildersModBusEventDist
{
    public static final ResourceLocation PREDICATE_USED = new ResourceLocation("buildcraft", "used");

    @SubscribeEvent
    public static void itemPropReg(FMLClientSetupEvent event)
    {
        event.enqueueWork(
                () ->
                {
                    ItemProperties.register(
                            BCBuildersItems.snapshotBLUEPRINT.get(),
                            PREDICATE_USED,
//                            (stack, world, entity, pSeed) -> (stack.hasTag() && stack.getTag().contains(ItemSnapshot.TAG_KEY, Tag.TAG_COMPOUND)) ? 1 : 0
                            (stack, world, entity, pSeed) -> stack.getTagElement(ItemSnapshot.TAG_KEY) != null ? 1 : 0
                    );
                    ItemProperties.register(
                            BCBuildersItems.snapshotTEMPLATE.get(),
                            PREDICATE_USED,
//                            (stack, world, entity, pSeed) -> (stack.hasTag() && stack.getTag().contains(ItemSnapshot.TAG_KEY, Tag.TAG_COMPOUND)) ? 1 : 0
                            (stack, world, entity, pSeed) -> stack.getTagElement(ItemSnapshot.TAG_KEY) != null ? 1 : 0
                    );
                    ItemProperties.register(
                            BCBuildersItems.schematicSingle.get(),
                            PREDICATE_USED,
                            (stack, world, entity, pSeed) ->
                            {
//                                if (stack.getDamageValue() == ItemSchematicSingle.DAMAGE_USED && stack.getTag() != null && stack.getTag().contains(ItemSchematicSingle.NBT_KEY))
                                if (stack.getDamageValue() == ItemSchematicSingle.DAMAGE_USED && stack.getTagElement(ItemSchematicSingle.NBT_KEY) != null)
                                {
                                    return 1;
                                }
//                                if (stack.getDamageValue() == ItemSchematicSingle.DAMAGE_CLEAN || !stack.hasTag() || !stack.getTag().contains(ItemSchematicSingle.NBT_KEY))
                                if (stack.getDamageValue() == ItemSchematicSingle.DAMAGE_CLEAN || stack.getTagElement(ItemSchematicSingle.NBT_KEY) == null)
                                {
                                    return 0;
                                }
                                throw new RuntimeException("[builders.item] damage not match nbt!");
                            }
                    );
                }
        );
    }
}
