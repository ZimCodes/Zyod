import re


class Parser:
    """Utility class for manipulating text"""
    regex = re.compile(r"\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$")

    @staticmethod
    def join_url(url, rel) -> str:
        """Appends relative path to URL

        :param str url: URL to join together
        :param str rel: relative path to join together
        :return: newly join URL with newly appended path
        """
        if url[-1] == '/' and rel[0] == '/':
            return url[:len(url) - 1] + rel[:]
        elif url[-1] != '/' and rel[0] != '/':
            return url[:] + '/' + rel[:]
        else:
            return url[:] + rel[:]

    @staticmethod
    def join_path(url, rel) -> str:
        """Replace entire path of URL with new relative path

        :param str url: URL to join together
        :param str rel: relative path to join together
        :return: URL with a new relative path
        """
        if rel.startswith("/"):
            rel = rel[1:]
        return re.sub("{0}$".format(rel), rel, url)

    @staticmethod
    def is_a_file(link) -> bool:
        """Determines if link points to a file

        :param str link: a URL
        :return: True if a file, False otherwise
        """
        return Parser.regex.search(link) is not None
