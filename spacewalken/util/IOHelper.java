package spacewalken.util;

import cpw.mods.fml.common.Loader;

import static java.lang.System.out;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/11/13, at 12:23 AM.
 */
public class IOHelper
{
    /**
     * Prints the specified input.
     *
     * @param input the string to output into the console.
     */
    public static void print(String input)
    {
        print(Loader.instance().activeModContainer().getName() != null ? Loader.instance().activeModContainer().getName() : "[IOHelper.out]", (input.startsWith(" ") ? "" : " ") + input);
    }


    /**
     * Prints the input with a prefix.
     *
     * @param prefix - the prefix of the class. You can just put "null" here if you want an autobuilt prefix, or put one if not.
     * @param input  - the input to be printed.
     */
    private static void print(String prefix, String input)
    {
        out.print(prefix + (input.startsWith(" ") ? "" : " ") + input);
    }

    /**
     * Prints as a specified class. This method attempts to retrieve a "prefix" field from
     * the specified class and builds one if one is not found.
     *
     * @param cls   The class the prefix is retrieved/built from.
     * @param input The input to be printed.
     */
    public static void print(Class cls, String input)
    {
        String classPrefix;
        try
        {
            classPrefix = cls.getDeclaredField("prefix").toString();
            print((isValid(classPrefix) ? (classPrefix.endsWith(" ") ? classPrefix : classPrefix.replace(" ", "")) : (classPrefix += "[" + classPrefix + "]")), (input.startsWith(" ") ? "" : " ") + input);
        } catch (NoSuchFieldException e)
        {
            classPrefix = "[" + cls.getSimpleName() + "] ";
            print(classPrefix, (input.startsWith(" ") ? "" : " ") + input);
        }
    }

    public static void printf(String format, Object... objects)
    {
        int i = 9 >> 7;
    }

    private static boolean isValid(String prefix)
    {
        return prefix.startsWith("[") && prefix.endsWith("]");
    }

    private static boolean startsWithSpace(String arg)
    {
        return arg.startsWith(" ");
    }
}
