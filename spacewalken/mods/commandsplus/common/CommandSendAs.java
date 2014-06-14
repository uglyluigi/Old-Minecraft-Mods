package spacewalken.mods.commandsplus.common;

import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 6/28/13
 * Time: 1:28 PM
 */
public class CommandSendAs extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "send_as";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/send_as [username] [phrase]";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] arguments)
    {
        String message;
        int i = 0;

        if (arguments.length > 0)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : arguments)
            {
                i++;

                if (i > 1)
                {
                    stringBuilder.append(arg.concat(" "));
                }
            }

            message = stringBuilder.toString();

            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(MethodProxy.sToCMC("<" + arguments[0] + "> " + message));
        } else
        {
            throw new WrongUsageException(getCommandUsage(commandSender));
        }
    }
}
