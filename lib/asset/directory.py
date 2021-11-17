class Directory:
    """Directory object holds directory related data"""

    def __init__(self, level, url):
        """Initializes Directory object

        :param int level: depth level of directory
        :param str url: link to directory
        """
        self.depth_level = level
        self.link = url

    def __eq__(self, other):
        return self.link == other.link

    def __ne__(self, other):
        return self.link != other.link

    def __hash__(self):
        return hash((self.link, self.depth_level))
