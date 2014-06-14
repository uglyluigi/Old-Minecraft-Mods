package spacewalken.mods.commandsplus;

import spacewalken.mods.commandsplus.common.CommandCalc;
import spacewalken.mods.commandsplus.common.CommandCmdPlusHelp;
import spacewalken.mods.commandsplus.common.CommandPing;
import spacewalken.mods.commandsplus.common.CommandSearchLog;
import spacewalken.mods.commandsplus.common.CommandSendAs;
import spacewalken.mods.commandsplus.common.CommandStats;
import spacewalken.mods.commandsplus.common.CommandWaypoint;
import spacewalken.util.IOHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import net.minecraft.command.ServerCommandManager;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 6/21/13
 * Time: 9:07 PM
 */
@Mod(modid = "commandsplus", name = "Commands++", version = "1.0")
public class Initializer
{
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        IOHelper.print("Starting Commands Plus...");
        ServerCommandManager manager = (ServerCommandManager) event.getServer().getCommandManager();
        manager.registerCommand(new CommandStats());
        manager.registerCommand(new CommandSearchLog());
        manager.registerCommand(new CommandWaypoint());
        manager.registerCommand(new CommandCalc());
        manager.registerCommand(new CommandSendAs());
        manager.registerCommand(new CommandCmdPlusHelp());
        manager.registerCommand(new CommandPing());
    }
}
