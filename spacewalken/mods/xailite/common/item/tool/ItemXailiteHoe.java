package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.Xailite;
import spacewalken.util.MethodProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.Random;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/3/13
 */
public class ItemXailiteHoe extends Item
{

    private final String textureFileName;

    public ItemXailiteHoe(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id);
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(Xailite.xailiteTab);
        this.textureFileName = textureFileName;
        this.setUnlocalizedName("XailiteTool|" + id);
    }

    public boolean onItemUse(ItemStack theItem, EntityPlayer thePlayer, World worldInstance, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (!thePlayer.canPlayerEdit(x, y, z, par7, theItem))
        {
            return false;
        } else
        {
            UseHoeEvent event = new UseHoeEvent(thePlayer, theItem, worldInstance, x, y, z);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return false;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                theItem.damageItem(1, thePlayer);
                return true;
            }

            int clickedBlock = worldInstance.getBlockId(x, y, z);
            int upperAdjacentBlock = worldInstance.getBlockId(x, y + 1, z);

            if (((par7 == 0 || upperAdjacentBlock != 0 || clickedBlock != Block.grass.blockID) && clickedBlock != Block.dirt.blockID) && clickedBlock != Block.crops.blockID && clickedBlock != Block.potato.blockID && clickedBlock != Block.carrot.blockID && clickedBlock != Block.sapling.blockID && clickedBlock != Block.netherStalk.blockID)
                return false;

            if (clickedBlock == Block.sapling.blockID)
            {
                System.out.println("Attempt to grow sapling using the hoe. This is not supported yet. :(");
                return true;
            } else if (clickedBlock == Block.crops.blockID || clickedBlock == Block.carrot.blockID || clickedBlock == Block.potato.blockID || clickedBlock == Block.netherStalk.blockID)
            {
                int l = worldInstance.getBlockMetadata(x, y, z);

                if (l == 0x7)
                    return false;

                l = 0x7;
                theItem.damageItem(1, thePlayer);

                if (worldInstance.getBlockId(x, y, z) == Block.netherStalk.blockID)
                    MethodProxy.spawnParticle("reddust", new Random().nextInt(10) + 5, x, y, z, false);
                else
                    MethodProxy.spawnParticle("happyVillager", new Random().nextInt(10) + 5, x, y, z, false);

                worldInstance.setBlockMetadataWithNotify(x, y, z, l, 2);
                return true;
            } else
            {
                Block block = Block.tilledField;
                worldInstance.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

                MethodProxy.spawnParticle("happyVillager", new Random().nextInt(15) + 7, x, y, z, false);

                if (worldInstance.isRemote)
                    return true;
                else
                {
                    theItem.damageItem(9, thePlayer);
                    return true;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("xailite:" + textureFileName);
    }
}
