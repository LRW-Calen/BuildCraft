package buildcraft.robotics;

import buildcraft.lib.misc.MessageUtil;
import buildcraft.robotics.container.ContainerProgrammingTable_Neptune;
import buildcraft.robotics.container.ContainerZonePlanner;
import buildcraft.robotics.gui.GuiProgrammingTable_Neptune;
import buildcraft.robotics.gui.GuiZonePlanner;
import buildcraft.robotics.tile.TileZonePlanner;
import buildcraft.silicon.tile.TileProgrammingTable_Neptune;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BCRoboticsMenuTypes {
    public static final ContainerType<ContainerZonePlanner> ZONE_PLANNER = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileZonePlanner) {
                    TileZonePlanner tile = (TileZonePlanner) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerZonePlanner(BCRoboticsMenuTypes.ZONE_PLANNER, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );
    public static final ContainerType<ContainerProgrammingTable_Neptune> PROGRAMMING_TABLE = IForgeContainerType.create((windowId, inv, data) ->
            {
                TileEntity te = inv.player.level.getBlockEntity(data.readBlockPos());
                if (te instanceof TileProgrammingTable_Neptune) {
                    TileProgrammingTable_Neptune tile = (TileProgrammingTable_Neptune) te;
                    MessageUtil.clientHandleUpdateTileMsgBeforeOpen(tile, data);
                    return new ContainerProgrammingTable_Neptune(BCRoboticsMenuTypes.PROGRAMMING_TABLE, windowId, inv.player, tile);
                } else {
                    return null;
                }
            }
    );

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                ZONE_PLANNER.setRegistryName("zone_planner"),
                PROGRAMMING_TABLE.setRegistryName("programming_table")
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ScreenManager.register(ZONE_PLANNER, GuiZonePlanner::new);
            ScreenManager.register(PROGRAMMING_TABLE, GuiProgrammingTable_Neptune::new);
        }
    }
}
