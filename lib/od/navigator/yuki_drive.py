from .base_navigator import BaseNavigator
from ...asset.nav_info import NavInfo
from .nav_type import YukiDrive as NavType
from ..identity.od_type import ODType
from ...scraper.text_scraper import TextScraper


class YukiDrive(BaseNavigator):
    """Navigator for Yuki Drive ODs"""

    def __init__(self, driver, opts):
        """Initializes Yuki Drive Navigator

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        """
        super().__init__(ODType.YUKI_DRIVE, driver, opts, True)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN,
                                       css_select="div.v-list div[role='listbox'] div[role] "
                                                  "div.v-list-item__title, "
                                                  "div.v-list div[role='listbox']"
                                                  " a[role] div.v-list-item__title",
                                       css_name="div.v-list div[role='listbox'] div[role] "
                                                "div.v-list-item__title, "
                                                "div.v-list div[role='listbox']"
                                                " a[role] div.v-list-item__title",
                                       css_download="div.v-list div[role='listbox'] a.v-btn")]

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = TextScraper(self._driver, self._opts, nav_info)
