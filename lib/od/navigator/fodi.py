from .touch_navigator import TouchNavigator
from ..identity.od_type import ODType
from .nav_type import FODI as NavType
from ...driver.support.driver_support import DriverSupport
from ...asset.nav_info import NavInfo
from ...download.downloader import Downloader
from ...download.filter.fodi import FODI as FODIDownloadFilter
from ...scraper.text_scraper import TextScraper


class FODI(TouchNavigator):
    """Scraper operations for FODI OD types"""

    def __init__(self, driver, opts):
        """Initializes FODI Navigator

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        """
        super().__init__(ODType.FODI, driver, opts, True)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN,
                                       css_select="div#file-list div.row.file-wrapper",
                                       css_name="div.file span.name",
                                       css_download="div#file-list div.row.file-wrapper",
                                       extra_task=FODI._main_extra_task)]

    def _go_to_directory(self, directory) -> None:
        super()._go_to_directory(directory)

    def _move_down_to_destination(self, directory) -> None:
        while self._cur_dir.depth_level != directory.depth_level:
            opts = self._opts
            opts._wait = 15
            elements = DriverSupport.get_elements_all(self._driver, opts,
                                                      self._scraper.nav_info.css_select,
                                                      self._scraper.nav_info.wait_err_message)
            for el in elements:
                name_element = DriverSupport.get_element(el, self._scraper.nav_info.css_name)
                name = name_element.text.strip()
                name = self._scraper.apply_filter(name)
                if name in directory.url:
                    el.click()
                    self._cur_dir.depth_level += 1

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = TextScraper(self._driver, self._opts, nav_info)

    def _setup_downloader(self, nav_info) -> None:
        self._downloader = Downloader(self._driver, self._opts, nav_info, FODIDownloadFilter)

    @staticmethod
    def _main_extra_task(driver) -> None:
        """Extra downloading instructions

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.btn.download")
        element.click()
        FODI._go_back(driver)

    def _go_back(self) -> None:
        """Press the back button located on the page"""
        back_btn = DriverSupport.get_element(self._driver, "div.header ion-icon#arrow-back")
        arrow_status = back_btn.get_attribute("style")
        if "black" in arrow_status:
            back_btn.click()
        else:
            home_btn = DriverSupport.get_element(self._driver, "ion-icon#main-page")
            home_btn.click()
