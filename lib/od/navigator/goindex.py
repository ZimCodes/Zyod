from . import base_navigator
from lib.scraper.file_filters import Generic as ScrapeFilter
from ...od.identity.od_type import ODType
from ...driver.support.driver_support import DriverSupport
from .nav_type import GoIndex as NavType
from ...asset.nav_info import NavInfo
from ...scraper.join_scraper import JoinScraper


class GoIndex(base_navigator.BaseNavigator):
    """Holds operations for navigating GoIndex ODs"""

    def __init__(self, driver, opts):
        """Initializes GoIndex object

        :param Opts opts: Opts class
        :param WebDriver driver: Selenium Webdriver object
        """
        super().__init__(ODType.GO_INDEX, driver, opts)

    def _prepare_nav_info_list(self) -> None:
        """Prepare a list of navigational instructions for different versions of GoIndex

        :return:
        """
        view_download_css = "div.golist tbody td span.icon:nth-child(n+3)"
        self._nav_info_list = [
            NavInfo(NavType.LIST_VIEW, css_select="div.golist "
                                                  "tbody td:first-child[title]",
                    css_attr="title",
                    css_download=view_download_css,
                    wait_err_message="List View navigation method failed!"),
            NavInfo(NavType.THUMBNAIL_VIEW,
                    css_select="div.column.is-one-quarter["
                               "data-v-1871190e] div[title]",
                    css_attr="title",
                    css_download=view_download_css,
                    wait_err_message="Thumbnail View navigation method failed!"),
            NavInfo(NavType.OLDER, css_select="ul#list li.mdui-list-item a",
                    css_download="a[gd-type]",
                    wait_err_message="Older version navigation failed! "
                                     "Elements cannot be "
                                     "obtained", extra_task=GoIndex._older_extra_download_task)]

    def _setup_scraper(self, nav_info) -> None:
        match nav_info.id:
            case NavType.OLDER:
                self._scraper = JoinScraper(self._driver, self._opts, nav_info,
                                            ScrapeFilter)
            case _:
                self._scraper = JoinScraper(self._driver, self._opts, nav_info, ScrapeFilter)

    @staticmethod
    def _older_extra_download_task(driver) -> None:
        """Extra downloading instructions for Older versions

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "a.mdui-fab")
        element.click()
        driver.back()
