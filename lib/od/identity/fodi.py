from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class FODI(BaseIdentity):
    """FODI object for identifying FODI OD types"""

    @staticmethod
    def is_od(driver) -> bool:
        """Check if current OD is FODI"""
        return FODI._script_tags(driver) or FODI._check_headers(driver)

    @staticmethod
    def _script_tags(driver) -> bool:
        """Script Tags ID

        :param WebDriver driver: Selenium WebDriver
        :return True if OD contains '/fodi/' in <script>, False otherwise
        """
        elements = DriverSupport.get_elements(driver, "script")
        for el in elements:
            if "/fodi/" in el.text:
                return True
        return False

    @staticmethod
    def _check_headers(driver) -> bool:
        """Check if headers are present

        :param WebDriver driver: Selenium WebDriver
        :return: True if all headers are exist, False otherwise
        """
        return FODI._items_header(driver) and FODI._time_header(driver) and FODI._size_header(
            driver)

    @staticmethod
    def _items_header(driver) -> bool:
        """Check if 'ITEMS' table head exist

        :param WebDriver driver: Selenium WebDriver
        :return: True if table header exist. False otherwise
        """
        element = DriverSupport.get_element(driver, "div.right div.list-header div.file span.name")
        return element.text.strip() == 'ITEMS'

    @staticmethod
    def _time_header(driver) -> bool:
        """Check if 'TIME' table head exist

        :param WebDriver driver: Selenium WebDriver
        :return: True if table header exist, False otherwise
        """
        element = DriverSupport.get_element(driver, "div.right div.list-header div.file span.time")
        return element.text.strip() == 'TIME'

    @staticmethod
    def _size_header(driver) -> bool:
        """Check if 'SIZE' table head exist

        :param WebDriver driver: Selenium WebDriver
        :return:True if table header exist, False otherwise
        """
        element = DriverSupport.get_element(driver, "div.right div.list-header div.file span.size")
        return element.text.strip() == 'SIZE'
