import time

from .join_scraper import JoinScraper
from selenium.common.exceptions import StaleElementReferenceException
from ..talker import Talker
from ..driver.support.driver_support import DriverSupport


class TextScraper(JoinScraper):
    """Scraper object focused on text content"""

    def __init__(self, driver, opts, nav_info, filter_obj=None, sleep=False):
        """Initializes TextScraper object

       :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic filter_obj: Filter object to use
        :param bool sleep: stops the program before scraping
        """
        super().__init__(driver, opts, nav_info, filter_obj, sleep)

    def _store_links(self, elements, directory) -> None:
        if not elements:
            return
        try:
            for el in elements:
                link = el.text.strip()
                self._join_links(directory, link)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)

    def scrape_items(self) -> list:
        if self._sleep:
            time.sleep(4)
        return DriverSupport.get_elements_all(self._driver, self._opts,
                                              self.nav_info.css_name,
                                              self.nav_info.wait_err_message)
