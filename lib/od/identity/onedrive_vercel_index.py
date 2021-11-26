from .base_identity import BaseIdentity
from ...driver.support.driver_support import DriverSupport


class OneDriveVercelIndex(BaseIdentity):
    """OneDriveVercelIndex object to identify said OD"""

    @staticmethod
    def is_od(driver) -> bool:
        return OneDriveVercelIndex._footer(driver) or OneDriveVercelIndex._meta_tag(driver) or \
               OneDriveVercelIndex._flag_crumb(driver)

    @staticmethod
    def _footer(driver) -> bool:
        """Check for the 'powered by onedrive-vercel-index' tagline

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        return OneDriveVercelIndex._attr_check(driver, "main + div a[href]", "href",
                                               "spencerwooo/onedrive-vercel-index")

    @staticmethod
    def _meta_tag(driver) -> bool:
        """Search meta tag for id

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, 'meta[content="OneDrive Vercel Index"]', "")
        return bool(element)

    @staticmethod
    def _flag_crumb(driver) -> bool:
        """Finds id of the through its iconic flag

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        element = DriverSupport.get_element(driver, r"div.dark\:text-gray-300 div a", "")
        return OneDriveVercelIndex._text_check(element, "🚩 Home")
