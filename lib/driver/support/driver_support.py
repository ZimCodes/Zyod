from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
from ...talker import Talker
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import StaleElementReferenceException, NoSuchElementException


class DriverSupport:
    """Utility class for WebDriver"""
    MIMES = ""

    @staticmethod
    def get_elements_all(driver, opts, css_select,
                         err_message="Unable to locate elements. Webpage probably "
                                     "did not load up in time.") -> list:
        """Retrieve a list of elements

        :param err_message: Error message when elements doesn't show
        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param str css_select: css selector
        :return: list of found elements
        """
        should_wait = opts.get_wait()
        if should_wait:
            return DriverSupport.get_elements_wait(driver, opts,
                                                   css_select,
                                                   err_message)
        else:
            return DriverSupport.get_elements(driver, css_select, err_message)

    @staticmethod
    def get_elements(driver, css_select, err_message="Unable to locate elements. Webpage probably "
                                                     "did not load up in time.") -> list:
        """Retrieve a list of page elements using CSS selectors

        :param WebDriver driver: Selenium WebDriver object
        :param str css_select: CSS selector
        :param str err_message: Error message for not finding elements
        :return: list of page elements
        """
        try:
            return driver.find_elements(By.CSS_SELECTOR, css_select)
        except StaleElementReferenceException:
            if err_message:
                Talker.warning(err_message, True)
        except NoSuchElementException:
            if err_message:
                Talker.warning(err_message, False)

    @staticmethod
    def get_element(driver, css_select, err_message="Unable to locate element. Webpage probably "
                                                    "did not load up in time."):
        """Retrieve a page element using CSS selector

        :param WebDriver driver: Selenium WebDriver object
        :param str css_select: CSS selector
        :param str err_message: Error message for not finding element
        :return a page element
        """
        try:
            return driver.find_element(By.CSS_SELECTOR, css_select)
        except StaleElementReferenceException:
            if err_message:
                Talker.warning(err_message, True)
        except NoSuchElementException:
            if err_message:
                Talker.warning(err_message, False)

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
        except TimeoutException | NoSuchElementException:
            if opts.verbose:
                Talker.warning(message, True)
        finally:
            return elements

    @staticmethod
    def right_click(driver, right_click_elem) -> None:
        """Mouse context click

        :param WebDriver driver: Selenium WebDriver
        :param WebElement right_click_elem: The element to right click on
        """
        ActionChains(driver).context_click(right_click_elem).perform()

    @staticmethod
    def global_scroll_down(driver) -> None:
        """Scroll to bottom of page

        :param WebDriver driver: Selenium WebDriver
        :return:
        """
        DriverSupport.execute_script(driver, "window.scrollBy(0,document.body.scrollHeight);")

    @staticmethod
    def scroll_to_element(driver, element) -> None:
        """Scroll until element is in view

        :param WebDriver driver: Selenium WebDriver
        :param WebElement element: element to scroll into view
        """
        driver.execute_script("arguments[0].scrollIntoView(true);", element)

    @staticmethod
    def execute_script(driver, js_script, *args) -> None:
        """Execute JavaScript code

        :param str js_script: JavaScript code to execute
        :param WebDriver driver: Selenium WebDriver
        :return:
        """

        driver.execute_script(js_script, args)
