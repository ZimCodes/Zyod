class BaseIdentity:
    """Base for identifying ODs"""

    @staticmethod
    def is_od(driver) -> bool:
        """Determines if current OD is this OD"""
        pass

    @staticmethod
    def text_check(element, match_text) -> bool:
        """Checks the text of an element

        :param WebElement element: the element to check
        :param str match_text: The text to match
        :return: True if element text matches False otherwise
        """
        if element:
            return element.text.strip() == match_text
        else:
            return False
