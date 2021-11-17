from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
from ...talker import Talker


class DriverSupport:
    """Utility class for WebDriver"""
    MIMES = ""

    @staticmethod
    def get_elements(driver, css_select) -> list:
        """Retrieve a list of page elements using CSS selectors

        :param WebDriver driver: Selenium WebDriver object
        :param str css_select: CSS selector
        :return: list of page elements
        """
        return driver.find_elements(By.CSS_SELECTOR, css_select)

    @staticmethod
    def get_element(driver, css_select):
        """Retrieve a page element using CSS selector

        :param WebDriver driver: Selenium WebDriver object
        :param str css_select: CSS selector
        :return: list of page elements
        """
        return driver.find_element(By.CSS_SELECTOR, css_select)

    @staticmethod
    def get_elements_wait(driver, opts, css_select, message='Element cannot be found! '
                                                            'Webpage might not be loaded.') -> list:
        """Retrieve a list of page elements using CSS selectors while waiting for elements to appear

        :param WebDriver driver: Selenium WebDriver object
        :param Opts opts: Opts class
        :param str css_select: CSS selector
        :param str message: error message to display when no elements are found
        :return: list of page elements
        """
        elements = []

        try:
            elements = WebDriverWait(driver, opts.get_wait()).until(
                lambda el:
                el.find_elements(
                    By.CSS_SELECTOR,
                    css_select
                ))
        except TimeoutException:
            if opts.verbose:
                Talker.warning(message, True)
        finally:
            return elements
