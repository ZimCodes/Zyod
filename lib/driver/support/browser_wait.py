from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By


class BrowserWait:
    """Browser driver with waiting features"""

    @staticmethod
    def get_elements(driver, opts, css_select, message='navigation failed!'):
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
                print(message)
        finally:
            return elements
