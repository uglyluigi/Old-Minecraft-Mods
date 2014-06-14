package spacewalken.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

import java.util.Random;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/11/13, at 12:26 AM.
 */
public class MethodProxy
{
    /**
     * Gets the maximum health of the passed entity argument.
     *
     * @param entity the entity to get the health from.
     *
     * @return the entity's maximum health.
     *
     * @see net.minecraft.entity.EntityLivingBase#func_110143_aJ()
     */
    public static float getMaxEntityHealth(EntityLivingBase entity)
    {
        return entity.func_110143_aJ();
    }

    /**
     * Gets the current health of the passed entity argument.
     *
     * @param entity the entity to get the health from.
     *
     * @return the entity's current health.
     *
     * @see net.minecraft.entity.EntityLivingBase#func_110138_aP()
     */
    public static float getEntityHealth(EntityLivingBase entity)
    {
        return entity.func_110138_aP();
    }

    /**
     * Gets the unlocalized name for the passed {@link net.minecraft.item.Item}.
     *
     * @param item the item to get the unlocalized name from.
     *
     * @return the item's unlocalized name.
     *
     * @see net.minecraft.item.Item#getUnlocalizedName().substring(5)
     */
    public static String getUnlocalizedNameForItem(Item item)
    {
        return item.getUnlocalizedName().substring(5);
    }

    /**
     * Gets the unlocalized name for the passed {@link net.minecraft.block.Block}.
     *
     * @param block the block to get the unlocalized name from.
     *
     * @return the block's unlocalized name.
     *
     * @deprecated for the metadata sensitive replacement.
     */
    public static String getUnlocalizedNameForBlock(Block block)
    {
        return block.getUnlocalizedName().substring(5);
    }

    /**
     * Gets the unlocalized name for the passed {@link net.minecraft.block.Block} and damage.
     *
     * @param block  the block.
     * @param damage the block's damage.
     *
     * @return the block's unlocalized name.
     */
    public static String getUnlocalizedNameForBlock(Block block, int damage)
    {
        return new ItemStack(block, 1, damage).getItem().getUnlocalizedName().substring(5);
    }

    /**
     * Casts {@link net.minecraft.entity.player.EntityPlayer} to the passed {@link net.minecraft.entity.EntityLivingBase}.
     * <br/>
     * This cast is checked. The passed <code>EntityLivingBase</code> is casted to EntityPlayer, and if the username is null,
     * an <code>IllegalArgumentException</code> is thrown.
     *
     * @param entityLivingBase the entity to convert.
     *
     * @return entityLivingBase as an EntityPlayer.
     *
     * @throws IllegalArgumentException
     */
    public static EntityPlayer getPlayerFromBase(EntityLivingBase entityLivingBase)
    {
        EntityPlayer player = (EntityPlayer) entityLivingBase;
        if (player.username != null)
        {
            return player;
        }

        throw new IllegalArgumentException("Designated entity is not a player");
    }

    /**
     * Gets the current world as a world instance. <br/>
     * This gets the instance from {@link net.minecraft.src.ModLoader#getMinecraftInstance()}.
     *
     * @return {@link net.minecraft.src.ModLoader#getMinecraftInstance()}
     */
    public static World getInstantiatedWorld()
    {
        return getInstantiatedMinecraft().theWorld;
    }

    /**
     * Gets the current world as a world instance. <br/>
     * This gets the instance from {@link net.minecraft.src.ModLoader#getMinecraftInstance()}.
     *
     * @return {@link net.minecraft.src.ModLoader#getMinecraftInstance()}.thePlayer
     */
    private static EntityPlayer getInstantiatedPlayer()
    {
        return getInstantiatedMinecraft().thePlayer;
    }

    /**
     * Gets the current instance of Minecraft in the JVM.
     *
     * @return {@link net.minecraft.src.ModLoader#getMinecraftInstance()}
     */
    private static Minecraft getInstantiatedMinecraft()
    {
        return ModLoader.getMinecraftInstance();
    }

    /**
     * Returns the block occupying the ID.
     *
     * @param id the id of the block.
     *
     * @return the block.
     */
    public static Block getBlockFromId(int id)
    {
        return Block.blocksList[id];
    }

    /**
     * Gets the item occupying the ID.
     *
     * @param id the id of the item.
     *
     * @return the item.
     */
    public static Item getItemFromId(int id)
    {
        return Item.itemsList[id];
    }

    /**
     * Registers a new armor material.
     *
     * @param name            the name of the material.
     * @param durability      the durability of the armor.
     * @param enchantibility  the enchantibility of the armor.
     * @param damageReduction the amount of damage reduction each armor part has.
     *
     * @return the armor material.
     *
     * @see net.minecraftforge.common.EnumHelper#addArmorMaterial(String, int, int[], int)
     */
    public static EnumArmorMaterial registerArmorMaterial(String name, int durability, int enchantibility, int... damageReduction)
    {
        return EnumHelper.addArmorMaterial(name, durability, damageReduction, enchantibility);
    }

