import time

from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
from ...talker import Talker


class DriverSupport:
    """Utility class for WebDriver"""
    MIMES = ""

    @staticmethod
    def get_elements_all(driver, opts, css_select, wait_err_message) -> list:
        """Retrieve a list of elements

        :param wait_err_message: Error message when elements doesn't show
        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param str css_select: css selector
        :return: list of found elements
        """
        should_wait = opts.get_wait()
        if should_wait:
            return DriverSupport.get_elements_wait(driver, opts,
                                                   css_select,
                                                   wait_err_message)
        else:
            return DriverSupport.get_elements(driver, css_select)

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

    @staticmethod
    def scroll_down(driver):
        """Scroll to bottom of page

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        driver.execute_script("window.scrollBy(0,document.body.scrollHeight);")

    @staticmethod
    def scroll_to_bottom(driver, opts, css_select, prev_elements=None) -> list:
        """Scroll recursively until bottom of page

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param str css_select: Selects all file/directory content
        :param list prev_elements: Previous list of elements
        :return:
        """
        if prev_elements is None:
            prev_elements = []
        DriverSupport.scroll_down(driver)
        time.sleep(opts.scroll_wait)
        elements = DriverSupport.get_elements_wait(driver, opts, css_select)
        if len(elements) > len(prev_elements):
            DriverSupport.scroll_to_bottom(driver, opts, css_select, elements)
        return elements
