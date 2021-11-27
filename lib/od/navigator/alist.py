import time

from .name_navigator import NameNavigator
from .nav_type import AList as NavType
from ..identity.od_type import ODType
from ...asset.nav_info import NavInfo
from ...driver.support.driver_support import DriverSupport
from ...scraper.text_scraper import TextScraper
from ...asset.directory import Directory
from ...asset.url import URL


class AList(NameNavigator):
    """Navigator for AList OD"""

    def __init__(self, driver, opts):
        """Initializes AList Navigator object

        :param WebDriver driver: Selenium WebDriver
        :param opts:
        """
        super().__init__(ODType.ALIST, driver, opts, True)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.ORIGINAL,
                                       css_select="div.chakra-stack div.chakra-linkbox a[href]",
                                       css_name="div.chakra-stack div.chakra-linkbox a[href] "
                                                "svg:first-child + p",
                                       css_download="div.chakra-stack div.chakra-linkbox a[href]",
                                       extra_task=AList._original_tasks),
                               NavInfo(NavType.WEB,
                                       css_select="tbody.ant-table-tbody tr td:first-child",
                                       css_name="tbody.ant-table-tbody tr td:first-child",
                                       css_download="tbody.ant-table-tbody tr td:first-child a")]

    @staticmethod
    def _original_tasks(driver) -> None:
        """Extra tasks for Original variant of AList"""
        time.sleep(3.2)
        """Extra downloading tasks for AList Original variant"""
        element = DriverSupport.get_element(driver, "div.header div.buttons span:first-child",
                                            "Unable to "
                                            "locate "
                                            "download "
                                            "button.")
        if element:
            element.click()
            driver.back()

    def _setup_scraper(self, nav_info) -> None:
        match nav_info.id:
            case NavType.WEB:
                self._scraper = TextScraper(self._driver, self._opts, nav_info, sleep=True)
            case _:
                self._scraper = TextScraper(self._driver, self._opts, nav_info, sleep=True)

    def _go_to_directory(self, directory) -> tuple:
        if self._driver.current_url != directory.url:
            total_paths = directory.total_paths()
            if total_paths <= 1:
                self._driver.get(self._driver.current_url)
                directory = Directory(directory.depth_level, URL(self._driver.current_url))
            elif directory.url is not None:
                if not directory.url.endswith("/"):
                    self._driver.get(directory.url)
                else:
                    self._driver.get(directory.url[:len(directory.url) - 1])
        return True, directory
