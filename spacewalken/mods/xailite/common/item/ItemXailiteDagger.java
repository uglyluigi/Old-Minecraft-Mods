package spacewalken.mods.xailite.common.item;

import spacewalken.mods.xailite.Xailite;
import spacewalken.util.MethodProxy;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ItemXailiteDagger extends Item
{
    private final String textureFileName;

    public ItemXailiteDagger(int id, String textureFileName)
    {
        super(id);
        this.maxStackSize = 1;
        this.setUnlocalizedName("XailiteDagger|" + id);
        this.setCreativeTab(Xailite.xailiteTab);
        this.textureFileName = textureFileName;
        this.setFull3D();
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("xailite:" + textureFileName);
    }

    //This list is here to prevent attacked entities that have been hit once but not one hit killed from being one hit killed.
    private final List<EntityLivingBase> hitEntityList = new LinkedList<EntityLivingBase>();
    private int entityListCeiling = 30;

    @Override
    public boolean hitEntity(ItemStack dagger, EntityLivingBase hitEntity, EntityLivingBase user)
    {
        /*
         To prevent memory leaks, once the hitEntityList reaches 30 entries, it is iterated through, and any dead entities are removed from the list.
         If all entities in the list are alive, the list size is increased to the current size plus 5. If the allocated size is 50, the list is cleared and the
         allocated list size is reset to 30.
         */
        if (hitEntityList.size() == entityListCeiling)
        {
            boolean shouldBePruned = false;

            //This list is for storing the dead entities that can be removed from the list.
            List<EntityLivingBase> deadEntities = new ArrayList<EntityLivingBase>();

            for (EntityLivingBase entity : hitEntityList)
            {
                if (entity.isDead)
                {
                    shouldBePruned = true;
                    deadEntities.add(entity);
                }
            }

            if (shouldBePruned || deadEntities.size() > 0)
            {
                hitEntityList.removeAll(deadEntities);
                System.out.println("Pruned hit entity list.");
            } else {
                if (entityListCeiling < 50)
                    entityListCeiling += 5;
                else {
                    entityListCeiling = 30;
                    hitEntityList.clear();
                    System.out.println("Cleared hit entity list and reduced allocation size to 50.");
                }
            }
        }

        EntityPlayer thePlayer = MethodProxy.getPlayerFromBase(user);
        boolean isCreative = thePlayer.capabilities.isCreativeMode;

        if (!hitEntityList.contains(hitEntity) && !(MethodProxy.getEntityHealth(hitEntity) < MethodProxy.getMaxEntityHealth(hitEntity)) && (Math.random() < (this == Xailite.xailiteDagger ? 0.3 : this == Xailite.diamondXailiteDagger ? 0.6 : 1.0) || (this == Xailite.diamondXailiteDagger && hitEntity instanceof EntityCreeper)) && !(hitEntity instanceof EntityDragon || hitEntity instanceof EntityWither))
        {
            MethodProxy.playSoundAtEntity(hitEntity, "mob.endermen.portal", 1.0F, 1.0F);

            //If the hit entity is a player, it gives the player a chance to fight back by only halving their health instead of setting them dead.
            if (hitEntity instanceof EntityPlayer)
                hitEntity.attackEntityFrom(DamageSource.causePlayerDamage(MethodProxy.getPlayerFromBase(user)), (int) MethodProxy.getMaxEntityHealth(hitEntity) / 2);
            else {
                MethodProxy.spawnParticle("smoke", new Random().nextInt(70) + 20, hitEntity.posX, hitEntity.posY, hitEntity.posZ, true);
                hitEntity.setDead();
            }
            if (!isCreative) dagger.damageItem(2, thePlayer);
        } else {
            hitEntity.attackEntityFrom(DamageSource.causeMobDamage(thePlayer), this == Xailite.xailiteDagger ? 2 : 3);
            if (!isCreative) dagger.damageItem(1, thePlayer);
        }

        hitEntityList.add(hitEntity);
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack dagger, int pass)
    {
        return dagger.getItem() == Xailite.diamondXailiteDagger;
    }
}
