from . import base_scraper
from ..filter.generic import Generic as GenericFilter
from ...od.identity.od_type import ODType
from ...driver.support.driver_support import DriverSupport
from .scrape_type import GoIndex as ScrapeType
from .helper.sub_navigation import SubNavigation


class GoIndex(base_scraper.BaseScraper):
    """Holds operations for navigating GoIndex ODs"""

    def __init__(self, driver, opts):
        """Initializes GoIndex object

        :param Opts opts: Opts class
        :param WebDriver driver: Selenium Webdriver object
        """
        super().__init__(ODType.GO_INDEX, driver, opts, filter_obj=GenericFilter)

    def _prepare_nav_list(self, opts) -> None:
        """Prepare a list of navigational instructions for different versions of GoIndex

        :param Opts opts: Opts class
        :return:
        """
        view_download_css = "div.golist tbody td span.icon:nth-child(n+3)"
        self._nav_list = [
            SubNavigation(ScrapeType.LIST_VIEW, opts, "div.golist "
                                                      "tbody td:first-child[title]",
                          view_download_css,
                          "List View navigation method failed!"),
            SubNavigation(ScrapeType.THUMBNAIL_VIEW, opts,
                          "div.column.is-one-quarter["
                          "data-v-1871190e] div[title]",
                          view_download_css,
                          "Thumbnail View navigation method failed!"),
            SubNavigation(ScrapeType.OLDER, opts, "ul#list li.mdui-list-item a",
                          "a[gd-type]",
                          "Older version navigation failed! "
                          "Elements cannot be "
                          "obtained", GoIndex._older_extra_download_task)]

    @staticmethod
    def _older_extra_download_task(driver) -> None:
        """Extra downloading instructions for Older versions

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "a.mdui-fab")
        element.click()
        driver.back()

    def _get_clean_links(self, elements, depth_level) -> tuple[list, list]:
        """Retrieve file/directory links with filters applied to them

        :param list elements: list of elements
        :param int depth_level: the current depth level
        :return: filtered list of files/directory links
        """
        if not self._nav_obj:
            return self._dirs, self._files
        match self._nav_obj.id:
            case ScrapeType.THUMBNAIL_VIEW | ScrapeType.LIST_VIEW:
                return self._from_thumbnail_list_view(elements, depth_level)
            case ScrapeType.OLDER:
                return self._from_old_version(elements, depth_level)
            case _:
                return self._dirs, self._files

    def _from_thumbnail_list_view(self, elements, level) -> tuple[list, list]:
        """Retrieve the files/directory links from GoIndex in thumbnail view

        :param list elements: list of elements
        :param int level: the current depth folder the elements came from
        :return: a tuple of corrected files and directory links
        """
        return self._get_files_append(elements, "title", level)

    def _from_old_version(self, elements, level) -> tuple[list, list]:
        """Retrieve the files/directory links from older versions of GoIndex

        :param list elements: list of elements
        :param int level: the current depth folder the elements came from
        :return: a tuple of corrected files and directory links
        """
        return self._get_files_link(elements, "href", level)
