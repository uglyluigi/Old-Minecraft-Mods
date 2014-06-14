package spacewalken.mods.xailite.common;

import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 10/29/13, at 6:01 PM.
 */
public class ConfigurationManager
{
    public static File cfgFile = new File("xailite.txt");
    public static Configuration xailiteCfg = new Configuration(cfgFile);

    public static void init()
    {
        xailiteCfg.get("general", "timber-mode-indicator", true);
    }
}
