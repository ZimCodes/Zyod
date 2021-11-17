from selenium.webdriver.chrome.webdriver import WebDriver
from .chromium import Chromium
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities


class Chrome(Chromium):
    """Google Chrome WebDriver"""

    def __init__(self, opts):
        """Initializes Chrome WebDriver

        :param Opts opts: opts class
        """
        super(Chrome, self).__init__(opts, Options())

    def _set_desired_capabilities(self) -> None:
        """Configure Desired Capabilities of WebDriver"""
        self._capabilities |= DesiredCapabilities.CHROME.copy()

    def get_driver(self):
        """Gets configured WebDriver

        :return configured Chrome WebDriver
        """
        return WebDriver(options=self._driver_opts, desired_capabilities=self._capabilities)
