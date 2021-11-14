from lib.driver import browser
from selenium.webdriver.chromium.options import ChromiumOptions
from selenium.webdriver.chromium.webdriver import ChromiumDriver


class Chromium(browser.Browser):
    """Chromium browser drivers"""

    def __init__(self, opts, driver_opts=ChromiumOptions()):
        super().__init__(driver_opts, opts)
        self._driver_opts.headless = True
        self.driver_name = opts.driver_type

    def get_driver(self):
        return ChromiumDriver(self.driver_name, 'webkit')
