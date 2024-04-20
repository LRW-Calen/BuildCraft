package buildcraft.lib.command;

import buildcraft.lib.script.ReloadableRegistryManager;

//public class CommandReloadRegistries extends CommandBase
public class CommandReloadRegistries extends BCSubCommandBase {
    public CommandReloadRegistries() {
        super(
                "reload",
                "command.buildcraft.reload",
                0,
                (arg) ->
                {
                    ReloadableRegistryManager.DATA_PACKS.reloadAll();
                    return 0;
                }
        );
    }

//    @Override
//    public String getName()
//    {
//        return "reload";
//    }
//
//    @Override
//    public String getUsage(ICommandSender sender)
//    {
//        return "command.buildcraft.reload";
//    }
//
//    @Override
//    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//    {
//        ReloadableRegistryManager.DATA_PACKS.reloadAll();
//    }
}
