package buildcraft.lib.command;

import buildcraft.lib.script.ReloadableRegistryManager;
import com.mojang.brigadier.Command;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;

//public class CommandReloadRegistries extends CommandBase
public class CommandReloadRegistries extends BCSubCommandBase
{
    public CommandReloadRegistries()
    {
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
