from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class GONEList(BaseIdentity):
    """GONEList object for identifying GONEList"""

    @staticmethod
    def is_od(driver) -> bool:
        return GONEList._header_title(driver) or GONEList._footer(
            driver) or GONEList._search_options_check(driver)

    @staticmethod
    def _header_title(driver) -> bool:
        """Find the id in the header

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div h1", "")
        return GONEList._text_check(element, "GONEList")

    @staticmethod
    def _footer(driver) -> bool:
        """Find the id in the Footer

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "#footer span a:first-child", "")
        return GONEList._text_check(element, "GONEList")

    @staticmethod
    def _search_options_check(driver) -> bool:
        """Check if search options exists

        :param WebDriver driver: Selenium WebDriver
        :return:
        """

        element = DriverSupport.get_element(driver, "div.search-container button:first-child "
                                                    "span", "")
        has_global_opt = GONEList._text_check(element, "全局")

        element = DriverSupport.get_element(driver, "div.search-container button:nth-child(2) span")
        return GONEList._text_check(element, "当前") and has_global_opt
