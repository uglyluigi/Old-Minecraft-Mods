package spacewalken.mods.commandsplus.common;

import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

/**
 * Created with Intellibro.
 * User: brennanforrest
 * Date: 6/29/13
 * Time: 9:25 PM
 */
public class CommandCmdPlusHelp extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "help++";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/help++";
    }

    @Override
    public List getCommandAliases()
    {
        return Arrays.asList("cmdhelp", "helpcmd++");
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] arguments)
    {
        if (arguments.length == 0)
        {
            commandSender.sendChatToPlayer(MethodProxy.sToCMC(String.format("\247a-Help page for Commands++-\n" +
                    "1)./* /stats*/ [player:hide:unhide] - Get some statistics from the specified player.\n" +
                    "2)./* /calc*/ [add:subtract:multiply:divide] [number] [number] - Do <operation> on <number> and <number2>.\n" +
                    "3)./* /searchlog*/ [word:phrase] - Search the server long for the word or phrase.\n" +
                    "4)./* /waypoint*/ [save:read:remove] [name] [@here:x] <y> <z> - Save a waypoint at the coordinates for future use.\n").replace("/*", EnumChatFormatting.DARK_GREEN.toString() + EnumChatFormatting.BOLD.toString()).replace("*/", EnumChatFormatting.RESET.toString() + "\247a")));
        } else
        {
            throw new WrongUsageException(getCommandUsage(commandSender));
        }
    }
}
