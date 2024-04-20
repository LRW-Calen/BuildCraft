package buildcraft.factory;

import buildcraft.factory.container.ContainerAutoCraftItems;
import buildcraft.factory.container.ContainerChute;
import buildcraft.factory.gui.GuiAutoCraftItems;
import buildcraft.factory.gui.GuiChute;
import buildcraft.factory.tile.TileAutoWorkbenchItems;
import buildcraft.factory.tile.TileChute;
import buildcraft.lib.misc.MessageUtil;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BCFactoryMenuTypes {
    public static final MenuType<ContainerChute> CHUTE = IForgeMenuType.create((windowId, inv, data) ->
            {
                if (inv.player.level.getBlockEntity(data.readBlockPos()) instanceof TileChute tile) {
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerChute(BCFactoryMenuTypes.CHUTE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final MenuType<ContainerAutoCraftItems> AUTO_WORKBENCH_ITEMS = IForgeMenuType.create((windowId, inv, data) ->
            {
                if (inv.player.level.getBlockEntity(data.readBlockPos()) instanceof TileAutoWorkbenchItems tile) {
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerAutoCraftItems(BCFactoryMenuTypes.AUTO_WORKBENCH_ITEMS, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );

    public static void registerAll(RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().registerAll(
                CHUTE.setRegistryName("chute"),
                AUTO_WORKBENCH_ITEMS.setRegistryName("auto_workbench_items")
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
//        MenuScreens.register(CHUTE, BCFactoryScreenConstructors.CHUTE);
//        MenuScreens.register(AUTO_WORKBENCH_ITEMS, BCFactoryScreenConstructors.AUTO_WORKBENCH_ITEMS);
            MenuScreens.register(CHUTE, GuiChute::new);
            MenuScreens.register(AUTO_WORKBENCH_ITEMS, GuiAutoCraftItems::new);
        }
    }
}
