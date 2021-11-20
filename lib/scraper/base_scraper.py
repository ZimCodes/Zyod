from ..driver.support.driver_support import DriverSupport


class BaseScraper:
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
        self._nav_info = nav_info

    def scrape(self, elements, depth_level) -> None:
        self._reset_lists()
        if elements is None:
            elements = BaseScraper.get_elements(self._driver, self._opts, self._nav_info)
        self._store_links(elements, depth_level)

    def _store_links(self, elements, depth_level) -> None:
        """ Appends the element link onto the current URL

        :param list elements: list of elements
        :param int depth_level: the current depth folder the elements came from
        :return:
        """
        pass

    def scroll_to_bottom(self, elements=None) -> list:
        """Scroll until bottom of page is reached

        :param list elements: list of elements
        :return:list of all elements
        """
        return DriverSupport.scroll_to_bottom(self._driver, self._opts, self._nav_info.css_select,
                                              elements)

    @staticmethod
    def get_elements(driver, opts, nav_info) -> list:
        """Navigating GoIndex in List View

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo class
        :return: list of found elements
        """
        should_wait = opts.get_wait()
        if should_wait:
            return DriverSupport.get_elements_wait(driver, opts,
                                                   nav_info.css_select,
                                                   nav_info.wait_err_message)
        else:
            return DriverSupport.get_elements(driver, nav_info.css_select)

    def _reset_lists(self) -> None:
        """Reset the list fields"""
        self._files = []
        self._dirs = []

    def get_results(self) -> tuple[list, list]:
        """Retrieve the files/directories as a result of scraping

        :return: list of files and directories
        """
        return self._dirs, self._files
