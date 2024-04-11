package buildcraft.compat;

import buildcraft.compat.network.IGuiCreator;
import buildcraft.lib.tile.item.ItemHandlerSimple;
import com.google.common.collect.Lists;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BCCompat.MOD_ID)
public class CompatUtils
{
    //    public static final Capability<IGuiCreator> CAP_GUI_CREATOR = CapabilitiesHelper.registerCapability(IGuiCreator.class);
//    public static final Capability<IGuiCreator> CAP_GUI_CREATOR = CapabilitiesHelper.registerCapability(IGuiCreator.class);
    public static final Capability<IGuiCreator> CAP_GUI_CREATOR = CapabilityManager.get(new CapabilityToken<>(){});

    private CompatUtils()
    {
    }

    public static List<ItemStack> compactInventory(ItemHandlerSimple inventory)
    {
        List<ItemStack> stacks = Lists.newArrayList();

        for (int slot = 0; slot < inventory.getSlots(); ++slot)
        {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty())
            {
                boolean handled = false;
                for (ItemStack existing : stacks)
                {
                    if (existing.sameItem(stack))
                    {
                        existing.grow(stack.getCount());
                        handled = true;
                        break;
                    }
                }

                if (!handled)
                {
                    stacks.add(stack.copy());
                }
            }
        }

        return stacks;
    }

    // Calen
    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event)
    {
        event.register(IGuiCreator.class);
    }
}
