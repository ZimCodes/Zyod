from .util.parser import Parser
from ...asset.directory import Directory


class BaseScraper:
    """Base scraper abstract class"""

    def __init__(self, driver, max_depth, filter_obj=None):
        self.driver = driver
        self.dirs = []
        self.files = []
        self._max_depth = max_depth
        self._filter = filter_obj()

    def navigate(self, directory):
        self._go_to_url(directory.link)

    def _go_to_url(self, url=None):
        if url is not None:
            self.driver.get(url)

    def _get_files_append(self, elements, attr, level) -> tuple:
        """
        Appends the element link onto the current URL
        :param elements: list of elements
        :param attr: The attribute to extract link from
        :param level: the current depth folder the elements came from
        :return: None
        """
        for el in elements:
            link = el.get_attribute(attr)
            if self._filter is not None:
                link = self._filter.filter_link(link)
            if Parser.is_a_file(link):
                self.files.append(Parser.join_url(self.driver.current_url, link))
            else:
                new_level = level + 1
                if new_level < self._max_depth:
                    link = Parser.join_url(self.driver.current_url, link)
                    if not link.endswith('/'):
                        link += '/'

                    new_dir = Directory(new_level, link)
                    self.dirs.append(new_dir)

        return self.dirs, self.files

    def _get_files_link(self, elements, attr, level) -> tuple:
        """
        Uses the link found in each element
        :param elements: list of dom elements
        :param attr: attribute to extract link from
        :param level: the current depth folder the elements came from
        :return: None
        """
        for el in elements:
            link = el.get_attribute(attr)
            if self._filter is not None:
                link = self._filter.filter_link(link)
            if Parser.is_a_file(link):
                self.files.append(link)
            else:
                new_level = level + 1
                if new_level < self._max_depth:
                    if not link.endswith('/'):
                        link += '/'
                    new_dir = Directory(new_level, link)
                    self.dirs.append(new_dir)
        return self.dirs, self.files

    def _get_files_replace_path(self, elements, attr, level) -> tuple:
        """
        Replaces the path of the URL with the pat found in element
        :param elements:list of dom elements
        :param attr: attribute to extract links from
        :param level: the current depth folder the elements came from
        :return: None
        """
        for el in elements:
            link = el.get_attribute(attr)
            if self._filter is not None:
                link = self._filter.filter_link(link)
            if Parser.is_a_file(link):
                self.files.append(Parser.join_path(self.driver.current_url, link))
            else:
                new_level = level + 1
                if new_level < self._max_depth:
                    link = Parser.join_path(self.driver.current_url, link)
                    if not link.endswith('/'):
                        link = link + '/'
                    new_dir = Directory(new_level, link)
                    self.dirs.append(new_dir)
        return self.dirs, self.files
