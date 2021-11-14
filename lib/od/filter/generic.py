import re


class Generic:
    """Generic Filter for all links"""

    def __init__(self):
        self._re = re
        self._link = None

    def filter_link(self, link) -> str:
        self._link = self._remove_queries(link)
        return self._link

    def _remove_queries(self, link) -> str:
        return self._re.sub(r"\?[a-zA-Z=\-&]{3,}$", "", link)
