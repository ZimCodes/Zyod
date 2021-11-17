import time

from ....driver.support.driver_support import DriverSupport
from selenium.common.exceptions import StaleElementReferenceException
from ....talker import Talker


class SubNavigation:
    """Scraper navigation object to hold navigation data"""

    def __init__(self, scrape_type, opts, css_extract,
                 css_download="", wait_err_message="navigation failed!", extra_task=None):
        """Initializes SubNavigation object

        :param extra_task: extra tasks to perform after clicking first element for download
        :param ScrapeType scrape_type: name of scraping technique
        :param Opts opts: Opts class
        :param str css_extract: the elements to extract
        :param str css_download: the elements to interact with to download
        :param wait_err_message: error message to show if no elements are found or present
        """
        self.id = scrape_type
        self._opts = opts
        self._css_select = css_extract
        self._css_download = css_download
        self._wait_err_message = wait_err_message
        self._extra_task = extra_task

    def get_elements(self, driver, should_wait=False) -> list:
        """Navigating GoIndex in List View

        :param WebDriver driver: Selenium WebDriver
        :param bool should_wait: enables waiting feature
        :return: list of found elements
        """
        if should_wait:
            return DriverSupport.get_elements_wait(driver, self._opts,
                                                   self._css_select,
                                                   self._wait_err_message)
        else:
            return DriverSupport.get_elements(driver, self._css_select)

    def download(self, driver, opts) -> None:
        """Download files located in current directory

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :return:
        """
        elements = self._get_download_elements(driver, opts)

        Talker.loading("Downloading Files", new_line=True)

        try:
            if self._extra_task:
                self._multiple_tasks(driver, opts, elements)
            else:
                SubNavigation._single_task(opts, elements)

        except StaleElementReferenceException:
            Talker.warning(f"Cannot find download button at {driver.current_url}. Webpage probably "
                           f"did not load up in time.", True)

    def _get_download_elements(self, driver, opts) -> list:
        """Retrieve all elements needed for downloading

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :return: list of elements related to downloading
        """
        opts.get_wait = lambda: 30
        return DriverSupport.get_elements_wait(driver, opts,
                                               self._css_download,
                                               f"Download element cannot be found at"
                                               f"{driver.current_url}!")

    @staticmethod
    def _single_task(opts, elements) -> None:
        """Download operation with a single action

        :param Opts opts: Opts class
        :param list elements: list of elements
        :return:
        """
        for el in elements:
            SubNavigation._download_step(opts, el)

    def _multiple_tasks(self, driver, opts, elements) -> None:
        """Download operation with multiple actions

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param list elements: list of elements
        :return:
        """
        for i, el in enumerate(elements):
            if i == 0:
                SubNavigation._download_step(opts, el)
            else:
                els = self._get_download_elements(driver, opts)
                SubNavigation._download_step(opts, els[i])
            self._extra_task(driver)

    @staticmethod
    def _download_step(opts, element) -> None:
        """The basic steps for downloading

        :param Opts opts: Opts class
        :param element: list of elements
        :return:
        """
        wait = opts.get_download_wait()
        if wait:
            if opts.verbose:
                Talker.loading(f"Waiting for {wait} seconds")
            time.sleep(wait)
        element.click()
