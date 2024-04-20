package buildcraft.silicon;

import buildcraft.lib.misc.MessageUtil;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import buildcraft.silicon.container.ContainerAssemblyTable;
import buildcraft.silicon.container.ContainerGate;
import buildcraft.silicon.container.ContainerIntegrationTable;
import buildcraft.silicon.gui.GuiAdvancedCraftingTable;
import buildcraft.silicon.gui.GuiAssemblyTable;
import buildcraft.silicon.gui.GuiGate;
import buildcraft.silicon.gui.GuiIntegrationTable;
import buildcraft.silicon.plug.PluggableGate;
import buildcraft.silicon.tile.TileAdvancedCraftingTable;
import buildcraft.silicon.tile.TileAssemblyTable;
import buildcraft.silicon.tile.TileIntegrationTable;
import buildcraft.transport.tile.TilePipeHolder;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BCSiliconMenuTypes {
    public static final MenuType<ContainerAssemblyTable> ASSEMBLY_TABLE = IForgeMenuType.create((windowId, inv, data) ->
            {
                if (inv.player.level.getBlockEntity(data.readBlockPos()) instanceof TileAssemblyTable tile) {
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerAssemblyTable(BCSiliconMenuTypes.ASSEMBLY_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final MenuType<ContainerIntegrationTable> INTEGRATION_TABLE = IForgeMenuType.create((windowId, inv, data) ->
            {
                if (inv.player.level.getBlockEntity(data.readBlockPos()) instanceof TileIntegrationTable tile) {
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerIntegrationTable(BCSiliconMenuTypes.INTEGRATION_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final MenuType<ContainerAdvancedCraftingTable> ADVANCED_CRAFTING_TABLE = IForgeMenuType.create((windowId, inv, data) ->
            {
                if (inv.player.level.getBlockEntity(data.readBlockPos()) instanceof TileAdvancedCraftingTable tile) {
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerAdvancedCraftingTable(BCSiliconMenuTypes.INTEGRATION_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    // Calen：for Client
    public static final MenuType<ContainerGate> GATE = IForgeMenuType.create((windowId, inv, data) ->
            {
                BlockPos pos = data.readBlockPos();
                if (inv.player.level.getBlockEntity(pos) instanceof TilePipeHolder holder) {
                    int id = data.readInt();
                    Direction direction = Direction.from3DDataValue(id >>> 8);
                    if (holder.getPluggable(direction) instanceof PluggableGate gate) {
                        MessageUtil.clientHandleUpdateTileMsgBeforeOpen(holder, data);
//                        MessageUpdateTile message = new MessageUpdateTile();
//                        message.fromBytes(data);
//                        try
//                        {
////                        holder.receivePayload(new NetworkEvent.Context(null, NetworkDirection.PLAY_TO_CLIENT, -1), message.payload);
//                            // Calen: create a fake Context for tile to read NetworkDirection
//                            Constructor<NetworkEvent.Context> c = NetworkEvent.Context.class.getDeclaredConstructor(Connection.class, NetworkDirection.class, int.class);
//                            c.setAccessible(true);
//                            NetworkEvent.Context ctx = c.newInstance(null, NetworkDirection.PLAY_TO_CLIENT, -1);
//                            // Process the msg and create a new gate object
//                            holder.receivePayload(ctx, message.payload);
//                        }
//                        catch (Exception e)
//                        {
//                            BCLog.logger.warn("[silicon.gui] Failed to handle MessageUpdateTile of PluggableGate[" + gate.definition.identifier + "] at " + pos, e);
//                        }

                        // Calen 1.18.2: moved from ContainerGate#<init>
                        // Server call in PluggableGate#onPluggableActivate
                        gate.logic.getPipeHolder().onPlayerOpen(inv.player);

                        // Calen: Refresh the gate object
                        gate = (PluggableGate) holder.getPluggable(direction);

                        // Calen: from BCSiliconProxy implements IGuiHandler
                        return new ContainerGate(BCSiliconMenuTypes.GATE, windowId, inv.player, gate.logic);
                    }
                }
                return null;
            }
    );


    public static void registerAll(RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().registerAll(
                ASSEMBLY_TABLE.setRegistryName("assembly_table"),
                INTEGRATION_TABLE.setRegistryName("integration_table"),
                ADVANCED_CRAFTING_TABLE.setRegistryName("advanced_crafting_table"),
                GATE.setRegistryName("gate")
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
//        MenuScreens.register(ASSEMBLY_TABLE, BCSiliconScreenConstructors.ASSEMBLY_TABLE);
//        MenuScreens.register(INTEGRATION_TABLE, BCSiliconScreenConstructors.INTEGRATION_TABLE);
//        MenuScreens.register(ADVANCED_CRAFTING_TABLE, BCSiliconScreenConstructors.ADVANCED_CRAFTING_TABLE);
//        MenuScreens.register(GATE, BCSiliconScreenConstructors.GATE);
            MenuScreens.register(ASSEMBLY_TABLE, GuiAssemblyTable::new);
            MenuScreens.register(INTEGRATION_TABLE, GuiIntegrationTable::new);
            MenuScreens.register(ADVANCED_CRAFTING_TABLE, GuiAdvancedCraftingTable::new);
            MenuScreens.register(GATE, GuiGate::new);
        }
    }
}
