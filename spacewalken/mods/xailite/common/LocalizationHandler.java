package spacewalken.mods.xailite.common;

import spacewalken.util.IOHelper;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import static spacewalken.mods.xailite.Xailite.diamondStick;
import static spacewalken.mods.xailite.Xailite.diamondXailiteDagger;
import static spacewalken.mods.xailite.Xailite.goldXailiteDagger;
import static spacewalken.mods.xailite.Xailite.infinityPaxel;
import static spacewalken.mods.xailite.Xailite.infinityPaxelHead;
import static spacewalken.mods.xailite.Xailite.ironStick;
import static spacewalken.mods.xailite.Xailite.refinedXailiteAxe;
import static spacewalken.mods.xailite.Xailite.refinedXailiteBoots;
import static spacewalken.mods.xailite.Xailite.refinedXailiteChestplate;
import static spacewalken.mods.xailite.Xailite.refinedXailiteHelmet;
import static spacewalken.mods.xailite.Xailite.refinedXailiteHoe;
import static spacewalken.mods.xailite.Xailite.refinedXailiteIngot;
import static spacewalken.mods.xailite.Xailite.refinedXailiteLeggings;
import static spacewalken.mods.xailite.Xailite.refinedXailitePaxel;
import static spacewalken.mods.xailite.Xailite.refinedXailitePick;
import static spacewalken.mods.xailite.Xailite.refinedXailiteShovel;
import static spacewalken.mods.xailite.Xailite.xailiteAxe;
import static spacewalken.mods.xailite.Xailite.xailiteBlock;
import static spacewalken.mods.xailite.Xailite.xailiteBoots;
import static spacewalken.mods.xailite.Xailite.xailiteChestplate;
import static spacewalken.mods.xailite.Xailite.xailiteDagger;
import static spacewalken.mods.xailite.Xailite.xailiteHelmet;
import static spacewalken.mods.xailite.Xailite.xailiteHoe;
import static spacewalken.mods.xailite.Xailite.xailiteIngot;
import static spacewalken.mods.xailite.Xailite.xailiteLeggings;
import static spacewalken.mods.xailite.Xailite.xailitePaxel;
import static spacewalken.mods.xailite.Xailite.xailitePick;
import static spacewalken.mods.xailite.Xailite.xailiteShovel;
import static spacewalken.mods.xailite.Xailite.xailiteSword;
import static spacewalken.mods.xailite.Xailite.xailiteSwordBlade;
import static spacewalken.mods.xailite.Xailite.xailiteSwordDiamond;
import static spacewalken.mods.xailite.Xailite.xailiteSwordEmerald;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/21/13
 */
public class LocalizationHandler
{
    private String[] localeArray;
    private boolean unsupportedLang = false;

    public LocalizationHandler()
    {
    }

