package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.Xailite;
import spacewalken.util.MethodProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/3/13
 */
public class ItemXailitePaxel extends ItemXailiteTool
{
    private final String textureFileName;

    public ItemXailitePaxel(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id, Block.blocksList, toolMaterial, textureFileName);
        this.textureFileName = textureFileName;

        this.toolMaterial = toolMaterial;
        if (this == Xailite.infinityPaxel) this.setMaxDamage(10);
    }

    public boolean canHarvestBlock(Block block)
    {
        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("xailite:" + this.textureFileName);
    }

    public boolean onItemUse(ItemStack theItem, EntityPlayer thePlayer, World worldInstance, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, int blockId, int x, int y, int z, EntityLivingBase user)
    {
        return Xailite.xailiteAxe.onBlockDestroyed(tool, world, blockId, x, y, z, user);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase hitEntity, EntityLivingBase user)
    {
        itemStack.damageItem(2, user);
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack paxel, World world, EntityPlayer entityPlayer)
    {
        if (paxel.getItemDamage() > 0)
        {
            paxel.setItemDamage((int) Double.NEGATIVE_INFINITY);
            MethodProxy.spawnParticle("happyVillager", new Random().nextInt(10) + 5, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, true);
        }
        return paxel;
    }
}
