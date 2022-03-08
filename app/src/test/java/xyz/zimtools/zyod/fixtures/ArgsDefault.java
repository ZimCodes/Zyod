package xyz.zimtools.zyod.fixtures;

public class ArgsDefault {
    private static final String SEP = System.getProperty("file.separator");
    public static final String DRIVER_CHOICE = "firefox";
    public static final String RES_DIR = "src" + SEP + "test" + SEP + "resources";
    public static final String INPUT_FILE = RES_DIR + SEP + "input.txt";

    public static final int DEPTH_VALUE = 4;
    public static final int WAIT_VALUE = 30;

    public static final String[] MAIN_ARGS = new String[]{"-h", "-v", "-V"};
    public static final String[] NAVIGATOR_ARGS = new String[]{"--random-wait", "-d", String.valueOf(DEPTH_VALUE)};
    public static final String[] WEB_DRIVER_ARGS = new String[]{"--driver", DRIVER_CHOICE, "--headless"};
    public static final String[] DOWNLOAD_ARGS = new String[]{"--download"};
    public static final String[] RECORD_ARGS = new String[]{"--no-record", "-i", INPUT_FILE};
    public static final String[] INTERACTIVE_ARGS = new String[]{"--scroll", "--scroll-wait",
            String.valueOf(WAIT_VALUE)};
    public static final String[] MISC_ARGS = new String[]{"--web-wait", String.valueOf(WAIT_VALUE)
            , "--load-wait", String.valueOf(WAIT_VALUE), "-r"};
}