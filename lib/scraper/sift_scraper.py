import time
from .text_scraper import TextScraper
from ..driver.support.driver_support import DriverSupport
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By


class SiftScraper(TextScraper):
    """SiftScraper object to filter out DOM elements"""

    def __init__(self, driver, opts, nav_info, file_filter=None, sleep=False):
        """Initializes SiftScraper object

       :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic file_filter: Filter object to use for files
        :param bool sleep: stops the program before scraping
        """
        super().__init__(driver, opts, nav_info, file_filter, sleep)

    def scrape_items(self) -> list:
        if self._sleep:
            time.sleep(4)
        elements_select = []
        elements_to_keep = []
        elements_name = []
        child_elem = None

        elements_select = DriverSupport.get_elements_all(self._driver, self._opts,
                                                         self.nav_info.css_select,
                                                         self.nav_info.wait_err_message)
        elements_name = DriverSupport.get_elements_all(self._driver, self._opts,
                                                       self.nav_info.css_name,
                                                       self.nav_info.wait_err_message)
        for i, el in enumerate(elements_select):
            try:
                child_elem = el.find_element(By.CSS_SELECTOR, self.nav_info.css_rej_filter)
            except NoSuchElementException:
                elements_to_keep.append(elements_name[i])
        return elements_to_keep
