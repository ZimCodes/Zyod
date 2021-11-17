import os

from selenium import webdriver
from selenium.webdriver.firefox.options import Options
from . import browser
from .support.driver_support import DriverSupport
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities


class Firefox(browser.Browser):
    """Firefox WebDriver"""

    def __init__(self, opts):
        """Initializes Firefox WebDriver

        :param Opts opts: Opts class
        """
        super().__init__(Options(), opts)

    def _set_preferences(self, opts) -> None:
        """Configure Settings/Preferences of WebDriver

        :param Opts opts: Opts class
        :return:
        """
        self._driver_opts.headless = opts.headless
        if opts.download_dir is not None and opts.do_download:
            os.makedirs(opts.download_dir)
            self._driver_opts.set_preference('browser.download.dir', fr'{opts.download_dir}')
            self._driver_opts.set_preference('browser.download.folderList', 2)
        self._driver_opts.set_preference('browser.helperApps.neverAsk.saveToDisk',
                                         DriverSupport.MIMES)
        self._driver_opts.set_preference('pdfjs.disabled', True)

    def _set_desired_capabilities(self) -> None:
        """Configure Desired Capabilities of WebDriver"""
        super(Firefox, self)._set_desired_capabilities()
        self._capabilities |= DesiredCapabilities.FIREFOX.copy()

    def get_driver(self):
        """Gets the configured Firefox WebDriver"""
        return webdriver.Firefox(options=self._driver_opts, desired_capabilities=self._capabilities)
