package spacewalken.mods.xailite.common.item.tool;

import spacewalken.mods.xailite.Xailite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: brennanforrest
 * Date: 5/2/13
 * Time: 7:44 PM
 */
public class ItemXailiteTool extends ItemTool
{
    private final Block[] blocksEffectiveAgainst;
    float efficiencyOnProperMaterial = 4.0F;

    EnumToolMaterial toolMaterial;

    private final String textureFileName;

    ItemXailiteTool(int id, Block[] blocksEffectiveAgainst, EnumToolMaterial theToolMaterial, String textureFileName)
    {
        super(id, theToolMaterial.getDamageVsEntity(), theToolMaterial, blocksEffectiveAgainst);
        this.toolMaterial = theToolMaterial;
        this.blocksEffectiveAgainst = blocksEffectiveAgainst;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = (int) toolMaterial.getDamageVsEntity();
        this.setCreativeTab(Xailite.xailiteTab);
        this.textureFileName = textureFileName;
        this.setUnlocalizedName("XailiteTool|" + id);
    }

    public float getStrVsBlock(ItemStack tool, Block brokenBlock)
    {
        if (Arrays.asList(this.blocksEffectiveAgainst).contains(brokenBlock)) return this.efficiencyOnProperMaterial;
        return 1.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack tool, World world, int blockId, int x, int y, int z, EntityLivingBase user)
    {
        if ((double) Block.blocksList[blockId].getBlockHardness(world, x, y, z) != 0.0D)
        {
            tool.damageItem(1, user);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    public boolean getIsRepairable(ItemStack input, ItemStack repairItem)
    {
        return (this.toolMaterial == Xailite.XAILITE_TOOL && repairItem.getItem() == Xailite.xailiteIngot) || (this.toolMaterial == Xailite.XAILITE_TOOL_REFINED && repairItem.getItem() == Xailite.refinedXailiteIngot);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 12000;
    }

    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("xailite:" + textureFileName);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase hitEntity, EntityLivingBase user)
    {
        itemStack.damageItem(10, user);
        return false;
    }
}
