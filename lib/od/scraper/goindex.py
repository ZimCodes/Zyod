from . import base_scraper
from ..filter.goindex import GoIndex as GoIndexFilter
from ...driver.support.browser_wait import BrowserWait
from .util.parser import Parser


class GoIndex(base_scraper.BaseScraper):
    """GoIndex scraper method"""

    def __init__(self, max_depth, driver):
        super().__init__(driver, max_depth, GoIndexFilter)

    def navigate(self, directory):
        super().navigate(directory)
        # Thumbnail View
        elements = Parser.get_elements(self.driver, "div.column.is-one-quarter["
                                                    "data-v-1871190e] div[title]")
        if bool(elements):
            return self._from_thumbnail_list_view(elements, directory.depth_level)

        # List View
        elements = Parser.get_elements(self.driver, "div.golist tbody td:first-child["
                                                    "title]")
        if bool(elements):
            return self._from_thumbnail_list_view(elements, directory.depth_level)

        # Older Version
        elements = Parser.get_elements(self.driver, "ul#list li.mdui-list-item a")
        return self._from_old_version(elements, directory.depth_level)

    def navigate_wait(self, opts, directory):
        super(GoIndex, self).navigate(directory)

        # Thumbnail view
        elements = BrowserWait.get_elements(self.driver, opts,
                                            "div.column.is-one-quarter["
                                            "data-v-1871190e] div[title]",
                                            "Thumbnail View navigation failed! Using different "
                                            "method.")
        if bool(elements):
            return self._from_thumbnail_list_view(elements, directory.depth_level)
        # List View
        elements = BrowserWait.get_elements(self.driver, opts, "div.golist tbody td:first-child["
                                                               "title]",
                                            "List View navigation failed! "
                                            "Using different method.")
        if bool(elements):
            return self._from_thumbnail_list_view(elements, directory.depth_level)

        # Older Version
        elements = BrowserWait.get_elements(self.driver, opts, "ul#list li.mdui-list-item a",
                                            "Older version navigation failed! Elements cannot be "
                                            "obtained")
        if bool(elements):
            return self._from_old_version(elements, directory.depth_level)
        else:
            return self.dirs, self.files

    def _from_thumbnail_list_view(self, elements, level):
        return self._get_files_append(elements, "title", level)

    def _from_old_version(self, elements, level):
        return self._get_files_link(elements, "href", level)
