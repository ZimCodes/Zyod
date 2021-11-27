import time

from .base_navigator import BaseNavigator
from ...asset.nav_info import NavInfo
from ...scraper.join_scraper import JoinScraper
from ...scraper.file_filters import Generic
from .nav_type import WatchListOnFire as NavType
from ..identity.od_type import ODType
from ...driver.support.driver_support import DriverSupport


class WatchListOnFire(BaseNavigator):
    """Watchlist on fire navigator object"""

    def __init__(self, driver, opts):
        """Initializes Watchlist on fire navigator object

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        """
        super().__init__(ODType.WATCHLIST_ON_FIRE, driver, opts)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN,
                                       css_select="div#list a[href]",
                                       css_download="div#list svg + a",
                                       extra_task=WatchListOnFire._main_task)]

    @staticmethod
    def _main_task(driver) -> None:
        """Extra downloading tasks

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        time.sleep(3)
        element = DriverSupport.get_element(driver, "div.btn-group a[type='button']", "")
        DriverSupport.scroll_to_element(driver, element)
        time.sleep(2)
        element.click()
        driver.back()

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = JoinScraper(self._driver, self._opts, nav_info, Generic, True)
