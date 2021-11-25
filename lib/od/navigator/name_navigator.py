from .base_navigator import BaseNavigator
from ...driver.support.driver_support import DriverSupport


class NameNavigator(BaseNavigator):
    """NameNavigator object navigates ODs using the file/directory name"""

    def __init__(self, name, driver, opts, no_full_links=False):
        """Initializes BaseNavigator object

       :param name: type of OD
       :param WebDriver driver: Selenium Webdriver
       :param Opts opts: Opts class
       :param bool no_full_links: Whether or not correct file links can be recorded from this OD
       """
        super().__init__(name, driver, opts, no_full_links)

    def _get_nav_info_elements(self, nav_info) -> list:
        return DriverSupport.get_elements_all(self._driver, self._opts, nav_info.css_name,
                                              nav_info.wait_err_message)
