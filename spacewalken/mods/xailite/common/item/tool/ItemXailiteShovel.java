package spacewalken.mods.xailite.common.item.tool;

import spacewalken.util.Randomizer;
import spacewalken.util.MethodProxy;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/3/13
 */
public class ItemXailiteShovel extends ItemXailiteTool
{
    public ItemXailiteShovel(int id, String textureFileName, EnumToolMaterial toolMaterial)
    {
        super(id, new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium}, toolMaterial, textureFileName);
        this.toolMaterial = toolMaterial;
        this.damageVsEntity = 1.0F;
    }

    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.snow || par1Block == Block.blockSnow;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        World worldInstance = ModLoader.getMinecraftInstance().theWorld;

        int blockID = worldInstance.getBlockId(x, y, z);
        int blockBelow = worldInstance.getBlockId(x, y - 1, z);

        if (blockID == Block.snow.blockID && (blockBelow == Block.dirt.blockID || blockBelow == Block.grass.blockID || blockBelow == Block.sand.blockID))
        {
            Block.snow.blockHardness = 1.05F;
        }
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, int blockId, int x, int y, int z, EntityLivingBase user)
    {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase hitEntity, EntityLivingBase user)
    {
        //When you see a chicken, you gotta hit it with a...
        if (hitEntity instanceof EntityChicken && MethodProxy.getGameSettings().particleSetting <= 1)
        {
            MethodProxy.spawnParticles(new String[] {"largesmoke", "hugeexplosion"}, Randomizer.getRandomNumberInRange(15, 5), hitEntity.posX, hitEntity.posY, hitEntity.posZ, true);
            MethodProxy.playSoundAtEntity(hitEntity, "random.explode", 1.0F, 1.0F);
            hitEntity.setDead();
        }
        //...shovel
        return false;
    }
}
