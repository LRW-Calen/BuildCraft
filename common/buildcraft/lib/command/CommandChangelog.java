package buildcraft.lib.command;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;

//public class CommandChangelog extends CommandBase
public class CommandChangelog extends BCSubCommandBase {
    public CommandChangelog() {
        super(
                "changelog",
                "command.buildcraft.buildcraft.changelog.help",
                0,
                (arg) ->
                {
                    Entity e = arg.getSource().getEntity();
                    if (e != null) {
                        e.sendMessage(new TextComponent("TODO: Implement this!"), Util.NIL_UUID);
                    }
                    return 0;
                }
        );
    }

//    @Override
//    public String getName()
//    {
//        return "changelog";
//    }

//    @Override
//    public String getUsage(ICommandSender sender)
//    {
//        return "command.buildcraft.buildcraft.changelog.help";
//    }

//    @Override
//    public int getRequiredPermissionLevel()
//    {
//        return 0;
//    }

//    @Override
//    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//    {
//        sender.sendMessage(new TextComponentString("TODO: Implement this!"));
//    }
}


//public class CommandChangelog extends ISubCommand {
//    @Override
//    public String getName() {
//        return "changelog";
//    }
//
//    @Override
//    public String getUsage(ICommandSender sender) {
//        return "command.buildcraft.buildcraft.changelog.help";
//    }
//
//    @Override
//    public int getRequiredPermissionLevel() {
//        return 0;
//    }
//
//    @Override
//    public void call(MinecraftServer server, CommandContext<CommandSourceStack> ctx, CommandSourceStack sender) throws CommandSyntaxException
//    {
//        sender.getPlayerOrException().sendMessage(new TextComponentString("TODO: Implement this!"));
//    }
//}
