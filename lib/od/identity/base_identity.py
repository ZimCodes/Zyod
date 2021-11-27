from ...driver.support.driver_support import DriverSupport


class BaseIdentity:
    """Base for identifying ODs"""

    @staticmethod
    def is_od(driver) -> bool:
        """Determines if current OD is this OD"""
        pass

    @staticmethod
    def _text_check(element, match_text, case_sensitive=True) -> bool:
        """Checks the text of an element

        :param WebElement element: the element to check
        :param str match_text: The text to match
        :return: True if element text matches False otherwise
        """
        if element:
            if case_sensitive:
                return element.text.strip() == match_text
            else:
                return element.text.strip().lower() == match_text.lower()
        else:
            return False

    @staticmethod
    def _attr_check(driver, css_element, attr, match_text) -> bool:
        element = DriverSupport.get_element(driver, css_element, "")
        if not element:
            return False
        attr_text = element.get_attribute(attr)
        return match_text in attr_text
