package spacewalken.mods.commandsplus.common;

import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandPing extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "ping";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/ping";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] arguments)
    {
        commandSender.sendChatToPlayer(MethodProxy.sToCMC("\247aPong!"));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments)
    {
        return null;
    }
}