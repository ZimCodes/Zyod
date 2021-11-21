from ...asset.directory import Directory
from ...download.downloader import Downloader
from ...driver.support.driver_support import DriverSupport


class BaseNavigator:
    """Base scraper abstract class for all Scrapers"""

    def __init__(self, name, driver, opts, no_full_links=False):
        """Initializes BaseNavigator object

        :param name: name of Navigator
        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param bool no_full_links: Whether or not file links can be recorded from this OD
        """
        self.id = name
        self._driver = driver
        self._scraper = None
        self._downloader = None
        self._nav_info_list = []
        self._opts = opts
        self.no_full_links = no_full_links

    def navigate(self, directory) -> tuple[list, list]:
        """Navigate an OD

        :param Directory directory: directory to navigate to
        :return Directory/Files on current page
        """
        elements = self._setup_navigate(directory)
        if elements and self._opts.do_download:
            self.download()
        results = self._scraper.get_results()
        return results

    def _setup_navigate(self, directory) -> list:
        """Set up navigation dependencies

        :param Directory directory: current parent Directory object
        :return: list of navigation elements
        """

        self._go_to_directory(directory)
        elements = None
        if not self._scraper:
            elements = self._setup_dependencies()
        elif self._opts.scroll:
            elements = self._scraper.scroll_to_bottom()
        if self._scraper:
            self._scraper.scrape(elements, directory)
        return elements

    def download(self) -> None:
        """Download contents from current directory"""
        self._downloader.download()

    def _setup_dependencies(self) -> list:
        """ Selects a navigation type to use

        :return: list of elements
        """
        self._prepare_nav_info_list()
        for nav_info in self._nav_info_list:
            opts = self._opts
            opts._wait = 15
            elements = DriverSupport.get_elements_all(self._driver, self._opts,
                                                      nav_info.css_select,
                                                      nav_info.wait_err_message)
            if elements:
                self._setup_scraper(nav_info)
                self._setup_downloader(nav_info)
                if self._opts.scroll:
                    elements = self._scraper.scroll_to_bottom(elements)
                return elements
        return []

    def _setup_scraper(self, nav_info) -> None:
        """Setup Scraper dependencies"""
        pass

    def _setup_downloader(self, nav_info) -> None:
        """Setup Downloader dependencies

        :param NavInfo nav_info: NavInfo object
        """
        self._downloader = Downloader(self._driver, self._opts, nav_info)

    def _prepare_nav_info_list(self) -> None:
        """Prepare a list of navigational instructions for different variations"""
        pass

    def _go_to_directory(self, directory) -> None:
        """Navigate to directory

        :param Directory directory: directory to navigate to
        :return:
        """
        if self._driver.current_url != directory.url:
            if directory.url is not None:
                self._driver.get(directory.url)
