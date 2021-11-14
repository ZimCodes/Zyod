from lib.main_driver import MainDriver
from lib.od_scraper import OdScraper
from lib.opts import Opts
from lib.writer import Writer
from lib.asset.directory import Directory


class Program:
    """Zyod program manager"""

    def __init__(self):
        self._opts = None
        self._driver = None
        self._files = []
        self._dirs = []
        self._scraper = None
        self._scraper_obj = None
        self._writer = None

    def start(self):
        self._init_items()
        for url in self._opts.urls:
            self._go_to_page(url)
            self._init_navigation()
            self._traverse(url)
            self._output_to_file()
            self._reset_files()
        self._writer.close()
        print(f"Finish Scraping all URLs")

    def _init_items(self):
        self._opts = Opts()  # commandline args
        self._driver = MainDriver.get_driver(self._opts)  # Webdriver

    def _init_navigation(self):
        self._scraper_obj = OdScraper(self._driver, self._opts.depth)
        print(f'Scrape Method: \n----> {self._scraper_obj.id.name} <----\n')
        self._scraper = self._scraper_obj.scraper

    def _traverse(self, url):
        self._dirs.append(Directory(0, url))
        while len(self._dirs) != 0:
            current_dir = self._dirs.pop()
            print(f"Current Directory:")
            if self._opts.verbose:
                print(f"level -> {current_dir.depth_level}")
            print(f"url -> {current_dir.link}\n")
            (dirs, files) = self._scrape_page(current_dir)
            self._dirs = dirs
            self._files = files

    def _scrape_page(self, directory):
        """
        Navigate to a page and scrape it
        :param directory: Directory class
        :return:
        """
        if self._opts.get_wait() is None:
            return self._scraper.navigate(directory)
        else:
            return self._scraper.navigate_wait(self._opts, directory)

    def _go_to_page(self, url):
        self._driver.get(url)  # visit page

    def _output_to_file(self):
        if self._writer is None:
            self._writer = Writer(self._opts.output)
        self._writer.write(self._files)
        if self._opts.verbose:
            print(f"Files:\n"
                  f"{self._files}\n"
                  f"-------------------------------------\n")

    def _reset_files(self):
        self._files = []
        self._dirs = []
