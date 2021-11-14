from .driver import *
from .driver.chromium import *

class MainDriver:
    """Selects the appropriate browser driver to use"""

    @staticmethod
    def get_driver(opts):
        match opts.driver_type:
            case 'firefox':
                return firefox.Firefox(opts).get_driver()
            case 'safari':
                return safari.Safari(opts).get_driver()
            case 'opera':
                return opera.Opera(opts).get_driver()
            case 'edge':
                return edge.Edge(opts).get_driver()
            case 'chrome':
                return chrome.Chrome(opts).get_driver()
            case _:
                return chromium.Chromium(opts).get_driver()
