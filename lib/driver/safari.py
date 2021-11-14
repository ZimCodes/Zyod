from selenium.webdriver.safari.webdriver import WebDriver
from . import browser


class Safari(browser.Browser):
    """Safari Browser Driver"""

    def __init__(self, args):
        super().__init__(None, args)

    def get_driver(self):
        if self._opts.driver_path:
            return WebDriver(executable_path=self._opts.driver_path)
        else:
            return WebDriver()
