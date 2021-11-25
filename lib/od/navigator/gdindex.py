from .base_navigator import BaseNavigator
from ...asset.nav_info import NavInfo
from .nav_type import GDIndex as NavType
from ...scraper.join_scraper import JoinScraper
from ..identity.od_type import ODType


class GDIndex(BaseNavigator):
    """GDIndex Navigator object to navigate GDIndex ODs"""

    def __init__(self, driver, opts):
        """Initializes GDIndex Navigator

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        """
        super().__init__(ODType.GD_INDEX, driver, opts)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN,
                                       css_select="div a.v-list-item--link",
                                       css_download="div a.v-list-item--link "
                                                    "div.v-list-item__action a")]

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = JoinScraper(self._driver, self._opts, nav_info)
