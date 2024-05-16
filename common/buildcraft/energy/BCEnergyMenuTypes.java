package buildcraft.energy;

import buildcraft.energy.client.gui.GuiEngineIron_BC8;
import buildcraft.energy.client.gui.GuiEngineStone_BC8;
import buildcraft.energy.container.ContainerEngineIron_BC8;
import buildcraft.energy.container.ContainerEngineStone_BC8;
import buildcraft.energy.tile.TileEngineIron_BC8;
import buildcraft.energy.tile.TileEngineStone_BC8;
import buildcraft.lib.misc.MessageUtil;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BCEnergyMenuTypes {
    public static final ContainerType<ContainerEngineIron_BC8> ENGINE_IRON = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileEngineIron_BC8) {
                    TileEngineIron_BC8 tile = (TileEngineIron_BC8) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerEngineIron_BC8(BCEnergyMenuTypes.ENGINE_IRON, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final ContainerType<ContainerEngineStone_BC8> ENGINE_STONE = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileEngineStone_BC8) {
                    TileEngineStone_BC8 tile = (TileEngineStone_BC8) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerEngineStone_BC8(BCEnergyMenuTypes.ENGINE_STONE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                ENGINE_IRON.setRegistryName("engine_iron"),
                ENGINE_STONE.setRegistryName("engine_stone")
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ScreenManager.register(ENGINE_IRON, GuiEngineIron_BC8::new);
            ScreenManager.register(ENGINE_STONE, GuiEngineStone_BC8::new);
        }
    }
}
