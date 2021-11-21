from .join_scraper import JoinScraper
from selenium.common.exceptions import StaleElementReferenceException
from ..talker import Talker


class TextScraper(JoinScraper):
    """Scraper object focused on text content"""

    def __init__(self, driver, opts, nav_info, filter_obj=None):
        """Initializes Texter object

       :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic filter_obj: Filter object to use
        """
        super().__init__(driver, opts, nav_info, filter_obj)

    def _store_links(self, elements, directory) -> None:
        try:
            for el in elements:
                link = el.text.strip()
                self._join_links(directory, link)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)
