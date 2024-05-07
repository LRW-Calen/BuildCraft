package buildcraft.lib.command;

import buildcraft.lib.script.ReloadableRegistryManager;
import net.minecraft.server.MinecraftServer;

//public class CommandReloadRegistries extends CommandBase
public class CommandReloadRegistries extends BCSubCommandBase {
    public CommandReloadRegistries() {
        super(
                "reload",
                "command.buildcraft.reload",
                4,
                (arg) ->
                {
                    ReloadableRegistryManager.DATA_PACKS.reloadAll();
                    return 0;
                }
        );
    }
}
