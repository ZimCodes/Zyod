package xyz.zimtools.zyod.fixtures;

public final class ArgsDefault {
    public static final String RES_DIR = "src" + GlobalDefault.SEP + "test" + GlobalDefault.SEP + "resources";
    public static final String INPUT_FILE = RES_DIR + GlobalDefault.SEP + "input.txt";
    public static final String DOWNLOAD_DIR = System.getProperty("user.home") + GlobalDefault.SEP + "Downloads";

    public static final int DEPTH_VALUE = 4;
    public static final int WAIT_VALUE = 30;
    public static final String WAIT_VALUE_STR = String.valueOf(WAIT_VALUE);
    public static final String NEGATIVE_STR = "-3";

    public static final String[] URLS_ARGS = new String[]{"-i", INPUT_FILE, GlobalDefault.URL};
    public static final String[] MAIN_ARGS = new String[]{"-h", "-v", "-V", GlobalDefault.URL};
    public static final String[] NAVIGATOR_ARGS = new String[]{"--random-wait", "-d",
            String.valueOf(DEPTH_VALUE), GlobalDefault.URL};
    public static final String[] WEB_DRIVER_ARGS = new String[]{"--headless", "--all-certs", GlobalDefault.URL};
    public static final String[] WEB_DRIVER_NO_HEADLESS_ARGS = new String[]{"--all" +
            "-certs", GlobalDefault.URL};
    public static final String[] DOWNLOAD_ARGS = new String[]{"--download", GlobalDefault.URL};
    public static final String[] RECORD_ARGS = new String[]{"--no-record", "-i", INPUT_FILE};
    public static final String[] INTERACTIVE_ARGS = new String[]{"--scroll", "--scroll-wait",
            WAIT_VALUE_STR, GlobalDefault.URL};
    public static final String[] MISC_ARGS = new String[]{"--web-wait", WAIT_VALUE_STR
            , "--load-wait", WAIT_VALUE_STR, "-r", GlobalDefault.URL};

    public static final String[] RAND_WAIT_ARGS = new String[]{"--dwait",
            WAIT_VALUE_STR, "-w", WAIT_VALUE_STR, "--random-wait",
            GlobalDefault.URL};
    public static final String[] NEGATIVE_WAIT_ARGS = new String[]{"-w", NEGATIVE_STR, "-d",
            NEGATIVE_STR, "--dwait", NEGATIVE_STR, "--scroll-wait", NEGATIVE_STR, "--load-wait", NEGATIVE_STR, "--web-wait",
            NEGATIVE_STR, GlobalDefault.URL};
}