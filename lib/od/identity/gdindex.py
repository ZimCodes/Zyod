from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class GDIndex(BaseIdentity):
    """Identify GDIndex type ODs"""

    @staticmethod
    def is_od(driver) -> bool:
        return GDIndex.title(driver) or GDIndex.header(driver) or GDIndex.repo_link(
            driver) or GDIndex.script_tag(driver) or GDIndex.link_tag(driver)

    @staticmethod
    def title(driver) -> bool:
        """Look at title for OD id

        :param WebDriver driver: Selenium WebDriver
        :return True if id is found, False otherwise
        """
        element = DriverSupport.get_element(driver, "title", "")
        return GDIndex._text_check(element, "GDIndex")

    @staticmethod
    def header(driver) -> bool:
        """Look at header for id

        :param WebDriver driver: Selenium WebDriver
        """
        element = DriverSupport.get_element(driver, "div.v-toolbar__title span", "")
        return GDIndex._text_check(element, "GDIndex")

    @staticmethod
    def repo_link(driver) -> bool:
        """Look at header for link to repository

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        return GDIndex._attr_check(driver, "div.v-toolbar__items a[href]", "href",
                                   "maple3142/GDIndex")

    @staticmethod
    def script_tag(driver) -> bool:
        """Look for id in script tags

        :param WebDriver driver: selenium WebDriver
        """
        return GDIndex._attr_check(driver, "script[src*=gdindex]", "src", "gdindex")

    @staticmethod
    def link_tag(driver) -> bool:
        """Look for id in <link> tag

        :param WebDriver driver: Selenium WebDriver
        """
        return GDIndex._attr_check(driver, "link[href*=gdindex]", "href", "gdindex")
