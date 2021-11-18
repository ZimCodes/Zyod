from .helper.parser import Parser
from ...asset.directory import Directory
from selenium.common.exceptions import StaleElementReferenceException
from .helper.empty import Empty
from ...talker import Talker


class BaseScraper:
    """Base scraper abstract class for all Scrapers"""

    def __init__(self, name, driver, opts, can_scrape=True, filter_obj=None):
        """Initializes BaseScraper object

        :param name: name of Scraper
        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param bool can_scrape: Whether Scraper have scraping capabilities for their OD types
        :param generic.Generic filter_obj: Filter object to use
        """
        self.id = name
        self.can_scrape = can_scrape
        self.driver = driver
        self._dirs = []
        self._files = []
        self._filter = filter_obj()
        self._nav_obj = None
        self._nav_list = []
        self._opts = opts
        self._accept_list = []

    def navigate(self, directory, should_wait) -> tuple[list, list]:
        """Navigate an OD

        :param bool should_wait:
        :param Directory directory: directory to navigate to
        """
        self._files = []
        self._dirs = []
        self._accept_list = []
        if self.driver.current_url != directory.link:
            self._go_to_url(directory.link)

        if not self._nav_obj:
            elements = self._choose_nav(should_wait)
        else:
            elements = self._nav_obj.scroll_to_bottom(self.driver)
            # elements = self._nav_obj.get_elements(self.driver, should_wait)
        self._clean_links(elements, directory.depth_level)
        if elements and self._opts.do_download:
            self.download()
        return self._dirs, self._files

    def download(self) -> None:
        """Download contents from current directory"""
        self._nav_obj.download(self.driver, self._opts, self._accept_list)

    def _clean_links(self, elements, depth_level) -> None:
        """Retrieve file/directory links with filters applied to them

        :param list elements: list of elements
        :param int depth_level: current depth level
        :return: filtered links of files/directories
        """
        pass

    def _choose_nav(self, should_wait=False) -> list:
        """ Selects a navigation type to use

        :param bool should_wait: whether or not to wait for elements to appear
        :return: list of elements
        """
        self._prepare_nav_list()
        for nav_obj in self._nav_list:
            elements = nav_obj.get_elements(self.driver, should_wait)
            if elements:
                self._nav_obj = nav_obj
                if self._opts.scroll:
                    elements = self._nav_obj.scroll_to_bottom(self.driver, elements)
                return elements
        self._nav_obj = Empty()
        self._nav_obj.id = None
        return []

    def _prepare_nav_list(self) -> None:
        """Prepare a list of navigational instructions for different variations"""
        pass

    def _go_to_url(self, url=None) -> None:
        """Navigate to webpage

        :param str url: the link to navigate to
        :return:
        """
        if url is not None:
            self.driver.get(url)

    def _files_append(self, elements, level) -> None:
        """ Appends the element link onto the current URL

        :param elements: list of elements
        :param level: the current depth folder the elements came from
        :return: a tuple of corrected files and directory links
        """
        try:
            for index, el in enumerate(elements):
                link = el.get_attribute(self._nav_obj.css_attr)
                if self._filter is not None:
                    link = self._filter.filter_link(link)
                if Parser.is_a_file(link):
                    if self._user_regex_filter(link):
                        self._accept_list.append(index)
                        self._files.append(Parser.join_url(self.driver.current_url, link))
                else:
                    new_level = level + 1
                    if new_level < self._opts.depth:
                        link = Parser.join_url(self.driver.current_url, link)
                        if not link.endswith('/'):
                            link += '/'

                        new_dir = Directory(new_level, link)
                        self._dirs.append(new_dir)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)

    def _files_link(self, elements, level) -> None:
        """Uses the link found in each element

        :param elements: list of dom elements
        :param level: the current depth folder the elements came from
        :return: None
        """
        try:
            for index, el in enumerate(elements):
                link = el.get_attribute(self._nav_obj.css_attr)
                if self._filter is not None:
                    link = self._filter.filter_link(link)
                if Parser.is_a_file(link):
                    if self._user_regex_filter(link):
                        self._accept_list.append(index)
                        self._files.append(link)
                else:
                    new_level = level + 1
                    if new_level < self._opts.depth:
                        if not link.endswith('/'):
                            link += '/'
                        new_dir = Directory(new_level, link)
                        self._dirs.append(new_dir)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", True)

    def _files_replace_path(self, elements, level) -> None:
        """Replaces the path of the URL with the path found in element

        :param elements:list of dom elements
        :param level: the current depth folder the elements came from
        :return: None
        """
        try:
            for index, el in enumerate(elements):
                link = el.get_attribute(self._nav_obj.css_attr)
                if self._filter is not None:
                    link = self._filter.filter_link(link)
                if Parser.is_a_file(link):
                    if self._user_regex_filter(link):
                        self._accept_list.append(index)
                        self._files.append(Parser.join_path(self.driver.current_url, link))
                else:
                    new_level = level + 1
                    if new_level < self._opts.depth:
                        link = Parser.join_path(self.driver.current_url, link)
                        if not link.endswith('/'):
                            link = link + '/'
                        new_dir = Directory(new_level, link)
                        self._dirs.append(new_dir)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", True)

    def _user_regex_filter(self, link) -> bool:
        """User defined regex to filter out unwanted files

        :param str link: file like to check
        :return: Whether the file link is acceptable
        """
        if self._opts.accept is not None:
            return self._opts.accept.search(link)
        elif self._opts.reject is not None:
            return not self._opts.reject.search(link)
        else:
            return True
