"""The beginning of the Zyod program"""
import time

from lib.main_driver import MainDriver
from lib.od_navigator import OdNavigator
from lib.opts import Opts
from lib.writer import Writer
from lib.asset.directory import Directory
from lib.asset.url import URL
from selenium.common.exceptions import WebDriverException
from urllib3.exceptions import MaxRetryError
from lib.talker import Talker


class Program:
    """Program Class manages the entire Zyod application."""

    def __init__(self):
        """Program class constructor to initialize object.

        _opts: Opts
            Parsed command line options object
        _driver: WebDriver
            Selenium WebDriver object
        _total_files: set
            Links of files retrieved from all URLs
        _total_dirs: set
            Links of directories retrieved from all URLs
        _navigator: Navigator
            Scraper object
        _writer: Writer
            Writer object
        """
        self._opts = None
        self._driver = None
        self._total_files = set()
        self._total_dirs = set()
        self._navigator = None
        self._writer = None

    def start(self) -> None:
        """Starts the Zyod app"""
        self._init_items()
        for url in self._opts.urls:
            self._go_to_page(url)
            self._init_navigation()
            self._scrape_and_download(url)
        self._output_to_file()
        self._shutdown()

    def _init_items(self) -> None:
        """Initializes constant objects"""
        self._opts = Opts()  # commandline args
        Talker.loading('Initializing constants')
        self._writer = Writer(self._opts.output)
        self._driver = MainDriver.get_driver(self._opts)  # Webdriver
        self._driver.set_page_load_timeout(15)
        if self._opts.verbose:
            Talker.complete('Constants are loaded')

    def _init_navigation(self) -> None:
        """Initializes non-constant objects"""
        if self._opts.verbose:
            Talker.loading('Initializing Scraper')
        scraper_obj = OdNavigator(self._driver, self._opts)
        self._navigator = scraper_obj.navigator
        if self._opts.verbose:
            Talker.complete('Scraper finalized')
        Talker.arrow_header_info('Scrape Method', self._navigator.id.name, True)

    def _scrape_and_download(self, url: str) -> None:
        """Scrape and/or download from OD

        :param str url: starting link to OD
        """
        if self._navigator.no_full_links and not self._opts.dont_record:
            Talker.warning(f"Recording full file links from {self._navigator.id.name} ODs are not "
                           f"supported! However, the file names can be.")
            answer = input("Would you like to pursue this instead? (y,n)")
            if answer not in 'yes':
                exit(0)

        if not self._navigator.no_full_links:

            self._navigate_recurse(url, self._navigator.navigate)
        else:
            self._navigate_recurse(url, self._navigator.download)

    def _navigate_recurse(self, url, navigation_func) -> None:
        """Recursively navigate an OD

        :param str url: the starting URL of an OD
        :param function navigation_func: function for navigating an OD
        :return:
        """
        if not self._opts.do_download:
            Talker.loading("Begin Scrape process")
        else:
            Talker.loading("Begin Scrape & Download process")
        dirs_to_navigate = {Directory(0, URL(url))}
        while len(dirs_to_navigate) != 0:
            current_dir = dirs_to_navigate.pop()
            Talker.current_directory(self._opts.verbose, current_dir.url, current_dir.depth_level)
            (dirs, files) = navigation_func(current_dir)
            Talker.file_stats(self._opts.verbose, dirs, files)

            dir_set = set(dirs)
            dirs_to_navigate |= dir_set
            self._total_dirs |= dir_set
            file_set = set(files)
            self._total_files |= file_set
        if self._opts.do_download:
            Talker.complete("Finished Scrape & Download process", True)
        else:
            Talker.complete("Finished Scrape process", True)

    def _go_to_page(self, url) -> None:
        """Navigate to another page

        :param str url: page link to navigate to
        :return: None
        """
        Talker.arrow_info("Navigating", url, new_line=True)
        self._driver.get(url)  # visit page

    def _output_to_file(self) -> None:
        """Record all file links to a file"""
        if len(self._total_files) == 0 or self._opts.dont_record:
            return
        Talker.loading("Begin Recording process")
        self._writer.write(self._total_files)
        Talker.loading("Finished Recording process", new_line=True)

    def _close_resources(self) -> None:
        """Close all open resources"""
        self._writer.close()
        self._driver.quit()

    def _shutdown(self) -> None:
        """Shutdown Zyod"""
        if self._opts.verbose:
            Talker.header("Grand Total Summary")
            Talker.arrow_info("Directories", len(self._total_dirs))
            Talker.arrow_info("Files", len(self._total_files))
            Talker.divider()
        Talker.header("All Tasks completed")
        if self._opts.headless:
            self._shutdown_headless()
        else:
            self._shutdown_head()
        self._close_resources()

    def _shutdown_head(self) -> None:
        """Not Headless mode shutdown. When browser is closed, Zyod will stop running. """
        Talker.loading("Please close browser to end process", new_line=True)
        is_browser_running = True
        try:
            while is_browser_running:
                is_browser_running = self._driver.find_element_by_css_selector(
                    "html")
            time.sleep(4.5)
        except (WebDriverException, MaxRetryError):
            Talker.loading("Zyod is shutting down")

    @staticmethod
    def _shutdown_headless() -> None:
        """Headless shutdown"""
        input("Please submit any key to finish......\n")
        Talker.loading("Zyod is shutting down")
