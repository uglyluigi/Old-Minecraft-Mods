package spacewalken.mods.xailite.common.addon;

import spacewalken.util.IOHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.item.Item;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/14/13, at 2:13 AM.
 */
public class ThermalExpansionAddon implements IAddon
{
    private static Item xailiteDust;

    public String getAddonName()
    {
        return "ThermalExpansion";
    }

    public void loadAddon()
    {
        if (Loader.isModLoaded("thermalexpansion"))
        {
            IOHelper.print("Thermal Expansion detected, loading addon");
            GameRegistry.registerItem(xailiteDust, "XailiteDust");
            LanguageRegistry.addName(xailiteDust, "Xailite Dust");

            //TODO: Add Thermal Expansion API and add xailite dust pulverizer recipe
        }
    }
}
