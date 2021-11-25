from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class ZFile(BaseIdentity):
    """ZFile OD Identifier object"""

    @staticmethod
    def is_od(driver) -> bool:
        return ZFile._uniq_header(driver) or ZFile._table_header(driver) or ZFile._uniq_column(
            driver) or ZFile._uniq_italics(driver)

    @staticmethod
    def _uniq_header(driver) -> bool:
        """Search for unique ZFile header CSS selector

        :param WebDriver driver: Selenium WebDriver
        :return: True if selector exists, False otherwise
        """
        element = DriverSupport.get_element(driver, ".zfile-header")
        return element

    @staticmethod
    def _uniq_column(driver) -> bool:
        """Search for unique ZFile table column CSS Selector

        :param WebDriver driver: Selenium WebDriver
        :return True if selector exists, False otherwise
        """
        element = DriverSupport.get_element(driver, ".zfile-table-col-name")
        return element

    @staticmethod
    def _uniq_italics(driver) -> bool:
        """Search for unique ZFile italics CSS Selector

        :param WebDriver driver: Selenium WebDriver
        :return True if selector exists, False otherwise
        """
        element = DriverSupport.get_element(driver, "i.zfile-margin-left-5", "")
        return element

    @staticmethod
    def _table_header(driver) -> bool:
        """Table header name column

        :param WebDriver driver: Selenium WebDriver
        :return: True if column name is appropriate, False otherwise
        """
        element = DriverSupport.get_element(driver, "thead th div span:not(.caret-wrapper)", "")
        return ZFile.text_check(element, "文件名")
