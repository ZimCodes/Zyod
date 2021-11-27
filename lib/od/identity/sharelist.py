from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class ShareList(BaseIdentity):
    """Identify ShareList ODs"""

    @staticmethod
    def is_od(driver) -> bool:
        return ShareList._title_tag(driver) or ShareList._footer_check(driver) or \
               ShareList._header_title(driver)

    @staticmethod
    def _title_tag(driver) -> bool:
        """Find id in <title>

        :param WebDriver driver: Selenium WebDriver
        """
        element = DriverSupport.get_element(driver, "title", "")
        return ShareList._text_check(element, "ShareList")

    @staticmethod
    def _footer_check(driver) -> bool:
        """Check id for footer items

        :param WebDriver driver: Selenium WebDriver
        """
        has_repo = ShareList._attr_check(driver, "footer p a:first-child[href]", "href",
                                         "reruin/sharelist")
        element = DriverSupport.get_element(driver, "footer p a:nth-child(2)[href]", "")
        has_manage = ShareList._text_check(element, "Manage")
        if not has_manage:
            has_manage = ShareList._text_check(element, "管理")
        return has_manage and has_repo

    @staticmethod
    def _header_title(driver) -> bool:
        """Find id through the header title

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.wrap > a", "")
        has_title = ShareList._text_check(element, "ShareList", False)
        if not has_title:
            element = DriverSupport.get_element(driver, "div.drive-header__name", "")
            has_title = ShareList._text_check(element, "ShareList", False)
        return has_title
