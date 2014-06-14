package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.Xailite;
import spacewalken.util.MethodProxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 5/2/13
 * Time: 7:55 PM
 */
public class ItemXailitePickaxe extends ItemXailiteTool
{

    private static final Block[] blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator, Xailite.xailiteBlock, Xailite.xailiteBlock};

    public ItemXailitePickaxe(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id, blocksEffectiveAgainst, toolMaterial, textureFileName);
        this.toolMaterial = toolMaterial;
    }

    public boolean canHarvestBlock(Block block)
    {
        return block == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (block != Block.blockDiamond && block != Block.oreDiamond ? (block != Block.oreEmerald && block != Block.blockEmerald ? (block != Block.blockGold && block != Block.oreGold ? (block != Block.blockIron && block != Block.oreIron ? (block != Block.blockLapis && block != Block.oreLapis ? (block != Block.oreRedstone && block != Block.oreRedstoneGlowing ? (block.blockMaterial == Material.rock || (block.blockMaterial == Material.iron || block.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public void onCreated(ItemStack theItem, World theWorld, EntityPlayer thePlayer)
    {
        int enchantmentFactor = 0;
        if (theWorld.provider instanceof WorldProviderHell) enchantmentFactor = 2;
        if (theWorld.provider instanceof WorldProviderEnd) enchantmentFactor = 3;
        theItem.addEnchantment(Enchantment.fortune, enchantmentFactor);
    }

    public float getStrVsBlock(ItemStack tool, Block brokenBlock)
    {
        return brokenBlock != null && (brokenBlock.blockMaterial == Material.iron || brokenBlock.blockMaterial == Material.anvil || brokenBlock.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(tool, brokenBlock);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        if (MethodProxy.getInstantiatedWorld().getBlockId(x, y, z) == Block.obsidian.blockID)
        {
            Block.obsidian.blockHardness = 0.5F;
        }
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, int blockId, int x, int y, int z, EntityLivingBase user)
    {
        if (!tool.isItemEnchanted())
        {
            if (blockId == Block.oreGold.blockID)
            {
                world.setBlock(x, y, z, 0);
                world.spawnEntityInWorld(new EntityItem(world, user.posX, user.posY, user.posZ, new ItemStack(Item.ingotGold, 1)));
                MethodProxy.spawnParticle("flame", new Random().nextInt(10) + 5, x, y, z, true);
                return false;
            }

            if (blockId == Block.oreIron.blockID)
            {
                world.setBlock(x, y, z, 0);
                world.spawnEntityInWorld(new EntityItem(world, user.posX, user.posY, user.posZ, new ItemStack(Item.ingotIron, 1)));
                MethodProxy.spawnParticle("flame", new Random().nextInt(10) + 5, x, y, z, true);
                return false;
            }
        }

        tool.damageItem(1, user);
        return false;
    }
}
