from .chromium import Chromium
from selenium.webdriver.edge.webdriver import WebDriver
from selenium.webdriver.edge.options import Options


class Edge(Chromium):
    """Microsoft Edge WebDriver"""

    def __init__(self, opts):
        """Initializes Edge Webdriver

        :param Opts opts: Opts class
        """
        super(Edge, self).__init__(opts, Options())

    def _set_preferences(self, opts) -> None:
        """Configure Settings/Preferences of WebDriver

        :param Opts opts: Opts class
        :return:
        """
        self._prefs['printing.headless_save_as_pdf_enabled'] = False
        self._prefs["download.open_pdf_in_system_reader"] = False
        super()._set_preferences(opts)

    def get_driver(self):
        """Gets Configured Edge WebDriver

        :return: Configured Edge WebDriver
        """
        return WebDriver(options=self._driver_opts)
