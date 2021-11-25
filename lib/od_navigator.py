import time
from .talker import Talker
from .od.identity.od_type import ODType
import lib.od.identity.goindex as go_index_id
import lib.od.identity.fodi as fodi_id
import lib.od.navigator.fodi as fodi_nav
import lib.od.navigator.goindex as go_index_nav
import lib.od.identity.zfile as zfile_id
import lib.od.navigator.zfile as zfile_nav
import lib.od.identity.gdindex as gd_index_id
import lib.od.navigator.gdindex as gd_index_nav


class OdNavigator:
    """OdScraper object retrieves the appropriate navigator to use for an OD"""

    def __init__(self, driver, opts):
        """Initializes OdScraper object

        :param WebDriver driver: Selenium Webdriver object
        :param Opts opts: Opts class
        """
        self._init_navigator(driver, opts)

    def _init_navigator(self, driver, opts) -> None:
        """Initializes the appropriate Scraper to use

        :param WebDriver driver: Selenium Webdriver object
        :param Opts opts: Opts class
        :return:
        """
        if opts.web_wait:
            Talker.loading("Providing time for OD to finish loading up")
            time.sleep(opts.web_wait)  # Provides time for OD to load up
            Talker.loading("Setting Up Navigator")
        if go_index_id.GoIndex.is_od(driver):
            self.navigator = go_index_nav.GoIndex(driver, opts)
        elif fodi_id.FODI.is_od(driver):
            self.navigator = fodi_nav.FODI(driver, opts)
        elif zfile_id.ZFile.is_od(driver):
            self.navigator = zfile_nav.ZFile(driver, opts)
        elif gd_index_id.GDIndex.is_od(driver):
            self.navigator = gd_index_nav.GDIndex(driver, opts)
        else:
            self.id = ODType.GENERIC
            self.navigator = None
