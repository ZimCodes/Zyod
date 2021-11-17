"""The beginning of the Zyod program"""
import time

from lib.main_driver import MainDriver
from lib.od_scraper import OdScraper
from lib.opts import Opts
from lib.writer import Writer
from lib.asset.directory import Directory
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
        _scraper: Scraper
            Scraper object
        _writer: Writer
            Writer object
        """
        self._opts = None
        self._driver = None
        self._total_files = set()
        self._total_dirs = set()
        self._scraper = None
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
        scraper_obj = OdScraper(self._driver, self._opts)
        self._scraper = scraper_obj.scraper
        if self._opts.verbose:
            Talker.complete('Scraper finalized')
        Talker.arrow_header_info('Scrape Method', self._scraper.id.name, True)

    def _scrape_and_download(self, url: str) -> None:
        """Scrape and/or download from OD

        :param str url: starting link to OD
        """
        if not self._scraper.can_scrape:
            Talker.warning(f'Recording/Scraping features are not supported f'
                           f'or {self._scraper.id.name}', True)

        if self._scraper.can_scrape:
            if not self._opts.do_download:
                Talker.loading("Begin Scrape process")
            else:
                Talker.loading("Begin Scrape & Download process")

            dirs_to_navigate = {Directory(0, url)}
            while len(dirs_to_navigate) != 0:
                current_dir = dirs_to_navigate.pop()
                Talker.header("Current Directory")
                if self._opts.verbose:
                    Talker.arrow_info("level", current_dir.depth_level)
                Talker.arrow_info("url", current_dir.link, True)
                (dirs, files) = self._navigate_page(current_dir)
                if self._opts.verbose:
                    Talker.header("Directories")
                    Talker.list_dir_info(dirs, 'Dir')
                    Talker.arrow_info("Total", len(dirs), True)
                    Talker.header("Files")
                    Talker.list_info(files, 'File')
                    Talker.arrow_info("Total", len(files))
                    Talker.divider()

                dir_set = set(dirs)
                file_set = set(files)
                dirs_to_navigate |= dir_set
                self._total_dirs |= dir_set
                self._total_files |= file_set
            if self._opts.do_download:
                Talker.complete("Finished Scrape & Download process", True)
            else:
                Talker.complete("Finished Scrape process", True)
        elif self._opts.do_download:
            Talker.loading("Begin Download process")
            self._scraper.download(self._opts)
            Talker.complete("Finished Download process", True)

    def _navigate_page(self, directory) -> tuple[list, list]:
        """
        Navigate to a page and scrape it
        :param Directory directory: Current directory to scrape/download through
        :return: tuple containing a list of files & directories in the current directory
        """
        wait = self._opts.get_wait()
        return self._scraper.navigate(self._opts, directory, bool(wait))

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
