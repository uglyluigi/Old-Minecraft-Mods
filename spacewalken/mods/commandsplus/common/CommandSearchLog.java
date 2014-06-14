package spacewalken.mods.commandsplus.common;

import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with Intellibro.
 * User: brennanforrest
 * Date: 6/21/13
 * Time: 11:03 PM
 */
public class CommandSearchLog extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "searchlog";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "/searchlog [word/phrase]";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("server.log"));
            String line;
            int occurrences = 0;
            int occurrencesByPlayer = 0;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null)
            {
                if (line.contains(args[0]))
                {
                    occurrences++;
                }

                if (line.contains(icommandsender.getCommandSenderName()))
                {
                    occurrencesByPlayer++;
                }
            }

            if (occurrences > 0)
            {
                if (args.length > 1)
                {
                    for (String currentArg : args)
                    {
                        sb.append(currentArg.concat(" "));
                    }

                    while ((line = reader.readLine()) != null)
                    {
                        if (line.contains(sb.toString()))
                        {
                            occurrences++;

                            if (line.contains(icommandsender.getCommandSenderName()))
                            {
                                occurrencesByPlayer++;
                            }
                        }
                    }

                    icommandsender.sendChatToPlayer(MethodProxy.sToCMC(String.format("The phrase \"%s\" occurred %d times (%d by you)", sb.toString(), occurrences, occurrencesByPlayer)));
                } else if (args.length == 1)
                {

                    while ((line = reader.readLine()) != null)
                    {
                        if (line.contains(args[1]))
                        {
                            occurrences++;

                            if (line.contains(icommandsender.getCommandSenderName()))
                            {
                                occurrencesByPlayer++;
                            }
                        }
                    }

                    icommandsender.sendChatToPlayer(MethodProxy.sToCMC(String.format("The word \"%s\" occurred %d times (%d by you)", args[1], occurrences, occurrencesByPlayer)));
                }
            } else
            {
                icommandsender.sendChatToPlayer(MethodProxy.sToCMC("\247c\'" + (sb.toString()) + "\' was not found in the server log."));
            }
        } catch (FileNotFoundException e)
        {
            icommandsender.sendChatToPlayer(MethodProxy.sToCMC("\247cThere is no server log in single player."));
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e)
        {
            throw new WrongUsageException("/searchlog [word/phrase]");
        }
    }
}
