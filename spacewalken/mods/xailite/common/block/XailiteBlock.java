package spacewalken.mods.xailite.common.block;

import spacewalken.mods.xailite.Xailite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/19/13
 */
public class XailiteBlock extends Block
{
    public XailiteBlock(int id)
    {
        super(id, Material.rock);
        this.setCreativeTab(Xailite.xailiteTab);
        this.setHardness(5.5F);
    }

    @SideOnly(Side.CLIENT)
    private Icon[] blockTextures;

    public void registerIcons(IconRegister iconRegister)
    {
        blockTextures = new Icon[4]; //Change this according to how many blocks there are

        for (int i = 0; i < blockTextures.length; i++)
        {
            blockTextures[i] = iconRegister.registerIcon("xailite:" + (i == 0 ? "XailiteOre" : i == 1 ? "XailiteBlock" : i == 2 ? "RefinedXailiteBlock" : i == 3 ? "TemperedXailiteOre" : null));
        }
    }

    @Override
    public Icon getIcon(int id, int damage)
    {
        return blockTextures[damage];
    }

    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List subBlockList)
    {
        for (int i = 0; i < blockTextures.length; i++)
        {
            subBlockList.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    @Override
    public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return worldObj.getBlockMetadata(x, y, z) == 1;
    }
}
