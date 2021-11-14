from selenium.webdriver.chrome.webdriver import WebDriver
from .chromium import Chromium
from selenium.webdriver.chrome.options import Options


class Chrome(Chromium):
    """Google Chrome driver"""

    def __init__(self, opts):
        super(Chrome, self).__init__(opts, Options())

    def get_driver(self):
        return WebDriver(options=self._driver_opts)
