from lib.od.identity.ods import ODType
import lib.od.identity.goindex as go_index_id
import lib.od.scraper.goindex as go_index_scraper


class OdScraper:
    """Identifies what type of Scrape Method to use"""

    def __init__(self, driver, max_depth):
        self._init_scraper(driver, max_depth)

    def _init_scraper(self, driver, max_depth):
        if go_index_id.GoIndex.is_od(driver):
            self.id = ODType.GO_INDEX
            self.scraper = go_index_scraper.GoIndex(max_depth, driver)
        else:
            self.id = ODType.GENERIC
            self.scraper = None
