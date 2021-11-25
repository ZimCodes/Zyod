from ..driver.support.driver_support import DriverSupport
from ..asset.directory import Directory
from ..asset.url import URL
from selenium.common.exceptions import StaleElementReferenceException
from ..talker import Talker


class JoinScraper:
    """Scraper object for scraping ODs"""

    def __init__(self, driver, opts, nav_info, filter_obj=None):
        """Initializes Scraper object

        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic filter_obj: Filter object to use
        """
        self._driver = driver
        self._opts = opts
        self._dirs = []
        self._files = []
        self._filter = filter_obj() if filter_obj else None
        self.nav_info = nav_info

    def scrape(self, elements, directory) -> list:
        """Begin Scraping OD

        :param list elements: list of elements scraped
        :param Directory directory: current Directory
        """

        if elements is None:
            elements = self.scrape_items()
            if not elements:
                # Refresh the page and try again
                self._driver.refresh()
                elements = self.scrape_items()
        self._store_links(elements, directory)
        return elements

    def scrape_items(self) -> list:
        """Retrieve scraped elements"""
        return DriverSupport.get_elements_all(self._driver, self._opts,
                                              self.nav_info.css_select,
                                              self.nav_info.wait_err_message)

    def _store_links(self, elements, directory) -> None:
        """ Appends the element link onto the current URL

        :param list elements: list of elements
        :param Directory directory: current parent folder
        :return:
        """
        try:
            for el in elements:
                link = el.get_attribute(self.nav_info.css_attr)
                self._join_links(directory, link)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)

    def _join_links(self, directory, link) -> None:
        """ Filter & join links together to make a new URL

        :param directory: current Parent directory
        :param link: new link to join together
        :return:
        """
        if JoinScraper._is_home(link):
            return
        link = self.apply_filter(link)
        if URL.is_a_file(link):
            self._files.append(URL.joiner(directory.url, link))
        else:
            new_level = directory.depth_level + 1
            if new_level < self._opts.depth:
                url = URL.joiner(directory.url, link)
                url.dir_transform()
                new_dir = Directory(new_level, url)
                self._dirs.append(new_dir)

    @staticmethod
    def _is_home(text):
        home_list = {'/', '.', '..', '../', './'}
        init_total = len(home_list)
        home_list.add(text)
        return init_total == len(home_list)

    def apply_filter(self, link):
        if self._filter:
            return self._filter.apply(link)
        return link

    def reset(self) -> None:
        """Reset the list fields"""
        self._files = []
        self._dirs = []

    def get_results(self) -> tuple[list, list]:
        """Retrieve the files/directories as a result of scraping

        :return: list of files and directories
        """
        return self._dirs, self._files
