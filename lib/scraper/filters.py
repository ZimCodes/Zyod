import re


class Generic:
    """Generic Scraper Filter for all texts"""

    def __init__(self):
        """Initializes Generic filter object"""
        self._re = re
        self._text = None

    def apply(self, text) -> str:
        """Applies generic filters to text

        :param str text: link to apply filter on
        :return: filtered text
        """
        self._text = text
        self._apply_filters()
        return self._text

    def _apply_filters(self) -> None:
        """Apply all filters to text"""
        self._remove_queries()
        self._remove_double_quotes()

    def _remove_queries(self) -> None:
        """Removes query component from text"""
        self._text = self._re.sub(r"\?[a-zA-Z=\-&]{3,}$", "", self._text)

    def _remove_double_quotes(self):
        """Remove quotes from text"""
        self._text = self._re.sub(r"\"", "", self._text)
