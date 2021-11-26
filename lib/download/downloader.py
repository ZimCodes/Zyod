import time
from ..talker import Talker
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
        elements = self.get_download_elements(self._driver, self._opts)
        if not elements:
            return
        if self._filter_obj:
            elements = self._filter_obj.apply(elements)

        Talker.loading("Downloading Files", new_line=True)

        if self._nav_info.extra_task:
            self._multiple_tasks(elements)
        else:
            Downloader._single_task(self._opts, elements)

    def right_click_download(self) -> None:
        """Download files using the right click's context menu"""
        elements = DriverSupport.get_elements_all(self._driver,
                                                  self._opts,
                                                  self._nav_info.css_select,
                                                  f"Download Elements "
                                                  f"cannot be found at "
                                                  f"{self._driver.current_url}")
        if self._filter_obj:
            elements = self._filter_obj.apply(elements)
        context_download_element = DriverSupport.get_element(self._driver,
                                                             self._nav_info.css_download)

        for el in elements:
            if self._opts.scroll:
                DriverSupport.scroll_to_element(self._driver, el)
                if self._opts.scroll_wait:
                    time.sleep(self._opts.scroll_wait)
            DriverSupport.right_click(self._driver, el)
            context_download_element.click()

    def get_download_elements(self, driver, opts) -> list:
        """Retrieve all elements needed for downloading

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

    def _multiple_tasks(self, elements) -> None:
        """Download operation with multiple actions

        :param list elements: list of elements
        :return:
        """
        for i, el in enumerate(elements):
            if i == 0:
                Downloader._download_step(self._opts, el)
            else:
                els = self.get_download_elements(self._driver, self._opts)
                if self._filter_obj:
                    els = self._filter_obj.apply(els)
                Downloader._download_step(self._opts, els[i])
            self._nav_info.extra_task(self._driver)

    def multi_task_lazy(self, driver, opts, index):
        els = self.get_download_elements(driver, opts)
        if self._filter_obj:
            els = self._filter_obj.apply(els)
        Downloader._download_step(opts, els[index])
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
