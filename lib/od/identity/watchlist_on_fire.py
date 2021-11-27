from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class WatchListOnFire(BaseIdentity):
    """Watchlist on fire identity object"""

    @staticmethod
    def is_od(driver) -> bool:
        return WatchListOnFire._header_title(driver) and WatchListOnFire._external_link(driver)

    @staticmethod
    def _header_title(driver) -> bool:
        """Check header title for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "a.navbar-brand", "")
        return WatchListOnFire._has_text(element, "watchlist on fire", False)

    @staticmethod
    def _external_link(driver) -> bool:
        """Check external footer link for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        return WatchListOnFire._attr_check(driver, "footer p:nth-child(2) a[href]", "href",
                                           "pmny.in")
