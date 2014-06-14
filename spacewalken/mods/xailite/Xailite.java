package spacewalken.mods.xailite;

import spacewalken.mods.xailite.common.ConfigurationManager;
import spacewalken.mods.xailite.common.CreativeTabXailite;
import spacewalken.mods.xailite.common.LocalizationHandler;
import spacewalken.mods.xailite.common.TickHandler;
import spacewalken.mods.xailite.common.XailiteOreGenerator;
import spacewalken.mods.xailite.common.block.ItemXailiteBlock;
import spacewalken.mods.xailite.common.block.XailiteBlock;
import spacewalken.mods.xailite.common.item.ItemXailite;
import spacewalken.mods.xailite.common.item.ItemXailiteArmor;
import spacewalken.mods.xailite.common.item.ItemXailiteDagger;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteAxe;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteHoe;
import spacewalken.mods.xailite.common.item.tool.ItemXailitePaxel;
import spacewalken.mods.xailite.common.item.tool.ItemXailitePickaxe;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteShovel;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteSword;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteTool;
import spacewalken.util.IOHelper;
import spacewalken.util.MethodProxy;

import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.block.Block;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 4/25/13
 * Time: 4:54 PM
 */
@Mod(name = "Xailite", modid = "xailite", version = "0.9.9", acceptedMinecraftVersions = "1.6.2", useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class Xailite
{
    private static final EnumToolMaterial diamond = EnumToolMaterial.EMERALD;
    public static final EnumToolMaterial XAILITE_TOOL =
            EnumHelper.addToolMaterial("XAILITE",
                    diamond.getHarvestLevel(),
                    diamond.getMaxUses() * 2,
                    diamond.getEfficiencyOnProperMaterial() * 2,
                    diamond.getDamageVsEntity() * 2,
                    EnumToolMaterial.GOLD.getEnchantability());

    public static final EnumToolMaterial XAILITE_TOOL_REFINED =
            EnumHelper.addToolMaterial("REFINED_XAILITE",
                    diamond.getHarvestLevel(),
                    diamond.getMaxUses() * 3,
                    diamond.getEfficiencyOnProperMaterial() * 3,
                    diamond.getDamageVsEntity() * 3,
                    200);

    private static final EnumArmorMaterial XAILITE_ARMOR =
            MethodProxy.registerArmorMaterial("XAILITE_ARMOR", 66, EnumArmorMaterial.GOLD.getEnchantability(), 5, 10, 8, 6);


    private static final EnumArmorMaterial XAILITE_ARMOR_REFINED =
            MethodProxy.registerArmorMaterial("REFINED_XAILITE_ARMOR", 76, EnumArmorMaterial.GOLD.getEnchantability(), 7, 11, 9, 8);

    public static final CreativeTabs xailiteTab = CreativeTabXailite.instance().setNoScrollbar();

    //The
    private static int baseId = 9000;

    //Ingots
    public static final Item xailiteIngot = new ItemXailite(baseId++, "XailiteIngot").setCreativeTab(xailiteTab);
    public static final ItemXailite refinedXailiteIngot = new ItemXailite(baseId++, "XailiteIngotRefined");

    public static final Item ironStick = new ItemXailite(baseId++, "IronStick");
    public static final Item diamondStick = new ItemXailite(baseId++, "DiamondStick");

    //Tools
    public static final ItemXailiteTool xailitePick = new ItemXailitePickaxe(baseId++, "XailitePick", XAILITE_TOOL);
    public static final ItemXailiteTool xailiteAxe = new ItemXailiteAxe(baseId++, "XailiteAxe", XAILITE_TOOL);
    public static final ItemSword xailiteSword = new ItemXailiteSword(baseId++, "XailiteSword", XAILITE_TOOL);
    public static final ItemXailiteTool xailiteShovel = new ItemXailiteShovel(baseId++, "XailiteShovel", XAILITE_TOOL);
    public static final ItemXailiteTool xailitePaxel = new ItemXailitePaxel(baseId++, "XailitePaxel", XAILITE_TOOL);
    public static final Item xailiteHoe = new ItemXailiteHoe(baseId++, "XailiteHoe", XAILITE_TOOL);
    public static final ItemSword xailiteSwordDiamond = new ItemXailiteSword(baseId++, "XailiteSwordSubDiamond", XAILITE_TOOL);
    public static final ItemSword xailiteSwordEmerald = new ItemXailiteSword(baseId++, "XailiteSwordSubEmerald", XAILITE_TOOL);

    public static final Item xailiteDagger = new ItemXailiteDagger(baseId++, "XailiteDagger").setMaxDamage(100);
    public static final Item goldXailiteDagger = new ItemXailiteDagger(baseId++, "GoldenXailiteDagger").setMaxDamage(20);
    public static final Item diamondXailiteDagger = new ItemXailiteDagger(baseId++, "DiamondXailiteDagger").setMaxDamage(200);

    public static final ItemXailiteTool refinedXailitePick = new ItemXailitePickaxe(baseId++, "XailitePickRefined", XAILITE_TOOL_REFINED);
    public static final ItemXailiteTool refinedXailiteAxe = new ItemXailiteAxe(baseId++, "XailiteAxeRefined", XAILITE_TOOL_REFINED);
    public static final ItemXailiteTool refinedXailiteShovel = new ItemXailiteShovel(baseId++, "XailiteShovelRefined", XAILITE_TOOL_REFINED);
    public static final Item refinedXailiteHoe = new ItemXailiteHoe(baseId++, "XailiteHoeRefined", XAILITE_TOOL_REFINED);
    public static final ItemSword xailiteSwordBlade = new ItemXailiteSword(baseId++, "XailiteBlade", XAILITE_TOOL);
    public static final ItemXailiteTool refinedXailitePaxel = new ItemXailitePaxel(baseId++, "RefinedXailitePaxel", XAILITE_TOOL_REFINED);

    //THE INFINITY PAXEL and friends
    private static final int POS_INFINITY = (int) Double.POSITIVE_INFINITY;
    public static final Item infinityPaxelHead = new ItemXailite(baseId++, "InfinityPaxelHead");
    public static final ItemXailitePaxel infinityPaxel = new ItemXailitePaxel(baseId++, "InfinityPaxel", EnumHelper.addToolMaterial("Infinity", 3, POS_INFINITY, POS_INFINITY, POS_INFINITY, POS_INFINITY));

    //Armor
    public static final Item xailiteHelmet = new ItemXailiteArmor(baseId++, XAILITE_ARMOR, 0, 0);
    public static final Item xailiteChestplate = new ItemXailiteArmor(baseId++, XAILITE_ARMOR, 1, 1);
    public static final Item xailiteLeggings = new ItemXailiteArmor(baseId++, XAILITE_ARMOR, 2, 2);
    public static final Item xailiteBoots = new ItemXailiteArmor(baseId++, XAILITE_ARMOR, 3, 3);

    //Refined armor
    public static final Item refinedXailiteHelmet = new ItemXailiteArmor(baseId++, XAILITE_ARMOR_REFINED, 0, 0);
    public static final Item refinedXailiteChestplate = new ItemXailiteArmor(baseId++, XAILITE_ARMOR_REFINED, 1, 1);
    public static final Item refinedXailiteLeggings = new ItemXailiteArmor(baseId++, XAILITE_ARMOR_REFINED, 2, 2);
    public static final Item refinedXailiteBoots = new ItemXailiteArmor(baseId++, XAILITE_ARMOR_REFINED, 3, 3);

    public static final Block xailiteBlock = new XailiteBlock(161);

    private boolean incompatibleVersions = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        String mcVersion = new CallableMinecraftVersion(null).minecraftVersion(), minVersion = "1.6.2";

        ConfigurationManager.init();

        if (!mcVersion.startsWith(minVersion))
        {
            IOHelper.print(String.format("Version error: %s expected, found %s\nStopping initialization", mcVersion, minVersion));
            incompatibleVersions = true;
            return;
        }

        IOHelper.print("Beginning pre-initialization");
        /*Mod metadata*/
        ModMetadata mm = event.getModMetadata();

        mm.autogenerated = false;
        mm.authorList = Arrays.asList("SpaceWalken", "Drumz");
        mm.credits = "I did it.";
        mm.description = "Description coming in a µbit :o";
        mm.version = CallableVersion.getVersion();


        ChestGenHooks.getInfo("PYRAMID_JUNGLE_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(xailiteIngot), 1, 2, 1));
        ChestGenHooks.getInfo("PYRAMID_DESERT_CHEST").addItem(new WeightedRandomChestContent(new ItemStack(xailiteIngot), 1, 2, 1));

        IOHelper.print("Starting ore generator...");
        OreDictionary.registerOre(120, new ItemStack(xailiteBlock, 1, 0));
        OreDictionary.registerOre(121, new ItemStack(xailiteBlock, 1, 3));

        GameRegistry.registerWorldGenerator(new XailiteOreGenerator());
        GameRegistry.registerBlock(xailiteBlock, ItemXailiteBlock.class, "XailiteBlock");

        MinecraftForge.setBlockHarvestLevel(xailiteBlock, "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(xailiteBlock, 3, "pickaxe", 4);

        IOHelper.print("Activating tick listener...");
        TickRegistry.registerTickHandler(new TickHandler(), Side.SERVER);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (incompatibleVersions) return;

        MinecraftForge.setToolClass(xailitePick, "pickaxe", 3);
        MinecraftForge.setToolClass(xailitePick, "pickaxe", 4);
        MinecraftForge.setToolClass(xailiteAxe, "axe", 3);
        MinecraftForge.setToolClass(xailiteShovel, "shovel", 3);
        MinecraftForge.setToolClass(refinedXailitePick, "pickaxe", 3);
        MinecraftForge.setToolClass(refinedXailiteAxe, "axe", 3);
        MinecraftForge.setToolClass(refinedXailiteShovel, "shovel", 3);

        IOHelper.print("Initializing in-game recipes...");
        this.addRecipes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (incompatibleVersions) return;

        IOHelper.print("Setting language...");
        IOHelper.print("There\'s no guarantee that these translations are accurate. Keep that in mind!");
        (new LocalizationHandler()).initialize();

        if (event.getModState() == LoaderState.ModState.AVAILABLE || event.getModState() == LoaderState.ModState.POSTINITIALIZED)
        {
            IOHelper.print("Initialization successful! " + MethodProxy.selectStringAtRandom("Yay!", "Woo!", "It\'s the beeest daaaay eveeeerrrr!", "That\'s a good thing!", "No flames!", "Is best!", "Let\'s go bowling cousin!", "You followed the train CJ!", "!", "Is not die of malnourish!"));
        } else
        {
            IOHelper.print("Initialization failed! " + MethodProxy.selectStringAtRandom("Sorry :(", "I\'ll do better next time!", "Give me a chance! :(", "Awwwww....", "Well, there are always other mods...", "Be sure to tell the developer :|", "No more beaming."));
            return;
        }

        this.loadAddons();
    }

    private void loadAddons()
    {
        //TODO: write
    }

    /* This is just an inner method that adds all of the recipes because it would look ugly in the init method. */
    private void addRecipes()
    {
        Random rand = new Random();

        FurnaceRecipes.smelting().addSmelting(xailiteBlock.blockID, 0, new ItemStack(xailiteIngot, 1), rand.nextInt(100) + 5);
        FurnaceRecipes.smelting().addSmelting(xailiteBlock.blockID, 3, new ItemStack(refinedXailiteIngot, 1), rand.nextInt(200) + 50);

        GameRegistry.addShapelessRecipe(new ItemStack(xailiteIngot, 9), new ItemStack(xailiteBlock, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(refinedXailiteIngot, 9), new ItemStack(xailiteBlock, 1, 2));

        GameRegistry.addRecipe(new ItemStack(xailiteBlock, 1, 1), "***", "***", "***", '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailiteBlock, 1, 2), "***", "***", "***", '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(ironStick, 4), "x", "x", 'x', Item.ingotIron);
        GameRegistry.addRecipe(new ItemStack(diamondStick, 4), "x", "x", 'x', Item.diamond);
        GameRegistry.addRecipe(new ItemStack(xailitePick, 1), "***", " ^ ", " ^ ", '*', xailiteIngot, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteAxe, 1), "** ", "*^ ", " ^ ", '*', xailiteIngot, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteShovel, 1), "*", "^", "^", '*', xailiteIngot, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteHoe, 1), " **", " ^ ", " ^ ", '*', xailiteIngot, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteSwordDiamond, 1), " * ", "*.*", " ^ ", '*', xailiteIngot, '.', Item.diamond, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteSwordEmerald, 1), " * ", "*.*", " ^ ", '*', xailiteIngot, '.', Item.emerald, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(xailiteSwordBlade, 1), " **", " **", "*^*", '*', xailiteIngot, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteAxe, 1), "** ", "*^* ", " ^ ", '*', xailiteIngot, '^', diamondStick);
        GameRegistry.addRecipe(new ItemStack(refinedXailitePick, 1), "***", " ^ ", " ^ ", '*', xailiteIngot, '^', diamondStick);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteShovel, 1), "*", "^", "^", '*', xailiteIngot, '^', diamondStick);
        GameRegistry.addRecipe(new ItemStack(xailiteHelmet, 1), "***", "* *", '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailiteChestplate, 1), "* *", "***", "***", '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailiteLeggings, 1), "***", "* *", "* *", '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailiteBoots, 1), "* *", "* *", '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteHelmet, 1), "***", "* *", '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteChestplate, 1), "* *", "***", "***", '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteLeggings, 1), "***", "* *", "* *", '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(refinedXailiteBoots, 1), "* *", "* *", '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailiteDagger, 1), "   ", " * ", "^  " , '^', ironStick, '*', xailiteIngot);
        GameRegistry.addRecipe(new ItemStack(diamondXailiteDagger, 1), "   ", " * ", "^  ", '^', diamondStick, '*', refinedXailiteIngot);
        GameRegistry.addRecipe(new ItemStack(xailitePaxel, 1), "!@#", " ^ ", " ^ ", '!', xailiteAxe, '@', xailiteShovel, '#', xailitePick, '^', ironStick);
        GameRegistry.addRecipe(new ItemStack(infinityPaxelHead, 1), "+*+", "*+*", "+*+", '*', Block.blockDiamond, '+', new ItemStack(xailiteBlock, 1, 1));
        GameRegistry.addRecipe(new ItemStack(infinityPaxel, 1), "*", "^", "^", '*', infinityPaxelHead, '^', diamondStick);
        GameRegistry.addRecipe(new ItemStack(goldXailiteDagger, 1), " + ", " * ", "^  " , '^', ironStick, '*', xailiteIngot, '+', Item.ingotGold);
        GameRegistry.addRecipe(new ItemStack(goldXailiteDagger, 1), "***", "*+*", "***", '*', Item.goldNugget, '+', new ItemStack(xailiteDagger, 1, 0));
    }
}
