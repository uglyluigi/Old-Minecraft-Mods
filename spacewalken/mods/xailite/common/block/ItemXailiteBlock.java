package spacewalken.mods.xailite.common.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/10/13, at 11:25 AM.
 */
public class ItemXailiteBlock extends ItemBlock
{
    public ItemXailiteBlock(int id)
    {
        super(id);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name;

        switch (itemstack.getItemDamage())
        {
            case 0:
                name = "ore";
                break;

            case 1:
                name = "block";
                break;

            case 2:
                name = "block.refined";
                break;

            case 3:
                name = "ore.tempered";
                break;

            default:
                name = "err." + new Random().nextInt(itemstack.getItemDamage() * 5) + itemstack.getItemDamage();
                break;
        }
        return String.format("XailiteBlock.%s", name);
    }
}