    /**
     * Converts a string into a chat message component for use with {@link net.minecraft.command.ICommandSender}.
     * Really a dummy method to avoid clutter.
     *
     * @param s the string to convert.
     *
     * @return a {@link net.minecraft.util.ChatMessageComponent}.
     *
     * @see net.minecraft.util.ChatMessageComponent#func_111066_d(String)
     */
    public static ChatMessageComponent sToCMC(String s)
    {
        return ChatMessageComponent.func_111066_d(s);
    }

    /**
     * Selects an object randomly from the passed objects.
     *
     * @param objects the array of objects to select from.
     *
     * @return a randomly selected object.
     */
    private static Object selectObjAtRandom(Object... objects)
    {
        return objects[new Random().nextInt(objects.length)];
    }

    /**
     * Selects a string from a string array at random.
     *
     * @param strings the array of strings to select from.
     *
     * @return a randomly selected entry in the array.
     *
     * @see MethodProxy#selectObjAtRandom(Object...)
     */
    public static String selectStringAtRandom(String... strings)
    {
        return selectObjAtRandom(strings).toString();
    }

    /**
     * Spawns a particle at the given coordinates.
     *
     * @param particleName      The name of the particle.
     * @param frequency         The amount of times to spawn the particle.
     * @param x                 The X position of the particle.
     * @param y                 The Y position of the particle.
     * @param z                 The Z position of the particle.
     * @param settingsDependant used to decide if the particle should be spawned based on the current particle display setting. Set to "true" for dependant (particle setting has to be <= 1) or "false" to always spawn it.
     */
    public static void spawnParticle(String particleName, int frequency, double x, double y, double z, boolean settingsDependant)
    {
        Random itemRand = new Random();

        for (int i = 0; i < ((frequency >= 1) ? frequency : 1) && (!settingsDependant || getGameSettings().particleSetting <= 1); i++)
        {
            getInstantiatedWorld().spawnParticle(particleName, (double) ((float) x + itemRand.nextFloat()), (y + (double) itemRand.nextFloat()), (double) ((float) z + itemRand.nextFloat()), itemRand.nextGaussian() * 0.02D, itemRand.nextGaussian() * 0.02D, itemRand.nextGaussian() * 0.02D);
        }
    }

    /**
     * Similar to {@link MethodProxy#spawnParticle(String, int, double, double, double, boolean)}, but multiple particle types can be spawned simultaneously.
     *
     * @param particles         The names of the particles.
     * @param frequency         The amount of times to spawn the particle in the world.
     * @param x                 The X Position of the particle.
     * @param y                 The Y position of the particle.
     * @param z                 The Z position of the particle.
     * @param settingsDependant used to decide if the particle should be spawned based on the current particle display setting. Set to "true" for dependant (particle setting has to be <= 1) or "false" to always spawn it.
     */
    public static void spawnParticles(String[] particles, int frequency, double x, double y, double z, boolean settingsDependant)
    {
        for (String particle : particles)
        {
            spawnParticle(particle, frequency, x, y, z, settingsDependant);
        }
    }

    /**
     * Plays a given sound at a passed entity. This is the same as {@link World#playSound(double, double, double, String, float, float, boolean)}<br/>
     * and {@link Minecraft#sndManager}, but the coordinate parameters are fetched from the entity.
     *
     * @param entity The entity to get coordinates from.
     * @param name   The name of the sound to play.
     * @param volume The volume of the sound (default: 1.0F).
     * @param pitch  The pitch of the sound (default: 1.0F).
     *
     * @see Minecraft#sndManager#playSound()
     */
    public static void playSoundAtEntity(Entity entity, String name, float volume, float pitch)
    {
        getInstantiatedWorld().playSound(entity.posX, entity.posY, entity.posZ, name, volume, pitch, true);
    }

    /**
     * Spawns an entity into the world.
     *
     * @param entity the entity to spawn in the world.
     *
     * @see World#spawnEntityInWorld(net.minecraft.entity.Entity)
     */
    public static void spawnEntityInWorld(Entity entity)
    {
        getInstantiatedWorld().spawnEntityInWorld(entity);
    }

    /**
     * Returns an instance of the game's settings.
     *
     * @return an instance of the game's settings.
     */
    public static GameSettings getGameSettings()
    {
        return getInstantiatedMinecraft().gameSettings;
    }

    /**
     * Gets the X Y and Z positions of the passed entity.
     *
     * @param entity the entity to get the coordinates from.
     *
     * @return a <code>double[]</code> with three entries.
     */
    public static double[] getEntityCoordinates(Entity entity)
    {
        return new double[]{entity.posX, entity.posY, entity.posZ};
    }

    public static boolean clientCurrentlyInWorld()
    {
        return getInstantiatedPlayer() != null;
    }
}
