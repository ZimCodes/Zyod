from .base_navigator import BaseNavigator
from .nav_type import OneDriveVercelIndex as NavType
from ..identity.od_type import ODType
from ...asset.nav_info import NavInfo
from ...scraper.text_scraper import TextScraper
from ...scraper.filters import OneDriveVercelIndex as ScrapeFilter

class OneDriveVercelIndex(BaseNavigator):
    """Navigation object for onedrive-vercel-index"""

    def __init__(self, driver, opts):
        """Initializes OneDriveVercelIndex navigation object

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: opts class
        """
        super().__init__(ODType.ONEDRIVE_VERCEL_INDEX, driver, opts)

    def _prepare_nav_info_list(self) -> None:
        self._nav_info_list = [NavInfo(NavType.MAIN,
                                       css_select=r"div.md\:col-span-7.space-x-2",
                                       css_name=r"div.md\:col-span-7.space-x-2",
                                       css_download='a[title="Download file"]')]

    def _setup_scraper(self, nav_info) -> None:
        self._scraper = TextScraper(self._driver, self._opts, nav_info, ScrapeFilter)

