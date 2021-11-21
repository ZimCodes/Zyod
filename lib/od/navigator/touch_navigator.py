from .base_navigator import BaseNavigator
from ...asset.directory import Directory
from ...asset.url import URL


class TouchNavigator(BaseNavigator):
    """Navigator object that navigates purely through interaction"""

    def __init__(self, name, driver, opts, no_full_links=False):
        """Initializes Download Navigator object

        :param name: name of Navigator
        :param WebDriver driver: Selenium Webdriver
        :param Opts opts: Opts class
        :param bool no_full_links: Whether or not file links can be recorded from this OD
        """
        super().__init__(name, driver, opts, no_full_links)
        self._cur_dir = Directory(0, URL(self._driver.current_url))

    def download(self, directory=None) -> tuple[list, list]:
        self._setup_navigate(directory)
        return self._scraper.get_results()

    def _go_to_directory(self, directory) -> None:
        self._move_up_to_destination(directory)
        self._move_down_to_destination(directory)

    def _move_up_to_destination(self, directory) -> None:
        """Move up the directory tree before heading down to the destination directory

        :param Directory directory: Destination directory
        :return:
        """
        while self._cur_dir.url not in directory.url:
            self._go_back()
            self._cur_dir.pop_path()
            self._cur_dir.depth_level -= 1

    def _move_down_to_destination(self, directory) -> None:
        """Move down the directory tree towards destination directory

        :param Directory directory: Destination Directory
        :return:
        """
        pass

    def _go_back(self):
        """Interact with the OD to go back a page"""
        pass
