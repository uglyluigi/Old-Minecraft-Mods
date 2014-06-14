package spacewalken.util;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/27/13, at 3:26 PM.
 */
class ArrayImpl
{
    /**
     *
     * @param data
     * @return
     */
    public static <E> E[] assemble(E... data)
    {
        Object[] array = new Object[data.length];

        for (int i = 0; i < data.length; i++)
        {
            if (data[i] != null) array[i] = data[i];
        }

        return (E[]) array;
    }

    public static <T> T[] reverse(T[] arrayToReverse)
    {
        Object[] reversedArray = new Object[arrayToReverse.length];

        for (int i = 0; i < arrayToReverse.length; i++)
        {
            reversedArray[i] = arrayToReverse[-i];
        }

        return (T[]) reversedArray;
    }
}
