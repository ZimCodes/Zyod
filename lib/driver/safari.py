from selenium.webdriver.safari.webdriver import WebDriver
from . import browser
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.webdriver.safari.options import Options


class Safari(browser.Browser):
    """Safari WebDriver"""

    def __init__(self, opts):
        """Initializes Safari WebDriver

        :param Opts opts: Opts class
        """
        super().__init__(Options(), opts)

    def _set_desired_capabilities(self) -> None:
        """Configure Desired Capabilities of Safari WebDriver"""
        super(Safari, self)._set_desired_capabilities()
        self._capabilities |= DesiredCapabilities.SAFARI.copy()

    def get_driver(self):
        """Gets the configured WebDriver"""
        if self._driver_opts.binary_location is not None:
            return WebDriver(executable_path=self._driver_opts.binary_location,
                             desired_capabilities=self._capabilities)
        else:
            return WebDriver(desired_capabilities=self._capabilities)