    public void initialize()
    {
        final String locale = "en_US";

        IOHelper.print((unsupportedLang ? "Unsupported language detected, defaulting to English" : "Detected language: " + (locale.contains("en_") ? "English" : (locale.contains("fr_") ? "French" : (locale.contains("es_") ? "Spanish" : "missingno")))));

        if (locale.contains("en_") || unsupportedLang)
        {
            if (locale.equals("en_PT"))
            {
                IOHelper.print("Setting locale to PIRATE English!");
                localeArray = new String[]{"Pickaxe o\' Xailite", "Xailite Bullion", "Ir\'n Bludgeon", "Xailite Hatchet", "Xailite Cutlass", "Xailite Spaaade", "Xailite Paxel", "Xailite Farmin\' Stick",
                        "Xailite Skull Hardener", "Xailite Garb", "Xailite Greaves", "Xailite Boots", "Xailite Orrre", "Chunk o\' Xailite", "Bejeweled Xailite Cutlass", "Jaded Xailite Cutlass", "Xailite Cleaver",
                        "Pure\'d Xailite Ingot", "Pure\'d Xailite Pickaxe", "Pure\'d Xailite Hatchet", "Pure\'d Xailite Spaaade", "Pure\'d Xailite Farmin\' Stick", "Pure\'d Xailite Skull Hardener",
                        "Pure\'d Xailite Garb", "Pure\'d Xailite Greaves", "Pure\'d Xailite Boots", "Paxel o\' the rich", "Bejeweled Bludgeon", "Pure\'d Xailite Block", "Stressed Xailite Ore", "Xailite Shank", "Gilded Xailite Shank",
                        "Pure\'d Xailite Paxel", "Paxel o\' the rich", "Gilded Dagger"};
            } else {
                IOHelper.print("Setting locale to English");
                localeArray = new String[]{"Xailite Pickaxe", "Xailite Ingot", "Iron Stick", "Xailite Axe", "Xailite Sword", "Xailite Shovel", "Xailite Paxel", "Xailite Hoe", "Xailite Helmet",
                        "Xailite Chestplate", "Xailite Leggings", "Xailite Boots", "Xailite Ore", "Xailite Block", "Xailite Diamond Sword", "Xailite Emerald Sword", "Xailite Blade",
                        "Refined Xailite Ingot", "Refined Xailite Pickaxe", "Refined Xailite Axe", "Refined Xailite Shovel", "Refined Xailite Hoe", "Refined Xailite Helmet", "Refined Xailite Chestplate",
                        "Refined Xailite Leggings", "Refined Xailite Boots", "Infinity Paxel", "Diamond Stick", "Refined Xailite Block", "Tempered Xailite Ore", "Xailite Dagger", "Diamond Xailite Dagger", "Refined Xailite Paxel",
                        "Infinity Paxel Head", "Golden Xailite Dagger"};
            }
            unsupportedLang = false;
        }

        try
        {
            String purple = "\2475";
            String blue = "\2479";
            IOHelper.print("Adding colors to item names...");
            LanguageRegistry.addName(xailitePick, blue + localeArray[0]);
            LanguageRegistry.addName(xailiteIngot, localeArray[1]);
            LanguageRegistry.addName(ironStick, "\2477" + localeArray[2]);
            LanguageRegistry.addName(xailiteAxe, blue + localeArray[3]);
            LanguageRegistry.addName(xailiteSword, blue + localeArray[4]);
            LanguageRegistry.addName(xailiteShovel, blue + localeArray[5]);
            LanguageRegistry.addName(xailitePaxel, blue + localeArray[6]);
            LanguageRegistry.addName(xailiteHoe, blue + localeArray[7]);
            LanguageRegistry.addName(xailiteHelmet, blue + localeArray[8]);
            LanguageRegistry.addName(xailiteChestplate, blue + localeArray[9]);
            LanguageRegistry.addName(xailiteLeggings, blue + localeArray[10]);
            LanguageRegistry.addName(xailiteBoots, blue + localeArray[11]);
            LanguageRegistry.addName(new ItemStack(xailiteBlock, 1, 0), blue + localeArray[12]);
            LanguageRegistry.addName(new ItemStack(xailiteBlock, 1, 1), blue + localeArray[13]);
            LanguageRegistry.addName(xailiteSwordDiamond, "\2473" + localeArray[14]);
            LanguageRegistry.addName(xailiteSwordEmerald, "\2472" + localeArray[15]);
            LanguageRegistry.addName(xailiteSwordBlade, blue + localeArray[16]);
            LanguageRegistry.addName(refinedXailiteIngot, purple + localeArray[17]);
            LanguageRegistry.addName(refinedXailitePick, purple + localeArray[18]);
            LanguageRegistry.addName(refinedXailiteAxe, purple + localeArray[19]);
            LanguageRegistry.addName(refinedXailiteShovel, purple + localeArray[20]);
            LanguageRegistry.addName(refinedXailiteHoe, purple + localeArray[21]);
            LanguageRegistry.addName(refinedXailiteHelmet, purple + localeArray[22]);
            LanguageRegistry.addName(refinedXailiteChestplate, purple + localeArray[23]);
            LanguageRegistry.addName(refinedXailiteLeggings, purple + localeArray[24]);
            LanguageRegistry.addName(refinedXailiteBoots, purple + localeArray[25]);
            LanguageRegistry.addName(infinityPaxel, localeArray[26]);
            LanguageRegistry.addName(diamondStick, "\247b" + localeArray[27]);
            LanguageRegistry.addName(new ItemStack(xailiteBlock, 1, 2), blue + localeArray[28]);
            LanguageRegistry.addName(new ItemStack(xailiteBlock, 1, 3), blue + localeArray[29]);
            LanguageRegistry.addName(xailiteDagger, blue + localeArray[30]);
            LanguageRegistry.addName(diamondXailiteDagger, "\247b" + localeArray[31]);
            LanguageRegistry.addName(refinedXailitePaxel, purple + localeArray[32]);
            LanguageRegistry.addName(infinityPaxelHead, purple + localeArray[33]);
            LanguageRegistry.addName(goldXailiteDagger, EnumChatFormatting.YELLOW.toString() + localeArray[34]);
        } catch (ArrayIndexOutOfBoundsException e)
        {
            IOHelper.print("Missing name in " + locale + ", index(s) " + Integer.parseInt(e.getMessage().split(":")[0]) + " and up. This is a mistake on the dev\'s part, some names will appear blank ingame. This will be fixed shortly. Sorry!");
        }
    }
}
