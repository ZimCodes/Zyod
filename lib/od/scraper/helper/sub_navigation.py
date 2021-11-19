import time

from ....driver.support.driver_support import DriverSupport
from selenium.common.exceptions import StaleElementReferenceException
from ....talker import Talker


class SubNavigation:
    """Scraper navigation object to hold navigation data"""

    def __init__(self, scrape_type, opts, css_extract,
                 css_download="", css_link="href", wait_err_message="navigation failed!",
                 extra_task=None):
        """Initializes SubNavigation object

        :param extra_task: extra tasks to perform after clicking first element for download
        :param ScrapeType scrape_type: name of scraping technique
        :param Opts opts: Opts class
        :param str css_extract: the elements to extract
        :param str css_download: the elements to interact with to download
        :param str css_link: the attribute needed to extract links
        :param wait_err_message: error message to show if no elements are found or present
        """
        self.id = scrape_type
        self._opts = opts
        self._css_select = css_extract
        self._css_download = css_download
        self.css_attr = css_link
        self._wait_err_message = wait_err_message
        self._extra_task = extra_task

    def get_elements(self, driver) -> list:
        """Navigating GoIndex in List View

        :param WebDriver driver: Selenium WebDriver
        :return: list of found elements
        """
        should_wait = self._opts.get_wait()
        if should_wait:
            return DriverSupport.get_elements_wait(driver, self._opts,
                                                   self._css_select,
                                                   self._wait_err_message)
        else:
            return DriverSupport.get_elements(driver, self._css_select)

    def download(self, driver, opts, accept_list) -> None:
        """Download files located in current directory

        :param list accept_list: index of files acceptable for download
        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :return:
        """
        elements = self._get_download_elements(driver, opts)

        Talker.loading("Downloading Files", new_line=True)

        try:
            if self._extra_task:
                self._multiple_tasks(driver, opts, elements, accept_list)
            else:
                SubNavigation._single_task(opts, elements, accept_list)

        except StaleElementReferenceException:
            Talker.warning(f"Cannot find download button at {driver.current_url}. Webpage probably "
                           f"did not load up in time.", True)

    def scroll_to_bottom(self, driver, elements=None) -> list:
        """Scroll until bottom of page is reached

        :param WebDriver driver: Selenium WebDriver
        :param list elements: list of elements
        :return:list of all elements
        """
        return DriverSupport.scroll_to_bottom(driver, self._opts, self._css_select, elements)

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
    def _single_task(opts, elements, accept_list) -> None:
        """Download operation with a single action

        :param Opts opts: Opts class
        :param list elements: list of elements
        :param list accept_list: index of files acceptable for download
        :return:
        """
        if opts.accept or opts.reject:
            regular_index = 0  # switch back to regular indexing
            for index in accept_list:
                if index < len(elements):
                    SubNavigation._download_step(opts, elements[index])
                else:
                    SubNavigation._download_step(opts, elements[regular_index])
                regular_index += 1
        else:
            for el in elements:
                SubNavigation._download_step(opts, el)

    def _multiple_tasks(self, driver, opts, elements, accept_list) -> None:
        """Download operation with multiple actions

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param list elements: list of elements
        :param list accept_list: index of files acceptable for download
        :return:
        """
        if opts.accept or opts.reject:
            for i, pos in enumerate(accept_list):
                if i == 0:
                    SubNavigation._download_step(opts, elements[pos])
                else:
                    elements = self._get_download_elements(driver, opts)
                    SubNavigation._download_step(opts, elements[pos])
                self._extra_task(driver)
        else:
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
