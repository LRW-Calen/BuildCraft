package buildcraft.lib.command;

import buildcraft.api.core.BCLog;
import buildcraft.lib.BCLib;
import buildcraft.lib.script.ReloadableRegistryManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.versions.forge.ForgeVersion;
import org.apache.maven.artifact.versioning.ArtifactVersion;

//public class CommandVersion extends CommandBase
public class CommandVersion extends BCSubCommandBase
{
    CommandVersion()
    {
        super(
                "version",
                "command.buildcraft.buildcraft.version.help",
                0,
                (arg) ->
                {
                    Entity sender = arg.getSource().getEntity();
                    if (sender == null)
                    {
                        return 0;
                    }
//                    ForgeVersion.CheckResult result = ForgeVersion.getResult(BCLib.MOD_CONTAINER);
                    IModInfo modInfo = BCLib.MOD_CONTAINER.getModInfo();
                    ArtifactVersion version = modInfo.getVersion();
//                    if (result.status == Status.FAILED)
//                    {
//                        sender.sendMessage(new TextComponentTranslation("command.buildcraft.version.failed"));
//                        return;
//                    }

//                    Style style = new Style();
                    Style style = Style.EMPTY;
//                    if (result.status == Status.OUTDATED)
//                    {
////                        style.setColor(ChatFormatting.RED);
//                        style.withColor(ChatFormatting.RED);
//                    }
//                    else
//                    {
////                        style.setColor(ChatFormatting.GREEN);
//                        style.withColor(ChatFormatting.GREEN);
//                    }

//                    BCLog.logger.info("[lib.command.version] Result status = " + result.status);
//                    BCLog.logger.info("[lib.command.version] Result url = " + result.url);
                    BCLog.logger.info("[lib.command.version] Result url = " + modInfo.getUpdateURL());
//                    BCLog.logger.info("[lib.command.version] Result target = " + result.target);
                    BCLog.logger.info("[lib.command.version] Result target = " + modInfo.getVersion().toString());
//                    BCLog.logger.info("[lib.command.version] Result changes = " + result.changes);

                    String currentVersion = BCLib.VERSION;
                    if (currentVersion.startsWith("$"))
                    {
                        currentVersion = "?.??.??";
//                        style.setColor(ChatFormatting.GRAY);
                        style.withColor(ChatFormatting.GRAY);
                    }

//                    Object[] textArgs = {currentVersion, ForgeVersion.mcVersion, result.target.toString()};
                    Object[] textArgs = {currentVersion, ForgeVersion.getVersion(), version.toString()};
//                    sender.sendMessage(new TextComponentTranslation("command.buildcraft.version", textArgs).setStyle(style));
                    sender.sendMessage(new TranslatableComponent("command.buildcraft.version", textArgs).setStyle(style), Util.NIL_UUID);

                    if (currentVersion.contains("-pre"))
                    {
//                        sender.sendMessage(new TextComponentTranslation("command.buildcraft.version.prerelease"));
                        sender.sendMessage(new TranslatableComponent("command.buildcraft.version.prerelease"), Util.NIL_UUID);
                    }
                    return 0;
                }
        );
    }

//    @Override
//    public String getName()
//    {
//        return "version";
//    }
//
//    @Override
//    public String getUsage(ICommandSender sender)
//    {
//        return "command.buildcraft.buildcraft.version.help";
//    }
//
//    @Override
//    public int getRequiredPermissionLevel()
//    {
//        return 0;
//    }
//
//    @Override
//    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
//    {
//        ForgeVersion.CheckResult result = ForgeVersion.getResult(BCLib.MOD_CONTAINER);
//        if (result.status == Status.FAILED)
//        {
//            sender.sendMessage(new TextComponentTranslation("command.buildcraft.version.failed"));
//            return;
//        }
//
//        Style style = new Style();
//        if (result.status == Status.OUTDATED)
//        {
//            style.setColor(TextFormatting.RED);
//        }
//        else
//        {
//            style.setColor(TextFormatting.GREEN);
//        }
//
//        BCLog.logger.info("[lib.command.version] Result status = " + result.status);
//        BCLog.logger.info("[lib.command.version] Result url = " + result.url);
//        BCLog.logger.info("[lib.command.version] Result target = " + result.target);
//        BCLog.logger.info("[lib.command.version] Result changes = " + result.changes);
//
//        String currentVersion = BCLib.VERSION;
//        if (currentVersion.startsWith("$"))
//        {
//            currentVersion = "?.??.??";
//            style.setColor(TextFormatting.GRAY);
//        }
//
//        Object[] textArgs = {currentVersion, ForgeVersion.mcVersion, result.target.toString()};
//        sender.sendMessage(new TextComponentTranslation("command.buildcraft.version", textArgs).setStyle(style));
//
//        if (currentVersion.contains("-pre"))
//        {
//            sender.sendMessage(new TextComponentTranslation("command.buildcraft.version.prerelease"));
//        }
//    }
}
