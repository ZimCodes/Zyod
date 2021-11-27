import time
import re
from .name_navigator import NameNavigator
from .nav_type import ZFile as NavType
from ..identity.od_type import ODType
from ...asset.nav_info import NavInfo
from ...scraper.sift_scraper import SiftScraper
from lib.scraper.file_filters import Generic
from ...driver.support.driver_support import DriverSupport
from selenium.common.exceptions import NoSuchElementException
from ...asset.directory import Directory
from ...asset.url import URL
from ...download.downloader import Downloader
from ...download.filters import ZFile as DownloadFilter


class ZFile(NameNavigator):
    """ZFile Navigator object for navigating ZFile ODs"""

    def __init__(self, driver, opts):
        """Initializes ZFile Navigator object

        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        """
        super(ZFile, self).__init__(ODType.ZFILE, driver, opts, True)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN, css_select="tr.el-table__row",
                                       css_name="tr.el-table__row td:first-child div.cell",
                                       css_rej_filter="svg use[*|href='#el-icon-my-back']",
                                       css_download="ul.v-contextmenu "
                                                    "li.v-contextmenu-item:nth-child(2)")]

    def _setup_scraper(self, nav_info) -> None:
        match nav_info.id:
            case _:
                self._scraper = SiftScraper(self._driver, self._opts, nav_info, file_filter=Generic,
                                            sleep=True)

    def _setup_downloader(self, nav_info) -> None:
        self._downloader = Downloader(self._driver, self._opts, nav_info, DownloadFilter)

    def navigate(self, directory) -> tuple[list, list]:
        if not re.search(r'/[1-9]/main', directory.url):
            directory = Directory(0, URL(self._driver.current_url))
        return super().navigate(directory)

    def download(self) -> None:
        self._downloader.right_click_download()

    def _go_to_directory(self, directory) -> bool:
        super()._go_to_directory(directory)
        time.sleep(3.4)
        try:
            dialog_boxes = DriverSupport.get_elements_all(self._driver, self._opts,
                                                          "div.el-message-box__wrapper",
                                                          "")
            if dialog_boxes:
                dialog_box = dialog_boxes[0]
                attr = dialog_box.get_attribute("style")
                return "none" in attr
            else:
                return True
        except NoSuchElementException:
            return True
