package buildcraft.silicon;

import buildcraft.api.net.IMessage;
import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.api.transport.pluggable.PipePluggable;
import buildcraft.lib.misc.MessageUtil;
import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import buildcraft.silicon.container.ContainerAssemblyTable;
import buildcraft.silicon.container.ContainerGate;
import buildcraft.silicon.container.ContainerIntegrationTable;
import buildcraft.silicon.gate.GateLogic;
import buildcraft.silicon.gui.GuiAdvancedCraftingTable;
import buildcraft.silicon.gui.GuiAssemblyTable;
import buildcraft.silicon.gui.GuiGate;
import buildcraft.silicon.gui.GuiIntegrationTable;
import buildcraft.silicon.plug.PluggableGate;
import buildcraft.silicon.tile.TileAdvancedCraftingTable;
import buildcraft.silicon.tile.TileAssemblyTable;
import buildcraft.silicon.tile.TileIntegrationTable;
import buildcraft.transport.tile.TilePipeHolder;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

/** The static fields are used to create GUI on client thread. */
public class BCSiliconMenuTypes {
    public static final ContainerType<ContainerAssemblyTable> ASSEMBLY_TABLE = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileAssemblyTable) {
                    TileAssemblyTable tile = (TileAssemblyTable) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerAssemblyTable(BCSiliconMenuTypes.ASSEMBLY_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final ContainerType<ContainerIntegrationTable> INTEGRATION_TABLE = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileIntegrationTable) {
                    TileIntegrationTable tile = (TileIntegrationTable) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerIntegrationTable(BCSiliconMenuTypes.INTEGRATION_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final ContainerType<ContainerAdvancedCraftingTable> ADVANCED_CRAFTING_TABLE = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileAdvancedCraftingTable) {
                    TileAdvancedCraftingTable tile = (TileAdvancedCraftingTable) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerAdvancedCraftingTable(BCSiliconMenuTypes.INTEGRATION_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    /**
     * {@link IPipeHolder#onPlayerOpen(PlayerEntity)} is moved from {@link ContainerGate#ContainerGate(ContainerType, int, PlayerEntity, GateLogic)} in 1.12.2
     * to ensure the new gate obj created before GUI opened.
     * <p>
     * {@link IPipeHolder#getPluggable(Direction)} is used to receive the new gate obj.
     * <p>
     * {@link MessageUtil#clientHandleUpdateTileMsgBeforeOpen(TileBC_Neptune, PacketBuffer, Runnable...)}
     * handles the message created in {@link MessageUtil#serverOpenGUIWithMsg(PlayerEntity, INamedContainerProvider, BlockPos, int, IMessage)}
     * in {@link PluggableGate#onPluggableActivate(PlayerEntity, RayTraceResult, float, float, float)}
     */
    public static final ContainerType<ContainerGate> GATE = IForgeContainerType.create((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                TileEntity te = inv.player.level.getBlockEntity(pos);
                if (te instanceof TilePipeHolder) {
                    TilePipeHolder holder = (TilePipeHolder) te;
                    int id = data.readInt();
                    Direction direction = Direction.from3DDataValue(id >>> 8);
                    PipePluggable pluggable = holder.getPluggable(direction);
                    if (pluggable instanceof PluggableGate) {
                        PluggableGate gate = (PluggableGate) pluggable;
                        MessageUtil.clientHandleUpdateTileMsgBeforeOpen(holder, data);
                        gate.logic.getPipeHolder().onPlayerOpen(inv.player);

                        // Refresh the gate object
                        gate = (PluggableGate) holder.getPluggable(direction);

                        return new ContainerGate(BCSiliconMenuTypes.GATE, windowId, inv.player, gate.logic);
                    }
                }
                return null;
            }
    );

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                ASSEMBLY_TABLE.setRegistryName("assembly_table"),
                INTEGRATION_TABLE.setRegistryName("integration_table"),
                ADVANCED_CRAFTING_TABLE.setRegistryName("advanced_crafting_table"),
                GATE.setRegistryName("gate")
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ScreenManager.register(ASSEMBLY_TABLE, GuiAssemblyTable::new);
            ScreenManager.register(INTEGRATION_TABLE, GuiIntegrationTable::new);
            ScreenManager.register(ADVANCED_CRAFTING_TABLE, GuiAdvancedCraftingTable::new);
            ScreenManager.register(GATE, GuiGate::new);
        }
    }
}
