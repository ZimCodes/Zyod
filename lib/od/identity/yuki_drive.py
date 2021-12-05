from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class YukiDrive(BaseIdentity):
    """Identify Yuki Drive ODs"""

    @staticmethod
    def is_od(driver) -> bool:
        return YukiDrive._title_tag(driver) or YukiDrive._noscript(driver) or \
               YukiDrive._side_drawer(driver) or YukiDrive._file_sections(driver)

    @staticmethod
    def _title_tag(driver) -> bool:
        """Check <title> for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "title", "")
        return YukiDrive._text_check(element, "Yuki Drive")

    @staticmethod
    def _file_sections(driver) -> bool:
        """Look for the presence of file sections

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.v-list div:first-child div.v-subheader",
                                            "")
        has_file_section = YukiDrive._text_check(element, "文件")
        if has_file_section:
            return True
        return YukiDrive._text_check(element, "文件夹")

    @staticmethod
    def _noscript(driver) -> bool:
        """Check <noscript> for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        elements = DriverSupport.get_elements(driver, "noscript", "")
        for el in elements:
            has_id = YukiDrive._has_text(el, "Yuki Drive")
            if has_id:
                return True
        return False

    @staticmethod
    def _side_drawer(driver) -> bool:
        """Check the side drawer for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, "div.v-navigation-drawer__content "
                                                    "div.v-list-group__header "
                                                    "div.v-list-item__content "
                                                    "div.v-list-item__title",
                                            "")
        return YukiDrive._text_check(element, "驱动器")
