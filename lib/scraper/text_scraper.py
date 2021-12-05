import time

from .join_scraper import JoinScraper
from ..driver.support.driver_support import DriverSupport


class TextScraper(JoinScraper):
    """Scraper object focused on text content"""

    def __init__(self, driver, opts, nav_info, file_filter=None, sleep=False):
        """Initializes TextScraper object

       :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic file_filter: Filter object to use for files
        :param bool sleep: stops the program before scraping
        """
        super().__init__(driver, opts, nav_info, file_filter, sleep)

    def _join_loop(self, element, directory) -> None:
        link = element.text.strip()
        self._join_links(directory, link)

    def scrape_items(self) -> list:
        if self._sleep:
            time.sleep(4)
        return DriverSupport.get_elements_all(self._driver, self._opts,
                                              self.nav_info.css_name,
                                              self.nav_info.wait_err_message)
