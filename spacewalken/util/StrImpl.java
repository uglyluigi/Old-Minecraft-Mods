package spacewalken.util;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/25/13, at 1:14 AM.
 */
public class StrImpl
{
    private String str;

    public StrImpl(String s)
    {
        this.str = s;
    }

    public StrImpl strip(String sequence)
    {
        str = str.replace(sequence, "");
        return this;
    }
}
