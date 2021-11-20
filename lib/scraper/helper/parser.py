import re
from urllib.parse import urlsplit


class Parser:
    """Utility class for manipulating text"""
    regex = re.compile(r"\.(?:[a-zA-Z0-9]{3,7}|[a-zA-Z][a-zA-Z0-9]|[0-9][a-zA-Z])$")

    @staticmethod
    def joiner(url, rel) -> str:
        """Joins current URL & new link together

        :param str url: current URL
        :param str rel: link retrieved from an element
        :return: new URL
        """
        parsed_url = urlsplit(url)
        parsed_rel = urlsplit(rel)
        result = {"scheme": parsed_url.scheme, "netloc": parsed_url.netloc, "path": parsed_url.path,
                  "query": parsed_url.query, "fragment": parsed_url.fragment}
        if parsed_url == parsed_rel:
            return parsed_url.geturl()

        if parsed_url.path != parsed_rel.path:
            if parsed_url.path in parsed_rel.path:
                result["path"] = parsed_rel.path
            else:
                result["path"] += parsed_rel.path

        if parsed_url.query != parsed_rel.query:
            if parsed_url.query in parsed_rel.query:
                result["query"] = parsed_rel.query
            else:
                result["query"] += "&" + parsed_rel.query

        return Parser._get_new_url(result)

    @staticmethod
    def _get_new_url(result) -> str:
        """Retrieve the newly joined URL

        :param dict result: dictionary containing changes of the new URL
        :return: newly joined URL
        """
        new_url = f"{result['scheme']}://{result['netloc']}"
        if result['path']:
            new_url += result['path']
        if result['query']:
            new_url += '?' + result['query']
        if result['fragment']:
            new_url += "#" + result['fragment']
        return new_url

    @staticmethod
    def is_a_file(link) -> bool:
        """Determines if link points to a file

        :param str link: a URL
        :return: True if a file, False otherwise
        """
        return Parser.regex.search(link) is not None
