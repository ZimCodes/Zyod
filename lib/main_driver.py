from .driver import *
from .driver.chromium import *


class MainDriver:
    """MainDriver object maintains all available drivers for use"""

    @staticmethod
    def get_driver(opts):
        """Selects the appropriate browser driver to use

        :return the appropriate Selenium driver
        """
        match opts.driver_type:
            case 'firefox':
                return firefox.Firefox(opts).get_driver()
            case 'edge':
                return edge.Edge(opts).get_driver()
            case 'chrome':
                return chrome.Chrome(opts).get_driver()
            case _:
                return chromium.Chromium(opts).get_driver()
