from .base_scraper import BaseScraper
from ..asset.directory import Directory
from selenium.common.exceptions import StaleElementReferenceException
from ..talker import Talker
from .helper.parser import Parser


class JoinScraper(BaseScraper):
    """Scraper with the ability yo join links together"""

    def __init__(self, driver, opts, nav_info, filter_obj=None):
        """Initializes JoinScraper

        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo object
        :param generic.Generic filter_obj: Filter object to use
        """
        super().__init__(driver, opts, nav_info, filter_obj)

    def _store_links(self, elements, depth_level) -> None:
        try:
            for index, el in enumerate(elements):
                link = el.get_attribute(self._nav_info.css_attr)
                if self._filter:
                    link = self._filter.apply(link)
                if Parser.is_a_file(link):
                    self._files.append(Parser.joiner(self._driver.current_url, link))
                else:
                    new_level = depth_level + 1
                    if new_level < self._opts.depth:
                        link = Parser.joiner(self._driver.current_url, link)
                        if not link.endswith('/'):
                            link += '/'
                        new_dir = Directory(new_level, link)
                        self._dirs.append(new_dir)
        except StaleElementReferenceException:
            Talker.warning("Element cannot be found on current page!", new_line=True)
