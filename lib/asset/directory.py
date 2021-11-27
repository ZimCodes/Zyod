from .url import URL


class Directory:
    """Directory object holds directory related data"""

    def __init__(self, level, url):
        """Initializes Directory object

        :param int level: depth level of directory
        :param URL url: URL object of directory location
        """
        self.depth_level = level
        self._url = url

    @property
    def url(self) -> str:
        return self._url.geturl()

    def pop_path(self) -> None:
        self._url.pop_path()

    def total_paths(self) -> int:
        total = self._url.path.count("/")
        return total

    def __eq__(self, other):
        return self.url == other.url

    def __ne__(self, other):
        return self.url != other.url

    def __hash__(self):
        return hash((self.url, self.depth_level))
