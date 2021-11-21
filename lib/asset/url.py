from urllib.parse import urlsplit
import re


class URL:
    """Mutable URL object holding URL data"""
    regex = re.compile(r"\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$")

    def __init__(self, url):
        """Initializes Url object

        :param str url: url to parse
        """
        url = urlsplit(url)
        self.scheme = url.scheme
        self.netloc = url.netloc
        self.path = URL._add_start_slash(url.path)
        self.query = url.query
        self.fragment = url.fragment

    def geturl(self) -> str:
        url = ''
        if self.scheme:
            url += self.scheme + "://"
        if self.netloc:
            url += self.netloc
        if self.path:
            url += self.path
        if self.query:
            url += '?' + self.query
        if self.fragment:
            url += '#' + self.fragment
        return url

    @staticmethod
    def joiner(url, rel):
        """Joins current URL & new link together

        :param str url: current URL
        :param str rel: link retrieved from an element
        :return: new URL
        """
        parsed_url = URL(url)
        parsed_rel = URL(rel)
        if parsed_url == parsed_rel:
            return parsed_url.geturl()

        if parsed_url.path != parsed_rel.path:
            if parsed_url.path in parsed_rel.path:
                parsed_url.path = parsed_rel.path
            else:
                parsed_url.path = URL._slash_join(parsed_url.path, parsed_rel.path)

        if parsed_url.query != parsed_rel.query:
            if parsed_url.query in parsed_rel.query:
                parsed_url.query = parsed_rel.query
            else:
                parsed_url.query += "&" + parsed_rel.query

        return parsed_url

    def pop_path(self) -> None:
        paths = self.path.split('/')
        paths.pop()
        self.path = '/'.join(paths)

    def add_path(self, path):
        self.path = URL._add_last_slash(self.path) + path

    def dir_transform(self) -> None:
        """Transform current URL into a directory URL"""
        if self.query:
            self.query = URL._add_start_slash(self.query)
        elif self.path:
            self.path = URL._add_last_slash(self.path)

    @staticmethod
    def is_a_file(link) -> bool:
        """Determines if link points to a file

        :param str link: a URL
        :return: True if a file, False otherwise
        """
        return URL.regex.search(link) is not None

    @staticmethod
    def _add_start_slash(path) -> str:
        if not path.startswith('/'):
            return "/" + path
        return path

    @staticmethod
    def _slash_join(url, rel) -> str:
        if url.endswith('/') and rel.startswith('/'):
            return url + rel[1:]
        elif not url.endswith('/') and not rel.startswith('/'):
            return url + '/' + rel
        return url + rel

    @staticmethod
    def _add_last_slash(path) -> str:
        if not path.endswith('/'):
            return path + "/"
        return path

    @staticmethod
    def is_url(obj) -> bool:
        """Check if object is an URL object

        :param obj: Object to check
        :return: True if an URL object, False otherwise
        """
        return isinstance(obj, URL)

    def __eq__(self, other):
        if not URL.is_url(other):
            return False
        same_scheme = self.scheme == other.scheme
        same_netloc = self.netloc == other.netloc
        same_path = self.path == other.path
        same_query = self.query == other.query
        same_frag = self.fragment == other.fragment
        return same_scheme and same_netloc and same_path and same_query and same_frag

    def __ne__(self, other):
        if not URL.is_url(other):
            return False
        same_scheme = self.scheme != other.scheme
        same_netloc = self.netloc != other.netloc
        same_path = self.path != other.path
        same_query = self.query != other.query
        same_frag = self.fragment != other.fragment
        return same_scheme and same_netloc and same_path and same_query and same_frag

    def __str__(self):
        return self.geturl()

    def __hash__(self):
        return hash((self.scheme, self.query, self.path, self.netloc, self.fragment))
