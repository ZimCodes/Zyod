import time

from .base_navigator import BaseNavigator
from .nav_type import ShareList as NavType
from ..identity.od_type import ODType
from ...scraper.join_scraper import JoinScraper
from ...asset.nav_info import NavInfo
from ...driver.support.driver_support import DriverSupport
from ...scraper.file_filters import Generic
from ...download.filters import ShareListPreview as PreviewDownloadFilter
from ...download.filters import ShareListGeneral as DownloadFilter
from ...download.downloader import Downloader


class ShareList(BaseNavigator):
    """ShareList navigator object"""

    def __init__(self, driver, opts):
        """Initializes ShareList navigator object

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: opts class
        """
        super().__init__(ODType.SHARELIST, driver, opts, True)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.INTERACTIVE,
                                       css_select="div.drive-body div[title]",
                                       css_attr="title",
                                       css_download="div.drive-body div[title]",
                                       extra_task=ShareList._interactive_task),
                               NavInfo(NavType.DOWNLOAD_QUERY,
                                       css_select="div.drive-body a[href]",
                                       css_download="div.drive-body a[href]"),
                               NavInfo(NavType.PREVIEW_QUERY,
                                       css_select="ul.node-list__body li a[href]",
                                       css_download="ul.node-list__body li a[href]",
                                       extra_task=ShareList._extra_preview_task)]

    @staticmethod
    def _extra_preview_task(driver) -> None:
        """Extra downloading instructions for ShareList preview variant

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        time.sleep(5)
        total_windows = len(driver.window_handles)
        if total_windows > 1:
            next_tab = driver.window_handles[1]
            driver.switch_to.window(next_tab)
        element = DriverSupport.get_element(driver, "div a.download-menu", "")
        if element:
            time.sleep(3)
            element.click()
            driver.close()
            driver.switch_to.window(driver.window_handles[0])

    @staticmethod
    def _interactive_task(driver) -> None:
        """Extra downloading instructions for ShareList interactive variant

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        time.sleep(4)
        while len(driver.window_handles) > 1:
            last_tab = driver.window_handles[len(driver.window_handles) - 1]
            driver.switch_to.window(last_tab)
            driver.close()
        driver.switch_to.window(driver.window_handles[0])

    def _setup_scraper(self, nav_info) -> None:
        match nav_info.id:
            case NavType.PREVIEW_QUERY | NavType.DOWNLOAD_QUERY:
                self._scraper = JoinScraper(self._driver, self._opts, nav_info, Generic)
            case _:
                self._scraper = JoinScraper(self._driver, self._opts, nav_info)

    def _setup_downloader(self, nav_info) -> None:
        match nav_info.id:
            case NavType.PREVIEW_QUERY:
                self._downloader = Downloader(self._driver, self._opts, nav_info,
                                              PreviewDownloadFilter)
            case _:
                self._downloader = Downloader(self._driver, self._opts, nav_info, DownloadFilter)
