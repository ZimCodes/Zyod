import re


class Parser:
    """Utility class for manipulating text"""
    regex = re.compile(r"\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$")

    @staticmethod
    def join_url(url, rel) -> str:
        if url[-1] == '/' and rel[0] == '/':
            return url[:len(url) - 1] + rel[:]
        elif url[-1] != '/' and rel[0] != '/':
            return url[:] + '/' + rel[:]
        else:
            return url[:] + rel[:]

    @staticmethod
    def join_path(url, rel) -> str:
        if rel.startswith("/"):
            rel = rel[1:]
        return re.sub("{0}$".format(rel), rel, url)

    @staticmethod
    def is_a_file(link) -> bool:
        return Parser.regex.search(link) is not None

    @staticmethod
    def get_elements(driver, css_select):
        return driver.find_elements_by_css_selector(css_select)
