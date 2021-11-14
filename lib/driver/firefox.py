from selenium import webdriver
from selenium.webdriver.firefox.options import Options
from . import browser


class Firefox(browser.Browser):
    """Firefox Driver Capabilities"""

    def __init__(self, opts):
        super().__init__(Options(), opts)
        self._driver_opts.headless = True

    def get_driver(self):
        return webdriver.Firefox(options=self._driver_opts)
