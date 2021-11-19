from .base_navigator import BaseNavigator
from ..identity.od_type import ODType
from .helper.sub_navigation import SubNavigation
from .nav_type import FODI as NavType
from ...driver.support.driver_support import DriverSupport


class FODI(BaseNavigator):
    """Scroper operations for FODI OD types"""

    def __init__(self, driver, opts):
        """"""
        super().__init__(ODType.FODI, driver, opts, False)

    def download(self) -> None:
        self._nav_obj.download(self.driver, self._opts, self._accept_list)

    def _prepare_nav_list(self) -> None:
        nav_list = [SubNavigation(NavType.MAIN, self._opts,
                                  css_download="div#file-list div.row.file-wrapper",
                                  extra_task=FODI._main_extra_task)]

    @staticmethod
    def _main_extra_task(driver) -> None:
        """Extra downloading instructions

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.btn.download")
        element.click()
        FODI._go_back(driver)

    @staticmethod
    def _go_back(driver) -> None:
        back_btn = DriverSupport.get_element(driver, "div.header ion-icon#arrow-back")
        arrow_status = back_btn.get_attribute("style")
        if "black" in arrow_status:
            back_btn.click()
        else:
            home_btn = DriverSupport.get_element(driver, "ion-icon#main-page")
            home_btn.click()
