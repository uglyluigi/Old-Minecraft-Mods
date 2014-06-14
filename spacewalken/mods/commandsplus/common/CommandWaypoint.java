package spacewalken.mods.commandsplus.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import static spacewalken.util.MethodProxy.sToCMC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 6/21/13
 * Time: 11:28 PM
 */
public class CommandWaypoint extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "waypoint";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/waypoint [save:read:remove] [name:-all] [-here:x] <y> <z>";
    }

    @Override
    public List getCommandAliases()
    {
        return Arrays.asList("waypoints", "wp");
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] arguments)
    {
        ArrayList<String> waypoints = new ArrayList<String>();
        File waypointDir = new File("waypoints");
        File waypointFile = new File("waypoints/" + commandSender.getCommandSenderName() + ".txt");
        MinecraftServer server = MinecraftServer.getServer();

        /*
         * arguments[0] = mode
         * arguments[1] = name
         * arguments[2] = -here or X
         * arguments[3] = Y
         * arguments[4] = Z
         */

        try
        {
            //The waypoint directory handling expression
            if (waypointDir.mkdir())
            {
                server.logInfo("Create waypoint directory at " + waypointDir.getAbsolutePath());

                if (waypointFile.createNewFile())
                {
                    server.logInfo("Created waypoint file for " + commandSender.getCommandSenderName());
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(waypointFile, true));
            BufferedReader reader = new BufferedReader(new FileReader(waypointFile));

            if (arguments.length > 0 && waypointFile.exists())
            {
                if (arguments[0].equals("save") && arguments[1] != null && arguments[2] != null)
                {
                    //The process tree for saving a waypoint to the waypoint file.
                    EntityPlayer player = getCommandSenderAsPlayer(commandSender);
                    String line;
                    boolean isNamespaceOccupied = false;

                    //Iterate through the waypoint file to ensure the namespace of the new waypoint isn't already occupied.
                    while ((line = reader.readLine()) != null)
                    {
                        if (arguments[1].equals(line.split(":")[0]))
                        {
                            isNamespaceOccupied = true;
                            break;
                        }
                    }

                    if (!isNamespaceOccupied)
                    {
                        if (arguments[2].equals("-here") && arguments.length == 3)
                        {
                            //Determine the coordinates of the waypoint automagically instead of having them manually inputted.
                            writer.write(String.format("%s:%.0f:%.0f:%.0f:%d\n", arguments[1].replace(":", "$COLON$"), player.posX, player.posY, player.posZ, player.dimension));
                        } else if (!arguments[2].equals("-here") && arguments.length == 3)
                        {
                            //Write according to manually inputted coordinates.
                            writer.write(String.format("%s:%s:%s:%s:%d\n", arguments[1].replace(":", "$COLON$"), arguments[2], arguments[3], arguments[4], player.dimension));
                        } else
                        {
                            throw new IllegalArgumentException();
                        }
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aSuccessfully saved waypoint \'§6%s§a\'.", arguments[1])));
                    } else
                    {
                        commandSender.sendChatToPlayer(sToCMC(String.format("§cYou already have a waypoint named \'%s\'.", arguments[1])));
                    }
                } else if (arguments[0].equals("read") && arguments[1] != null)
                {
                    arguments[1] = arguments[1].replace(":", "$COLON$");

                    if (arguments[1].equals("-all"))
                    {
                        //Read all waypoints and output the result.
                        String line;

                        //Iterate through the waypoint file and add all of the waypoint names to a list.
                        while ((line = reader.readLine()) != null)
                        {
                            waypoints.add(getColorForDimension(Integer.parseInt(line.split(":")[4])) + line.split(":")[0].replace("$COLON$", ":") + "§a");
                        }

                        if (!waypoints.toString().equals("[]"))
                        {
                            commandSender.sendChatToPlayer(sToCMC(String.format("§aList of all saved waypoints: %s", waypoints.toString().replace("$COLON$", ":"))));
                        } else
                        {
                            commandSender.sendChatToPlayer(sToCMC("§cYou have no saved waypoints."));
                        }
                    } else
                    {
                        //Read the specified waypoint and its coordinates if it exists.

                        String line;
                        boolean exists = false;

                        //Iterate through the waypoint file and locate the specified waypoint, printing the waypoint's data and breaking the loop to avoid unnecessary iteration
                        while ((line = reader.readLine()) != null)
                        {
                            if (line.split(":")[0].equals(arguments[1]))
                            {
                                String[] waypointData = line.split(":");

                                //This message call is inside the loop because the loop only needs one result, and the properties of the waypoint cannot be fetched outside of the loop elegantly
                                commandSender.sendChatToPlayer(sToCMC(String.format("§aWaypoint: §6%s§a, X: §5%s§a, Y: §5%s§a, Z: §5%s§a, dimension: §5%s", waypointData[0].replace("$COLON$", ":"), waypointData[1], waypointData[2], waypointData[3], Integer.parseInt(waypointData[4]))));
                                exists = true;
                                //But that doesn't really matter since the loop is broken anyway
                                break;
                            }
                        }

                        //Sends the player a message if the waypoint failed to be found
                        if (!exists)
                        {
                            commandSender.sendChatToPlayer(sToCMC(String.format("§cYou have no waypoint saved as '%s'", arguments[1])));
                        }
                    }
                } else if (arguments[0].equals("remove"))
                {
                    //The process tree for removing waypoints.

                    if (arguments[1].equals("-all"))
                    {
                        //Delete the waypoint file (artificially clear it) if it exists.
                        //Just deleting the waypoint file directly saves time since it's automagically created on startup if it doesn't exist. C:
                        if (waypointFile.delete() && waypointFile.length() > 0)
                        {
                            commandSender.sendChatToPlayer(sToCMC("§cWaypoints deleted."));
                        } else
                        {
                            commandSender.sendChatToPlayer(sToCMC("§cThere are no waypoints to delete."));
                        }
                    } else
                    {
                        //Removes a specified waypoint.

                        String line;
                        boolean exists = false;
                        //The temporary waypoint file
                        File tempFile = new File(waypointFile.getName().replace(".txt", ".tmp"));
                        BufferedWriter tmpFileWriter = new BufferedWriter(new FileWriter(tempFile, true));

                        //Iterates through the primary file, checking if the specified waypoint exists
                        while ((line = reader.readLine()) != null)
                        {
                            if (arguments[1].equals(line.split(":")[0])) exists = true;
                        }

                        if (exists)
                        {
                            //Iterate though the primary file and write the contents to the temporary file, skipping the specified waypoint's line
                            while ((line = reader.readLine()) != null)
                            {
                                if (!arguments[1].equals(line.split(":")[0]))
                                {
                                    tmpFileWriter.write(line);
                                }
                            }

                            tmpFileWriter.close();

                            //Rename the temporary file to the primary file with the waypoint removed.
                            if (tempFile.renameTo(waypointFile))
                            {
                                commandSender.sendChatToPlayer(sToCMC("§aSuccessfully deleted."));
                            } else
                            {
                                commandSender.sendChatToPlayer(sToCMC("§cAn internal error occurred attempting to process your request."));
                            }
                        } else
                        {
                            commandSender.sendChatToPlayer(sToCMC(String.format("§cYou have no waypoint saved as \'%s\'.", arguments[1])));
                        }
                    }
                }
            } else
            {
                throw new IllegalArgumentException();
            }

            reader.close();
            writer.close();
        } catch (IOException ioe)
        {
            server.logWarning("IOException caught trying to process waypoint request: " + ioe.getMessage());
        } catch (IllegalArgumentException iae)
        {
            throw new WrongUsageException(this.getCommandUsage(commandSender));
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new WrongUsageException(this.getCommandUsage(commandSender));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments)
    {
        List<String> completionOptions = new ArrayList<String>();
        File waypointFile = new File("waypoints/" + commandSender.getCommandSenderName() + ".txt");

        if (arguments.length == 1)
        {
            //The three management functions of this command
            completionOptions.add("save");
            completionOptions.add("read");
            completionOptions.add("remove");
        }

        if (arguments.length == 2 && (arguments[0].equals("read") || arguments[0].equals("remove")))
        {
            completionOptions.add("-all");
        }

        if (arguments.length == 3 && (arguments[0].equals("save")))
        {
            completionOptions.add("-here");
        }

        try
        {
            //This adds waypoint names to the list of tab completion options if the file exists, and if it doesn't, skip
            if (waypointFile.exists())
            {
                BufferedReader reader = new BufferedReader(new FileReader(waypointFile));
                String line;

                while ((line = reader.readLine()) != null)
                {
                    if (arguments.length == 2 && !arguments[0].equals("save"))
                        completionOptions.add(line.split(":")[0].replace("$COLON$", ":"));
                }

                reader.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return completionOptions;
    }

    String getColorForDimension(int dimension)
    {
        return dimension == -1 ? EnumChatFormatting.RED.toString() : dimension == 0 ? EnumChatFormatting.DARK_GREEN.toString() : EnumChatFormatting.YELLOW.toString();
    }
}
