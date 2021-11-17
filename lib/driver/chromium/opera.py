from .chromium import Chromium
from selenium.webdriver.opera.webdriver import OperaDriver
from selenium.webdriver.opera.options import Options
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities


class Opera(Chromium):
    """Opera WebDriver"""

    def __init__(self, opts):
        """Initializes Opera WebDriver

        :param Opts opts: Opts class
        """
        super(Opera, self).__init__(opts, Options())

    def _set_desired_capabilities(self) -> None:
        """Configure Desired Capabilities of WebDriver"""
        self._capabilities |= DesiredCapabilities.OPERA.copy()

    def get_driver(self):
        """Gets configured Opera WebDriver

        :return: Configured Opera WebDriver
        """
        return OperaDriver(options=self._driver_opts, desired_capabilities=self._capabilities)
