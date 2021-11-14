from .chromium import Chromium
from selenium.webdriver.opera.webdriver import OperaDriver
from selenium.webdriver.opera.options import Options


class Opera(Chromium):
    """Opera Browser driver"""

    def __init__(self, opts):
        super(Opera, self).__init__(opts, Options())

    def get_driver(self):
        return OperaDriver(options=self._driver_opts)
