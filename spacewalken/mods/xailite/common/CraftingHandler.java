package spacewalken.mods.xailite.common;

import spacewalken.mods.xailite.common.item.tool.ItemXailitePaxel;
import spacewalken.mods.xailite.common.item.tool.ItemXailiteTool;

import cpw.mods.fml.common.ICraftingHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/22/13, at 4:56 PM.
 */
class CraftingHandler implements ICraftingHandler
{
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
    {
        if (item.getItem() instanceof ItemXailitePaxel)
        {
            int damage = 0;
            for (int i = 0; i < 9; i++)
            {
                ItemStack itemStack = craftMatrix.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof ItemXailiteTool)
                {
                    damage += itemStack.getItemDamage();
                }
            }

            item.damageItem(damage, player);
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item)
    {

    }
}
