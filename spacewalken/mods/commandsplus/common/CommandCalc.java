package spacewalken.mods.commandsplus.common;

import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.util.Arrays;
import java.util.List;

/**
 * Created with Intellibro.
 * User: brennanforrest
 * Date: 6/11/02
 * Time: 9:56 PM
 */
public class CommandCalc extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "calc";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/calc [add:subtract:multiply:divide] [number] [number]";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args)
    {
        boolean send = true;
        if (args.length == 3)
        {
            long result = 0;
            double divisionResult;
            if (args[0].equals("add"))
            {
                result = (long) (Double.parseDouble(args[1]) + Double.parseDouble(args[2]));
            } else if (args[0].equals("subtract"))
            {
                result = (long) (Double.parseDouble(args[1]) - Double.parseDouble(args[2]));
            } else if (args[0].equals("divide"))
            {
                send = false;
                if (Double.parseDouble(args[2]) > Double.parseDouble(args[1]))
                {
                    divisionResult = Double.parseDouble(args[1]) / Double.parseDouble(args[2]);
                } else
                {
                    divisionResult = Double.parseDouble(Double.parseDouble(args[1]) / Double.parseDouble(args[2]) + "." + Double.parseDouble(args[1]) % Double.parseDouble(args[2]));
                }

                commandSender.sendChatToPlayer(MethodProxy.sToCMC(String.format("\247aResult: %.4f", divisionResult)));
            } else if (args[0].equals("multiply"))
            {
                result = (long) (Double.parseDouble(args[1]) * Double.parseDouble(args[2]));
            } else if (args[0].equals("pow"))
            {
                send = false;
                commandSender.sendChatToPlayer(MethodProxy.sToCMC(String.format("§aResult: %.4f", Math.pow(Double.parseDouble(args[1]), Double.parseDouble(args[2])))));
            }

            if (send) commandSender.sendChatToPlayer(MethodProxy.sToCMC("\247aResult: " + result));
        } else
        {
            throw new WrongUsageException(getCommandUsage(commandSender));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments)
    {
        return arguments.length == 1 ? Arrays.asList("add", "subtract", "multiply", "divide", "pow") : null;
    }
}
