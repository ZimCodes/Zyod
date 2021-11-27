from . import base_identity
from ...driver.support.driver_support import DriverSupport


class GoIndex(base_identity.BaseIdentity):
    """Identify if OD is type GoIndex"""

    @staticmethod
    def is_od(driver) -> bool:
        return (GoIndex._total_files_english(driver) or GoIndex._total_files_chinese(driver) or
                GoIndex._footer_link(driver) or
                GoIndex._script_tags(driver) or
                GoIndex._link_tags(driver) or
                GoIndex._older_version(driver))

    @staticmethod
    def _total_files_english(driver) -> bool:
        """
        The footer divider containing the total number of files
        :param driver: Webdriver
        :return: bool
        """
        elements = DriverSupport.get_elements(driver, r"div.is-divider[data-content~=Total]", "")
        return bool(elements)

    @staticmethod
    def _total_files_chinese(driver) -> bool:
        """
        The footer divider containing the total number of files
        :param driver: Webdriver
        :return: bool
        """
        elements = DriverSupport.get_elements(driver, r"div.is-divider[data-content~=共]", "")
        return bool(elements)

    @staticmethod
    def _footer_link(driver) -> bool:
        """
        Footer links to author resources.
        :param driver: Webdriver
        :return:bool
        """
        elements = DriverSupport.get_elements(driver, r"footer .tag[href]", "")
        return bool(elements)

    @staticmethod
    def _script_tags(driver) -> bool:
        """
        Script Tags ID
        :param driver: WebDriver
        :return: bool
        """
        elements = DriverSupport.get_elements(driver, r"script[src*=goindex]", "")
        return bool(elements)

    @staticmethod
    def _link_tags(driver) -> bool:
        """
        Link Tags ID
        :param driver: Webdriver
        :return: bool
        """
        elements = DriverSupport.get_elements(driver, r"link[href*=goindex]", "")
        return bool(elements)

    @staticmethod
    def _older_version(driver) -> bool:
        """
        Older version of this OD type
        :param driver: Webdriver
        :return: bool
        """
        return GoIndex._attr_check(driver, "form#search_bar_form[action]", "action", "/0:search")
