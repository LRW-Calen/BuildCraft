package buildcraft.lib.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;

public class BCSubCommandBase
{
    private final String NAME;
    private final String USAGE;
    private final int PERMISSION;
    private final Command<CommandSourceStack> EXECUTE;

    BCSubCommandBase(String NAME, String USAGE, int PERMISSION, Command<CommandSourceStack> EXECUTE)
    {
        this.NAME = NAME;
        this.USAGE = USAGE;
        this.PERMISSION = PERMISSION;
        this.EXECUTE = EXECUTE;
    }

    void addSubcommand(LiteralArgumentBuilder<CommandSourceStack> parentCommand)
    {
        parentCommand
                .then(Commands.literal(NAME) // 子命令
                        .requires((req) -> req.hasPermission(PERMISSION)) // 0->Player 2-> OP
                        .executes(EXECUTE) // 回调
                );
    }
}
