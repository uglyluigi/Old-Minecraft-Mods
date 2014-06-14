package spacewalken.mods.commandsplus.common;

import spacewalken.util.IOHelper;
import spacewalken.util.MethodProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import static spacewalken.util.MethodProxy.sToCMC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 6/21/13
 * Time: 9:11 PM
 */
public class CommandStats extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "stats";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return "/stats [<username>:hide:unhide]";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args)
    {
        File hiddenPlayersFile = new File("hidden_players.txt");
        String line;

        if (args.length == 1)
        {
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(hiddenPlayersFile, true));
                BufferedReader reader = new BufferedReader(new FileReader(hiddenPlayersFile));

                if (!hiddenPlayersFile.exists() && hiddenPlayersFile.createNewFile())
                    IOHelper.print("Created hidden players file");

                if (args[0].equals("hide"))
                {
                    boolean isHidden = false;

                    while ((line = reader.readLine()) != null)
                    {
                        if (line.equals(commandSender.getCommandSenderName()))
                        {
                            isHidden = true;
                            break;
                        }
                    }

                    if (!isHidden)
                    {
                        writer.write(commandSender.getCommandSenderName());
                        commandSender.sendChatToPlayer(sToCMC("§aSuccessfully hidden."));
                    } else
                        commandSender.sendChatToPlayer(sToCMC("§cYou\'re already hidden."));

                } else if (args[0].equals("unhide"))
                {
                    File tempFile = new File("hidden_players.tmp");
                    BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(tempFile, true));
                    boolean found = false;

                    while ((line = reader.readLine()) != null)
                    {
                        if (!line.equals(commandSender.getCommandSenderName()))
                            tempFileWriter.write(line);
                        else
                        {
                            found = true;
                            break;
                        }
                    }

                    if (!tempFile.renameTo(hiddenPlayersFile))
                    {
                        MinecraftServer.getServer().logWarning("Unable to rename hidden players file");
                        commandSender.sendChatToPlayer(sToCMC("\247cAn internal error occurred while trying to process your request."));
                    }

                    if (found)
                        commandSender.sendChatToPlayer(sToCMC("§aSuccessfully unhidden."));
                    else
                        commandSender.sendChatToPlayer(sToCMC("§cYou\'re not hidden."));

                    tempFileWriter.close();
                } else
                {
                    EntityPlayer target = (args[0].equals("@p") ? (EntityPlayer) commandSender : func_82359_c(commandSender, args[0]));

                    if (!this.isPlayerHidden(target.username))
                    {
                        //In-lining this variable would've been messy and long.
                        String targetEffects = target.getActivePotionEffects().toString();

                        commandSender.sendChatToPlayer(sToCMC(String.format("§a-Statistics for §5%s§a-", target.getEntityName())));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aLocation: X: §5%s§a, §aY: §5%s§a, Z: §5%s§a", (int) target.posX, (int) target.posY, (int) target.posZ)));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aDimension: §%s", target.dimension == 0 ? "aOverworld" : target.dimension == -1 ? "cNether" : "6End")));
                        commandSender.sendChatToPlayer(sToCMC((String.format("§aBiome: §5%s", target.worldObj.getBiomeGenForCoords((int) target.posX, (int) target.posZ))).replace("net.minecraft.world.biome.BiomeGen", "").split("@")[0]));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aHealth: total: §5%s§a (%s/%s hearts)", MethodProxy.getEntityHealth(target), MethodProxy.getEntityHealth(target) / 2, MethodProxy.getMaxEntityHealth(target) / 2)));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aArmor: §5%s", target.getTotalArmorValue() > 0 ? target.getTotalArmorValue() : "none")));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aLevel: §5%s§a (%s total experience)", target.experienceLevel, target.experienceTotal)));
                        commandSender.sendChatToPlayer(sToCMC(String.format("§aPotion effects: §5%s", targetEffects.equals("[]") ? "none" : targetEffects.replace("potion.", "").replaceAll("[^a-zA-Z\\s]", "").replace("Duration", "").replace("x", ""))));
                        if (!target.equals(commandSender) && !commandSender.getCommandSenderName().equals("b0mbshellAntics"))
                            commandSender.sendChatToPlayer(sToCMC("§a" + commandSender.getCommandSenderName() + " just retrieved stats from you.\nUse \'§5/stats hide\'§a to prevent this from happening."));
                    } else
                    {
                        commandSender.sendChatToPlayer(sToCMC("§cStat retrieving error: " + target.getEntityName() + " is hidden."));
                    }
                }

                writer.close();
                reader.close();
            } catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        } else
        {
            throw new WrongUsageException(this.getCommandUsage(commandSender));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] arguments)
    {
        String[] usernames = MinecraftServer.getServer().getAllUsernames();
        ArrayList<String> completionOptions = new ArrayList<String>();

        //The two default completion options
        completionOptions.add("hide");
        completionOptions.add("unhide");

        //Iterate through all usernames currently available and add them to the options list as needed, but exclude hidden players
        for (String username : usernames)
            if (!this.isPlayerHidden(username))
                completionOptions.add(username);

        return completionOptions;
    }

    /**
     * Returns if the player is hidden.
     *
     * @param playerUsername The username of the player.
     *
     * @return if the player is hidden.
     */
    private boolean isPlayerHidden(String playerUsername)
    {
        boolean isHidden = false;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("hidden_players.txt")));
            String line;

            while ((line = reader.readLine()) != null)
            {
                if (line.equals(playerUsername))
                    isHidden = true;
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return isHidden;
    }
}
