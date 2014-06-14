package spacewalken.mods.xailite.common;

import spacewalken.mods.xailite.Xailite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;

/**
 * Created with Intellibro.
 * User: brennanforrest
 * Date: 6/8/13
 * Time: 10:57 PM
 */
public class CreativeTabXailite extends CreativeTabs
{

    private CreativeTabXailite()
    {
        super("Xailite");
    }

    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return Xailite.xailiteBlock.blockID;
    }

    public String getTranslatedTabLabel()
    {
        return "Xailite";
    }

    public static CreativeTabXailite instance()
    {
        return new CreativeTabXailite();
    }
}
