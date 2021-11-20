import re


class Generic:
    """Generic Scraper Filter for all links"""

    def __init__(self):
        """Initializes Generic filter object"""
        self._re = re
        self._link = None

    def apply(self, link) -> str:
        """Applies generic filters to link

        :param str link: link to apply filter on
        :return: filtered link
        """
        self._apply_filters(link)
        return self._link

    def _apply_filters(self, link) -> None:
        """Apply all filters to link

        :param str link: link to apply filters to
        :return:
        """
        self._remove_queries(link)

    def _remove_queries(self, link) -> None:
        """Removes query component from link

        :param str link: link to apply filter on
        :return:
        """
        self._link = self._re.sub(r"\?[a-zA-Z=\-&]{3,}$", "", link)
