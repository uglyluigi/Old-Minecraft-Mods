package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.common.ConfigurationManager;
import spacewalken.util.MethodProxy;
import spacewalken.util.Randomizer;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/2/13
 */
public class ItemXailiteAxe extends ItemXailiteTool
{
    private boolean timberModeActive = true;

    public ItemXailiteAxe(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id, new Block[] {Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern}, toolMaterial, textureFileName);
        this.toolMaterial = toolMaterial;
    }

    public float getStrVsBlock(ItemStack tool, Block brokenBlock)
    {
        return Item.axeDiamond.getStrVsBlock(tool, brokenBlock);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, int blockId, int x, int y, int z, EntityLivingBase user)
    {
        if (timberModeActive)
        {
            int offset;
            for (offset = 0; world.getBlockId(x, y + offset, z) == Block.wood.blockID; offset++)
            {
                int compY = y + offset;
                MethodProxy.spawnParticle("happyVillager", Randomizer.getRandomNumberInRange(8, 1), x, compY, z, false);
                //Get the metadata of the wood that was broken. This is before the wood is destroyed in order to prevent it always reading 0.
                final int metadata = world.getBlockMetadata(x, compY, z);
                world.setBlock(x, compY, z, 0);
                //Spawn the items in the world ready to be picked up.
                world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(Block.wood, 1, metadata)));
            }
            tool.damageItem((offset > 0) ? offset : 1, user);
        }
        return false;
    }

    boolean flag = true;
    @Override
    public ItemStack onItemRightClick(ItemStack axe, World world, EntityPlayer player)
    {
        if (flag && ConfigurationManager.xailiteCfg.get("general", "timber-mode-indicator", true).getBoolean(true))
        {
            timberModeActive = !timberModeActive;
            if (timberModeActive) player.sendChatToPlayer(MethodProxy.sToCMC("Timber mode \247aactivated."));
            else player.sendChatToPlayer(MethodProxy.sToCMC("Timber mode \247cdeactivated."));
            flag = false;
        } else {
            flag = true;
        }
        return axe;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        if (ModLoader.getMinecraftInstance().theWorld.getBlockId(x, y, z) == Block.leaves.blockID)
        {
            Block.leaves.blockHardness = 0.01F;
        }
        return false;
    }
}
