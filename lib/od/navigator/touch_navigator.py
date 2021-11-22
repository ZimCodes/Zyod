from .base_navigator import BaseNavigator
from ...asset.directory import Directory
from ...asset.url import URL
from ...driver.support.driver_support import DriverSupport
import time


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

    def _get_nav_info_elements(self, nav_info) -> list:
        return DriverSupport.get_elements_all(self._driver, self._opts, nav_info.css_name,
                                              nav_info.wait_err_message)

    def _go_to_directory(self, directory) -> None:
        self._move_up_to_destination(directory)
        self._move_down_to_destination(directory)

    def _move_up_to_destination(self, directory) -> None:
        """Move up the directory tree before heading down to the destination directory

        :param Directory directory: Destination directory
        :return:
        """
        while self._cur_dir.url not in directory.url and self._cur_dir.url != \
                self._driver.current_url:
            time.sleep(3)
            is_home = self._go_back_button()
            if is_home:
                self._cur_dir = Directory(0, self._driver.current_url)
                break
            self._cur_dir.pop_path()
            self._cur_dir.depth_level -= 1

    def _move_down_to_destination(self, directory) -> None:
        """Move down the directory tree towards destination directory

        :param Directory directory: Destination Directory
        :return:
        """
        while self._cur_dir.depth_level != directory.depth_level:
            time.sleep(3)
            elements = DriverSupport.get_elements_all(self._driver, self._opts,
                                                      self._scraper.nav_info.css_select,
                                                      self._scraper.nav_info.wait_err_message)
            for el in elements:
                name_element = DriverSupport.get_element(el, self._scraper.nav_info.css_name)
                name = name_element.text.strip()
                name = self._scraper.apply_filter(name)
                if name in directory.url:
                    el.click()
                    self._cur_dir.depth_level += 1
                    break
        self._cur_dir = directory

    def _go_back_button(self) -> bool:
        """Interact with the OD to go back a page"""
        pass
