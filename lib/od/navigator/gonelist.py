from .base_navigator import BaseNavigator
from .nav_type import GONEList as NavType
from ..identity.od_type import ODType
from ...asset.nav_info import NavInfo
from ...scraper.join_scraper import JoinScraper
from ...download.downloader import Downloader
from ...download.filters import GONEList as DownloadFilter


class GONEList(BaseNavigator):
    """Navigator object for GONEList"""

    def __init__(self, driver, opts):
        """Initializes GONEList Navigator object

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        """
        super().__init__(ODType.GONELIST, driver, opts)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [
            NavInfo(NavType.MAIN, css_select="div.ivu-table-cell div.ivu-table-cell-slot a",
                    css_download="div.ivu-table-cell div.ivu-table-cell-slot a")]

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = JoinScraper(self._driver, self._opts, nav_info, sleep=True)

    def _setup_downloader(self, nav_info) -> None:
        self._downloader = Downloader(self._driver, self._opts, nav_info, DownloadFilter)

