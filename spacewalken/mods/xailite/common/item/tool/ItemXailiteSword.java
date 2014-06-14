package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.Xailite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/2/13
 */
public class ItemXailiteSword extends ItemSword
{
    private final EnumToolMaterial toolMaterial;
    private final String textureFileName;

    public ItemXailiteSword(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id, toolMaterial);
        this.toolMaterial = toolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.setCreativeTab(Xailite.xailiteTab);
        this.textureFileName = textureFileName;
        this.setUnlocalizedName("XailiteSword|" + id);
    }

    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block.blockID != Block.web.blockID)
        {
            Material material = par2Block.blockMaterial;
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.pumpkin ? 1.0F : 1.5F;
        } else
        {
            return 15.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
    }

    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("xailite:" + textureFileName);
    }

    public void onCreated(ItemStack sword, World world, EntityPlayer player)
    {
        if (sword.getItem() == Xailite.xailiteSwordDiamond)
            sword.addEnchantment(Enchantment.sharpness, new Random().nextInt(5) + 1);
        if (sword.getItem() == Xailite.xailiteSwordEmerald)
            sword.addEnchantment(Enchantment.looting, 4);
    }

    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 12000;
    }
}
