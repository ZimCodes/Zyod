from .od.identity.od_type import ODType
import lib.od.identity.goindex as go_index_id
import lib.od.scraper.goindex as go_index_scraper


class OdScraper:
    """OdScraper object retrieves the appropriate scraper to use for an OD"""

    def __init__(self, driver, opts):
        """Initializes OdScraper object

        :param WebDriver driver: Selenium Webdriver object
        :param Opts opts: Opts class
        """
        self._init_scraper(driver, opts)

    def _init_scraper(self, driver, opts) -> None:
        """Initializes the appropriate Scraper to use

        :param WebDriver driver: Selenium Webdriver object
        :param Opts opts: Opts class
        :return:
        """
        if go_index_id.GoIndex.is_od(driver):
            self.scraper = go_index_scraper.GoIndex(driver, opts)
        else:
            self.id = ODType.GENERIC
            self.scraper = None
