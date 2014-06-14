package spacewalken.util;

import spacewalken.util.exception.IntegerGenerationException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Random;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/21/13, at 10:54 AM.
 */
public class Randomizer
{

    public static int getUniqueBlockId()
    {
        Block[] blocks = Block.blocksList;

        int i;

        for (i = 0; i < blocks.length; i++)
        {
            if (blocks[i] == null) return i;
        }

        throw new IntegerGenerationException("All block IDs are occupied.");
    }

    /**
     * Checks if the passed id is already occupied and iterates through the items list to find an empty ID
     * if it's taken.
     *
     * @param id the id to have fallback for.
     *
     * @return
     */
    public static int generateIdWithDefault(int id)
    {
        Item[] items = Item.itemsList;

        if (items[id] == null)
        {
            return id;
        } else
        {
            return getUniqueItemId();
        }
    }

    private static int getUniqueItemId()
    {
        Item[] items = Item.itemsList;

        int i;

        for (i = 0; i < items.length; i++)
        {
            if (items[i] == null) return i;
        }

        return 0;
    }

    public static int getRandomNumberInRange(int max, int min)
    {
        return new Random().nextInt(max - min) + min;
    }
}
