from .chromium import Chromium
from selenium.webdriver.edge.webdriver import WebDriver
from selenium.webdriver.edge.options import Options


class Edge(Chromium):
    """Microsoft Edge driver"""

    def __init__(self, opts):
        super(Edge, self).__init__(opts, Options())

    def get_driver(self):
        return WebDriver(options=self._driver_opts)
