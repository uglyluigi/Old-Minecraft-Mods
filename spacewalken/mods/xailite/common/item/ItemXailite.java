package spacewalken.mods.xailite.common.item;

import spacewalken.mods.xailite.Xailite;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/2/13
 */
public class ItemXailite extends Item
{
    private final String textureFileName;

    public ItemXailite(int id, String textureFileName)
    {
        super(id);
        this.maxStackSize = 64;
        this.setUnlocalizedName("XailiteItem|" + id);
        this.setCreativeTab(Xailite.xailiteTab);
        this.textureFileName = textureFileName;
    }

    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("xailite:" + textureFileName);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 12000;
    }
}
