from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class AList(BaseIdentity):
    """AList identifier object"""

    @staticmethod
    def is_od(driver) -> bool:
        return AList._title(driver) or AList._footer(driver) or AList._qr_code(driver) or \
               AList._image_logo(driver) or AList._noscript(driver) or AList._link_preload_tag(
            driver) or AList._toolbar_icons(driver) or AList._search_bar(driver)

    @staticmethod
    def _footer(driver) -> bool:
        """Find the id in the footer

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.footer div a:first-child", "")
        has_powered_tag = AList._text_check(element, "Powered by Alist")
        if not has_powered_tag:
            return False
        element = DriverSupport.get_element(driver, "div.chakra-stack div span + a", "")
        has_manage_tag = AList._text_check(element, "Manage")
        return has_manage_tag

    @staticmethod
    def _toolbar_icons(driver) -> bool:
        """Search for iconic Toolbar

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        elements = DriverSupport.get_elements(driver, "div.overlay div.chakra-stack span", "")
        if not elements:
            return False
        return len(elements) == 3

    @staticmethod
    def _title(driver) -> bool:
        """Find id in <title>

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "title", "")
        return AList._has_text(element, "Alist")

    @staticmethod
    def _link_preload_tag(driver) -> bool:
        """Check preload <link> for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        elements = DriverSupport.get_elements(driver, "link[rel=modulepreload][href]", "")
        if not elements:
            return False
        for el in elements:
            attr = el.get_attribute("href")
            has_id = "alist-web" in attr
            if has_id:
                return True
        return False

    @staticmethod
    def _qr_code(driver) -> bool:
        """Search for the QR code button

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "button.qrcode", "")
        return bool(element)

    @staticmethod
    def _search_bar(driver) -> bool:
        """Look for id in search bar placeholder

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        return AList._attr_check(driver, "input.ant-input[placeholder]", "placeholder", "搜索文件(夹)")

    @staticmethod
    def _noscript(driver) -> bool:
        """Look inside noscript for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "noscript", "")
        return AList._has_text(element, "alist-web")

    @staticmethod
    def _image_logo(driver) -> bool:
        """Search image logo for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        return AList._attr_check(driver, "img#logo[alt]", "alt", "AList")
