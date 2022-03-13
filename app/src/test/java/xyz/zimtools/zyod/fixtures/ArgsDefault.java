package xyz.zimtools.zyod.fixtures;

import com.beust.jcommander.JCommander;

public final class ArgsDefault {
    private static final String SEP = System.getProperty("file.separator");
    public static final String RES_DIR = "src" + SEP + "test" + SEP + "resources";
    public static final String INPUT_FILE = RES_DIR + SEP + "input.txt";
    public static final String DOWNLOAD_DIR = System.getProperty("user.home") + SEP + "Downloads";

    public static final int DEPTH_VALUE = 4;
    public static final int WAIT_VALUE = 30;
    public static final String WAIT_VALUE_STR = String.valueOf(WAIT_VALUE);
    public static final String NEGATIVE_STR = "-3";

    public static final String[] URLS_ARGS = new String[]{"-i", INPUT_FILE, DriversDefault.URL};
    public static final String[] MAIN_ARGS = new String[]{"-h", "-v", "-V", DriversDefault.URL};
    public static final String[] NAVIGATOR_ARGS = new String[]{"--random-wait", "-d",
            String.valueOf(DEPTH_VALUE), DriversDefault.URL};
    public static final String[] WEB_DRIVER_ARGS = new String[]{"--headless", "--all-certs", DriversDefault.URL};
    public static final String[] WEB_DRIVER_NO_HEADLESS_ARGS = new String[]{"--all" +
            "-certs", DriversDefault.URL};
    public static final String[] DOWNLOAD_ARGS = new String[]{"--download", DriversDefault.URL};
    public static final String[] RECORD_ARGS = new String[]{"--no-record", "-i", INPUT_FILE};
    public static final String[] INTERACTIVE_ARGS = new String[]{"--scroll", "--scroll-wait",
            WAIT_VALUE_STR, DriversDefault.URL};
    public static final String[] MISC_ARGS = new String[]{"--web-wait", WAIT_VALUE_STR
            , "--load-wait", WAIT_VALUE_STR, "-r", DriversDefault.URL};

    public static final String[] RAND_WAIT_ARGS = new String[]{"--dwait",
            WAIT_VALUE_STR, "-w", WAIT_VALUE_STR, "--random-wait",
            DriversDefault.URL};
    public static final String[] NEGATIVE_WAIT_ARGS = new String[]{"-w", NEGATIVE_STR, "-d",
            NEGATIVE_STR, "--dwait", NEGATIVE_STR, "--scroll-wait", NEGATIVE_STR, "--load-wait", NEGATIVE_STR, "--web-wait",
            NEGATIVE_STR, DriversDefault.URL};

    public static void argParse(String[] args, Object... obj) {
        JCommander.newBuilder()
                .addObject(obj)
                .args(args)
                .build();
    }
}