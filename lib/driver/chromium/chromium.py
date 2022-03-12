import os

from lib.driver import browser
from selenium.webdriver.chromium.options import ChromiumOptions
from selenium.webdriver.chromium.webdriver import ChromiumDriver


class Chromium(browser.Browser):
    """Chromium WebDriver"""

    def __init__(self, opts, driver_opts=ChromiumOptions()):
        """Initializes Chromium WebDriver

        :param Opts opts: Opts class
        :param Options driver_opts: WebDriver Options
        """
        driver_opts.headless = opts.headless
        self._prefs = {}
        super().__init__(driver_opts, opts)
        self.driver_name = opts.driver_type

    def _set_preferences(self, opts) -> None:
        """Configure Settings/Preferences of WebDriver

        :param Opts opts: Opts class
        :return:
        """
        if opts.download_dir is not None:
            os.makedirs(opts.download_dir)
            self._prefs['download.default_directory'] = opts.download_dir

        if opts.do_download:
            self._prefs['download.prompt_for_download'] = False
            self._prefs['profile.default_content_setting_values.popups'] = 0
            self._prefs["download.directory_upgrade"] = True
            self._prefs['plugins.always_open_pdf_externally'] = True
            self._prefs["browser.helperApps.alwaysAsk.force"] = False
            self._driver_opts.add_experimental_option('prefs', self._prefs)

    def get_driver(self):
        """Gets configured Chromium WebDriver"""
        return ChromiumDriver(self.driver_name, 'webkit', desired_capabilities=self._capabilities)
