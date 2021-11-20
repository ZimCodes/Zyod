from .base_scraper import BaseScraper
from ..asset.directory import Directory
from .helper.parser import Parser
from selenium.common.exceptions import StaleElementReferenceException
from ..talker import Talker


class TextScraper(BaseScraper):
    """Scraper object focused on text content"""

    def __init__(self, driver, opts, nav_info, filter_obj=None):
        """Initializes Texter object

       :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic filter_obj: Filter object to use
        """
        super().__init__(driver, opts, nav_info, filter_obj)

    def _store_links(self, elements, depth_level) -> None:
        try:
            for index, el in enumerate(elements):
                text = el.text.strip()
                if self._filter:
                    text = self._filter.apply(text)
                if Parser.is_a_file(text):
                    self._files.append(text)
                else:
                    new_level = depth_level + 1
                    if new_level < self._opts.depth:
                        if not text.endswith('/'):
                            text += '/'
                        new_dir = Directory(new_level, text)
                        self._dirs.append(new_dir)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)
