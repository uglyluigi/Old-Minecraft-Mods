package spacewalken.mods.xailite.common.item;

import spacewalken.mods.xailite.Xailite;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class ItemXailiteArmor extends ItemArmor
{

    private final int armorType;

    private final EnumArmorMaterial material;

    public ItemXailiteArmor(int id, EnumArmorMaterial toolMaterial, int slot, int armorType)
    {
        super(id, toolMaterial, slot, armorType);
        this.material = toolMaterial;
        this.armorType = armorType;
        this.setMaxDamage(toolMaterial.getDurability(armorType));
        this.maxStackSize = 1;
        this.setCreativeTab(Xailite.xailiteTab);
        this.setUnlocalizedName("XailiteArmor|" + id);
    }


    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    public EnumArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        String iconName;

        switch (armorType)
        {
            case 0:
                iconName = this == Xailite.xailiteHelmet ? "XailiteHelmet" : "XailiteHelmetRefined";
                break;
            case 1:
                iconName = this == Xailite.xailiteChestplate ? "XailiteChestplate" : "XailiteChestplateRefined";
                break;
            case 2:
                iconName = this == Xailite.xailiteLeggings ? "XailiteLeggings" : "XailiteLeggingsRefined";
                break;
            case 3:
                iconName = this == Xailite.xailiteBoots ? "XailiteBoots" : "XailiteBootsRefined";
                break;
            default:
                iconName = "empty";
                break;
        }

        this.itemIcon = iconRegister.registerIcon("xailite:" + iconName);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, int layer)
    {
        return (ModLoader.getMinecraftInstance().thePlayer.isPotionActive(Potion.invisibility) ? "xailite:textures/armor/empty.png" : (itemstack.itemID == Xailite.xailiteHelmet.itemID || itemstack.itemID == Xailite.xailiteChestplate.itemID || itemstack.itemID == Xailite.xailiteBoots.itemID) ? "xailite:textures/armor/XailiteArmor_1.png" : (itemstack.itemID == Xailite.xailiteLeggings.itemID) ? "xailite:textures/armor/XailiteArmor_2.png" : (itemstack.itemID == Xailite.refinedXailiteHelmet.itemID || itemstack.itemID == Xailite.refinedXailiteChestplate.itemID || itemstack.itemID == Xailite.refinedXailiteBoots.itemID) ? "xailite:textures/armor/XailiteArmor_3.png" : (itemstack.itemID == Xailite.refinedXailiteLeggings.itemID) ? "xailite:textures/armor/XailiteArmor_4.png" : null);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 12000;
    }

    @Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
        boolean[] hasArmorInSlot = new boolean[4];
        int[] armorItemInSlot = new int[4];

        /*
         * Boots = 0
         * Pants = 1
         * Chestplate = 2
         * Helmet = 3
         */

        for (int i = 0; i < armorItemInSlot.length; i++)
        {
            ItemStack item = player.getCurrentItemOrArmor(i + 1);

            if (item != null)
            {
                armorItemInSlot[i] = item.getItem().itemID;
                hasArmorInSlot[i] = true;
            } else {
                armorItemInSlot[i] = Integer.MIN_VALUE;
                hasArmorInSlot[i] = false;
            }
        }

        if (hasArmorInSlot[0])
        {
            if (armorItemInSlot[0] == Xailite.xailiteBoots.itemID || armorItemInSlot[0] == Xailite.refinedXailiteBoots.itemID)
            {
                player.fallDistance = 0;

                if (armorItemInSlot[0] == Xailite.refinedXailiteBoots.itemID && player.isSprinting())
                {
                    player.capabilities.setPlayerWalkSpeed(0.256F);
                } else {
                    if (player.capabilities.getWalkSpeed() > 0.1F) player.capabilities.setPlayerWalkSpeed(0.1F);
                }
            }
        }
    }
}
