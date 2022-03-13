package xyz.zimtools.zyod.browsers;

import xyz.zimtools.zyod.args.Args;

import java.util.HashMap;
import java.util.Map;


public abstract class ChromiumBrowser extends Browser {
    protected final Map<String, Object> prefs;

    public ChromiumBrowser(Args args) {
        super(args);
        this.prefs = new HashMap<>();
    }

    @Override
    protected void setPreferences() {
        this.prefs.put("download.prompt_for_download", false);
        this.prefs.put("profile.default_content_setting_values.popups", 0);
        this.prefs.put("download.directory_upgrade", true);
        this.prefs.put("plugins.always_open_pdf_externally", true);
    }
}