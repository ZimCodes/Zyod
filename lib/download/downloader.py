import time
from ..talker import Talker
from selenium.common.exceptions import StaleElementReferenceException
from ..driver.support.driver_support import DriverSupport


class Downloader:
    """Base class for Downloader object"""

    def __init__(self, driver, opts, nav_info, download_filter_obj=None):
        """Initializes Downloader object

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param NavInfo nav_info: NavInfo class
        :param download_filter_obj: download filter to use
        """
        self._driver = driver
        self._opts = opts
        self._nav_info = nav_info
        self._filter_obj = download_filter_obj() if download_filter_obj else None

    def download(self) -> None:
        """Download files located in current directory

        :return:
        """
        elements = self._get_download_elements(self._driver, self._opts)
        if self._filter_obj:
            elements = self._filter_obj.apply(elements)

        Talker.loading("Downloading Files", new_line=True)

        try:
            if self._nav_info.extra_task:
                self._multiple_tasks(self._driver, self._opts, elements)
            else:
                Downloader._single_task(self._opts, elements)

        except StaleElementReferenceException:
            Talker.warning(f"Cannot find download button at {self._driver.current_url}. Webpage "
                           f"probably "
                           f"did not load up in time.", True)

    def _get_download_elements(self, driver, opts) -> list:
        """Retrieve all elements needed for downloading

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :return: list of elements related to downloading
        """
        opts.get_wait = lambda: 30
        return DriverSupport.get_elements_wait(driver, opts,
                                               self._nav_info.css_download,
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
            Downloader._download_step(opts, el)

    def _multiple_tasks(self, driver, opts, elements) -> None:
        """Download operation with multiple actions

        :param WebDriver driver: Selenium WebDriver
        :param Opts opts: Opts class
        :param list elements: list of elements
        :return:
        """
        for i, el in enumerate(elements):
            if i == 0:
                Downloader._download_step(opts, el)
            else:
                els = self._get_download_elements(driver, opts)
                Downloader._download_step(opts, els[i])
            self._nav_info.extra_task(driver)

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
