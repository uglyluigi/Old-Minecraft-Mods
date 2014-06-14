package spacewalken.mods.xailite;

/**
 * Created with IntelliJ IDEA
 * Date of creation: 6/1/13
 * Author: Brennan Forrest
 */

class CallableVersion
{
    private static final String version = "0";
    private static final String subversion = "9";
    private static final String revisionVersion = "9";
    private static final String devStage = "Beta";

    public static String getVersion()
    {
        return String.format("%s %s.%s.%s", devStage, version, subversion, revisionVersion);
    }
}
